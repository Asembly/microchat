package asembly.message_service.client;

import asembly.dto.chat.ChatResponse;
import asembly.message_service.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "chat-client", url ="${feign.service.chat-service}", configuration = FeignConfig.class)
public interface ChatClient {

    @GetMapping("/chats/{id}")
    ResponseEntity<ChatResponse> getChatById(@PathVariable String id);

}
