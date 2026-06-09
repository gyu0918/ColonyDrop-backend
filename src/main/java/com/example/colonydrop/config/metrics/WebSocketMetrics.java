package com.example.colonydrop.config.metrics;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.concurrent.atomic.AtomicInteger;

@Component
@RequiredArgsConstructor
public class WebSocketMetrics {

    private final MeterRegistry meterRegistry;
    private final StringRedisTemplate redisTemplate;

    @PostConstruct
    public void init() {
        // Redis의 site:users 값을 실시간으로 읽어서 메트릭으로 노출
        Gauge.builder("websocket_active_connections", this, WebSocketMetrics::getActiveUsers)
                .description("현재 사이트 접속자 수 (Redis 기반)")
                .register(meterRegistry);
    }

    private double getActiveUsers() {
        String value = redisTemplate.opsForValue().get("site:users");
        return value != null ? Double.parseDouble(value) : 0.0;
    }
}