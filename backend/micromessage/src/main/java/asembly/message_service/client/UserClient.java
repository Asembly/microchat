package asembly.message_service.client;

import asembly.dto.user.UserIdsRequest;
import asembly.dto.user.UserResponse;
import asembly.message_service.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "user-client", url = "${feign.service.user-service}", configuration = FeignConfig.class)
public interface UserClient {

    @GetMapping("/users/{id}")
    ResponseEntity<UserResponse> getUserById(@PathVariable String id);

    @PostMapping("/users/byIds")
    ResponseEntity<List<UserResponse>> getAllByIds(@RequestBody UserIdsRequest dto);

}