package kkkombinator.Controllers;


import Models.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final AuthenticationManager authenticationManager;

    @Autowired
    private final KafkaTemplate<String, Object> producer;

    private final ResponseAwaitService responseAwaitService;

    @Value("${spring.kafka.userCreateTopic}")
    private final String userCreateTopic;

    @Value("${spring.kafka.userFindAllTopic}")
    private final String userFindAllTopic;

    @Value("${spring.kafka.userUpdateTopic}")
    private final String userUpdateTopic;

    @Value("${spring.kafka.userDeleteTopic}")
    private final String userDeleteTopic;



    @GetMapping("/")
    public CompletableFuture<List<UserDTO>> findAll() {
        String correlationId = UUID.randomUUID().toString();

        CompletableFuture<List<UserDTO>> responseFuture =
                responseAwaitService.waitForResponse(correlationId);

        producer.send(
                userFindAllTopic,
                correlationId,
                null
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

        CompletableFuture<UserDTO> responseFuture =
                responseAwaitService.waitForResponse(correlationId);

        producer.send(
                userCreateTopic,
                correlationId,
                dto
        );

        return responseFuture;


    }

    @DeleteMapping("/{id}")
    public CompletableFuture<UserDTO> delete(@PathVariable Long id) {
        String correlationId = UUID.randomUUID().toString();
        UserDTO dto = new UserDTO();
        dto.setId(id);

        CompletableFuture<UserDTO> responseFuture =
                responseAwaitService.waitForResponse(correlationId);

        producer.send(
                userDeleteTopic,
                correlationId,
                dto
        );

        return responseFuture;
    }

    @PutMapping("/{id}")
    public CompletableFuture<UserDTO> updateUser(@RequestBody UserDTO dto) {
        String correlationId = UUID.randomUUID().toString();

        CompletableFuture<UserDTO> responseFuture =
                responseAwaitService.waitForResponse(correlationId);

        producer.send(
                userUpdateTopic,
                correlationId,
                dto
        );

        return responseFuture;
    }
}