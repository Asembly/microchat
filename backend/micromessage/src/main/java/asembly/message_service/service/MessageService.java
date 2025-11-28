package asembly.message_service.service;

import asembly.dto.message.MessageCreateRequest;
import asembly.dto.message.MessageLazyResponse;
import asembly.dto.message.MessageResponse;
import asembly.dto.user.UserIdsRequest;
import asembly.event.message.MessageEventType;
import asembly.exception.ChatNotFoundException;
import asembly.exception.MessageNotFoundException;
import asembly.exception.UserNotFoundException;
import asembly.message_service.client.ChatClient;
import asembly.message_service.client.UserClient;
import asembly.message_service.entity.Message;
import asembly.message_service.kafka.ProducerMessage;
import asembly.message_service.repository.MessageRepository;
import asembly.util.GeneratorId;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class MessageService {
    @Autowired
    private ProducerMessage producerService;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private ChatClient chatClient;

    @Autowired
    private UserClient userClient;

    @Transactional
    public MessageResponse create(MessageCreateRequest dto){
        chatClient.getChatById(dto.chat_id());
        var user = userClient.getUserById(dto.author_id()).getBody();

        var message = new Message(
                GeneratorId.generateShortUuid(),
                dto.text(),
                dto.author_id(),
                dto.chat_id(),
                LocalDateTime.now());

        producerService.sendEvent(
                MessageEventType.MESSAGE_CREATED,
                message.getId(),
                dto.chat_id()
        );
        messageRepository.save(message);

        assert user != null;
        return new MessageResponse(
                message.getId(),
                message.getText(),
                user.username(),
                message.getCreated_at()
        );

    }

    @Transactional
    public ResponseEntity<MessageLazyResponse> findOldByChatId(String chat_id, LocalDateTime cursorDate)
    {
        var limit = 20;

        var messages = messageRepository.findOldByChatId(chat_id, cursorDate, 20)
                .orElseThrow(ChatNotFoundException::new);

        var hasMore = (messageRepository.findOldByChatId(chat_id, cursorDate, limit + 1)
                .orElseThrow(ChatNotFoundException::new).size() > limit);

        log.info("{}", messageRepository.findOldByChatId(chat_id, cursorDate, limit + 1)
                .orElseThrow(ChatNotFoundException::new).size());

       log.info("HAS MORE{}",hasMore);

       return ResponseEntity.ok(
                new MessageLazyResponse(
                        convertToMessageResponse(messages),
                        hasMore
                ));
    }

    @Transactional
    public ResponseEntity<MessageLazyResponse> findNewByChatId(String chat_id)
    {
        var limit = 20;

        var messages = messageRepository.findNewByChatId(chat_id, limit)
                .orElseThrow(ChatNotFoundException::new);

        var hasMore = (messageRepository.findNewByChatId(chat_id, limit + 1)
                .orElseThrow(ChatNotFoundException::new).size() > limit);

        log.info("{}", messageRepository.findNewByChatId(chat_id, limit + 1)
                .orElseThrow(ChatNotFoundException::new).size());

        log.info("HAS MORE{}",hasMore);

        return ResponseEntity.ok(
                new MessageLazyResponse(
                        convertToMessageResponse(messages),
                        hasMore
                ));
    }

    @Transactional
    public ResponseEntity<List<Message>> findAll()
    {
        var messages = messageRepository.findAll();
        return ResponseEntity.ok(messages);
    }

    @Transactional
    public ResponseEntity<Message> findById(String id)
    {
        var message = messageRepository.findById(id).orElseThrow(
                MessageNotFoundException::new
        );
        return ResponseEntity.ok(message);
    }

    @Transactional
    public ResponseEntity<List<MessageResponse>> findByChatId(String chat_id)
    {
        var messages = messageRepository.findByChatId(chat_id).orElseThrow(
                MessageNotFoundException::new
        );
        return ResponseEntity.ok(convertToMessageResponse(messages));
    }

    @Transactional
    public ResponseEntity<String> deleteAll()
    {
        messageRepository.deleteAll();
        return ResponseEntity.ok("Messages deleted");
    }

    @Transactional
    public ResponseEntity<Message> delete(String id)
    {
        var message = messageRepository.findById(id).orElseThrow();

        producerService.sendEvent(
                MessageEventType.MESSAGE_DELETED,
                message.getAuthor_id(),
                message.getChat_id()
        );

        messageRepository.delete(message);
        return ResponseEntity.ok(message);
    }

    public List<MessageResponse> convertToMessageResponse(List<Message> messages)
    {
        log.info("Messages: \n{}", messages);

        var authorIds = messages.stream()
                .map(Message::getAuthor_id)
                .toList();

        log.info("Author Ids: \n{}", authorIds);

        var users = userClient.getAllByIds(new UserIdsRequest(authorIds)).getBody();

        log.info("Users: \n{}", users);

        assert users != null;
        List<MessageResponse> result = new ArrayList<>();
        for(var user: users)
        {
            String userId = user.id();
            String username = user.username();
            result.addAll(messages.stream()
                    .filter(msg -> msg.getAuthor_id().equals(userId))
                    .map(msg -> new MessageResponse(
                                    msg.getId(),
                                    msg.getText(),
                                    username,
                                    msg.getCreated_at()
                            )
                    ).toList());
        }

        if(result.isEmpty())
            throw new UserNotFoundException();

        return result;
    }
}
