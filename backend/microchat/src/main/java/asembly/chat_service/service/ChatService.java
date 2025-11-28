package asembly.chat_service.service;

import asembly.chat_service.client.UserClient;
import asembly.chat_service.entity.Chat;
import asembly.chat_service.kafka.ProducerChat;
import asembly.chat_service.mapper.ChatMapper;
import asembly.chat_service.repository.ChatRepository;
import asembly.dto.chat.ChatCreateRequest;
import asembly.dto.chat.ChatUsersRequest;
import asembly.event.chat.ChatEventType;
import asembly.exception.ChatNotFoundException;
import asembly.exception.UserAlreadyExistException;
import asembly.exception.UserNotFoundException;
import asembly.util.GeneratorId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@Slf4j
@Service
public class ChatService {

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private ProducerChat producerService;

    @Autowired
    private UserClient userClient;

    @Autowired
    private ChatMapper chatMapper;

    public ResponseEntity<Chat> update(String id, ChatCreateRequest dto)
    {
        var chat = chatRepository.findById(id)
                .orElseThrow(ChatNotFoundException::new);

        if(!dto.title().isEmpty())
            chat.setTitle(dto.title());

        return ResponseEntity.ok(chatRepository.save(chat));
    }

    public ResponseEntity<?> kickUser(String chat_id, ChatUsersRequest dto)
    {
        var chat = chatRepository.findById(chat_id)
                .orElseThrow(ChatNotFoundException::new);

        checkUsers(dto.users_id());

        var isRemoved = chat.getUsers_id().removeAll(dto.users_id());

        if(!isRemoved)
            throw new UserNotFoundException();

        producerService.sendEvent(
                ChatEventType.CHAT_KICK_USER,
                chat.getId(),
                dto.users_id()
        );

        return ResponseEntity.ok(chatRepository.save(chat));
    }

    public ResponseEntity<?> addUser(String chat_id, ChatUsersRequest dto)
    {
        var chat = chatRepository.findById(chat_id)
                .orElseThrow(ChatNotFoundException::new);

        checkUsers(dto.users_id());

        if(!new HashSet<>(chat.getUsers_id()).containsAll(dto.users_id()))
        {
            chat.getUsers_id().addAll(dto.users_id());
        }else{
            throw new UserAlreadyExistException();
        }

        producerService.sendEvent(
                ChatEventType.CHAT_ADD_USER,
                chat.getId(),
                dto.users_id()
        );

        return ResponseEntity.ok(chatRepository.save(chat));
    }

    public ResponseEntity<List<Chat>> findAll()
    {
        var chats = chatRepository.findAll();
        return ResponseEntity.ok(chats);
    }

    public ResponseEntity<?> findById(String id)
    {
        var chat = chatRepository.findById(id).orElseThrow(ChatNotFoundException::new);
        log.info(chat.toString());
        return ResponseEntity.status(HttpStatus.OK).body(chat);
    }

    public ResponseEntity<String> deleteAll()
    {
        chatRepository.deleteAll();
        return ResponseEntity.ok("Chats deleted");
    }

    public ResponseEntity<Chat> delete(String id)
    {
        var chat = chatRepository.findById(id).orElseThrow(
                ChatNotFoundException::new
        );

        producerService.sendEvent(
                ChatEventType.CHAT_DELETED,
                id,
                chat.getUsers_id());

        chatRepository.delete(chat);
        return ResponseEntity.ok(chat);
    }

    public ResponseEntity<Chat> create(ChatCreateRequest dto)
    {

        checkUsers(dto.users_id());

        var chat = new Chat(
                GeneratorId.generateShortUuid(),
                dto.title(),
                dto.users_id(),
                List.of(),
                LocalDate.now()
        );

        producerService.sendEvent(
                ChatEventType.CHAT_CREATED,
                chat.getId(),
                chat.getUsers_id()
        );

        return ResponseEntity.ok(chatRepository.save(chat));
    }

    public ResponseEntity<List<Chat>> findByUserId(String user_id)
    {
       var chats = chatRepository.findChatsByUserId(user_id).orElseThrow(
               ChatNotFoundException::new
       );
       return ResponseEntity.ok(chats);
    }

    public void checkUsers(List<String> users_id)
    {
        if(users_id.isEmpty())
            throw new UserNotFoundException();

        for(var id : users_id)
        {
            var user = userClient.getUserById(id);
            if(!user.hasBody())
                throw new UserNotFoundException();
        }
    }
}
