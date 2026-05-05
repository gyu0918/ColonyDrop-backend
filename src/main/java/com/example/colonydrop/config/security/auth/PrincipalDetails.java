package com.example.colonydrop.config.security.auth;



import com.example.colonydrop.entity.member.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.*;

public class PrincipalDetails implements UserDetails, OAuth2User {

    private final Member member;
    private Map<String, Object> attributes;

    // 일반 로그인용 생성자
    public PrincipalDetails(Member member) {
        this.member = member;
    }

    // OAuth2 로그인용 생성자
    public PrincipalDetails(Member member, Map<String, Object> attributes) {
        this.member = member;
        this.attributes = attributes;
    }

    // 권한 반환
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
//        if (member.getRoleList() != null) {
//            member.getRoleList().forEach(role -> authorities.add(() -> role));
//        }

        //밑에 줄 인가 부분없는거 떄문에 이렇게 처리했다 우선 override를 해서 필수 메서드는 모두 구현해야되는거 때문에 만약에 인가부분이 생기면 그떄 이거 지우고 위에 주석제거
        authorities = null;
        return authorities;
    }

    // 비밀번호
    @Override
    public String getPassword() {
        return member.getMemberPw();
    }

    // 사용자 id 가 지금 email이다.
    @Override
    public String getUsername() {
        return member.getMemberId();
    }

    // 계정 만료 여부
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 계정 잠김 여부
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 비밀번호 만료 여부
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 계정 활성화 여부
    @Override
    public boolean isEnabled() {
        return true;
    }

    // OAuth2User 구현부
    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public String getName() {
        // unique한 사용자 식별자 (보통 providerId 또는 username 사용)
        return member.getMemberId();
    }

    //소셜 로그인쪽
    public String getProvider(){
        return member.getProvider();
    }
    // 추가로 사용자 객체 반환이 필요한 경우
    public Member getUser() {
        return member;
    }
}
