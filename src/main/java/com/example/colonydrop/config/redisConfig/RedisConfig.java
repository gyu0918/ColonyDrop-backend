package com.example.colonydrop.config.redisConfig;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.lettuce.core.ClientOptions;
import io.lettuce.core.SocketOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

import java.time.Duration;

//RedisSubscriber를 Redis 채널에 등록
//서버 시작 시 자동으로 구독 시작
@Configuration
//@RequiredArgsConstructor
public class RedisConfig {

//    private final RedisSubscriber redisSubscriber;

    // ObjectMapper 빈 등록 (JsonProcessingException 해결)
//    JSON 변환기 빈 등록
//    RedisPublisher, RedisSubscriber에서 주입받아 사용
//    @Bean
//    public ObjectMapper objectMapper() {
//        return new ObjectMapper();
//    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(com.fasterxml.jackson.core.JsonGenerator.Feature.ESCAPE_NON_ASCII, false);
        return mapper;
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
//    @Bean
//    public RedisMessageListenerContainer redisMessageListenerContainer(
//            RedisConnectionFactory connectionFactory,
//            RedisSubscriber redisSubscriber) {
//        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
//        container.setConnectionFactory(connectionFactory);
//        container.addMessageListener(redisSubscriber,
//                new PatternTopic(RedisPublisher.CHANNEL_PREFIX + "*"));
//        return container;
//    }

    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(
            RedisConnectionFactory connectionFactory,
            RedisSubscriber redisSubscriber) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);

        //장기간 연결시 끊어지는 현상 방지
        container.setRecoveryInterval(3000L);  // (3초마다 재연결 시도)
        // 주문 채널 구독
        container.addMessageListener(redisSubscriber,
                new PatternTopic(RedisPublisher.CHANNEL_PREFIX + "*"));
        // 채팅 채널 구독
        container.addMessageListener(redisSubscriber,
                new PatternTopic(RedisPublisher.CHAT_CHANNEL_PREFIX + "*"));
        return container;
    }

    @Value("${spring.data.redis.host}")
    private String redisHost;

    @Value("${spring.data.redis.port}")
    private int redisPort;

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration serverConfig = new RedisStandaloneConfiguration();
        serverConfig.setHostName(redisHost);
        serverConfig.setPort(redisPort);

        LettuceClientConfiguration clientConfig = LettuceClientConfiguration.builder()
                .clientOptions(ClientOptions.builder()
                        .socketOptions(SocketOptions.builder()
                                .keepAlive(SocketOptions.KeepAliveOptions.builder()
                                        .enable()
                                        .idle(Duration.ofSeconds(30))
                                        .interval(Duration.ofSeconds(10))
                                        .count(3)
                                        .build())
                                .build())
                        .build())
                .build();

        return new LettuceConnectionFactory(serverConfig, clientConfig);
    }

}