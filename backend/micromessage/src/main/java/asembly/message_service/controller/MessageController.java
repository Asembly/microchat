package asembly.message_service.controller;

import asembly.dto.message.MessageLazyResponse;
import asembly.message_service.entity.Message;
import asembly.message_service.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/messages")
public class MessageController {

    @Autowired
    private MessageService service;

    @GetMapping
    public ResponseEntity<List<Message>> findAll()
    {
       return service.findAll();
    }

//    @GetMapping("/chat/{chat_id}")
//    public ResponseEntity<List<MessageResponse>> findByChatId(@PathVariable String chat_id)
//    {
//        return service.findByChatId(chat_id);
//    }

    @GetMapping("/chat/{chat_id}")
    public ResponseEntity<MessageLazyResponse> findOlderMessagesByCreatedAt(
            @PathVariable String chat_id,
            @RequestParam(value = "beforeDate", required = false) LocalDateTime cursorDate
    )
    {
        if(cursorDate == null)
            return service.findNewByChatId(chat_id);
        else
            return service.findOldByChatId(chat_id, cursorDate);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Message> findById(@PathVariable String id)
    {
        return service.findById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Message> delete(@PathVariable String id)
    {
        return service.delete(id);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteAll()
    {
        return service.deleteAll();
    }

}