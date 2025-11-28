package asembly.chat_service.client;

import asembly.chat_service.config.FeignConfig;
import asembly.dto.user.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-client", url = "${feign.service.user-service}", configuration = FeignConfig.class)
public interface UserClient {

    @GetMapping("/users/{id}")
    ResponseEntity<UserResponse> getUserById(@PathVariable String id);
}