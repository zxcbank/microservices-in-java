package Main.Service;

import Main.Entities.User;
import Main.Models.UserDTO;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.StreamSupport;


@Service
@EnableKafka
public class UserMessagingService {
    private final String bootstrapServers = "${spring.kafka.bootstrap-servers}";

    private static final String userCreateTopic = "${spring.kafka.userCreateTopic}";

    private final String userFindAllTopic = "${spring.kafka.userFindAllTopic}";

    private final String userUpdateTopic = "${spring.kafka.userUpdateTopic}";

    private final String userDeleteTopic = "${spring.kafka.userDeleteTopic}";

    private final String kafkaConsumerGroupId = "${spring.kafka.consumer.group-id}";

    private final UserMapper userMapper;

    private final UserService userService;

    private final KafkaTemplate<String, Object> producer;

    public UserMessagingService(UserMapper userMapper, UserService userService, @Qualifier("kafkaTemplate") KafkaTemplate<String, Object> producer) {

        this.userMapper = userMapper;
        this.userService = userService;
        this.producer = producer;
    }

//    @Transactional
//    @KafkaListener(topics = userCreateTopic, groupId = kafkaConsumerGroupId, properties = {"spring.json.value.default.type=Models.UserDTO"})
//    public UserDTO createUserEvent(UserDTO dto) {
//        userService.save(userMapper.toUser(dto));
//
//        return dto;
//    }
//
//    @Transactional
//    @KafkaListener(topics = userUpdateTopic, groupId = kafkaConsumerGroupId, properties = {"spring.json.value.default.type=Models.UserDTO"})
//    public UserDTO updateUserEvent(UserDTO dto) {
//        userService.update(userMapper.toUser(dto));
//
//        return dto;
//    }

    @Transactional
    @KafkaListener(topics = userFindAllTopic, containerFactory = "kafkaListenerContainerFactory")
    public void findAllUserEvent(Message<UserDTO> message) {
        System.out.println("|||||||||||||||||||||||||||||||||||||||||||||||||||||||");
        Iterable<User> res = userService.findAll();
        for (User user : res) {
            System.out.println(user.getName());
        }
        Message<List<UserDTO>> messageReply = MessageBuilder
                .withPayload((List<UserDTO>) userMapper.toUserDTOs(res))
                .setHeader(KafkaHeaders.CORRELATION_ID, message.getHeaders().get(KafkaHeaders.CORRELATION_ID))
                .setHeader(KafkaHeaders.TOPIC, "users-replies")
                .build();
        producer.send(messageReply);
    }

//    @Transactional
//    @KafkaListener(topics = "users-find-all", containerFactory = "kafkaListenerContainerFactory")
//    public void handleFindAllRequest(
//            Object message
//    ) {
//
//
//        Iterable<User> users = userService.findAll();
//        List<UserDTO> userDTOs = StreamSupport.stream(users.spliterator(), false)
//                .map(userMapper::toUserDTO)
//                .collect(Collectors.toList());
//
//        Message<List<UserDTO>> messageReply = MessageBuilder
//                .withPayload(userDTOs)
//                        .setHeader(KafkaHeaders.CORRELATION_ID, correlationId)
//                                .build();
//        producer.send("users-replies", messageReply);
//    }

//    @KafkaListener(topics = "users-find-all", containerFactory = "kafkaListenerContainerFactory")
//    @SendTo // This annotation sends the return value to the reply topic specified in the header
//    public String listenAndReply(String requestMessage) {
//        // Process the request message
//        String replyMessage = "Reply to: " + requestMessage;
//        return replyMessage; // This will be sent as the reply
//    }

//    @Transactional
//    @KafkaListener(topics = userDeleteTopic, groupId = kafkaConsumerGroupId, properties = {"spring.json.value.default.type=Models.UserDTO"})
//    public UserDTO deleteUserEvent(UserDTO dto) {
//        userService.delete(userMapper.toUser(dto));
//
//        return dto;
//    }
}
