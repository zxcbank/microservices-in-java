package Main.Controllers;


import Main.Models.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/v1/users/")
@RequiredArgsConstructor
public class UserController {

//    private final AuthenticationManager authenticationManager;

    private final KafkaTemplate<String, Object> kafkaTemplate;

    private final ResponseAwaitService responseAwaitService;


    @Value("${spring.kafka.userCreateTopic}")
    private String userCreateTopic;

    @Value("${spring.kafka.userFindAllTopic}")
    private String userFindAllTopic;

    @Value("${spring.kafka.userUpdateTopic}")
    private String userUpdateTopic;

    @Value("${spring.kafka.userDeleteTopic}")
    private String userDeleteTopic;

    @Value("${spring.kafka.userRepliesTopic}")
    private String userRepliesTopic;


    @GetMapping
    public List<UserDTO> findAll() throws ExecutionException, InterruptedException {
        String correlationId = UUID.randomUUID().toString();

        Message<?> message = MessageBuilder.withPayload(new UserDTO())
                .setHeader(KafkaHeaders.CORRELATION_ID, correlationId)
                .setHeader(KafkaHeaders.TOPIC, userFindAllTopic)
                .build();

        kafkaTemplate.send(message);

        CompletableFuture<List<UserDTO>> future = responseAwaitService.waitForResponse(correlationId);

        future.whenComplete((result, ex) -> {
            if (ex == null) {
                System.out.println(result);
            }
            else {
                System.out.println("some exception in findAll");
            }
        });
        return future.get();
    }

//    @PostMapping("/login")
//    public UserDTO login(@RequestBody UserDTO dto) {
//        Authentication auth = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        dto.getName(),
//                        dto.getPassword()
//                )
//        );
//        if (!auth.isAuthenticated()) {
//            throw new UsernameNotFoundException("Invalid username or password");
//        }
//        SecurityContextHolder.getContext().setAuthentication(auth);
//        return dto;
//    }
//
//    @PostMapping("/reg")
//    @ResponseStatus(HttpStatus.CREATED)
//    public CompletableFuture<UserDTO> create(@RequestBody UserDTO dto) {
//        String correlationId = UUID.randomUUID().toString();
//
//        CompletableFuture<UserDTO> responseFuture =
//                responseAwaitService.waitForResponse(correlationId);
//
//        producer.send(
//                userCreateTopic,
//                correlationId,
//                dto
//        );
//
//        return responseFuture;
//    }

//    @DeleteMapping("/{id}")
//    public CompletableFuture<UserDTO> delete(@PathVariable Long id) {
//        String correlationId = UUID.randomUUID().toString();
//        UserDTO dto = new UserDTO();
//        dto.setId(id);
//
//        Message<UserDTO> message = MessageBuilder
//                .withPayload(new UserDTO())
//                .setHeader(KafkaHeaders.CORRELATION_ID, correlationId)
//                .setHeader(KafkaHeaders.REPLY_TOPIC, "users-replies")
//                .build();
//
//        CompletableFuture<UserDTO> responseFuture =
//                responseAwaitService.waitForResponse(correlationId);
//
//        producer.send(
//                userDeleteTopic,
//                message
//        );
//
//        return responseFuture;
//    }

    @KafkaListener(topics = "users-replies", containerFactory = "kafkaListenerContainerFactory")
    public void handleUserResponse(
            Message<Object> message
    ) {
        System.out.println("}}}}}}}}}}}}}}}}}}}}}");

        responseAwaitService.completeResponse(message.getHeaders().get(KafkaHeaders.CORRELATION_ID).toString(), null);
    }

//    @PutMapping("/{id}")
//    public CompletableFuture<UserDTO> updateUser(@RequestBody UserDTO dto) {
//        String correlationId = UUID.randomUUID().toString();
//
//        CompletableFuture<UserDTO> responseFuture =
//                responseAwaitService.waitForResponse(correlationId);
//
//        producer.send(
//                userUpdateTopic,
//                correlationId,
//                dto
//        );
//
//        return responseFuture;
//    }
}