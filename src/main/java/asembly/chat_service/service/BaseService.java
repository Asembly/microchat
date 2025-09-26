package asembly.chat_service.service;

import asembly.chat_service.entity.Chat;
import asembly.chat_service.kafka.ProducerChat;
import asembly.chat_service.repository.ChatRepository;
import asembly.dto.chat.ChatCreateRequest;
import asembly.event.chat.ChatEventType;
import asembly.util.GeneratorId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BaseService {

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private ProducerChat producerService;

    public ResponseEntity<List<Chat>> findAll()
    {
        var chats = chatRepository.findAll();
        return ResponseEntity.ok(chats);
    }

    public ResponseEntity<Chat> findById(String id)
    {
        var chat = chatRepository.findById(id).orElseThrow();
        return ResponseEntity.ok(chat);
    }

    public ResponseEntity<String> deleteAll()
    {
        chatRepository.deleteAll();
        return ResponseEntity.ok("Chats deleted");
    }

    public ResponseEntity<Chat> delete(String id)
    {
        var chat = chatRepository.findById(id).orElseThrow();

        producerService.sendEvent(
                ChatEventType.CHAT_DELETED,
                id,
                chat.getUsers_id());

        chatRepository.delete(chat);
        return ResponseEntity.ok(chat);
    }

    public ResponseEntity<Chat> create(ChatCreateRequest dto)
    {
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

}
