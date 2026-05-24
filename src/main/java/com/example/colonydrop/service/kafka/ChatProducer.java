package com.example.colonydrop.service.kafka;


import com.example.colonydrop.dto.chat.ChatMessageDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

//@Slf4j
//@Service
//@RequiredArgsConstructor
//public class ChatProducer {
//
//    private final KafkaTemplate<String, String> kafkaTemplate;
//
//    public void sendMessage(String topic, String message){
//        log.info("[ChatProducer] topic={}, message={}", topic, message);
//        kafkaTemplate.send(topic, message);
//    }
//}

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public void sendMessage(String topic, ChatMessageDto message) throws JsonProcessingException {
        String json = objectMapper.writeValueAsString(message);
        log.info("[ChatProducer] topic={}, message={}", topic, json);
        kafkaTemplate.send(topic, json);
    }
}
