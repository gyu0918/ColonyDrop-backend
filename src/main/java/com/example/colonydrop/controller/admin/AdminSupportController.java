package com.example.colonydrop.controller.admin;

import com.example.colonydrop.entity.support.Support;
import com.example.colonydrop.repository.support.SupportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/support")
@RequiredArgsConstructor
public class AdminSupportController {

    private final SupportRepository supportRepository;

    // 전체 문의 목록 조회
    @GetMapping
    public ResponseEntity<List<Support>> getAllSupports() {
        return ResponseEntity.ok(supportRepository.findAllWithAuthor());
    }

    // 문의 상세 조회
    @GetMapping("/{id}")
    public ResponseEntity<?> getSupport(@PathVariable Long id) {
        Support support = supportRepository.findByIdWithAuthor(id)
                .orElseThrow(() -> new RuntimeException("문의 없음"));
        return ResponseEntity.ok(support);
    }

    // 답변 작성
    @PatchMapping("/{id}/answer")
    public ResponseEntity<?> answerSupport(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {

        Support support = supportRepository.findByIdWithAuthor(id)
                .orElseThrow(() -> new RuntimeException("문의 없음"));

        support.setAnswer(body.get("answer"));
        support.setAnsweredAt(LocalDateTime.now());
        support.setStatus("DONE");

        return ResponseEntity.ok(supportRepository.save(support));
    }

    // 상태 변경 (PENDING / IN_PROGRESS / DONE)
    @PatchMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {

        Support support = supportRepository.findByIdWithAuthor(id)
                .orElseThrow(() -> new RuntimeException("문의 없음"));

        support.setStatus(body.get("status"));
        return ResponseEntity.ok(supportRepository.save(support));
    }

    // 문의 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSupport(@PathVariable Long id) {
        supportRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}