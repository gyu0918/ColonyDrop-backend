package com.example.colonydrop.config.redisConfig;

import com.example.colonydrop.config.redisConfig.RedisSubscriber;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;


//RedisSubscriber를 Redis 채널에 등록
//서버 시작 시 자동으로 구독 시작
@Configuration
//@RequiredArgsConstructor
public class RedisConfig {

//    private final RedisSubscriber redisSubscriber;

    // ObjectMapper 빈 등록 (JsonProcessingException 해결)
//    JSON 변환기 빈 등록
//    RedisPublisher, RedisSubscriber에서 주입받아 사용
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

//    RedisMessageListenerContainer
//    → Redis 채널을 구독하는 컨테이너
//
//    PatternTopic("order-result:*")
//    → order-result: 로 시작하는 모든 채널 구독
//    → 어떤 sessionId로 와도 다 받음
//
//    서버 시작 시 자동으로 구독 시작
//    → 메시지 오면 RedisSubscriber.onMessage 호출
//    @Bean
//    public RedisMessageListenerContainer redisMessageListenerContainer(
//            RedisConnectionFactory connectionFactory) {
//        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
//        container.setConnectionFactory(connectionFactory);
//        container.addMessageListener(redisSubscriber,
//                new PatternTopic(RedisPublisher.CHANNEL_PREFIX + "*"));
//        return container;
//    }

    //순환참조 해결
    //redisConfig → redisSubscriber → redisConfig 순환 참조
    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(
            RedisConnectionFactory connectionFactory,
            RedisSubscriber redisSubscriber) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(redisSubscriber,
                new PatternTopic(RedisPublisher.CHANNEL_PREFIX + "*"));
        return container;
    }
}