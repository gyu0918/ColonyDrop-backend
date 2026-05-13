package com.example.colonydrop.config.redisConfig;

import com.example.colonydrop.dto.order.OrderStatusMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;


//Redis 채널에 메시지가 오면
//해당 sessionId의 WebSocket 세션으로 전달
@Slf4j
@Component
@RequiredArgsConstructor
public class RedisSubscriber implements MessageListener {

    private final SimpMessagingTemplate messagingTemplate;
    private final ObjectMapper objectMapper;

//  MessageListener 구현
//  → Redis 채널에 메시지 오면 자동으로 onMessage 호출
    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
//          채널명에서 sessionId 추출
//          "order-result:abc-123" → "abc-123"
            String channel   = new String(message.getChannel());
            String sessionId = channel.replace(RedisPublisher.CHANNEL_PREFIX, "");
            String body      = new String(message.getBody());

//          JSON 문자열 → OrderStatusMessage 객체로 변환
//          → WebSocket /queue/order/{sessionId} 채널로 전송
//          서버A, B 모두 이 코드 실행
//          서버A: sessionId 가진 WebSocket 세션 있으면 전송
//          서버B: 세션 없으면 그냥 무시
            OrderStatusMessage status = objectMapper.readValue(body, OrderStatusMessage.class);
            messagingTemplate.convertAndSend("/queue/order/" + sessionId, status);
            log.info("WebSocket 전송 완료 → sessionId: {}, status: {}", sessionId, status.getStatus());

        } catch (Exception e) {
            log.error("Redis 메시지 처리 실패", e);
        }
    }
}