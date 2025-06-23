package Main.Service;

import Main.Entities.Cat;
import Main.Models.CatDTO;
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
public class CatMessagingService {
    private final String bootstrapServers = "${spring.kafka.bootstrap-servers}";

    private static final String catsCreateTopic = "${spring.kafka.catsCreateTopic}";

    private final String catsFindAllTopic = "${spring.kafka.catsFindAllTopic}";

    private final String catsUpdateTopic = "${spring.kafka.catsUpdateTopic}";

    private final String catsDeleteTopic = "${spring.kafka.catsDeleteTopic}";

    private final CatMapper catMapper;

    private final CatService catService;

    private final KafkaTemplate<String, Object> producer;

    public CatMessagingService(CatMapper catMapper, CatService catService, @Qualifier("kafkaTemplate") KafkaTemplate<String, Object> producer) {

        this.catMapper = catMapper;
        this.catService = catService;
        this.producer = producer;
    }

    @Transactional
    @KafkaListener(topics = catsCreateTopic, containerFactory = "kafkaListenerContainerFactory")
    public CatDTO createEvent(Message<CatDTO> message) {
        CatDTO dto = message.getPayload();

        catService.save(catMapper.toCat(dto));

        Message<CatDTO> messageReply = MessageBuilder
                .withPayload(dto)
                .setHeader(KafkaHeaders.CORRELATION_ID, message.getHeaders().get(KafkaHeaders.CORRELATION_ID))
                .setHeader(KafkaHeaders.TOPIC, "cats-create-replies")
                .build();
        producer.send(messageReply);

        return dto;
    }


    @Transactional
    @KafkaListener(topics = catsFindAllTopic, containerFactory = "kafkaListenerContainerFactory")
    public void findAllEvent(Message<CatDTO> message) {
        Iterable<Cat> res = catService.findAll();


        Message<List<CatDTO>> messageReply = MessageBuilder
                .withPayload((List<CatDTO>) catMapper.toCatDTOs(res))
                .setHeader(KafkaHeaders.CORRELATION_ID, message.getHeaders().get(KafkaHeaders.CORRELATION_ID))
                .setHeader(KafkaHeaders.TOPIC, "cats-find-all-replies")
                .build();
        producer.send(messageReply);
    }


    @Transactional
    @KafkaListener(topics = catsDeleteTopic, containerFactory = "kafkaListenerContainerFactory")
    public void deleteByIdEvent(Message<CatDTO> message) {

        CatDTO dto = message.getPayload();

        catService.deleteById(dto.getId());

        Message<CatDTO> messageReply = MessageBuilder
                .withPayload(dto)
                .setHeader(KafkaHeaders.CORRELATION_ID, message.getHeaders().get(KafkaHeaders.CORRELATION_ID))
                .setHeader(KafkaHeaders.TOPIC, "cats-delete-replies")
                .build();
        producer.send(messageReply);
    }

    @Transactional
    @KafkaListener(topics = catsUpdateTopic, containerFactory = "kafkaListenerContainerFactory")
    public void updateByIdEvent(Message<CatDTO> message) {

        CatDTO dto = message.getPayload();

        catService.update(catMapper.toCat(dto));

        Message<CatDTO> messageReply = MessageBuilder
                .withPayload(dto)
                .setHeader(KafkaHeaders.CORRELATION_ID, message.getHeaders().get(KafkaHeaders.CORRELATION_ID))
                .setHeader(KafkaHeaders.TOPIC, "cats-update-replies")
                .build();
        producer.send(messageReply);
    }
}
