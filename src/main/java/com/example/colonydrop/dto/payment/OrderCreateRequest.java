package com.example.colonydrop.dto.payment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


//@Getter
//@NoArgsConstructor
//public class OrderCreateRequest {
//    @JsonProperty("itemId")
//    private Long itemId;
//}

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreateRequest {
    private Long itemId;
    private int quantity;
    private String buyerName;
    private String buyerTel;
    private String buyerAddr;
}