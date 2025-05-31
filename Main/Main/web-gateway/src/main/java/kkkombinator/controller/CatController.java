package kkkombinator.controller;

import kkkombinator.KafkaTopics;
import kkkombinator.Requests.CatRequest;
import kkkombinator.CatDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
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
@RequestMapping("/cats")
@RequiredArgsConstructor
public class CatController {

    private final AuthenticationManager authenticationManager;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final String CatRequestTopic = KafkaTopics.USERS_TOPIC;
    private final ResponseAwaitService responseAwaitService;



    @GetMapping("/")
    public CompletableFuture<List<CatDTO>> findAll(Reader reader) {
        String correlationId = UUID.randomUUID().toString();

        CatRequest request = new CatRequest("FindAll.Cat",  null);

        CompletableFuture<List<CatDTO>> responseFuture =
                responseAwaitService.waitForResponse(correlationId);

        kafkaTemplate.send(
                CatRequestTopic,
                correlationId,
                request
        );
        return responseFuture;
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public CompletableFuture<CatDTO> create(@RequestBody CatDTO dto) {
        String correlationId = UUID.randomUUID().toString();
        CatRequest request = new CatRequest("Create.Cat", null);

        CompletableFuture<CatDTO> future =
                responseAwaitService.waitForResponse(correlationId);

        kafkaTemplate.send(CatRequestTopic, correlationId, request);


        return future;
    }

    @DeleteMapping("/{id}")
    public CompletableFuture<CatDTO> delete(@PathVariable Long id) {
        String correlationId = UUID.randomUUID().toString();
        CatDTO dto = new CatDTO();
        dto.setId(id);
        var request = new CatRequest("Delete.Cat", dto);
        CompletableFuture<CatDTO> response = responseAwaitService.waitForResponse(correlationId);
        kafkaTemplate.send(CatRequestTopic,request);

        return response;
    }

    @PutMapping("/{id}")
    public CompletableFuture<CatDTO> updateUser(@RequestBody CatDTO dto) {
        String correlationId = UUID.randomUUID().toString();
        var request = new CatRequest("Update.Cat", dto);
        CompletableFuture<CatDTO> response = responseAwaitService.waitForResponse(correlationId);

        kafkaTemplate.send(CatRequestTopic,request);

        return response;
    }
}