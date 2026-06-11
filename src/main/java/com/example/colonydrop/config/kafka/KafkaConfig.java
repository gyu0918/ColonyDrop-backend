//package com.example.colonydrop.config.kafka;
//
//import com.example.colonydrop.dto.chat.ChatMessageDto;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.apache.kafka.clients.admin.NewTopic;
//import org.apache.kafka.clients.consumer.ConsumerConfig;
//import org.apache.kafka.clients.producer.ProducerConfig;
//import org.apache.kafka.common.serialization.StringDeserializer;
//import org.apache.kafka.common.serialization.StringSerializer;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
//import org.springframework.kafka.config.TopicBuilder;
//import org.springframework.kafka.core.*;
//
//
//import java.util.HashMap;
//import java.util.Map;
//
//@Configuration
//public class KafkaConfig {
//    @Bean
//    public ProducerFactory<String, String> producerFactory() {
//        Map<String, Object> config = new HashMap<>();
//        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
//        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//        return new DefaultKafkaProducerFactory<>(config);
//    }
//
//    @Bean
//    public KafkaTemplate<String, String> kafkaTemplate() {
//        return new KafkaTemplate<>(producerFactory());
//    }
//
//    @Bean
//    public ConsumerFactory<String, String> consumerFactory() {
//        Map<String, Object> config = new HashMap<>();
//        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
//        config.put(ConsumerConfig.GROUP_ID_CONFIG, "colonydrop");
//        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//        return new DefaultKafkaConsumerFactory<>(config);
//    }
//
//    @Bean
//    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
//        ConcurrentKafkaListenerContainerFactory<String, String> factory =
//                new ConcurrentKafkaListenerContainerFactory<>();
//        factory.setConsumerFactory(consumerFactory());
//        return factory;
//    }
//
//
//    //채팅 토픽 4개 (각 파티션 별로 1개씩)
//    @Bean
//    public NewTopic gundamChat(){
//        return TopicBuilder.name("chat-gundam")
//                .partitions(1)
//                .replicas(1)
//                .build();
//    }
//    @Bean
//    public NewTopic openrunChat() {
//        return TopicBuilder.name("chat-openrun")
//                .partitions(1)
//                .replicas(1)
//                .build();
//    }
//
//    @Bean
//    public NewTopic sharingChat() {
//        return TopicBuilder.name("chat-sharing")
//                .partitions(1)
//                .replicas(1)
//                .build();
//    }
//
//    @Bean
//    public NewTopic freeChat() {
//        return TopicBuilder.name("chat-free")
//                .partitions(1)
//                .replicas(1)
//                .build();
//    }
//}

