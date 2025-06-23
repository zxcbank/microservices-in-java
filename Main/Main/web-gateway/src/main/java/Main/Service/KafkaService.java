package Main.Service;

import Main.Controllers.ResponseAwaitService;
import Main.Models.CatDTO;
import Main.Models.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


@Service
@RequiredArgsConstructor
public class KafkaService {

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

    private final String userFindAllRepliesTopic = "${spring.kafka.userFindAllRepliesTopic}";

    private final String userDeleteRepliesTopic = "${spring.kafka.userDeleteRepliesTopic}";

    private final String userUpdateRepliesTopic = "${spring.kafka.userUpdateRepliesTopic}";

    private final String userCreateRepliesTopic = "${spring.kafka.userCreateRepliesTopic}";

    @Value("${spring.kafka.catCreateTopic}")
    private String catCreateTopic;

    @Value("${spring.kafka.catFindAllTopic}")
    private String catFindAllTopic;

    @Value("${spring.kafka.catUpdateTopic}")
    private String catUpdateTopic;

    @Value("${spring.kafka.catDeleteTopic}")
    private String catDeleteTopic;

    private final String catFindAllRepliesTopic = "${spring.kafka.catFindAllRepliesTopic}";

    private final String catDeleteRepliesTopic = "${spring.kafka.catDeleteRepliesTopic}";

    private final String catUpdateRepliesTopic = "${spring.kafka.catUpdateRepliesTopic}";

    private final String catCreateRepliesTopic = "${spring.kafka.catCreateRepliesTopic}";

    public List<UserDTO> sendFindAllUsersMessage() throws ExecutionException, InterruptedException {
        String correlationId = UUID.randomUUID().toString();

        Message<?> message = MessageBuilder.withPayload(new UserDTO())
                .setHeader(KafkaHeaders.CORRELATION_ID, correlationId)
                .setHeader(KafkaHeaders.TOPIC, userFindAllTopic)
                .build();

        kafkaTemplate.send(message);

        CompletableFuture<List<UserDTO>> future = responseAwaitService.waitForCollectionResponse(correlationId);

        future.whenComplete((result, ex) -> {
            if (ex != null) {
                System.out.println("some exception in kafkaService while FindAllUser");
            }
        });
        return future.get();
    }

    public UserDTO sendSaveUserMessage(UserDTO dto) throws ExecutionException, InterruptedException {
        String correlationId = UUID.randomUUID().toString();

        Message<?> message = MessageBuilder.withPayload(dto)
                .setHeader(KafkaHeaders.CORRELATION_ID, correlationId)
                .setHeader(KafkaHeaders.TOPIC, userCreateTopic)
                .build();

        kafkaTemplate.send(message);

        CompletableFuture<UserDTO> future = responseAwaitService.waitForElementResponse(correlationId);

        future.whenComplete((result, ex) -> {
            if (ex != null) {
                System.out.println("some exception in kafkaService while CreateUserById");
            }
        });
        return future.get();
    }

    public UserDTO sendDeleteUserByIdMessage(Long Id) throws ExecutionException, InterruptedException {
        String correlationId = UUID.randomUUID().toString();
        UserDTO dto = new UserDTO();
        dto.setId(Id);

        Message<?> message = MessageBuilder.withPayload(dto)
                .setHeader(KafkaHeaders.CORRELATION_ID, correlationId)
                .setHeader(KafkaHeaders.TOPIC, userDeleteTopic)
                .build();

        kafkaTemplate.send(message);

        CompletableFuture<UserDTO> future = responseAwaitService.waitForElementResponse(correlationId);

        future.whenComplete((result, ex) -> {
            if (ex != null) {
                System.out.println("some exception in kafkaService while DeleteUserById");
            }
        });
        return future.get();
    }

    public UserDTO sendUpdateUserByIdMessage(UserDTO dto) throws ExecutionException, InterruptedException {
        String correlationId = UUID.randomUUID().toString();

        Message<?> message = MessageBuilder.withPayload(dto)
                .setHeader(KafkaHeaders.CORRELATION_ID, correlationId)
                .setHeader(KafkaHeaders.TOPIC, userUpdateTopic)
                .build();

        kafkaTemplate.send(message);

        CompletableFuture<UserDTO> future = responseAwaitService.waitForElementResponse(correlationId);

        future.whenComplete((result, ex) -> {
            if (ex != null) {
                System.out.println("some exception in kafkaService while UpdateUserById");
            }
        });
        return future.get();
    }

    @KafkaListener(topics = userFindAllRepliesTopic, containerFactory = "kafkaListenerContainerFactory")
    public void handleUserFindAllResponse(Message<List<UserDTO>> message) {

        responseAwaitService.completeResponse(message.getHeaders().get(KafkaHeaders.CORRELATION_ID).toString(), message.getPayload());
    }

    @KafkaListener(topics = userDeleteRepliesTopic, containerFactory = "kafkaListenerContainerFactory")
    public void handleUserDeleteResponse(Message<UserDTO> message) {

        responseAwaitService.completeResponse(message.getHeaders().get(KafkaHeaders.CORRELATION_ID).toString(), message.getPayload());
    }

