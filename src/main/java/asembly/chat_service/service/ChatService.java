package asembly.chat_service.service;

import asembly.chat_service.entity.Chat;
import asembly.chat_service.kafka.ProducerChat;
import asembly.chat_service.repository.ChatRepository;
import asembly.dto.chat.ChatCreateRequest;
import asembly.dto.chat.ChatUsersRequest;
import asembly.event.chat.ChatEventType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Slf4j
@Service
public class ChatService {

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private ProducerChat producerService;

    public ResponseEntity<Chat> update(String id, ChatCreateRequest dto)
    {
        var chat = chatRepository.findById(id).orElseThrow();

        if(!dto.title().isEmpty())
            chat.setTitle(dto.title());

        return ResponseEntity.ok(chatRepository.save(chat));
    }

    public ResponseEntity<?> kickUser(String chat_id, ChatUsersRequest dto)
    {
        var chat = chatRepository.findById(chat_id).orElseThrow();

        if(dto.users_id().isEmpty())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Users is empty");

        var isRemoved = chat.getUsers_id().removeAll(dto.users_id());

        if(!isRemoved)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Users not found in chat");

        producerService.sendEvent(
                ChatEventType.CHAT_KICK_USER,
                chat.getId(),
                dto.users_id()
        );

        return ResponseEntity.ok(chatRepository.save(chat));
    }

    public ResponseEntity<?> addUser(String chat_id, ChatUsersRequest dto)
    {
        var chat = chatRepository.findById(chat_id).orElseThrow();

        if(dto.users_id().isEmpty())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Users is empty");

        if(!new HashSet<>(chat.getUsers_id()).containsAll(dto.users_id()))
        {
            chat.getUsers_id().addAll(dto.users_id());
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Users already exits in chat");
        }

        producerService.sendEvent(
                ChatEventType.CHAT_ADD_USER,
                chat.getId(),
                dto.users_id()
        );

        return ResponseEntity.ok(chatRepository.save(chat));
    }
}
