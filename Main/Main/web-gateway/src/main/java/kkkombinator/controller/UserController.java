package kkkombinator.controller;

import kkkombinator.KafkaTopics;
import kkkombinator.Requests.UserRequest;
import kkkombinator.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.io.Reader;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final AuthenticationManager authenticationManager;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final String userRequestTopic = KafkaTopics.USERS_TOPIC;
    private final ResponseAwaitService responseAwaitService;



    @GetMapping("/")
    public CompletableFuture<List<UserDTO>> findAll(Reader reader) {
        String correlationId = UUID.randomUUID().toString();

        UserRequest request = new UserRequest("FindAll",  null);

        CompletableFuture<List<UserDTO>> responseFuture =
                responseAwaitService.waitForResponse(correlationId);

        kafkaTemplate.send(
                userRequestTopic,
                correlationId,
                request
        );
        return responseFuture;
    }

    @PostMapping("/login")
    public UserDTO login(@RequestBody UserDTO dto) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        dto.getName(),
                        dto.getPassword()
                )
        );
        if (!auth.isAuthenticated()) {
            throw new UsernameNotFoundException("Invalid username or password");
        }
        SecurityContextHolder.getContext().setAuthentication(auth);
        return dto;
    }

    @PostMapping("/reg")
    @ResponseStatus(HttpStatus.CREATED)
    public CompletableFuture<UserDTO> create(@RequestBody UserDTO dto) {
        String correlationId = UUID.randomUUID().toString();
        UserRequest request = new UserRequest("Create.User", null);

        CompletableFuture<UserDTO> future =
                responseAwaitService.waitForResponse(correlationId);

        kafkaTemplate.send(userRequestTopic, correlationId, request);


        return future;


    }

    @DeleteMapping("/{id}")
    public CompletableFuture<UserDTO> delete(@PathVariable Long id) {
        String correlationId = UUID.randomUUID().toString();
        UserDTO dto = new UserDTO();
        dto.setId(id);
        var request = new UserRequest("Delete.User", dto);
        CompletableFuture<UserDTO> response = responseAwaitService.waitForResponse(correlationId);
        kafkaTemplate.send(userRequestTopic,request);

        return response;
    }

    @PutMapping("/{id}")
    public CompletableFuture<UserDTO> updateUser(@RequestBody UserDTO dto) {
        String correlationId = UUID.randomUUID().toString();
        var request = new UserRequest("Update.User", dto);
        CompletableFuture<UserDTO> response = responseAwaitService.waitForResponse(correlationId);

        kafkaTemplate.send(userRequestTopic,request);

        return response;
    }
}