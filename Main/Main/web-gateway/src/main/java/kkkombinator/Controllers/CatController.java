package kkkombinator.Controllers;

import Models.CatDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/v1/cats")
@RequiredArgsConstructor
public class CatController {

    private final AuthenticationManager authenticationManager;
    private final ResponseAwaitService responseAwaitService;

    @Value("${spring.kafka.catCreateTopic}")
    private final String catCreateTopic;

    @Value("${spring.kafka.catFindAllTopic}")
    private final String catFindAllTopic;

    @Value("${spring.kafka.catUpdateTopic}")
    private final String catUpdateTopic;

    @Value("${spring.kafka.catDeleteTopic}")
    private final String catDeleteTopic;

    private final KafkaTemplate<String, Object> producer;



    @GetMapping("/")
    public CompletableFuture<List<CatDTO>> findAll() {
        String correlationId = UUID.randomUUID().toString();

        CompletableFuture<List<CatDTO>> responseFuture =
                responseAwaitService.waitForResponse(correlationId);

        producer.send(
                catFindAllTopic,
                correlationId,
                null
        );

        return responseFuture;
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public CompletableFuture<CatDTO> create(@RequestBody CatDTO dto) {
        String correlationId = UUID.randomUUID().toString();

        CompletableFuture<CatDTO> responseFuture =
                responseAwaitService.waitForResponse(correlationId);

        producer.send(
                catCreateTopic,
                correlationId,
                null
        );

        return responseFuture;
    }

    @DeleteMapping("/{id}")
    public CompletableFuture<CatDTO> delete(@PathVariable Long id) {
        String correlationId = UUID.randomUUID().toString();

        CompletableFuture<CatDTO> responseFuture =
                responseAwaitService.waitForResponse(correlationId);

        producer.send(
                catDeleteTopic,
                correlationId,
                null
        );

        return responseFuture;
    }

    @PutMapping("/{id}")
    public CompletableFuture<CatDTO> updateCat(@RequestBody CatDTO dto) {
        String correlationId = UUID.randomUUID().toString();

        CompletableFuture<CatDTO> responseFuture =
                responseAwaitService.waitForResponse(correlationId);

        producer.send(
                catUpdateTopic,
                correlationId,
                null
        );

        return responseFuture;
    }
}