package Config;


import Entities.User;
import Models.UserDTO;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import Service.*;

import java.util.Iterator;
import java.util.List;


@Service
@AllArgsConstructor
public class KafkaMessagingService {
    private final String bootstrapServers = "${spring.kafka.bootstrap-servers}";

    private static final String userCreateTopic = "${spring.kafka.userCreateTopic}";

    private final String userFindAllTopic = "${spring.kafka.userFindAllTopic}";

    private final String userUpdateTopic = "${spring.kafka.userUpdateTopic}";

    private final String userDeleteTopic = "${spring.kafka.userDeleteTopic}";

    private final String kafkaConsumerGroupId = "${spring.kafka.consumer.group-id}";

    private final UserMapper userMapper;

    private final UserService userService;

    @Transactional
    @KafkaListener(topics = userCreateTopic, groupId = kafkaConsumerGroupId, properties = {"spring.json.value.default.type=java.Models.UserDTO"})
    public UserDTO createUserEvent(UserDTO dto) {
        userService.save(userMapper.toUser(dto));

        return dto;
    }

    @Transactional
    @KafkaListener(topics = userUpdateTopic, groupId = kafkaConsumerGroupId, properties = {"spring.json.value.default.type=java.Models.UserDTO"})
    public UserDTO updateUserEvent(UserDTO dto) {
        userService.update(userMapper.toUser(dto));

        return dto;
    }

    @Transactional
    @KafkaListener(topics = userFindAllTopic, groupId = kafkaConsumerGroupId, properties = {"spring.json.value.default.type=java.Models.UserDTO"})
    public List<UserDTO> findAllUserEvent(UserDTO dto) {
        Iterable<User> res = userService.findAll();

        return (List<UserDTO>) userMapper.toUserDTOs(res);
    }

    @Transactional
    @KafkaListener(topics = userDeleteTopic, groupId = kafkaConsumerGroupId, properties = {"spring.json.value.default.type=java.Models.UserDTO"})
    public UserDTO deleteUserEvent(UserDTO dto) {
        userService.delete(userMapper.toUser(dto));

        return dto;
    }
}
