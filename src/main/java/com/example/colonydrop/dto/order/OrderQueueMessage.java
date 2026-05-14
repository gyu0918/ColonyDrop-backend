package com.example.colonydrop.dto.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderQueueMessage  implements Serializable {
    private String sessionId;
    private String memberId;
    private Long   itemId;
    private String buyerName;
    private String buyerTel;
    private String buyerAddr;
}