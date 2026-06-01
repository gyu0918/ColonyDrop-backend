package com.example.colonydrop.controller.support;

import com.example.colonydrop.config.security.auth.PrincipalDetails;
import com.example.colonydrop.entity.member.Member;
import com.example.colonydrop.entity.support.Support;
import com.example.colonydrop.repository.support.SupportRepository;
import com.example.colonydrop.service.support.SlackNotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/support")
@RequiredArgsConstructor
public class SupportController {

    private final SupportRepository supportRepository;
    private final SlackNotificationService slackNotificationService;

    // 문의 작성
    @PostMapping
    public ResponseEntity<?> createSupport(
            @RequestBody Support request,
            @AuthenticationPrincipal PrincipalDetails principalDetails) {

        if (principalDetails == null) {
            return ResponseEntity.status(401).body("로그인이 필요합니다.");
        }

        Member member = principalDetails.getUser();

        Support support = Support.builder()
                .author(member)
                .title(request.getTitle())
                .content(request.getContent())
                .build();

        Support saved = supportRepository.save(support);

        // Slack 알림 전송
        slackNotificationService.sendSupportNotification(member.getMemberName(), request.getTitle());

        return ResponseEntity.ok(saved);
    }

    // 내 문의 목록 조회
    @GetMapping("/my")
    public ResponseEntity<?> getMySupports(
            @AuthenticationPrincipal PrincipalDetails principalDetails) {

        if (principalDetails == null) {
            return ResponseEntity.status(401).body("로그인이 필요합니다.");
        }

        String memberId = principalDetails.getUsername();
        List<Support> supports = supportRepository.findByAuthorMemberId(memberId);
        return ResponseEntity.ok(supports);
    }

    // 문의 상세 조회
    @GetMapping("/{id}")
    public ResponseEntity<?> getSupport(
            @PathVariable Long id,
            @AuthenticationPrincipal PrincipalDetails principalDetails) {

        if (principalDetails == null) {
            return ResponseEntity.status(401).body("로그인이 필요합니다.");
        }

        Support support = supportRepository.findByIdWithAuthor(id)
                .orElseThrow(() -> new RuntimeException("문의 없음"));

        return ResponseEntity.ok(support);
    }
}