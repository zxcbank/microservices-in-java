package Config;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;


@Configuration
public class KafkaConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.consumer.group-id}")
    private String groupId;

    @Value("${spring.kafka.userCreateTopic}")
    private String userCreateTopic;

    @Value("${spring.kafka.userFindAllTopic}")
    private String userFindAllTopic;

    @Value("${spring.kafka.userUpdateTopic}")
    private String userUpdateTopic;

    @Value("${spring.kafka.userDeleteTopic}")
    private String userDeleteTopic;

    @Value("${spring.kafka.catCreateTopic}")
    private String catCreateTopic;

    @Value("${spring.kafka.catFindAllTopic}")
    private String catFindAllTopic;

    @Value("${spring.kafka.catUpdateTopic}")
    private String catUpdateTopic;

    @Value("${spring.kafka.catDeleteTopic}")
    private String catDeleteTopic;

    @Value("${spring.kafka.globalPartition}")
    private int globalPartition;

    @Value("${spring.kafka.globalReplicationFactor}")
    private short globalReplicationFactor;

    @Bean
    public ProducerFactory<String, Object> producerFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(config);
    }


    @Bean
    public ConsumerFactory<String, Object> consumerFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(config);
    }

    @Bean
    public NewTopic catCreateTopic() {
        return new NewTopic(catCreateTopic, globalPartition, globalReplicationFactor);
    }

    @Bean
    public NewTopic catUpdateTopic() {
        return new NewTopic(catUpdateTopic, globalPartition, globalReplicationFactor);
    }

    @Bean
    public NewTopic catDeleteTopic() {
        return new NewTopic(catDeleteTopic, globalPartition, globalReplicationFactor);
    }

    @Bean
    public NewTopic catFindAllTopic() {
        return new NewTopic(catFindAllTopic, globalPartition, globalReplicationFactor);
    }

    @Bean
    public NewTopic userCreateTopic() {
        return new NewTopic(userCreateTopic, globalPartition, globalReplicationFactor);
    }

    @Bean
    public NewTopic userUpdateTopic() {
        return new NewTopic(userUpdateTopic, globalPartition, globalReplicationFactor);
    }

    @Bean
    public NewTopic userDeleteTopic() {
        return new NewTopic(userDeleteTopic, globalPartition, globalReplicationFactor);
    }

    @Bean
    public NewTopic userFindAllTopic() {
        return new NewTopic(userFindAllTopic, globalPartition, globalReplicationFactor);
    }

//    @Bean
//    public ReplyingKafkaTemplate<String, Object, Object> replyingKafkaTemplate(
//            ProducerFactory<String, Object> producerFactory,
//            KafkaMessageListenerContainer<String, Object> container) {
//        return new ReplyingKafkaTemplate<>(producerFactory, container);
//    }
}
