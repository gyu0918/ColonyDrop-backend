package com.example.colonydrop.config.websocket;

import com.example.colonydrop.dto.chat.ChatRoomUsersDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
import org.springframework.web.socket.messaging.SessionUnsubscribeEvent;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketEventListener {

    private final StringRedisTemplate stringRedisTemplate;
    private final SimpMessagingTemplate simpMessagingTemplate;

    // webSocket 연결시
    @EventListener
    public void handleConnect(SessionConnectedEvent event) {

        // site:users increment
        Long count = stringRedisTemplate.opsForValue().increment("site:users"); //Redis에서 접속자 수 1 증가

        // /topic/users 로 현재 접속자 수 브로드캐스트
        simpMessagingTemplate.convertAndSend("/topic/users", count); // 현재 접속자수를 구독 중인 모든 클라이언트에게 실시
        log.info("[WebSocketEventListener] 접속 - 현재 접속자 수: {}", count);
    }

    //webSocket 해제 시
    @EventListener
    public void handleDisconnect(SessionDisconnectEvent event) {
        // site:users decrement
        Long count =  stringRedisTemplate.opsForValue().decrement("site:users");

        // /topic/users 로 현재 접속자 수 브로드 캐스트
        simpMessagingTemplate.convertAndSend("/topic/users", count); // 현재 접속자수를 구독 중인 모든 클라이언트에게 실시
        log.info("[WebSocketEventListener] 해제 - 현재 접속자 수: {}", count);
    }

    // 채팅방 구독 시
    @EventListener
    public void handleSubscribe(SessionSubscribeEvent event) {
        // destination 확인
        String destination = (String) event.getMessage().getHeaders()
                .get(SimpMessageHeaderAccessor.DESTINATION_HEADER);// destination — 클라이언트가 구독한 경로예요. /topic/chat/gundam 이런 식으로 들어옴

        if (destination == null) return;
        // /topic/chat/gundam → chat:users:gundam increment
        // /topic/chat/users 로 채팅방별 접속자 수 브로드캐스트
        if (destination.startsWith("/topic/chat/") && !destination.equals("/topic/chat/users")){  //startsWith("/topic/chat/") — 채팅방 구독인지 확인해요. /topic/users 같은 다른 구독은 무시
            String roomType = destination.replace("/topic/chat/", ""); //gundam, openrun 등 채팅방 이름만 추출
            Long count = stringRedisTemplate.opsForValue().increment("chat:users:" + roomType);
            simpMessagingTemplate.convertAndSend("/topic/chat/users",  new ChatRoomUsersDto(roomType, count));//어느 채팅방이 몇 명인지 같이 브로드캐스트

            log.info("[WebSocketEventListener] 구독 - roomType={}, 접속자 수={}", roomType, count);
        }

    }

    // 채팅방 구독 해제 시
    @EventListener
    public void handleUnsubscribe(SessionUnsubscribeEvent event) {
        // chat:users:{roomType} decrement
        // /topic/chat/users 로 채팅방별 접속자 수 브로드캐스트

        String destination = (String) event.getMessage().getHeaders()
                .get(SimpMessageHeaderAccessor.DESTINATION_HEADER);

        if (destination == null) return;

        if (destination.startsWith("/topic/chat/") && !destination.equals("/topic/chat/users")) {
            String roomType = destination.replace("/topic/chat/", "");
            Long count = stringRedisTemplate.opsForValue().decrement("chat:users:" + roomType);
            if (count != null && count < 0) {
                stringRedisTemplate.opsForValue().set("chat:users:" + roomType, "0");
                count = 0L;
            }
            simpMessagingTemplate.convertAndSend("/topic/chat/users",  new ChatRoomUsersDto(roomType, count));
            log.info("[WebSocketEventListener] 구독해제 - roomType={}, 접속자 수={}", roomType, count);
        }
    }

}
