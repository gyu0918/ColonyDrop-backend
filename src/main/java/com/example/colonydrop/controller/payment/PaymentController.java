package com.example.colonydrop.controller.payment;


import com.example.colonydrop.dto.payment.PaymentRefundRequest;
import com.example.colonydrop.dto.payment.PaymentVerifyRequest;
import com.example.colonydrop.service.payment.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payment")
public class PaymentController {

    // PaymentService 주입
    private final PaymentService paymentService;

    // 결제 검증 API
    // POST /api/payment/verify
    @PostMapping("/verify")
    public ResponseEntity<?> verify(@RequestBody PaymentVerifyRequest request) {

        // 1. PaymentService.verifyPayment 호출
        // 2. 성공 → ResponseEntity.ok("결제 완료")
        // 3. 예외 → ResponseEntity.badRequest().body(에러메시지)
        try{
            paymentService.verifyPayment(request);
            return ResponseEntity.ok("결제 완료");
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 환불 API
    // POST /api/payment/refund
    @PostMapping("/refund")
    public ResponseEntity<?> refund(@RequestBody PaymentRefundRequest request) {

        // 1. PaymentService.refundPayment 호출
        // 2. 성공 → ResponseEntity.ok("환불 완료")
        // 3. 예외 → ResponseEntity.badRequest().body(에러메시지)
        try{
            paymentService.refundPayment(request);
            return ResponseEntity.ok("환불 완료");
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    // POST /api/payment/webhook
    @PostMapping("/webhook")
    public ResponseEntity<?> webhook(@RequestBody Map<String, String> payload) {
        try {
            String impUid = payload.get("imp_uid");
            String merchantUid = payload.get("merchant_uid");
            String status = payload.get("status");

            log.info("웹훅 수신 - impUid: {}, merchantUid: {}, status: {}",
                    impUid, merchantUid, status);

            paymentService.processWebhook(impUid, merchantUid, status);
            return ResponseEntity.ok("OK");
        } catch (Exception e) {
            log.error("웹훅 처리 실패: {}", e.getMessage());
            // 포트원은 OK가 아니면 재시도함 → 500 반환
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

}
