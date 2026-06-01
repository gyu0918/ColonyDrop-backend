package com.example.colonydrop.controller.admin;

import com.example.colonydrop.entity.order.Order;
import com.example.colonydrop.repository.order.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/orders")
@RequiredArgsConstructor
public class AdminOrderController {

    private final OrderRepository orderRepository;

    // 전체 주문 조회
    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(orderRepository.findAllWithItemAndBuyer()); // ✅ 수정
    }

    // 주문 상태 변경
    @PatchMapping("/{merchantUid}/status")
    public ResponseEntity<Order> updateOrderStatus(
            @PathVariable String merchantUid,
            @RequestBody Map<String, String> body) {

        Order order = orderRepository.findByMerchantUid(merchantUid)
                .orElseThrow(() -> new RuntimeException("주문 없음"));
        order.setStatus(body.get("status"));
        return ResponseEntity.ok(orderRepository.save(order));
    }
}