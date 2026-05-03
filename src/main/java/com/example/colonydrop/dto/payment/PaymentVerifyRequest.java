package com.example.colonydrop.dto.payment;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PaymentVerifyRequest {
    private String impUid;   //포트원 결제 번호 부분
    private String merchantUid;   // 내서버 주문번호
}
