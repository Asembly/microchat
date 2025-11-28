package asembly.message_service.controller;

import asembly.dto.message.MessageCreateRequest;
import asembly.dto.message.MessageResponse;
import asembly.message_service.client.AuthFeignInterceptor;
import asembly.message_service.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
public class WebSocketController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private MessageService service;

    @MessageMapping("/chat/sendMessage/{chatId}")
    @SendTo("/topic/chat/{chatId}")
    public MessageResponse sendMessage(
            @Payload MessageCreateRequest dto,
            @DestinationVariable String chatId,
            @Header("Authorization") String token
    ){
        try{
            if (token != null && token.startsWith("Bearer ")) {
                String tkn = token.substring(7);
                AuthFeignInterceptor.setAuthToken(tkn);
            }
            log.info(chatId);
            log.info(dto.text(), dto.author_id(), dto.chat_id());
            log.info(token);
            return service.create(new MessageCreateRequest(dto.text(), dto.author_id(), dto.chat_id()));
        }
        finally {
            AuthFeignInterceptor.clearAuthToken();
        }
    }
}
