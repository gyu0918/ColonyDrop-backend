package com.example.colonydrop.service.payment;

import com.example.colonydrop.dto.payment.PaymentRefundRequest;
import com.example.colonydrop.dto.payment.PaymentVerifyRequest;
import com.example.colonydrop.entity.order.Order;
import com.example.colonydrop.repository.order.OrderRepository;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.request.CancelData;
import com.siot.IamportRestClient.response.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PaymentService {
    // 필요한 것들 주입 (IamportClient, OrderRepository)
    private final OrderRepository orderRepository;
    private final IamportClient iamportClient;

    // 결제 검증 메서드
    // 파라미터: PaymentVerifyRequest
    @Transactional
    public void verifyPayment(PaymentVerifyRequest paymentVerifyRequest) throws Exception {

        // 1. 포트원 API로 실제 결제 정보 조회
        Payment payment = iamportClient
                .paymentByImpUid(paymentVerifyRequest.getImpUid())
                .getResponse();

        //null 처리
        if(payment == null){
            throw new IllegalArgumentException("결제 정보가 존재하지 않습니다.");
        }

        // 2. DB에서 주문 조회 (merchantUid로)
        Order order = orderRepository.findByMerchantUid(paymentVerifyRequest
                .getMerchantUid()).orElseThrow(()-> new IllegalArgumentException("주문을 찾을수 없습니다."));

        // 3. 이미 처리된 주문인지 확인 (PENDING 상태인지)
        if (!"PENDING".equals(order.getStatus())) {
            throw new IllegalArgumentException("이미 처리된 주문입니다.");
        }

        // 4. 결제 금액 검증 (포트원 금액 vs DB 금액 비교)
        if (payment.getAmount().compareTo(order.getTotalPrice()) != 0) {
            // 금액 불일치 → 즉시 결제 취소
//            iamportClient.cancelPaymentByImpUid(paymentVerifyRequest.getImpUid(), "금액 위변조 감지");
            CancelData cancelData = new CancelData(
                    paymentVerifyRequest.getImpUid(),
                    true  // imp_uid 기준으로 취소
            );
            cancelData.setReason("금액 위변조 감지");
            iamportClient.cancelPaymentByImpUid(cancelData);
            throw new IllegalStateException("결제 금액이 일치하지 않습니다.");
        }  //    불일치 시 → 포트원 결제 즉시 취소 + 예외 발생


        // 5. 결제 상태 확인 (paid 인지)
        if (!"paid".equals(payment.getStatus())) {
            throw new IllegalArgumentException("결제 실패!!");
        }

        // 6. 주문 상태 PAID로 변경 + impUid 저장 + paidAt 저장
        order.setImpUid(payment.getImpUid());
        order.setStatus("PAID");
        order.setPaidAt(LocalDateTime.now());
        // 7. 상품 상태 SOLD로 변경
        order.getItem().setStatus("SOLD");

        orderRepository.save(order);

    }

    // 환불 메서드
    // 파라미터: PaymentRefundRequest
    @Transactional
    public void refundPayment(PaymentRefundRequest paymentRefundRequest) throws Exception {

        // 1. 주문 조회 (merchantUid로)
        Order order = orderRepository.findByMerchantUid(paymentRefundRequest.getMerchantUid())
                .orElseThrow(() -> new IllegalArgumentException("주문을 찾을수 없습니다."));

        // 2. PAID 상태인지 확인
        if (!"PAID".equals(order.getStatus())) {
            throw new IllegalArgumentException("환불 상태가 아닙니다 결제를 하지 않으셨습니다.");
        }

        // 3. 환불 금액이 잔여 금액 초과하지 않는지 확인
        //    잔여 금액 = totalPrice - refundedAmount
        BigDecimal  remainAmount = order.getTotalPrice().subtract(order.getRefundedAmount());

        if (paymentRefundRequest.getRefundAmount().compareTo(remainAmount) > 0) {
            throw new IllegalStateException("환불 금액이 잔여 금액을 초과합니다.");
        }

        // 4. 포트원 환불 API 호출 (CancelData 사용)
        CancelData cancelData = new CancelData(
                order.getImpUid(),
                true,
                paymentRefundRequest.getRefundAmount()
        );
        cancelData.setReason(paymentRefundRequest.getRefundReason());
        iamportClient.cancelPaymentByImpUid(cancelData);

        // 5. 환불 금액 업데이트
        order.setRefundedAmount(
                order.getRefundedAmount().add(paymentRefundRequest.getRefundAmount())
        );

        // 6. 전액 환불이면 REFUNDED + 상품 SALE로 복구
        //    부분 환불이면 PARTIALLY_REFUNDED
        if (order.getRefundedAmount().compareTo(order.getTotalPrice()) == 0) {
            order.setStatus("REFUNDED");
            order.getItem().setStatus("SALE"); // 상품 다시 판매 가능
        } else {
            order.setStatus("PARTIALLY_REFUNDED");
        }

        orderRepository.save(order);

    }

}