//package com.example.colonydrop.config.kafka;
//
//import org.apache.kafka.clients.admin.NewTopic;
//import org.apache.kafka.clients.consumer.ConsumerConfig;
//import org.apache.kafka.clients.producer.ProducerConfig;
//import org.apache.kafka.common.serialization.StringDeserializer;
//import org.apache.kafka.common.serialization.StringSerializer;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
//import org.springframework.kafka.config.TopicBuilder;
//import org.springframework.kafka.core.*;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@Configuration
//public class KafkaConfig {
//
//    @Value("${spring.kafka.bootstrap-servers}")
//    private String bootstrapServers;
//
//    private Map<String, Object> commonProps() {
//        Map<String, Object> props = new HashMap<>();
//        props.put("bootstrap.servers", bootstrapServers);
//        props.put("security.protocol", "SASL_SSL");
//        props.put("sasl.mechanism", "AWS_MSK_IAM");
//        props.put("sasl.jaas.config", "software.amazon.msk.auth.iam.IAMLoginModule required;");
//        props.put("sasl.client.callback.handler.class", "software.amazon.msk.auth.iam.IAMClientCallbackHandler");
//        return props;
//    }
//
//    @Bean
//    public ProducerFactory<String, String> producerFactory() {
//        Map<String, Object> props = commonProps();
//        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//        return new DefaultKafkaProducerFactory<>(props);
//    }
//
//    @Bean
//    public KafkaTemplate<String, String> kafkaTemplate() {
//        return new KafkaTemplate<>(producerFactory());
//    }
//
//    @Bean
//    public ConsumerFactory<String, String> consumerFactory() {
//        Map<String, Object> props = commonProps();
//        props.put(ConsumerConfig.GROUP_ID_CONFIG, "colonydrop");
//        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
//        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//        return new DefaultKafkaConsumerFactory<>(props);
//    }
//
//    @Bean
//    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
//        ConcurrentKafkaListenerContainerFactory<String, String> factory =
//                new ConcurrentKafkaListenerContainerFactory<>();
//        factory.setConsumerFactory(consumerFactory());
//        return factory;
//    }
//
//    @Bean
//    public NewTopic gundamChat() {
//        return TopicBuilder.name("chat-gundam")
//                .partitions(1)
//                .replicas(1)
//                .build();
//    }
//
//    @Bean
//    public NewTopic openrunChat() {
//        return TopicBuilder.name("chat-openrun")
//                .partitions(1)
//                .replicas(1)
//                .build();
//    }
//
//    @Bean
//    public NewTopic sharingChat() {
//        return TopicBuilder.name("chat-sharing")
//                .partitions(1)
//                .replicas(1)
//                .build();
//    }
//
//    @Bean
//    public NewTopic freeChat() {
//        return TopicBuilder.name("chat-free")
//                .partitions(1)
//                .replicas(1)
//                .build();
//    }
//}

package com.example.colonydrop.config.kafka;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.*;


import org.springframework.kafka.annotation.EnableKafka;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka  // ← 이거 추가
public class KafkaConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.username}")
    private String kafkaUsername;

    @Value("${spring.kafka.password}")
    private String kafkaPassword;

    private Map<String, Object> commonProps() {
        Map<String, Object> props = new HashMap<>();
        props.put("bootstrap.servers", bootstrapServers);
        props.put("security.protocol", "SASL_SSL");
        props.put("sasl.mechanism", "PLAIN");
        props.put("sasl.jaas.config",
                "org.apache.kafka.common.security.plain.PlainLoginModule required " +
                        "username=\"" + kafkaUsername + "\" " +
                        "password=\"" + kafkaPassword + "\";");
        return props;
    }

    @Bean
    public ProducerFactory<String, String> producerFactory() {
        Map<String, Object> props = commonProps();
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return new DefaultKafkaProducerFactory<>(props);
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
        Map<String, Object> props = commonProps();
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "colonydrop");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.FETCH_MAX_WAIT_MS_CONFIG, 100);  // ✅ 추가 (기본 500ms → 100ms)
        props.put(ConsumerConfig.FETCH_MIN_BYTES_CONFIG, 1);       // ✅ 추가 (즉시 처리)
        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

    @Bean
    public NewTopic orderQueue() {
        return TopicBuilder.name("order-queue")
                .partitions(1)
                .replicas(3)
                .build();
    }

//    @Bean
//    public NewTopic gundamChat() {
//        return TopicBuilder.name("chat-gundam")
//                .partitions(1)
//                .replicas(3)
//                .build();
//    }
//
//    @Bean
//    public NewTopic openrunChat() {
//        return TopicBuilder.name("chat-openrun")
//                .partitions(1)
//                .replicas(3)
//                .build();
//    }
//
//    @Bean
//    public NewTopic sharingChat() {
//        return TopicBuilder.name("chat-sharing")
//                .partitions(1)
//                .replicas(3)
//                .build();
//    }
//
//    @Bean
//    public NewTopic freeChat() {
//        return TopicBuilder.name("chat-free")
//                .partitions(1)
//                .replicas(3)
//                .build();
//    }

//    @Bean
//    @Primary
//    public ObjectMapper objectMapper() {
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
//        mapper.configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, false);
//        return mapper;
//    }
}