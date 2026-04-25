package com.example.colonydrop.entity.member;


import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
@Table(name = "member")
public class Member implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ← auto increment
    @Column(name = "id")
    private Long id;

    @Column(name = "member_id", length = 20, unique = true, nullable = false)
    private String memberId;        // 로그인 식별자 (중복 불가)

    @Column(name = "member_pw", length = 256)
    private String memberPw;

    @Column(name = "member_name", length = 20)
    private String memberName;      // ← 다시 추가

    @Column(name = "provider", length = 10)
    private String provider;

    @Column(name = "provider_id", length = 30)
    private String providerId;

    @Column(name = "roles", length = 15, nullable = false)
    private String roles;

    @Column(name = "img_url", length = 70)
    private String imgUrl;

    public Member(Long id, String memberId, String memberPw, String memberName, String provider, String providerId, String roles, String imgUrl) {
        this.id = id;
        this.memberId = memberId;
        this.memberPw = memberPw;
        this.memberName = memberName;
        this.provider = provider;
        this.providerId = providerId;
        this.roles = roles;
        this.imgUrl = imgUrl;
    }
}