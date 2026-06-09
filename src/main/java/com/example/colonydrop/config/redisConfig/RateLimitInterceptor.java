package com.example.colonydrop.config.redisConfig;


import com.example.colonydrop.config.security.oauth2.JwtProperties;
import com.example.colonydrop.service.redis.RateLimitService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

@Component
@RequiredArgsConstructor
public class RateLimitInterceptor implements HandlerInterceptor {

    private final RateLimitService rateLimitService;
    private final JwtProperties jwtProperties;  // ← 이걸로 교체

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        String uri = request.getRequestURI();
        String method = request.getMethod();

        if ("POST".equals(method) && uri.equals("/api/orders")) {
            String token = resolveToken(request);
            if (token != null) {
                try {
                    String memberId = JWT.require(Algorithm.HMAC512(jwtProperties.getSecret()))
                            .build()
                            .verify(token)
                            .getClaim("memberId").asString();

                    if (!rateLimitService.isAllowed("order:" + memberId, 5, 60)) {
                        response.setStatus(429);
                        response.setContentType("application/json;charset=UTF-8");
                        response.getWriter().write(
                                "{\"code\":\"TOO_MANY_REQUESTS\",\"message\":\"잠시 후 다시 시도해주세요.\"}"
                        );
                        return false;
                    }
                } catch (Exception e) {
                    // 토큰 파싱 실패 시 Rate Limit 스킵 (인증 필터에서 처리)
                }
            }
        }

        // 전체 API → IP 기반 1분 100회 제한
        String ip = getClientIp(request);
        if (!rateLimitService.isAllowed("ip:" + ip, 600, 60)) {
            response.setStatus(429);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(
                    "{\"code\":\"TOO_MANY_REQUESTS\",\"message\":\"요청이 너무 많습니다.\"}"
            );
            return false;
        }

        return true;
    }

    private String resolveToken(HttpServletRequest request) {
        String bearer = request.getHeader("Authorization");
        if (bearer != null && bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        }
        return null;
    }

    private String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty()) {
            ip = request.getRemoteAddr();
        }
        return ip.split(",")[0].trim();
    }
}