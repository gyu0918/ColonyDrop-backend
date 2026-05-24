//package com.example.colonydrop.queue;
//
//import com.example.colonydrop.config.rabbitmq.RabbitMQConfig;
//import com.example.colonydrop.config.redisConfig.RedisPublisher;
//import com.example.colonydrop.dto.order.OrderQueueMessage;
//import com.example.colonydrop.dto.order.OrderStatusMessage;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.amqp.rabbit.core.RabbitAdmin;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.stereotype.Component;
//import tools.jackson.databind.ObjectMapper;
//
//
////결제하기 클릭 시
////주문 요청을 RabbitMQ 큐에 넣고
////즉시 대기 순번을 WebSocket으로 전송
//@Slf4j
////@Component
//@RequiredArgsConstructor
//public class OrderQueueProducer {
//
//    private final RabbitTemplate rabbitTemplate;
//    private final RedisPublisher redisPublisher;
//    private final RabbitAdmin rabbitAdmin;
//    private final ObjectMapper objectMapper;
//
//    public void enqueue(OrderQueueMessage message) {
//        // 현재 큐 대기 수 조회
//        Integer queueCount = 0;
//        try {
//            //현재 큐에 쌓인 메시지 수 조회
//            //→ 대기 순번 계산에 사용
//            //예시:
//            //큐에 3명 대기 중
//            //→ 내가 4번째
//            var props = rabbitAdmin.getQueueProperties(RabbitMQConfig.ORDER_QUEUE);
//            if (props != null) {
////                queueCount = (Integer) props.get("QUEUE_MESSAGE_COUNT");
//                Object count = props.get("QUEUE_MESSAGE_COUNT");
//                queueCount = count != null ? ((Long) count).intValue() : 0;
//            }
//        } catch (Exception e) {
//            log.warn("큐 사이즈 조회 실패, 기본값 사용", e);
//        }
//
//        //대기 순번 계산
//        //큐 메시지 수 + 1 = 내 순번
//        //queueCount가 null이면 0으로 처리
//        //→ 내가 첫 번째
//        int position = (queueCount == null ? 0 : queueCount) + 1;
//
//        // RabbitMQ 큐에 적재
//        // Exchange: order-exchange
//        // RoutingKey: order-routing
//        // Message: OrderQueueMessage 객체
//        //
//        //→ Exchange가 라우팅 키 보고
//        //→ order-queue로 전달
////        rabbitTemplate.convertAndSend(
////                RabbitMQConfig.ORDER_EXCHANGE,
////                RabbitMQConfig.ORDER_ROUTING,
////                message
////        );
//        // 큐에 적재 시
//        String messageJson = objectMapper.writeValueAsString(message);
//        rabbitTemplate.convertAndSend(
//                RabbitMQConfig.ORDER_EXCHANGE,
//                RabbitMQConfig.ORDER_ROUTING,
//                messageJson
//        );
//
//        // 즉시 대기 순번 전송
//        redisPublisher.publish(message.getSessionId(),
//                OrderStatusMessage.waiting(position));
//
//        log.info("큐 적재 완료 → sessionId: {}, position: {}",
//                message.getSessionId(), position);
//    }
//}
