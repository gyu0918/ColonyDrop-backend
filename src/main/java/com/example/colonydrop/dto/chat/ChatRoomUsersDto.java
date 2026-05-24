package com.example.colonydrop.dto.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChatRoomUsersDto {
    private String roomType;
    private Long count;
}