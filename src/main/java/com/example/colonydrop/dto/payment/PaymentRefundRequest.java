package com.example.colonydrop.dto.payment;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class PaymentRefundRequest {
    private String merchantUid;     //환불할 주문 번호 부분
    private BigDecimal refundAmount;     //환불금액
    private String refundReason;     //환불 이유
}