    @KafkaListener(topics = userUpdateRepliesTopic, containerFactory = "kafkaListenerContainerFactory")
    public void handleUserUpdateResponse(Message<UserDTO> message) {

        responseAwaitService.completeResponse(message.getHeaders().get(KafkaHeaders.CORRELATION_ID).toString(), message.getPayload());
    }

    @KafkaListener(topics = userCreateRepliesTopic, containerFactory = "kafkaListenerContainerFactory")
    public void handleUserCreateResponse(Message<UserDTO> message) {

        responseAwaitService.completeResponse(message.getHeaders().get(KafkaHeaders.CORRELATION_ID).toString(), message.getPayload());
    }


    public List<CatDTO> sendFindAllCatsMessage() throws ExecutionException, InterruptedException {
        String correlationId = UUID.randomUUID().toString();

        Message<?> message = MessageBuilder.withPayload(new CatDTO())
                .setHeader(KafkaHeaders.CORRELATION_ID, correlationId)
                .setHeader(KafkaHeaders.TOPIC, catFindAllTopic)
                .build();

        kafkaTemplate.send(message);

        CompletableFuture<List<CatDTO>> future = responseAwaitService.waitForCollectionResponse(correlationId);

        future.whenComplete((result, ex) -> {
            if (ex != null) {
                System.out.println("some exception in kafkaService while FindAllCats");
            }
        });
        return future.get();
    }

    public CatDTO sendSaveCatMessage(CatDTO dto) throws ExecutionException, InterruptedException {
        String correlationId = UUID.randomUUID().toString();

        Message<?> message = MessageBuilder.withPayload(dto)
                .setHeader(KafkaHeaders.CORRELATION_ID, correlationId)
                .setHeader(KafkaHeaders.TOPIC, catCreateTopic)
                .build();

        kafkaTemplate.send(message);

        CompletableFuture<CatDTO> future = responseAwaitService.waitForElementResponse(correlationId);

        future.whenComplete((result, ex) -> {
            if (ex != null) {
                System.out.println("some exception in kafkaService while CreateCatById");
            }
        });
        return future.get();
    }

    public CatDTO sendDeleteCatByIdMessage(Long Id) throws ExecutionException, InterruptedException {
        String correlationId = UUID.randomUUID().toString();
        CatDTO dto = new CatDTO();
        dto.setId(Id);

        Message<?> message = MessageBuilder.withPayload(dto)
                .setHeader(KafkaHeaders.CORRELATION_ID, correlationId)
                .setHeader(KafkaHeaders.TOPIC, catDeleteTopic)
                .build();

        kafkaTemplate.send(message);

        CompletableFuture<CatDTO> future = responseAwaitService.waitForElementResponse(correlationId);

        future.whenComplete((result, ex) -> {
            if (ex != null) {
                System.out.println("some exception in kafkaService while DeleteCatById");
            }
        });
        return future.get();
    }

    public CatDTO sendUpdateCatByIdMessage(CatDTO dto) throws ExecutionException, InterruptedException {
        String correlationId = UUID.randomUUID().toString();

        Message<?> message = MessageBuilder.withPayload(dto)
                .setHeader(KafkaHeaders.CORRELATION_ID, correlationId)
                .setHeader(KafkaHeaders.TOPIC, catUpdateTopic)
                .build();

        kafkaTemplate.send(message);

        CompletableFuture<CatDTO> future = responseAwaitService.waitForElementResponse(correlationId);

        future.whenComplete((result, ex) -> {
            if (ex != null) {
                System.err.println("some exception in kafkaService while UpdateById");
            }
        });
        return future.get();
    }

    @KafkaListener(topics = catFindAllRepliesTopic, containerFactory = "kafkaListenerContainerFactory")
    public void handleCatFindAllResponse(Message<List<CatDTO>> message) {

        responseAwaitService.completeResponse(Objects.requireNonNull(message.getHeaders().get(KafkaHeaders.CORRELATION_ID)).toString(), message.getPayload());
    }

    @KafkaListener(topics = catDeleteRepliesTopic, containerFactory = "kafkaListenerContainerFactory")
    public void handleCatDeleteResponse(Message<CatDTO> message) {

        responseAwaitService.completeResponse(Objects.requireNonNull(message.getHeaders().get(KafkaHeaders.CORRELATION_ID)).toString(), message.getPayload());
    }

    @KafkaListener(topics = catUpdateRepliesTopic, containerFactory = "kafkaListenerContainerFactory")
    public void handleCatUpdateResponse(Message<CatDTO> message) {

        responseAwaitService.completeResponse(Objects.requireNonNull(message.getHeaders().get(KafkaHeaders.CORRELATION_ID)).toString(), message.getPayload());
    }

    @KafkaListener(topics = catCreateRepliesTopic, containerFactory = "kafkaListenerContainerFactory")
    public void handleCatCreateResponse(Message<CatDTO> message) {

        responseAwaitService.completeResponse(Objects.requireNonNull(message.getHeaders().get(KafkaHeaders.CORRELATION_ID)).toString(), message.getPayload());
    }
}