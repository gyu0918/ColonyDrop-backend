package com.example.colonydrop.controller.order;


import com.example.colonydrop.config.security.auth.PrincipalDetails;
import com.example.colonydrop.dto.order.OrderResponse;
import com.example.colonydrop.dto.payment.OrderCreateRequest;
import com.example.colonydrop.entity.member.Member;
import com.example.colonydrop.entity.order.Order;
import com.example.colonydrop.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {
    // OrderService 주입
    private final OrderService orderService;


    // 주문 생성 API
    // POST /api/orders
    // 파라미터: OrderCreateRequest, 현재 로그인한 Member
    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody OrderCreateRequest request,
                                         @AuthenticationPrincipal PrincipalDetails principalDetails) {

        // 임시 테스트용 - 토큰 없을 때 처리
        if (principalDetails == null) {
            return ResponseEntity.ok("테스트 모드: 주문 API 정상 동작");
        }

        Member member =  principalDetails.getUser();
        // 1. OrderService.createOrder 호출
        Order order = orderService.createOrder(member, request);
        // 2. 생성된 Order 반환
        return ResponseEntity.ok(order);
    }

    // 주문 내역 조회 API
    // GET /api/orders/my
    @GetMapping("/my")
    public ResponseEntity<?> getMyOrders(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        if (principalDetails == null) {
            return ResponseEntity.status(401).body("로그인이 필요합니다.");
        }
        String memberId = principalDetails.getUsername();
        List<OrderResponse> orders = orderService.getMyOrders(memberId);
        return ResponseEntity.ok(orders);
    }

    // 주문 상세 조회 API
    // GET /api/orders/{merchantUid}
    @GetMapping("/{merchantUid}")
    public ResponseEntity<?> getOrderDetail(@PathVariable String merchantUid,
                                            @AuthenticationPrincipal PrincipalDetails principalDetails) {
        if (principalDetails == null) {
            return ResponseEntity.status(401).body("로그인이 필요합니다.");
        }
        String memberId = principalDetails.getUsername();
        OrderResponse order = orderService.getOrderDetail(merchantUid, memberId);
        return ResponseEntity.ok(order);
    }

    //주문취소
    @PostMapping("/cancel")
    public ResponseEntity<?> cancelOrder(@RequestBody Map<String, String> body,
                                         @AuthenticationPrincipal PrincipalDetails principalDetails) {
        String merchantUid = body.get("merchantUid");
        String memberId = principalDetails.getUsername();
        orderService.cancelOrder(merchantUid, memberId);
        return ResponseEntity.ok().build();
    }


}
