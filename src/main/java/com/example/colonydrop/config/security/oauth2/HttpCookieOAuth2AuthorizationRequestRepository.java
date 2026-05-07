//package com.example.colonydrop.config.security.oauth2;
//
//import jakarta.servlet.http.Cookie;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
//import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
//import org.springframework.stereotype.Component;
//
//import java.io.*;
//import java.util.Base64;
//
//@Component
//public class HttpCookieOAuth2AuthorizationRequestRepository
//        implements AuthorizationRequestRepository<OAuth2AuthorizationRequest> {
//
//    private static final String COOKIE_NAME = "oauth2_auth_request";
//    private static final int COOKIE_EXPIRE_SECONDS = 180;
//
//    @Override
//    public OAuth2AuthorizationRequest loadAuthorizationRequest(HttpServletRequest request) {
//        Cookie[] cookies = request.getCookies();
//        if (cookies != null) {
//            for (Cookie cookie : cookies) {
//                if (COOKIE_NAME.equals(cookie.getName())) {
//                    return deserialize(cookie.getValue());
//                }
//            }
//        }
//        return null;
//    }
//
//    @Override
//    public void saveAuthorizationRequest(OAuth2AuthorizationRequest authorizationRequest,
//                                         HttpServletRequest request,
//                                         HttpServletResponse response) {
//        System.out.println("🔥🔥🔥 saveAuthorizationRequest 호출됨! state="
//                + (authorizationRequest != null ? authorizationRequest.getState() : "NULL"));
//
//
//
//        if (authorizationRequest == null) {
//            deleteCookie(response);
//            return;
//        }
//
//        String cookieValue = serialize(authorizationRequest);
//        String cookie = COOKIE_NAME + "=" + cookieValue
//                + "; Path=/"
//                + "; HttpOnly"
//                + "; Max-Age=" + COOKIE_EXPIRE_SECONDS
//                + "; SameSite=None"
//                + "; Secure";
//        response.addHeader("Set-Cookie", cookie);
//    }
//
//    @Override
//    public OAuth2AuthorizationRequest removeAuthorizationRequest(
//            HttpServletRequest request, HttpServletResponse response) {
//        OAuth2AuthorizationRequest authRequest = loadAuthorizationRequest(request);
//        deleteCookie(response);
//        return authRequest;
//    }
//
//    private void deleteCookie(HttpServletResponse response) {
//        String expiredCookie = COOKIE_NAME + "="
//                + "; Path=/"
//                + "; HttpOnly"
//                + "; Max-Age=0"
//                + "; SameSite=None"
//                + "; Secure";
//        response.addHeader("Set-Cookie", expiredCookie);
//    }
//
//    // SerializationUtils 제거 → 표준 Java 직렬화로 교체 (Spring Boot 4.x 호환)
//    private String serialize(OAuth2AuthorizationRequest authorizationRequest) {
//        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
//             ObjectOutputStream oos = new ObjectOutputStream(baos)) {
//            oos.writeObject(authorizationRequest);
//            return Base64.getUrlEncoder().encodeToString(baos.toByteArray());
//        } catch (IOException e) {
//            throw new RuntimeException("OAuth2 직렬화 실패", e);
//        }
//    }
//
//    private OAuth2AuthorizationRequest deserialize(String value) {
//        try {
//            byte[] bytes = Base64.getUrlDecoder().decode(value);
//            try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bytes))) {
//                return (OAuth2AuthorizationRequest) ois.readObject();
//            }
//        } catch (Exception e) {
//            System.out.println("❌❌❌ 역직렬화 실패: " + e.getMessage());
//            e.printStackTrace(); // ← 이게 핵심
//            return null;
//        }
//    }
//}

package com.example.colonydrop.config.security.oauth2;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Arrays;
import java.util.Base64;
import java.util.stream.Collectors;

@Component
public class HttpCookieOAuth2AuthorizationRequestRepository
        implements AuthorizationRequestRepository<OAuth2AuthorizationRequest> {

    private static final String COOKIE_NAME = "oauth2_auth_request";
    private static final int COOKIE_EXPIRE_SECONDS = 180;

    @Override
    public OAuth2AuthorizationRequest loadAuthorizationRequest(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            System.out.println("❌ loadAuthorizationRequest - 쿠키 자체가 null");
            return null;
        }
        for (Cookie cookie : cookies) {
            if (COOKIE_NAME.equals(cookie.getName())) {
                System.out.println("✅ 쿠키 발견! 역직렬화 시도...");
                OAuth2AuthorizationRequest result = deserialize(cookie.getValue());
                System.out.println("✅ 역직렬화 결과: " + (result != null ? "성공 state=" + result.getState() : "null ← 역직렬화 실패"));
                return result;
            }
        }
        System.out.println("❌ oauth2_auth_request 쿠키 없음. 전체 쿠키: "
                + Arrays.stream(cookies).map(Cookie::getName).collect(Collectors.joining(", ")));
        return null;
    }

    @Override
    public void saveAuthorizationRequest(OAuth2AuthorizationRequest authorizationRequest,
                                         HttpServletRequest request,
                                         HttpServletResponse response) {
        System.out.println("🔥🔥🔥 saveAuthorizationRequest 호출됨! state="
                + (authorizationRequest != null ? authorizationRequest.getState() : "NULL"));

        if (authorizationRequest == null) {
            deleteCookie(response);
            return;
        }

        String cookieValue = serialize(authorizationRequest);
        String cookie = COOKIE_NAME + "=" + cookieValue
                + "; Path=/"
                + "; HttpOnly"
                + "; Max-Age=" + COOKIE_EXPIRE_SECONDS
                + "; SameSite=None"
                + "; Secure";
        response.addHeader("Set-Cookie", cookie);
    }

    @Override
    public OAuth2AuthorizationRequest removeAuthorizationRequest(
            HttpServletRequest request, HttpServletResponse response) {
        System.out.println("🔶 removeAuthorizationRequest 호출됨");
        OAuth2AuthorizationRequest authRequest = loadAuthorizationRequest(request);
        System.out.println("🔶 removeAuthorizationRequest 결과: " + (authRequest != null ? "성공" : "null ← 여기서 실패!"));
        deleteCookie(response);
        return authRequest;
    }

    private void deleteCookie(HttpServletResponse response) {
        String expiredCookie = COOKIE_NAME + "="
                + "; Path=/"
                + "; HttpOnly"
                + "; Max-Age=0"
                + "; SameSite=None"
                + "; Secure";
        response.addHeader("Set-Cookie", expiredCookie);
    }

    private String serialize(OAuth2AuthorizationRequest authorizationRequest) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(authorizationRequest);
            return Base64.getUrlEncoder().encodeToString(baos.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("OAuth2 직렬화 실패", e);
        }
    }

    private OAuth2AuthorizationRequest deserialize(String value) {
        try {
            byte[] bytes = Base64.getUrlDecoder().decode(value);
            try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bytes))) {
                return (OAuth2AuthorizationRequest) ois.readObject();
            }
        } catch (Exception e) {
            System.out.println("❌❌❌ 역직렬화 실패: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}