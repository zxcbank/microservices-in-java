package Config;

import Entities.Cat;
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
public class CatMessagingService {
    private final String bootstrapServers = "${spring.kafka.bootstrap-servers}";

    private static final String catCreateTopic = "${spring.kafka.catCreateTopic}";

    private final String catFindAllTopic = "${spring.kafka.catFindAllTopic}";

    private final String catUpdateTopic = "${spring.kafka.catUpdateTopic}";

    private final String catDeleteTopic = "${spring.kafka.catDeleteTopic}";

    private final String kafkaConsumerGroupId = "${spring.kafka.consumer.group-id}";

    private final CatMapper catMapper;

    private final CatService catSerivce;

    @Transactional
    @KafkaListener(topics = catCreateTopic, groupId = kafkaConsumerGroupId, properties = {"spring.json.value.default.type=java.Models.CatDTO"})
    public CatDTO createUserEvent(CatDTO dto) {
        catSerivce.save(catMapper.toCat(dto));

        return dto;
    }

    @Transactional
    @KafkaListener(topics = catUpdateTopic, groupId = kafkaConsumerGroupId, properties = {"spring.json.value.default.type=java.Models.CatDTO"})
    public CatDTO updateUserEvent(CatDTO dto) {
        catSerivce.update(catMapper.toCat(dto));

        return dto;
    }

    @Transactional
    @KafkaListener(topics = catFindAllTopic, groupId = kafkaConsumerGroupId, properties = {"spring.json.value.default.type=java.Models.CatDTO"})
    public List<CatDTO> findAllUserEvent(CatDTO dto) {
        Iterable<Cat> res = catSerivce.findAll();

        return (List<CatDTO>) catMapper.toCatDTOs(res);
    }

    @Transactional
    @KafkaListener(topics = catDeleteTopic, groupId = kafkaConsumerGroupId, properties = {"spring.json.value.default.type=java.Models.CatDTO"})
    public CatDTO deleteUserEvent(CatDTO dto) {
        catSerivce.delete(catMapper.toCat(dto));

        return dto;
    }
}
