package com.example.colonydrop.dto.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageDto {

    public enum RoomType {
        GUNDAM, OPENRUN, SHARING, FREE
    }
    private String senderId;
    private String senderName;
    private RoomType roomType;
    private String content;
    private String time;
}
