package com.example.colonydrop.service.payment;

import com.example.colonydrop.dto.payment.PaymentRefundRequest;
import com.example.colonydrop.dto.payment.PaymentVerifyRequest;
import com.example.colonydrop.entity.order.Order;
import com.example.colonydrop.repository.order.OrderRepository;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.request.CancelData;
import com.siot.IamportRestClient.response.Payment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentService {

    private final OrderRepository orderRepository;
    private final IamportClient iamportClient;

    @Transactional
    public void verifyPayment(PaymentVerifyRequest paymentVerifyRequest) throws Exception {

        log.info("verify impUid: {}", paymentVerifyRequest.getImpUid());
        log.info("verify merchantUid: {}", paymentVerifyRequest.getMerchantUid());

        // 1. DB에서 주문 먼저 조회
        Order order = orderRepository.findByMerchantUid(paymentVerifyRequest.getMerchantUid())
                .orElseThrow(() -> new IllegalArgumentException("주문을 찾을 수 없습니다."));

        log.info("verify 주문 찾음: {}, status: {}", paymentVerifyRequest.getMerchantUid(), order.getStatus());

        // 웹훅이 이미 PAID 처리한 경우 → 성공으로 반환
        if ("PAID".equals(order.getStatus())) {
            return;
        }

        // PENDING 외 다른 상태 (CANCELLED 등) → 에러
        if (!"PENDING".equals(order.getStatus())) {
            throw new IllegalArgumentException("이미 처리된 주문입니다.");
        }

        log.info("포트원 API 호출 시작");
        // 2. merchantUid로 포트원 API 조회 (재시도 포함)
        Payment payment = getPaymentWithRetry(paymentVerifyRequest.getImpUid(), paymentVerifyRequest.getMerchantUid());
        log.info("포트원 API 성공: {}", payment.getStatus());

        if (payment == null) {
            throw new IllegalArgumentException("결제 정보가 존재하지 않습니다.");
        }

        // 3. 결제 금액 검증
        if (payment.getAmount().compareTo(order.getTotalPrice()) != 0) {
            CancelData cancelData = new CancelData(paymentVerifyRequest.getImpUid(), true);
            cancelData.setReason("금액 위변조 감지");
            iamportClient.cancelPaymentByImpUid(cancelData);
            throw new IllegalStateException("결제 금액이 일치하지 않습니다.");
        }

        // 4. 결제 상태 확인
        if (!"paid".equals(payment.getStatus())) {
            throw new IllegalArgumentException("결제 실패!!");
        }

        // 5. 주문 상태 PAID로 변경
        order.setImpUid(payment.getImpUid());
        order.setStatus("PAID");
        order.setPaidAt(LocalDateTime.now());
        order.getItem().setStatus("SOLD");

        orderRepository.save(order);
    }

    @Transactional
    public void refundPayment(PaymentRefundRequest paymentRefundRequest) throws Exception {

        Order order = orderRepository.findByMerchantUid(paymentRefundRequest.getMerchantUid())
                .orElseThrow(() -> new IllegalArgumentException("주문을 찾을수 없습니다."));

        // ✅ 배송중/배송완료 상태면 환불 불가
        if ("SHIPPING".equals(order.getStatus())) {
            throw new IllegalArgumentException("배송 중인 주문은 환불이 불가합니다.");
        }
        if ("DELIVERED".equals(order.getStatus())) {
            throw new IllegalArgumentException("배송 완료된 주문은 환불이 불가합니다.");
        }

        // ✅ PAID 상태만 환불 가능
        if (!"PAID".equals(order.getStatus())) {
            throw new IllegalArgumentException("결제 완료 상태의 주문만 환불 가능합니다.");
        }

//        if (!"PAID".equals(order.getStatus())) {
//            throw new IllegalArgumentException("환불 상태가 아닙니다 결제를 하지 않으셨습니다.");
//        }

        BigDecimal remainAmount = order.getTotalPrice().subtract(order.getRefundedAmount());

        if (paymentRefundRequest.getRefundAmount().compareTo(remainAmount) > 0) {
            throw new IllegalStateException("환불 금액이 잔여 금액을 초과합니다.");
        }

        CancelData cancelData = new CancelData(
                order.getImpUid(),
                true,
                paymentRefundRequest.getRefundAmount()
        );
        cancelData.setReason(paymentRefundRequest.getRefundReason());
        iamportClient.cancelPaymentByImpUid(cancelData);

        order.setRefundedAmount(
                order.getRefundedAmount().add(paymentRefundRequest.getRefundAmount())
        );

        if (order.getRefundedAmount().compareTo(order.getTotalPrice()) == 0) {
            order.setStatus("REFUNDED");
            order.getItem().setStatus("SALE");
        } else {
            order.setStatus("PARTIALLY_REFUNDED");
        }

        orderRepository.save(order);
    }

    @Transactional
    public void processWebhook(String impUid, String merchantUid, String status) throws Exception {

        Order order = null;
        for (int i = 0; i < 3; i++) {
            log.info("주문 조회 시도 {}: {}", i + 1, merchantUid);
            order = orderRepository.findByMerchantUid(merchantUid).orElse(null);
            if (order != null) {
                log.info("주문 찾음: {}, status: {}", merchantUid, order.getStatus());
                break;
            }
            log.info("주문 못찾음 {}회차, 재시도...", i + 1);
            Thread.sleep(500);
        }
        if (order == null) {
            throw new IllegalArgumentException("존재하지 않는 결제정보입니다.");
        }

        if ("PAID".equals(order.getStatus()) || "CANCELLED".equals(order.getStatus())) {
            log.info("이미 처리된 주문 웹훅 무시: {}", merchantUid);
            return;
        }

        if (!"paid".equals(status)) {
            log.info("결제 미완료 웹훅 무시: {}, status: {}", merchantUid, status);
            return;
        }

        // merchantUid로 포트원 API 조회 (재시도 포함)
        Payment payment = getPaymentWithRetry(impUid, merchantUid);
        if (payment == null) {
            throw new IllegalArgumentException("포트원에서 결제 정보를 찾을 수 없습니다.");
        }

        if (payment.getAmount().compareTo(order.getTotalPrice()) != 0) {
            CancelData cancelData = new CancelData(impUid, true);
            cancelData.setReason("금액 위변조 감지 (웹훅)");
            iamportClient.cancelPaymentByImpUid(cancelData);
            throw new IllegalStateException("결제 금액 불일치 - 자동 취소됨");
        }

        order.setImpUid(impUid);
        order.setStatus("PAID");
        order.setPaidAt(LocalDateTime.now());
        order.getItem().setStatus("SOLD");
        orderRepository.save(order);

        log.info("웹훅으로 결제 완료 처리: {}", merchantUid);
    }

    // imp_uid 먼저 시도, 404면 merchant_uid로 재시도
    private Payment getPaymentWithRetry(String impUid, String merchantUid) throws Exception {
        try {
            // imp_uid로 먼저 시도
            Payment payment = iamportClient.paymentByImpUid(impUid).getResponse();
            if (payment != null) return payment;
        } catch (com.siot.IamportRestClient.exception.IamportResponseException e) {
            if (e.getHttpStatusCode() == 404) {
                log.warn("imp_uid 404, merchant_uid로 즉시 조회: {}", merchantUid);
                // 재시도 없이 바로 merchant_uid로 조회
                String token = iamportClient.getAuth().getResponse().getToken();
                okhttp3.OkHttpClient client = new okhttp3.OkHttpClient();
                okhttp3.Request request = new okhttp3.Request.Builder()
                        .url("https://api.iamport.kr/payments/find/" + merchantUid)
                        .header("Authorization", token)
                        .build();
                try (okhttp3.Response resp = client.newCall(request).execute()) {
                    String body = resp.body().string();
                    com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
                    mapper.configure(com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                    com.fasterxml.jackson.databind.JsonNode node = mapper.readTree(body);
                    if (node.get("code").asInt() == 0) {
                        return mapper.treeToValue(node.get("response"), Payment.class);
                    }
                }
            }
            throw e;
        }
        return null;
    }

//    private Payment getPaymentWithRetry(String impUid, String merchantUid) throws Exception {
//        int[] delaysMs = {1000, 2000, 3000, 5000};
//        for (int i = 0; i <= delaysMs.length; i++) {
//            try {
//                Payment payment = iamportClient.paymentByImpUid(impUid).getResponse();
//                if (payment != null) return payment;
//            } catch (com.siot.IamportRestClient.exception.IamportResponseException e) {
//                if (e.getHttpStatusCode() == 404 && i < delaysMs.length) {
//                    log.warn("포트원 API 404, {}ms 후 재시도 ({}/{})", delaysMs[i], i + 1, delaysMs.length);
//                    Thread.sleep(delaysMs[i]);
//                    continue;
//                }
//                // 4회 재시도 후에도 404면 merchant_uid로 HTTP 직접 조회
//                if (e.getHttpStatusCode() == 404) {
//                    log.warn("imp_uid 조회 실패, merchant_uid로 조회 시도: {}", merchantUid);
//                    String token = iamportClient.getAuth().getResponse().getToken();
//                    okhttp3.OkHttpClient client = new okhttp3.OkHttpClient();
//                    okhttp3.Request request = new okhttp3.Request.Builder()
//                            .url("https://api.iamport.kr/payments/find/" + merchantUid)
//                            .header("Authorization", token)
//                            .build();
//                    try (okhttp3.Response resp = client.newCall(request).execute()) {
//                        String body = resp.body().string();
//                        com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
//                        mapper.configure(com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//                        com.fasterxml.jackson.databind.JsonNode node = mapper.readTree(body);
//                        if (node.get("code").asInt() == 0) {
//                            return mapper.treeToValue(node.get("response"), Payment.class);
//                        }
//                    }
//                }
//                throw e;
//            }
//        }
//        return null;
//    }

}