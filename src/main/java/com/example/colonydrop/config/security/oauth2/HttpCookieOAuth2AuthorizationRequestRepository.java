//package com.example.colonydrop.config.security.oauth2;
//
//import jakarta.servlet.http.Cookie;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
//import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
//import org.springframework.stereotype.Component;
//import org.springframework.util.SerializationUtils;
//
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
//        if (authorizationRequest == null) {
//            deleteCookie(request, response);
//            return;
//        }
//        Cookie cookie = new Cookie(COOKIE_NAME, serialize(authorizationRequest));
//        cookie.setPath("/");
//        cookie.setHttpOnly(true);
//        cookie.setMaxAge(COOKIE_EXPIRE_SECONDS);
//        response.addCookie(cookie);
//    }
//
//    @Override
//    public OAuth2AuthorizationRequest removeAuthorizationRequest(
//            HttpServletRequest request, HttpServletResponse response) {
//        OAuth2AuthorizationRequest authRequest = loadAuthorizationRequest(request);
//        deleteCookie(request, response);
//        return authRequest;
//    }
//
//    private void deleteCookie(HttpServletRequest request, HttpServletResponse response) {
//        Cookie[] cookies = request.getCookies();
//        if (cookies != null) {
//            for (Cookie cookie : cookies) {
//                if (COOKIE_NAME.equals(cookie.getName())) {
//                    cookie.setValue("");
//                    cookie.setPath("/");
//                    cookie.setMaxAge(0);
//                    response.addCookie(cookie);
//                }
//            }
//        }
//    }
//
//    private String serialize(OAuth2AuthorizationRequest authorizationRequest) {
//        return Base64.getUrlEncoder().encodeToString(
//                SerializationUtils.serialize(authorizationRequest));
//    }
//
//    private OAuth2AuthorizationRequest deserialize(String cookie) {
//        return (OAuth2AuthorizationRequest) SerializationUtils.deserialize(
//                Base64.getUrlDecoder().decode(cookie));
//    }
//}
package com.example.colonydrop.config.security.oauth2;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.SerializationUtils;

import java.util.Base64;


public class HttpCookieOAuth2AuthorizationRequestRepository
        implements AuthorizationRequestRepository<OAuth2AuthorizationRequest> {

    private static final String COOKIE_NAME = "oauth2_auth_request";
    private static final int COOKIE_EXPIRE_SECONDS = 180;

    @Override
    public OAuth2AuthorizationRequest loadAuthorizationRequest(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (COOKIE_NAME.equals(cookie.getName())) {
                    return deserialize(cookie.getValue());
                }
            }
        }
        return null;
    }

    @Override
    public void saveAuthorizationRequest(OAuth2AuthorizationRequest authorizationRequest,
                                         HttpServletRequest request,
                                         HttpServletResponse response) {
        if (authorizationRequest == null) {
            deleteCookie(request, response);
            return;
        }

        // ✅ SameSite=None; Secure 추가 (크로스 도메인 쿠키 전송)
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
        OAuth2AuthorizationRequest authRequest = loadAuthorizationRequest(request);
        deleteCookie(request, response);
        return authRequest;
    }

    private void deleteCookie(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (COOKIE_NAME.equals(cookie.getName())) {
                    // ✅ SameSite=None; Secure 추가
                    String expiredCookie = COOKIE_NAME + "="
                            + "; Path=/"
                            + "; HttpOnly"
                            + "; Max-Age=0"
                            + "; SameSite=None"
                            + "; Secure";
                    response.addHeader("Set-Cookie", expiredCookie);
                }
            }
        }
    }

    private String serialize(OAuth2AuthorizationRequest authorizationRequest) {
        return Base64.getUrlEncoder().encodeToString(
                SerializationUtils.serialize(authorizationRequest));
    }

    private OAuth2AuthorizationRequest deserialize(String cookie) {
        return (OAuth2AuthorizationRequest) SerializationUtils.deserialize(
                Base64.getUrlDecoder().decode(cookie));
    }
}