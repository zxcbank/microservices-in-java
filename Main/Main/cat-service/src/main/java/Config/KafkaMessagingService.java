package Config;

import Entities.Cat;
import Entities.User;
import Models.CatDTO;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import Service.CatService;
import Service.CatMapper;

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

    private final CatMapper catMapper;

    private final CatService catSerivce;

    @Transactional
    @KafkaListener(topics = userCreateTopic, groupId = kafkaConsumerGroupId, properties = {"spring.json.value.default.type=java.Models.CatDTO"})
    public CatDTO createUserEvent(CatDTO dto) {
        catSerivce.save(catMapper.toCat(dto));

        return dto;
    }

    @Transactional
    @KafkaListener(topics = userUpdateTopic, groupId = kafkaConsumerGroupId, properties = {"spring.json.value.default.type=java.Models.CatDTO"})
    public CatDTO updateUserEvent(CatDTO dto) {
        catSerivce.update(catMapper.toCat(dto));

        return dto;
    }

    @Transactional
    @KafkaListener(topics = userFindAllTopic, groupId = kafkaConsumerGroupId, properties = {"spring.json.value.default.type=java.Models.CatDTO"})
    public List<CatDTO> findAllUserEvent(CatDTO dto) {
        Iterable<Cat> res = catSerivce.findAll();

        return (List<CatDTO>) catMapper.toCatDTOs(res);
    }

    @Transactional
    @KafkaListener(topics = userDeleteTopic, groupId = kafkaConsumerGroupId, properties = {"spring.json.value.default.type=java.Models.CatDTO"})
    public CatDTO deleteUserEvent(CatDTO dto) {
        catSerivce.delete(catMapper.toCat(dto));

        return dto;
    }
}
