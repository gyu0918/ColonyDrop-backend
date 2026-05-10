package com.example.colonydrop.dto.order;


import com.example.colonydrop.entity.order.Order;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class OrderResponse {
    private String merchantUid;
    private String itemTitle;
    private String itemImgUrl;
    private BigDecimal totalPrice;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime paidAt;
    // 상세 조회용 추가 필드
    private String buyerName;
    private String buyerTel;
    private String buyerAddr;
    private BigDecimal refundedAmount;

    public OrderResponse(Order order) {
        this.merchantUid = order.getMerchantUid();
        this.itemTitle = order.getItem().getTitle();
        this.itemImgUrl = order.getItem().getImgUrl();
        this.totalPrice = order.getTotalPrice();
        this.status = order.getStatus();
        this.createdAt = order.getCreatedAt();
        this.paidAt = order.getPaidAt();
        this.buyerName = order.getBuyerName();
        this.buyerTel = order.getBuyerTel();
        this.buyerAddr = order.getBuyerAddr();
        this.refundedAmount = order.getRefundedAmount();
    }
}