package Main.Service;

import Main.Entities.User;
import Main.Models.UserDTO;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;

import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@EnableKafka
public class UserMessagingService {
    private final String bootstrapServers = "${spring.kafka.bootstrap-servers}";

    private static final String userCreateTopic = "${spring.kafka.userCreateTopic}";

    private final String userFindAllTopic = "${spring.kafka.userFindAllTopic}";

    private final String userUpdateTopic = "${spring.kafka.userUpdateTopic}";

    private final String userDeleteTopic = "${spring.kafka.userDeleteTopic}";

    private final String userDeleteRepliesTopic = "${spring.kafka.userDeleteRepliesTopic}";

    private final String userFindAllRepliesTopic = "${spring.kafka.userFindAllRepliesTopic}";

    private final String kafkaConsumerGroupId = "${spring.kafka.consumer.group-id}";

    private final UserMapper userMapper;

    private final UserService userService;

    private final KafkaTemplate<String, Object> producer;

    public UserMessagingService(UserMapper userMapper, UserService userService, @Qualifier("kafkaTemplate") KafkaTemplate<String, Object> producer) {

        this.userMapper = userMapper;
        this.userService = userService;
        this.producer = producer;
    }

    @Transactional
    @KafkaListener(topics = userCreateTopic, containerFactory = "kafkaListenerContainerFactory")
    public UserDTO createUserEvent(Message<UserDTO> message) {
        UserDTO dto = message.getPayload();

        userService.save(userMapper.toUser(dto));

        Message<UserDTO> messageReply = MessageBuilder
                .withPayload(dto)
                .setHeader(KafkaHeaders.CORRELATION_ID, message.getHeaders().get(KafkaHeaders.CORRELATION_ID))
                .setHeader(KafkaHeaders.TOPIC, "users-create-replies")
                .build();
        producer.send(messageReply);

        return dto;
    }


    @Transactional
    @KafkaListener(topics = userFindAllTopic, containerFactory = "kafkaListenerContainerFactory")
    public void findAllUserEvent(Message<UserDTO> message) {
        Iterable<User> res = userService.findAll();


        Message<List<UserDTO>> messageReply = MessageBuilder
                .withPayload((List<UserDTO>) userMapper.toUserDTOs(res))
                .setHeader(KafkaHeaders.CORRELATION_ID, message.getHeaders().get(KafkaHeaders.CORRELATION_ID))
                .setHeader(KafkaHeaders.TOPIC, "users-find-all-replies")
                .build();
        producer.send(messageReply);
    }


    @Transactional
    @KafkaListener(topics = userDeleteTopic, containerFactory = "kafkaListenerContainerFactory")
    public void deleteByIdEvent(Message<UserDTO> message) {

        UserDTO dto = message.getPayload();

        userService.deleteById(dto.getId());

        Message<UserDTO> messageReply = MessageBuilder
                .withPayload(dto)
                .setHeader(KafkaHeaders.CORRELATION_ID, message.getHeaders().get(KafkaHeaders.CORRELATION_ID))
                .setHeader(KafkaHeaders.TOPIC, "users-delete-replies")
                .build();
        producer.send(messageReply);
    }

    @Transactional
    @KafkaListener(topics = userUpdateTopic, containerFactory = "kafkaListenerContainerFactory")
    public void updateByIdEvent(Message<UserDTO> message) {

        UserDTO dto = message.getPayload();

        userService.update(userMapper.toUser(dto));

        Message<UserDTO> messageReply = MessageBuilder
                .withPayload(dto)
                .setHeader(KafkaHeaders.CORRELATION_ID, message.getHeaders().get(KafkaHeaders.CORRELATION_ID))
                .setHeader(KafkaHeaders.TOPIC, "users-update-replies")
                .build();
        producer.send(messageReply);
    }
}
