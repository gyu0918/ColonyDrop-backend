//package com.example.colonydrop.config.redisConfig;
//
//import com.example.colonydrop.dto.order.OrderStatusMessage;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.stereotype.Component;
//
//
////컨슈머가 주문 처리 완료 후
////결과를 Redis 채널에 던지는 역할
//@Component
//@RequiredArgsConstructor
//public class RedisPublisher {
//
//    private final StringRedisTemplate redisTemplate;
//    private final ObjectMapper objectMapper;
//
//    public static final String CHANNEL_PREFIX = "order-result:";
//
//
////    OrderStatusMessage 객체를 JSON 문자열로 변환
////    → Redis 채널에 publish
////    예시:
////    채널: "order-result:abc-123"
////    내용: {"status":"READY","merchantUid":"CD-xxx"}
//    public void publish(String sessionId, OrderStatusMessage message) {
//        try {
//            String payload = objectMapper.writeValueAsString(message);
//            redisTemplate.convertAndSend(CHANNEL_PREFIX + sessionId, payload);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException("Redis publish 직렬화 실패", e);
//        }
//    }
//}


package com.example.colonydrop.config.redisConfig;

import com.example.colonydrop.dto.chat.ChatMessageDto;
import com.example.colonydrop.dto.order.OrderStatusMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class RedisPublisher {

    private final StringRedisTemplate redisTemplate;
    private final ObjectMapper objectMapper;

    public static final String CHANNEL_PREFIX = "order-result:";
    public static final String CHAT_CHANNEL_PREFIX = "chat:";

    // ✅ 앱 시작 시 Redis 연결 미리 맺어두기
    @jakarta.annotation.PostConstruct
    public void warmUp() {
        try {
            redisTemplate.hasKey("warmup"); // ✅ 이걸로 대체
            log.info("[RedisPublisher] Redis warmup 완료"); // ✅ 로그 추가
        } catch (Exception e) {
            // 연결 실패해도 앱 시작은 정상 진행
            log.warn("[RedisPublisher] Redis warmup 실패", e);
        }
    }
    public void publish(String sessionId, OrderStatusMessage message) {
        try {
            String payload = objectMapper.writeValueAsString(message);
            redisTemplate.convertAndSend(CHANNEL_PREFIX + sessionId, payload);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Redis publish 직렬화 실패", e);
        }
    }

    public void publishChat(String roomType, ChatMessageDto message) {
        try {
            String payload = objectMapper.writeValueAsString(message);
            redisTemplate.convertAndSend(CHAT_CHANNEL_PREFIX + roomType, payload);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Redis chat publish 직렬화 실패", e);
        }
    }
}