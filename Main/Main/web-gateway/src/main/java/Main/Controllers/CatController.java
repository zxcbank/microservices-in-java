package Main.Controllers;

import Main.Models.CatDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/v1/cats/")
public class CatController {

    //    private final AuthenticationManager authenticationManager;
    private final ResponseAwaitService responseAwaitService;
    private final KafkaTemplate<String, Object> producer;

    @Autowired
    public CatController(ResponseAwaitService responseAwaitService, KafkaTemplate<String, Object> producer) {
        this.responseAwaitService = responseAwaitService;
        this.producer = producer;
    }

    @Value("${spring.kafka.catCreateTopic}")
    private String catCreateTopic;

    @Value("${spring.kafka.catFindAllTopic}")
    private String catFindAllTopic;

    @Value("${spring.kafka.catUpdateTopic}")
    private String catUpdateTopic;

    @Value("${spring.kafka.catDeleteTopic}")
    private String catDeleteTopic;

    @GetMapping
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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompletableFuture<CatDTO> create(@RequestBody CatDTO dto) {
        String correlationId = UUID.randomUUID().toString();

        CompletableFuture<CatDTO> responseFuture =
                responseAwaitService.waitForResponse(correlationId);

        producer.send(
                catCreateTopic,
                correlationId,
                dto
        );

        return responseFuture;
    }

    @DeleteMapping("/{id}")
    public CompletableFuture<CatDTO> delete(@PathVariable Long id) {
        String correlationId = UUID.randomUUID().toString();
        CatDTO dto = new CatDTO();
        dto.setId(id);

        CompletableFuture<CatDTO> responseFuture =
                responseAwaitService.waitForResponse(correlationId);

        producer.send(
                catDeleteTopic,
                correlationId,
                dto
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
                dto
        );

        return responseFuture;
    }
}