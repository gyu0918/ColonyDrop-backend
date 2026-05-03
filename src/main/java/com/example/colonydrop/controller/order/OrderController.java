package com.example.colonydrop.controller.order;


import com.example.colonydrop.config.security.auth.PrincipalDetails;
import com.example.colonydrop.dto.payment.OrderCreateRequest;
import com.example.colonydrop.entity.member.Member;
import com.example.colonydrop.entity.order.Order;
import com.example.colonydrop.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
