package com.example.colonydrop.controller.chat;


import com.example.colonydrop.config.redisConfig.RedisPublisher;
import com.example.colonydrop.dto.chat.ChatMessageDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/chat")
public class ChatController {

    private final RedisPublisher redisPublisher; //redis pub/sub 직통 연결을 위해서
//    private final ChatProducer chatProducer;
    private final StringRedisTemplate redisTemplate;
    private final ObjectMapper objectMapper;

    @MessageMapping("/chat/gundam")
    public void sendGundamMessage(ChatMessageDto message) throws JsonProcessingException {
        log.info("[ChatController] 건담 전송 시각={}", java.time.LocalDateTime.now());
        message.setTime(java.time.LocalDateTime.now().toString());
        message.setRoomType(ChatMessageDto.RoomType.GUNDAM);
//        chatProducer.sendMessage("chat-gundam", message);

//        Before: ChatProducer → Kafka → ChatConsumer → Redis
//        After:  RedisPublisher → Redis 직통 (5ms)
        String json = objectMapper.writeValueAsString(message);
        redisTemplate.opsForList().leftPush("chat:history:gundam", json);
        redisTemplate.opsForList().trim("chat:history:gundam", 0, 99);
        redisPublisher.publishChat("gundam", message); //redis 직통


    }

    @MessageMapping("/chat/openrun")
    public void sendOpenrunMessage(ChatMessageDto message) throws JsonProcessingException {
        message.setTime(java.time.LocalDateTime.now().toString());
        message.setRoomType(ChatMessageDto.RoomType.OPENRUN);
//        chatProducer.sendMessage("chat-openrun", message);

        String json = objectMapper.writeValueAsString(message);
        redisTemplate.opsForList().leftPush("chat:history:openrun", json);
        redisTemplate.opsForList().trim("chat:history:openrun", 0, 99);
        redisPublisher.publishChat("openrun", message);
    }

    @MessageMapping("/chat/sharing")
    public void sendSharingMessage(ChatMessageDto message) throws JsonProcessingException {
        message.setTime(java.time.LocalDateTime.now().toString());
        message.setRoomType(ChatMessageDto.RoomType.SHARING);
//        chatProducer.sendMessage("chat-sharing", message);
        String json = objectMapper.writeValueAsString(message);
        redisTemplate.opsForList().leftPush("chat:history:sharing", json);
        redisTemplate.opsForList().trim("chat:history:sharing", 0, 99);
        redisPublisher.publishChat("sharing", message);
    }

    @MessageMapping("/chat/free")
    public void sendFreeMessage(ChatMessageDto message) throws JsonProcessingException {
        message.setTime(java.time.LocalDateTime.now().toString());
        message.setRoomType(ChatMessageDto.RoomType.FREE);
//        chatProducer.sendMessage("chat-free", message);

        String json = objectMapper.writeValueAsString(message);
        redisTemplate.opsForList().leftPush("chat:history:free", json);
        redisTemplate.opsForList().trim("chat:history:free", 0, 99);
        redisPublisher.publishChat("free", message);
    }

    @ResponseBody
    @GetMapping("/history/{roomType}")
    public List<ChatMessageDto> getHistory(@PathVariable String roomType) {

        // Redis에서 해당 채팅방 최근 100개 조회
        // "chat:history:" + roomType 키로 조회
        List<String> messages = redisTemplate.opsForList().range("chat:history:" + roomType, 0, 99);
        List<ChatMessageDto> result = new ArrayList<>();

        if (messages == null) return result;

        // JSON 문자열 → ChatMessageDto 변환해서 반환
        for (String message : messages) {
            try {
                ChatMessageDto dto = objectMapper.readValue(message, ChatMessageDto.class);
                result.add(dto);
            } catch (JsonProcessingException e) {
                log.error("[ChatController] 메시지 파싱 실패: {}", message);
            }
        }
        return result;
    }

    // 채팅방별 접속자 수
    @ResponseBody
    @GetMapping("/users/{roomType}")
    public Map<String, Object> getChatUsers(@PathVariable String roomType) {
        // Redis에서 "chat:users:" + roomType 조회
        String value = redisTemplate.opsForValue().get("chat:users:" + roomType);
        Long count  = value != null ? Long.parseLong(value) : 0L;
        // count 반환
        return Map.of("roomType", roomType, "count", count);
    }

    // 전체 접속자 수
    @ResponseBody
    @GetMapping("/site/users")
    public Map<String, Object> getSiteUsers() {
        // Redis에서 "site:users" 조회
        String value = redisTemplate.opsForValue().get("site:users:");
        Long count = value != null ? Long.parseLong(value) : 0L;

        // count 반환
        return Map.of("count", count);
    }
}