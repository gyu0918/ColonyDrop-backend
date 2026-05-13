package com.example.colonydrop.dto.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderStatusMessage {
    private String status;
    private Integer queuePosition;
    private String merchantUid;
    private String message;

    public static OrderStatusMessage waiting(int position) {
        return new OrderStatusMessage("WAITING", position, null,
                "현재 " + position + "번째 대기 중입니다.");
    }

    public static OrderStatusMessage processing() {
        return new OrderStatusMessage("PROCESSING", null, null,
                "결제 처리 중입니다...");
    }

    public static OrderStatusMessage ready(String merchantUid) {
        return new OrderStatusMessage("READY", null, merchantUid,
                "결제창을 여는 중입니다.");
    }

    public static OrderStatusMessage soldOut() {
        return new OrderStatusMessage("SOLD_OUT", null, null,
                "품절되었습니다.");
    }
}