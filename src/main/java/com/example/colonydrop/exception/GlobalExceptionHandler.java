//package com.example.colonydrop.exception;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//import java.util.Map;
//
//@Slf4j
//@RestControllerAdvice
//public class GlobalExceptionHandler {
//
//    // 잘못된 요청 (품절, 주문 못찾음 등)
//    @ExceptionHandler(IllegalArgumentException.class)
//    public ResponseEntity<?> handleIllegalArgument(IllegalArgumentException e) {
//        log.warn("IllegalArgumentException: {}", e.getMessage());
//        return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
//    }
//
//    // 결제 금액 불일치, 환불 초과 등
//    @ExceptionHandler(IllegalStateException.class)
//    public ResponseEntity<?> handleIllegalState(IllegalStateException e) {
//        log.warn("IllegalStateException: {}", e.getMessage());
//        return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
//    }
//
//    // 그 외 예상치 못한 에러
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<?> handleException(Exception e) {
//        log.error("Unhandled Exception: {}", e.getMessage(), e);
//        return ResponseEntity.internalServerError().body(Map.of("message", "서버 오류가 발생했습니다. 고객센터에 문의해주세요."));
//    }
//}
