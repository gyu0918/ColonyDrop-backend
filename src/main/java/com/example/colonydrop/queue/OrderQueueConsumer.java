package com.example.colonydrop.queue;

import com.example.colonydrop.config.rabbitmq.RabbitMQConfig;
import com.example.colonydrop.config.redisConfig.RedisPublisher;
import com.example.colonydrop.dto.order.OrderQueueMessage;
import com.example.colonydrop.dto.order.OrderStatusMessage;
import com.example.colonydrop.entity.member.Member;
import com.example.colonydrop.repository.member.MemberRepository;
import com.example.colonydrop.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderQueueConsumer {

    private final OrderService orderService;
    private final MemberRepository memberRepository;
    private final RedisPublisher redisPublisher;

    @RabbitListener(queues = RabbitMQConfig.ORDER_QUEUE, concurrency = "1")
    public void consume(OrderQueueMessage message) {
        String sessionId = message.getSessionId();
        log.info("큐 처리 시작 → sessionId: {}, memberId: {}",
                sessionId, message.getMemberId());

        // 처리 중 상태 전송
        redisPublisher.publish(sessionId, OrderStatusMessage.processing());

        try {
            // Member 조회
            Member member = memberRepository.findByMemberId(message.getMemberId())
                    .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));

            // 주문 생성
            String merchantUid = orderService.createOrderFromQueue(member, message);

            // 성공 → READY 전송
            redisPublisher.publish(sessionId, OrderStatusMessage.ready(merchantUid));
            log.info("주문 생성 성공 → merchantUid: {}", merchantUid);

        } catch (IllegalArgumentException e) {
            log.warn("주문 실패 → sessionId: {}, 사유: {}", sessionId, e.getMessage());
            redisPublisher.publish(sessionId, OrderStatusMessage.soldOut());

        } catch (Exception e) {
            log.error("주문 처리 중 예외 → sessionId: {}", sessionId, e);
            redisPublisher.publish(sessionId, OrderStatusMessage.soldOut());
        }
    }
}