package com.example.colonydrop.config.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker     //WebSocket + STOMP 기능 활성화 -> STOMP = 채널 구독 개념이 있는 WebSocket 프로토콜
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

//    /queue → 1:1 개인 채널 (우리가 사용)
//         /queue/order/{sessionId}
//    나만 구독하는 채널
//
//    /topic → 1:N 브로드캐스트 채널
//         /topic/notice
//    모두에게 보내는 채널
//
//    /app   → 클라이언트 → 서버 메시지 보낼 때 prefix
//    나중에 채팅 구현할 때 사용
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/queue", "/topic");
        registry.setApplicationDestinationPrefixes("/app");
    }

//    /ws → 클라이언트가 WebSocket 연결하는 주소
//    프론트에서 new SockJS('/ws') 로 연결
//
//    setAllowedOriginPatterns("*") → CORS 허용
//    운영 시 실제 도메인으로 변경 필요
//    colonydrop0079.com 으로
//
//    withSockJS() → WebSocket 미지원 브라우저에서
//    HTTP 폴링으로 자동 대체
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }
}