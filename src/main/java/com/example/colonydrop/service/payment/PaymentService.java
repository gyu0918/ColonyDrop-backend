//package com.example.colonydrop.service.payment;
//
//import com.example.colonydrop.dto.payment.PaymentRefundRequest;
//import com.example.colonydrop.dto.payment.PaymentVerifyRequest;
//import com.example.colonydrop.entity.order.Order;
//import com.example.colonydrop.repository.order.OrderRepository;
//import com.siot.IamportRestClient.IamportClient;
//import com.siot.IamportRestClient.request.CancelData;
//import com.siot.IamportRestClient.response.Payment;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.DeserializationFeature;
//import com.fasterxml.jackson.databind.JsonNode;
//
//@Service
//@Slf4j
//@RequiredArgsConstructor
//public class PaymentService {
//
//    private final OrderRepository orderRepository;
//    private final IamportClient iamportClient;
//
//    @Transactional
//    public void verifyPayment(PaymentVerifyRequest paymentVerifyRequest) throws Exception {
//
//        log.info("verify impUid: {}", paymentVerifyRequest.getImpUid());
//        log.info("verify merchantUid: {}", paymentVerifyRequest.getMerchantUid());
//
//        // 1. DB에서 주문 먼저 조회
//        Order order = orderRepository.findByMerchantUid(paymentVerifyRequest.getMerchantUid())
//                .orElseThrow(() -> new IllegalArgumentException("주문을 찾을 수 없습니다."));
//
//        log.info("verify 주문 찾음: {}, status: {}", paymentVerifyRequest.getMerchantUid(), order.getStatus());
//
//        // 웹훅이 이미 PAID 처리한 경우 → 성공으로 반환
//        if ("PAID".equals(order.getStatus())) {
//            return;
//        }
//
//        // PENDING 외 다른 상태 (CANCELLED 등) → 에러
//        if (!"PENDING".equals(order.getStatus())) {
//            throw new IllegalArgumentException("이미 처리된 주문입니다.");
//        }
//
//        log.info("포트원 API 호출 시작");
//        // 2. merchantUid로 포트원 API 조회 (재시도 포함)
//        Payment payment = getPaymentWithRetry(paymentVerifyRequest.getImpUid(), paymentVerifyRequest.getMerchantUid());
//        log.info("포트원 API 성공: {}", payment.getStatus());
//
//        if (payment == null) {
//            throw new IllegalArgumentException("결제 정보가 존재하지 않습니다.");
//        }
//
//        // 3. 결제 금액 검증
//        if (payment.getAmount().compareTo(order.getTotalPrice()) != 0) {
//            CancelData cancelData = new CancelData(paymentVerifyRequest.getImpUid(), true);
//            cancelData.setReason("금액 위변조 감지");
//            iamportClient.cancelPaymentByImpUid(cancelData);
//            throw new IllegalStateException("결제 금액이 일치하지 않습니다.");
//        }
//
//        // 4. 결제 상태 확인
//        if (!"paid".equals(payment.getStatus())) {
//            throw new IllegalArgumentException("결제 실패!!");
//        }
//
//        // 5. 주문 상태 PAID로 변경
//        order.setImpUid(payment.getImpUid());
//        order.setStatus("PAID");
//        order.setPaidAt(LocalDateTime.now());
//        order.getItem().setStatus("SOLD");
//
//        orderRepository.save(order);
//    }
//
//    @Transactional
//    public void refundPayment(PaymentRefundRequest paymentRefundRequest) throws Exception {
//
//        Order order = orderRepository.findByMerchantUid(paymentRefundRequest.getMerchantUid())
//                .orElseThrow(() -> new IllegalArgumentException("주문을 찾을수 없습니다."));
//
//        // ✅ 배송중/배송완료 상태면 환불 불가
//        if ("SHIPPING".equals(order.getStatus())) {
//            throw new IllegalArgumentException("배송 중인 주문은 환불이 불가합니다.");
//        }
//        if ("DELIVERED".equals(order.getStatus())) {
//            throw new IllegalArgumentException("배송 완료된 주문은 환불이 불가합니다.");
//        }
//
//        // ✅ PAID 상태만 환불 가능
//        if (!"PAID".equals(order.getStatus())) {
//            throw new IllegalArgumentException("결제 완료 상태의 주문만 환불 가능합니다.");
//        }
//
////        if (!"PAID".equals(order.getStatus())) {
////            throw new IllegalArgumentException("환불 상태가 아닙니다 결제를 하지 않으셨습니다.");
////        }
//
//        BigDecimal remainAmount = order.getTotalPrice().subtract(order.getRefundedAmount());
//
//        if (paymentRefundRequest.getRefundAmount().compareTo(remainAmount) > 0) {
//            throw new IllegalStateException("환불 금액이 잔여 금액을 초과합니다.");
//        }
//
//        CancelData cancelData = new CancelData(
//                order.getImpUid(),
//                true,
//                paymentRefundRequest.getRefundAmount()
//        );
//        cancelData.setReason(paymentRefundRequest.getRefundReason());
//        iamportClient.cancelPaymentByImpUid(cancelData);
//
//        order.setRefundedAmount(
//                order.getRefundedAmount().add(paymentRefundRequest.getRefundAmount())
//        );
//
//        if (order.getRefundedAmount().compareTo(order.getTotalPrice()) == 0) {
//            order.setStatus("REFUNDED");
//            order.getItem().setStatus("SALE");
//        } else {
//            order.setStatus("PARTIALLY_REFUNDED");
//        }
//
//        orderRepository.save(order);
//    }
//
//    @Transactional
//    public void processWebhook(String impUid, String merchantUid, String status) throws Exception {
//
//        Order order = null;
//        for (int i = 0; i < 3; i++) {
//            log.info("주문 조회 시도 {}: {}", i + 1, merchantUid);
//            order = orderRepository.findByMerchantUid(merchantUid).orElse(null);
//            if (order != null) {
//                log.info("주문 찾음: {}, status: {}", merchantUid, order.getStatus());
//                break;
//            }
//            log.info("주문 못찾음 {}회차, 재시도...", i + 1);
//            Thread.sleep(500);
//        }
//        if (order == null) {
//            throw new IllegalArgumentException("존재하지 않는 결제정보입니다.");
//        }
//
//        if ("PAID".equals(order.getStatus()) || "CANCELLED".equals(order.getStatus())) {
//            log.info("이미 처리된 주문 웹훅 무시: {}", merchantUid);
//            return;
//        }
//
//        if (!"paid".equals(status)) {
//            log.info("결제 미완료 웹훅 무시: {}, status: {}", merchantUid, status);
//            return;
//        }
//
//        // merchantUid로 포트원 API 조회 (재시도 포함)
//        Payment payment = getPaymentWithRetry(impUid, merchantUid);
//        if (payment == null) {
//            throw new IllegalArgumentException("포트원에서 결제 정보를 찾을 수 없습니다.");
//        }
//
//        if (payment.getAmount().compareTo(order.getTotalPrice()) != 0) {
//            CancelData cancelData = new CancelData(impUid, true);
//            cancelData.setReason("금액 위변조 감지 (웹훅)");
//            iamportClient.cancelPaymentByImpUid(cancelData);
//            throw new IllegalStateException("결제 금액 불일치 - 자동 취소됨");
//        }
//
//        order.setImpUid(impUid);
//        order.setStatus("PAID");
//        order.setPaidAt(LocalDateTime.now());
//        order.getItem().setStatus("SOLD");
//        orderRepository.save(order);
//
//        log.info("웹훅으로 결제 완료 처리: {}", merchantUid);
//    }
//
//    // imp_uid 먼저 시도, 404면 merchant_uid로 재시도
//    private Payment getPaymentWithRetry(String impUid, String merchantUid) throws Exception {
//        try {
//            // imp_uid로 먼저 시도
//            Payment payment = iamportClient.paymentByImpUid(impUid).getResponse();
//            if (payment != null) return payment;
//        } catch (com.siot.IamportRestClient.exception.IamportResponseException e) {
//            if (e.getHttpStatusCode() == 404) {
//                log.warn("imp_uid 404, merchant_uid로 즉시 조회: {}", merchantUid);
//                // 재시도 없이 바로 merchant_uid로 조회
//                String token = iamportClient.getAuth().getResponse().getToken();
//                okhttp3.OkHttpClient client = new okhttp3.OkHttpClient();
//                okhttp3.Request request = new okhttp3.Request.Builder()
//                        .url("https://api.iamport.kr/payments/find/" + merchantUid)
//                        .header("Authorization", token)
//                        .build();
//                try (okhttp3.Response resp = client.newCall(request).execute()) {
//                    String body = resp.body().string();
//                    com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
//                    mapper.configure(com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//                    com.fasterxml.jackson.databind.JsonNode node = mapper.readTree(body);
//                    if (node.get("code").asInt() == 0) {
//                        return mapper.treeToValue(node.get("response"), Payment.class);
//                    }
//                }
//            }
//            throw e;
//        }
//        return null;
//    }
//
////    private Payment getPaymentWithRetry(String impUid, String merchantUid) throws Exception {
////        int[] delaysMs = {1000, 2000, 3000, 5000};
////        for (int i = 0; i <= delaysMs.length; i++) {
////            try {
////                Payment payment = iamportClient.paymentByImpUid(impUid).getResponse();
////                if (payment != null) return payment;
////            } catch (com.siot.IamportRestClient.exception.IamportResponseException e) {
////                if (e.getHttpStatusCode() == 404 && i < delaysMs.length) {
////                    log.warn("포트원 API 404, {}ms 후 재시도 ({}/{})", delaysMs[i], i + 1, delaysMs.length);
////                    Thread.sleep(delaysMs[i]);
////                    continue;
////                }
////                // 4회 재시도 후에도 404면 merchant_uid로 HTTP 직접 조회
////                if (e.getHttpStatusCode() == 404) {
////                    log.warn("imp_uid 조회 실패, merchant_uid로 조회 시도: {}", merchantUid);
////                    String token = iamportClient.getAuth().getResponse().getToken();
////                    okhttp3.OkHttpClient client = new okhttp3.OkHttpClient();
////                    okhttp3.Request request = new okhttp3.Request.Builder()
////                            .url("https://api.iamport.kr/payments/find/" + merchantUid)
////                            .header("Authorization", token)
////                            .build();
////                    try (okhttp3.Response resp = client.newCall(request).execute()) {
////                        String body = resp.body().string();
////                        com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
////                        mapper.configure(com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
////                        com.fasterxml.jackson.databind.JsonNode node = mapper.readTree(body);
////                        if (node.get("code").asInt() == 0) {
////                            return mapper.treeToValue(node.get("response"), Payment.class);
////                        }
////                    }
////                }
////                throw e;
////            }
////        }
////        return null;
////    }
//
//}
//
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
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentService {

    private final OrderRepository orderRepository;
    private final IamportClient iamportClient;
    private final RedissonClient redissonClient;   //  분산락용 추가

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

        // ===========================================================
        // 5. ✅ 재고 검증 + PAID 처리 (분산락으로 동시성 제어)
        //    여러 명이 동시에 결제 완료 시, 먼저 락을 잡은 사람만 SOLD 처리.
        //    이미 품절이면 방금 한 결제를 자동 취소(환불)한다.
        // ===========================================================
        Long itemId = order.getItem().getId();
        String lockKey = "item:lock:" + itemId;
        RLock lock = redissonClient.getLock(lockKey);

        boolean isLocked = false;
        try {
            isLocked = lock.tryLock(5, 3, TimeUnit.SECONDS);
            if (!isLocked) {
                // 락 획득 실패 → 결제 취소 후 에러
                cancelPaidPayment(payment.getImpUid(), "재고 확인 지연으로 인한 취소");
                throw new IllegalStateException("주문 처리가 지연되었습니다. 다시 시도해주세요.");
            }

            // 락 안에서 재고 상태 재조회 (최신 상태 확인)
            String itemStatus = order.getItem().getStatus();

            // 이미 팔렸으면 → 방금 한 결제를 취소(환불)하고 품절 처리
            if (!"SALE".equals(itemStatus)) {
                log.info("verify 재고 없음(이미 품절): itemId={}, status={}", itemId, itemStatus);
                cancelPaidPayment(payment.getImpUid(), "재고 소진으로 인한 자동 취소");
                order.setStatus("CANCELLED");
                orderRepository.save(order);
                throw new IllegalStateException("SOLD_OUT"); // 프론트에서 품절 메시지로 처리
            }

            // 재고 있음 → PAID 처리 + 상품 SOLD
            order.setImpUid(payment.getImpUid());
            order.setStatus("PAID");
            order.setPaidAt(LocalDateTime.now());
            order.getItem().setStatus("SOLD");
            orderRepository.save(order);

            log.info("verify 결제 완료 처리: {}", paymentVerifyRequest.getMerchantUid());

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("결제 처리 중 오류가 발생했습니다.");
        } finally {
            if (isLocked && lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
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

        // ===========================================================
        // ✅ 웹훅도 동일하게 재고 검증 (분산락)
        //    verify를 못 보낸 경우(창 꺼짐 등) 웹훅이 백업으로 처리.
        //    verify와 동시에 들어와도 같은 락을 공유하므로 안전.
        // ===========================================================
        Long itemId = order.getItem().getId();
        String lockKey = "item:lock:" + itemId;
        RLock lock = redissonClient.getLock(lockKey);

        boolean isLocked = false;
        try {
            isLocked = lock.tryLock(5, 3, TimeUnit.SECONDS);
            if (!isLocked) {
                throw new IllegalStateException("웹훅 처리 지연");
            }

            // 락 진입 후 상태 재확인 (verify가 먼저 처리했을 수 있음)
            if ("PAID".equals(order.getStatus()) || "CANCELLED".equals(order.getStatus())) {
                log.info("웹훅: 이미 처리됨, 무시: {}", merchantUid);
                return;
            }

            String itemStatus = order.getItem().getStatus();

            // 이미 품절 → 방금 결제 취소
            if (!"SALE".equals(itemStatus)) {
                log.info("웹훅 재고 없음(이미 품절): itemId={}", itemId);
                cancelPaidPayment(impUid, "재고 소진으로 인한 자동 취소 (웹훅)");
                order.setStatus("CANCELLED");
                orderRepository.save(order);
                return;
            }

            // 재고 있음 → PAID 처리
            order.setImpUid(impUid);
            order.setStatus("PAID");
            order.setPaidAt(LocalDateTime.now());
            order.getItem().setStatus("SOLD");
            orderRepository.save(order);

            log.info("웹훅으로 결제 완료 처리: {}", merchantUid);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("웹훅 처리 중 오류가 발생했습니다.");
        } finally {
            if (isLocked && lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }

    // ✅ 결제 취소(환불) 헬퍼 - 품절 시 방금 결제한 돈을 돌려줌
    private void cancelPaidPayment(String impUid, String reason) {
        try {
            CancelData cancelData = new CancelData(impUid, true);
            cancelData.setReason(reason);
            iamportClient.cancelPaymentByImpUid(cancelData);
            log.info("결제 자동 취소 완료: impUid={}, reason={}", impUid, reason);
        } catch (Exception e) {
            log.error("결제 자동 취소 실패: impUid={}, error={}", impUid, e.getMessage());
        }
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
}

//package com.example.colonydrop.service.payment;
//
//import com.example.colonydrop.dto.payment.PaymentRefundRequest;
//import com.example.colonydrop.dto.payment.PaymentVerifyRequest;
//import com.example.colonydrop.entity.order.Order;
//import com.example.colonydrop.repository.item.ItemRepository;
//import com.example.colonydrop.repository.order.OrderRepository;
//import com.siot.IamportRestClient.IamportClient;
//import com.siot.IamportRestClient.request.CancelData;
//import com.siot.IamportRestClient.response.Payment;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.redisson.api.RLock;
//import org.redisson.api.RedissonClient;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//import java.util.concurrent.TimeUnit;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.DeserializationFeature;
//import com.fasterxml.jackson.databind.JsonNode;
//
//@Service
//@Slf4j
//@RequiredArgsConstructor
//public class PaymentService {
//
//    private final OrderRepository orderRepository;
//    private final IamportClient iamportClient;
//    private final RedissonClient redissonClient;   //  분산락용 추가
//    private final ItemRepository itemRepository;   // ✅ 추가 - 조건부 UPDATE용
//
//    @Transactional
//    public void verifyPayment(PaymentVerifyRequest paymentVerifyRequest) throws Exception {
//
//        log.info("verify impUid: {}", paymentVerifyRequest.getImpUid());
//        log.info("verify merchantUid: {}", paymentVerifyRequest.getMerchantUid());
//
//        // 1. DB에서 주문 먼저 조회
//        Order order = orderRepository.findByMerchantUid(paymentVerifyRequest.getMerchantUid())
//                .orElseThrow(() -> new IllegalArgumentException("주문을 찾을 수 없습니다."));
//
//        log.info("verify 주문 찾음: {}, status: {}", paymentVerifyRequest.getMerchantUid(), order.getStatus());
//
//        // 웹훅이 이미 PAID 처리한 경우 → 성공으로 반환
//        if ("PAID".equals(order.getStatus())) {
//            return;
//        }
//
//        // PENDING 외 다른 상태 (CANCELLED 등) → 에러
//        if (!"PENDING".equals(order.getStatus())) {
//            throw new IllegalArgumentException("이미 처리된 주문입니다.");
//        }
//
//        log.info("포트원 API 호출 시작");
//        // 2. merchantUid로 포트원 API 조회 (재시도 포함)
//        Payment payment = getPaymentWithRetry(paymentVerifyRequest.getImpUid(), paymentVerifyRequest.getMerchantUid());
//        log.info("포트원 API 성공: {}", payment.getStatus());
//
//        if (payment == null) {
//            throw new IllegalArgumentException("결제 정보가 존재하지 않습니다.");
//        }
//
//        // 3. 결제 금액 검증
//        if (payment.getAmount().compareTo(order.getTotalPrice()) != 0) {
//            CancelData cancelData = new CancelData(paymentVerifyRequest.getImpUid(), true);
//            cancelData.setReason("금액 위변조 감지");
//            iamportClient.cancelPaymentByImpUid(cancelData);
//            throw new IllegalStateException("결제 금액이 일치하지 않습니다.");
//        }
//
//        // 4. 결제 상태 확인
//        if (!"paid".equals(payment.getStatus())) {
//            throw new IllegalArgumentException("결제 실패!!");
//        }
//
//        // ===========================================================
//        // 5. ✅ 재고 검증 + PAID 처리 (분산락 + 조건부 UPDATE로 동시성 제어)
//        //    여러 명이 동시에 결제 완료 시, 먼저 락을 잡은 사람만 SOLD 처리.
//        //    이미 품절이면 방금 한 결제를 자동 취소(환불)한다.
//        //    ✅ stale read 방지: 영속성 컨텍스트 캐시 대신 DB 조건부 UPDATE로 판단
//        // ===========================================================
//        Long itemId = order.getItem().getId();
//        String lockKey = "item:lock:" + itemId;
//        RLock lock = redissonClient.getLock(lockKey);
//
//        boolean isLocked = false;
//        try {
//            isLocked = lock.tryLock(5, 3, TimeUnit.SECONDS);
//            if (!isLocked) {
//                // 락 획득 실패 → 결제 취소 후 에러
//                cancelPaidPayment(payment.getImpUid(), "재고 확인 지연으로 인한 취소");
//                throw new IllegalStateException("주문 처리가 지연되었습니다. 다시 시도해주세요.");
//            }
//            // ✅ 추가: 락 안에서 최신 order 상태 확인
//            String freshOrderStatus = orderRepository.findStatusById(order.getId());
//            if (!"PENDING".equals(freshOrderStatus)) {
//                // 이미 webhook이 PAID 처리했거나, 이미 CANCELLED(품절) 처리됨
//                log.info("verify: 이미 처리된 주문(최신상태={}), 무시: {}", freshOrderStatus, paymentVerifyRequest.getMerchantUid());
//                return; // 성공으로 처리 (프론트에 "결제 완료" 응답)
//            }
//
//            // 락 안에서 DB에 조건부 UPDATE 시도 (SALE → SOLD, 영향row=1이면 성공)
//            int updated = itemRepository.markAsSoldIfAvailable(itemId);
//
//            // 이미 팔렸으면 → 방금 한 결제를 취소(환불)하고 품절 처리
//            if (updated == 0) {
//                log.info("verify 재고 없음(이미 품절): itemId={}", itemId);
//                cancelPaidPayment(payment.getImpUid(), "재고 소진으로 인한 자동 취소");
//                order.setStatus("CANCELLED");
//                orderRepository.save(order);
//                throw new IllegalStateException("SOLD_OUT"); // 프론트에서 품절 메시지로 처리
//            }
//
//            // 재고 있음 → PAID 처리 + 상품 SOLD
//            order.setImpUid(payment.getImpUid());
//            order.setStatus("PAID");
//            order.setPaidAt(LocalDateTime.now());
//            order.getItem().setStatus("SOLD"); // 메모리상 엔티티도 동기화 (DB는 위 UPDATE에서 이미 변경됨)
//            orderRepository.save(order);
//
//            log.info("verify 결제 완료 처리: {}", paymentVerifyRequest.getMerchantUid());
//
//        } catch (InterruptedException e) {
//            Thread.currentThread().interrupt();
//            throw new IllegalStateException("결제 처리 중 오류가 발생했습니다.");
//        } finally {
//            if (isLocked && lock.isHeldByCurrentThread()) {
//                lock.unlock();
//            }
//        }
//    }
//
//    @Transactional
//    public void refundPayment(PaymentRefundRequest paymentRefundRequest) throws Exception {
//
//        Order order = orderRepository.findByMerchantUid(paymentRefundRequest.getMerchantUid())
//                .orElseThrow(() -> new IllegalArgumentException("주문을 찾을수 없습니다."));
//
//        // ✅ 배송중/배송완료 상태면 환불 불가
//        if ("SHIPPING".equals(order.getStatus())) {
//            throw new IllegalArgumentException("배송 중인 주문은 환불이 불가합니다.");
//        }
//        if ("DELIVERED".equals(order.getStatus())) {
//            throw new IllegalArgumentException("배송 완료된 주문은 환불이 불가합니다.");
//        }
//
//        // ✅ PAID 상태만 환불 가능
//        if (!"PAID".equals(order.getStatus())) {
//            throw new IllegalArgumentException("결제 완료 상태의 주문만 환불 가능합니다.");
//        }
//
//        BigDecimal remainAmount = order.getTotalPrice().subtract(order.getRefundedAmount());
//
//        if (paymentRefundRequest.getRefundAmount().compareTo(remainAmount) > 0) {
//            throw new IllegalStateException("환불 금액이 잔여 금액을 초과합니다.");
//        }
//
//        CancelData cancelData = new CancelData(
//                order.getImpUid(),
//                true,
//                paymentRefundRequest.getRefundAmount()
//        );
//        cancelData.setReason(paymentRefundRequest.getRefundReason());
//        iamportClient.cancelPaymentByImpUid(cancelData);
//
//        order.setRefundedAmount(
//                order.getRefundedAmount().add(paymentRefundRequest.getRefundAmount())
//        );
//
//        if (order.getRefundedAmount().compareTo(order.getTotalPrice()) == 0) {
//            order.setStatus("REFUNDED");
//            order.getItem().setStatus("SALE");
//        } else {
//            order.setStatus("PARTIALLY_REFUNDED");
//        }
//
//        orderRepository.save(order);
//    }
//
//    @Transactional
//    public void processWebhook(String impUid, String merchantUid, String status) throws Exception {
//
//        Order order = null;
//        for (int i = 0; i < 3; i++) {
//            log.info("주문 조회 시도 {}: {}", i + 1, merchantUid);
//            order = orderRepository.findByMerchantUid(merchantUid).orElse(null);
//            if (order != null) {
//                log.info("주문 찾음: {}, status: {}", merchantUid, order.getStatus());
//                break;
//            }
//            log.info("주문 못찾음 {}회차, 재시도...", i + 1);
//            Thread.sleep(500);
//        }
//        if (order == null) {
//            throw new IllegalArgumentException("존재하지 않는 결제정보입니다.");
//        }
//
//        if ("PAID".equals(order.getStatus()) || "CANCELLED".equals(order.getStatus())) {
//            log.info("이미 처리된 주문 웹훅 무시: {}", merchantUid);
//            return;
//        }
//
//        if (!"paid".equals(status)) {
//            log.info("결제 미완료 웹훅 무시: {}, status: {}", merchantUid, status);
//            return;
//        }
//
//        // merchantUid로 포트원 API 조회 (재시도 포함)
//        Payment payment = getPaymentWithRetry(impUid, merchantUid);
//        if (payment == null) {
//            throw new IllegalArgumentException("포트원에서 결제 정보를 찾을 수 없습니다.");
//        }
//
//        if (payment.getAmount().compareTo(order.getTotalPrice()) != 0) {
//            CancelData cancelData = new CancelData(impUid, true);
//            cancelData.setReason("금액 위변조 감지 (웹훅)");
//            iamportClient.cancelPaymentByImpUid(cancelData);
//            throw new IllegalStateException("결제 금액 불일치 - 자동 취소됨");
//        }
//
//        // ===========================================================
//        // ✅ 웹훅도 동일하게 재고 검증 (분산락 + 조건부 UPDATE)
//        //    verify를 못 보낸 경우(창 꺼짐 등) 웹훅이 백업으로 처리.
//        //    verify와 동시에 들어와도 같은 락을 공유하므로 안전.
//        //    ✅ stale read 방지: DB 조건부 UPDATE로 판단
//        // ===========================================================
//        Long itemId = order.getItem().getId();
//        String lockKey = "item:lock:" + itemId;
//        RLock lock = redissonClient.getLock(lockKey);
//
//        boolean isLocked = false;
//        try {
//            isLocked = lock.tryLock(5, 3, TimeUnit.SECONDS);
//            if (!isLocked) {
//                throw new IllegalStateException("웹훅 처리 지연");
//            }
//
////            // 락 진입 후 상태 재확인 (verify가 먼저 처리했을 수 있음)
////            if ("PAID".equals(order.getStatus()) || "CANCELLED".equals(order.getStatus())) {
////                log.info("웹훅: 이미 처리됨, 무시: {}", merchantUid);
////                return;
////            }
//
//            // ✅ 변경: 락 안에서 최신 order 상태 확인
//            String freshOrderStatus = orderRepository.findStatusById(order.getId());
//            if (!"PENDING".equals(freshOrderStatus)) {
//                log.info("웹훅: 이미 처리된 주문(최신상태={}), 무시: {}", freshOrderStatus, merchantUid);
//                return; // PAID면 verify가 이미 처리함, CANCELLED면 이미 환불됨 → 둘 다 추가 작업 불필요
//            }
//
//            // 락 안에서 DB에 조건부 UPDATE 시도 (SALE → SOLD, 영향row=1이면 성공)
//            int updated = itemRepository.markAsSoldIfAvailable(itemId);
//
//            // 이미 품절 → 방금 결제 취소
//            if (updated == 0) {
//                log.info("웹훅 재고 없음(이미 품절): itemId={}", itemId);
//                cancelPaidPayment(impUid, "재고 소진으로 인한 자동 취소 (웹훅)");
//                order.setStatus("CANCELLED");
//                orderRepository.save(order);
//                return;
//            }
//
//            // 재고 있음 → PAID 처리
//            order.setImpUid(impUid);
//            order.setStatus("PAID");
//            order.setPaidAt(LocalDateTime.now());
//            order.getItem().setStatus("SOLD"); // 메모리상 엔티티도 동기화
//            orderRepository.save(order);
//
//            log.info("웹훅으로 결제 완료 처리: {}", merchantUid);
//
//        } catch (InterruptedException e) {
//            Thread.currentThread().interrupt();
//            throw new IllegalStateException("웹훅 처리 중 오류가 발생했습니다.");
//        } finally {
//            if (isLocked && lock.isHeldByCurrentThread()) {
//                lock.unlock();
//            }
//        }
//    }
//
//    // ✅ 결제 취소(환불) 헬퍼 - 품절 시 방금 결제한 돈을 돌려줌
//    private void cancelPaidPayment(String impUid, String reason) {
//        try {
//            CancelData cancelData = new CancelData(impUid, true);
//            cancelData.setReason(reason);
//            iamportClient.cancelPaymentByImpUid(cancelData);
//            log.info("결제 자동 취소 완료: impUid={}, reason={}", impUid, reason);
//        } catch (Exception e) {
//            log.error("결제 자동 취소 실패: impUid={}, error={}", impUid, e.getMessage());
//        }
//    }
//
//    // imp_uid 먼저 시도, 404면 merchant_uid로 재시도
//    private Payment getPaymentWithRetry(String impUid, String merchantUid) throws Exception {
//        try {
//            // imp_uid로 먼저 시도
//            Payment payment = iamportClient.paymentByImpUid(impUid).getResponse();
//            if (payment != null) return payment;
//        } catch (com.siot.IamportRestClient.exception.IamportResponseException e) {
//            if (e.getHttpStatusCode() == 404) {
//                log.warn("imp_uid 404, merchant_uid로 즉시 조회: {}", merchantUid);
//                String token = iamportClient.getAuth().getResponse().getToken();
//                okhttp3.OkHttpClient client = new okhttp3.OkHttpClient();
//                okhttp3.Request request = new okhttp3.Request.Builder()
//                        .url("https://api.iamport.kr/payments/find/" + merchantUid)
//                        .header("Authorization", token)
//                        .build();
//                try (okhttp3.Response resp = client.newCall(request).execute()) {
//                    String body = resp.body().string();
//                    com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
//                    mapper.configure(com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//                    com.fasterxml.jackson.databind.JsonNode node = mapper.readTree(body);
//                    if (node.get("code").asInt() == 0) {
//                        return mapper.treeToValue(node.get("response"), Payment.class);
//                    }
//                }
//            }
//            throw e;
//        }
//        return null;
//    }
//}