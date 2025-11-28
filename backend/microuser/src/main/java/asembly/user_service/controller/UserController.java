package asembly.user_service.controller;

import asembly.dto.user.UserCreateRequest;
import asembly.dto.user.UserIdsRequest;
import asembly.dto.user.UserResponse;
import asembly.dto.user.UserUpdateRequest;
import asembly.user_service.entity.User;
import asembly.user_service.service.UserService;
import org.apache.http.auth.InvalidCredentialsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<UserResponse> create(@RequestBody UserCreateRequest dto) throws InvalidCredentialsException {
        return userService.create(dto);
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<UserResponse> findByUsername(@PathVariable String username)
    {
        return userService.findByUsername(username);
    }

    @GetMapping
    public ResponseEntity<List<User>> findAll()
    {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable String id)
    {
        return userService.findById(id);
    }


    @GetMapping("/chat/{chat_id}")
    public ResponseEntity<?> findByChatId(@PathVariable String chat_id)
    {
        return userService.findByChatId(chat_id);
    }

    @PostMapping("/byIds")
    public ResponseEntity<List<UserResponse>> findAllByIds(@RequestBody UserIdsRequest dto)
    {
       return userService.findAllByIds(dto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable String id,@RequestBody UserUpdateRequest dto)
    {
       return userService.update(id, dto);
    }

    @DeleteMapping("/")
    public ResponseEntity<String> deleteAll()
    {
        return userService.deleteAll();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> delete(@PathVariable String id)
    {
        return userService.delete(id);
    }

}
