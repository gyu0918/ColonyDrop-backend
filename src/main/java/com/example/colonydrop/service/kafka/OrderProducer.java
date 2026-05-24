package com.example.colonydrop.service.kafka;

import com.example.colonydrop.dto.order.OrderQueueMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public void sendOrder(OrderQueueMessage message) throws JsonProcessingException {
        String json = objectMapper.writeValueAsString(message);
        log.info("[OrderProducer] message={}", json);
        kafkaTemplate.send("order-queue", json);
    }
}
