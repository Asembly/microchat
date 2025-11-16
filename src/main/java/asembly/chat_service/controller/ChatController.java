package asembly.chat_service.controller;

import asembly.chat_service.entity.Chat;
import asembly.chat_service.service.ChatService;
import asembly.dto.chat.ChatCreateRequest;
import asembly.dto.chat.ChatUsersRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chats")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @GetMapping
    public ResponseEntity<List<Chat>> findAll()
    {
       return chatService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable String id)
    {
        return chatService.findById(id);
    }

    @GetMapping("/user/{user_id}")
    public ResponseEntity<List<Chat>> findByUserId(@PathVariable String user_id){
        return chatService.findByUserId(user_id);
    }

    @PostMapping
    public ResponseEntity<Chat> create(@RequestBody ChatCreateRequest dto)
    {
        return chatService.create(dto);
    }

    @DeleteMapping("/")
    public ResponseEntity<String> deleteAll()
    {
        return chatService.deleteAll();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Chat> delete(@PathVariable String id)
    {
        return chatService.delete(id);
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