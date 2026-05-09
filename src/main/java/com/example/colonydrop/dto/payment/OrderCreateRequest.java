package com.example.colonydrop.dto.payment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class OrderCreateRequest {
    @JsonProperty("itemId")
    private Long itemId;
}
