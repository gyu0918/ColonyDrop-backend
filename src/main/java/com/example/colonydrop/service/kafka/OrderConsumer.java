package com.example.colonydrop.service.kafka;

import com.example.colonydrop.config.redisConfig.RedisPublisher;
import com.example.colonydrop.dto.order.OrderQueueMessage;
import com.example.colonydrop.dto.order.OrderStatusMessage;
import com.example.colonydrop.entity.member.Member;
import com.example.colonydrop.repository.member.MemberRepository;
import com.example.colonydrop.service.order.OrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderConsumer {

    private final OrderService orderService;
    private final MemberRepository memberRepository;
    private final RedisPublisher redisPublisher;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "order-queue", groupId = "colonydrop")
    public void consume(String messageJson) throws JsonProcessingException {
        OrderQueueMessage message = objectMapper.readValue(messageJson, OrderQueueMessage.class);
        String sessionId = message.getSessionId();

        log.info("[OrderConsumer] 주문 처리 시작 → sessionId: {}", sessionId);

        redisPublisher.publish(sessionId, OrderStatusMessage.processing());

        try {
            Member member = memberRepository.findByMemberId(message.getMemberId());
            if (member == null) {
                throw new IllegalArgumentException("회원을 찾을 수 없습니다.");
            }

            String merchantUid = orderService.createOrderFromQueue(member, message);
            redisPublisher.publish(sessionId, OrderStatusMessage.ready(merchantUid));
            log.info("[OrderConsumer] 주문 성공 → merchantUid: {}", merchantUid);

        } catch (IllegalArgumentException e) {
            log.warn("[OrderConsumer] 주문 실패 → {}", e.getMessage());
            redisPublisher.publish(sessionId, OrderStatusMessage.soldOut());
        } catch (Exception e) {
            log.error("[OrderConsumer] 주문 예외 → sessionId: {}", sessionId, e);
            redisPublisher.publish(sessionId, OrderStatusMessage.soldOut());
        }
    }
}