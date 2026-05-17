package com.example.colonydrop.service.redis;


import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;


//위치: com.example.colonydrop.config 패키지
//주입: RateLimitService, JwtProperties, StringRedisTemplate
//동작:
//        - POST /api/orders → memberId 기반 1분 5회 제한
//  - 전체 API → IP 기반 1분 100회 제한
//  - 초과 시 429 + JSON 반환
//
//토큰 파싱은 JwtAuthorizationFilter와 동일한 방식 사용
//@Service
//@RequiredArgsConstructor
//public class RateLimitService {
//    private final StringRedisTemplate redisTemplate;
//
//    public boolean isAllowed(String key, int maxRequests, int windowSeconds) {
//        String redisKey = "rate_limit:" + key;
////        Redis의 INCR 명령어. 키가 없으면 0에서 시작해서 1로 만들고,
////        있으면 기존 값에서 1 증가. 원자적(Atomic) 연산이라 동시 요청이 와도 정확하게 카운터가 올라
//        Long count = redisTemplate.opsForValue().increment(redisKey);
//        if (count != null && count == 1) {
//            redisTemplate.expire(redisKey, windowSeconds, TimeUnit.SECONDS);
//        }
//        return count != null && count <= maxRequests;
//    }
//}

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class RateLimitService {

    private final StringRedisTemplate redisTemplate;

    private static final String RATE_LIMIT_SCRIPT =
            "local count = redis.call('INCR', KEYS[1]) " +
                    "if count == 1 then " +
                    "  redis.call('EXPIRE', KEYS[1], ARGV[1]) " +
                    "end " +
                    "return count";

    public boolean isAllowed(String key, int maxRequests, int windowSeconds) {
        String redisKey = "rate_limit:" + key;

        Long count = redisTemplate.execute(
                new DefaultRedisScript<>(RATE_LIMIT_SCRIPT, Long.class),
                Collections.singletonList(redisKey),
                String.valueOf(windowSeconds)
        );

        return count != null && count <= maxRequests;
    }
}