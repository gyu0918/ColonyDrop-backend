package com.example.colonydrop.service.order;


import com.example.colonydrop.entity.order.Order;
import com.example.colonydrop.repository.order.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


@Slf4j
@Component
@RequiredArgsConstructor
public class OrderScheduler {

    private final OrderRepository orderRepository;

    // 1분마다 실행
    @Scheduled(fixedDelay = 60000)
    @Transactional
    public void cancelExpiredOrders() {

        // 15분 이상 PENDING/RESERVED 상태인 주문 조회
        LocalDateTime expireTime = LocalDateTime.now().minusMinutes(15);

        List<Order> expiredOrders = orderRepository
                .findExpiredOrders(expireTime);

        for (Order order : expiredOrders) {
            log.info("만료 주문 취소: {}", order.getMerchantUid());

            // 상품 SALE로 복구
            order.getItem().setStatus("SALE");

            // 주문 CANCELLED로 변경
            order.setStatus("CANCELLED");
        }

        if (!expiredOrders.isEmpty()) {
            log.info("총 {}건 만료 주문 처리 완료", expiredOrders.size());
        }
    }
}
