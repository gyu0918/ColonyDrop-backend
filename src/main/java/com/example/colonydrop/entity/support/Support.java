package com.example.colonydrop.entity.support;

import com.example.colonydrop.entity.member.Member;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "support")
public class Support {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member author;

    @Column(name = "title", length = 100, nullable = false)
    private String title;

    @Column(name = "content", columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(name = "answer", columnDefinition = "TEXT")
    private String answer;  // 관리자 답변

    @Column(name = "status", length = 20, nullable = false)
    private String status;  // PENDING, IN_PROGRESS, DONE

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "answered_at")
    private LocalDateTime answeredAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        if (this.status == null) this.status = "PENDING";
    }
}