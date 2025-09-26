package asembly.chat_service.controller;

import asembly.chat_service.entity.Chat;
import asembly.chat_service.service.BaseService;
import asembly.chat_service.service.ChatService;
import asembly.dto.chat.ChatCreateRequest;
import asembly.dto.chat.ChatUsersRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @Autowired
    private BaseService service;

    @GetMapping
    public ResponseEntity<List<Chat>> findAll()
    {
       return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Chat> findById(@PathVariable String id)
    {
        return service.findById(id);
    }

    @PostMapping
    public ResponseEntity<Chat> create(@RequestBody ChatCreateRequest dto)
    {
        return service.create(dto);
    }

    @DeleteMapping("/")
    public ResponseEntity<String> deleteAll()
    {
        return service.deleteAll();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Chat> delete(@PathVariable String id)
    {
        return service.delete(id);
    }

    @PostMapping("/{id}/add")
    public ResponseEntity<?> addUser(@PathVariable String id, @RequestBody ChatUsersRequest dto)
    {
        return chatService.addUser(id, dto);
    }

    @PostMapping("/{id}/kick")
    public ResponseEntity<?> kickUser(@PathVariable String id, @RequestBody ChatUsersRequest dto)
    {
        return chatService.kickUser(id, dto);
    }
}