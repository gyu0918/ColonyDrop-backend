package com.example.colonydrop.config.metrics;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.concurrent.atomic.AtomicInteger;

@Component
@RequiredArgsConstructor
public class WebSocketMetrics {

    private final MeterRegistry meterRegistry;
    private final AtomicInteger activeConnections = new AtomicInteger(0);

    @PostConstruct
    public void init() {
        Gauge.builder("websocket_active_connections", activeConnections, AtomicInteger::get)
                .description("현재 WebSocket 연결 수")
                .register(meterRegistry);
    }

    // WebSocket 연결 시 +1
    @EventListener
    public void onConnect(SessionConnectedEvent event) {
        activeConnections.incrementAndGet();
    }

    // WebSocket 연결 해제 시 -1
    @EventListener
    public void onDisconnect(SessionDisconnectEvent event) {
        activeConnections.decrementAndGet();
    }
}