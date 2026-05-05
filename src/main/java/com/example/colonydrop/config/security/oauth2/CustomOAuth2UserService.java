package com.example.colonydrop.config.security.oauth2;

import com.example.colonydrop.config.security.auth.PrincipalDetails;
import com.example.colonydrop.config.security.oauth2.provider.GoogleUserInfo;
import com.example.colonydrop.config.security.oauth2.provider.KakaoUserInfo;
import com.example.colonydrop.config.security.oauth2.provider.NaverUserInfo;
import com.example.colonydrop.config.security.oauth2.provider.OAuth2UserInfo;
import com.example.colonydrop.entity.member.Member;
import com.example.colonydrop.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        // 1. 소셜에서 사용자 정보 가져오기
        OAuth2User oAuth2User = super.loadUser(userRequest);

        // 2. 어떤 소셜인지 확인
        String provider = userRequest.getClientRegistration().getRegistrationId();
        System.out.println("provider = " + provider);

        // 3. 소셜별 사용자 정보 파싱
        OAuth2UserInfo oAuth2UserInfo = null;

        if (provider.equals("google")) {
            oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
        } else if (provider.equals("kakao")) {
            oAuth2UserInfo = new KakaoUserInfo(oAuth2User.getAttributes());
        } else if (provider.equals("naver")) {
            // 네이버는 response 안에 실제 데이터가 있음
            oAuth2UserInfo = new NaverUserInfo(
                    (Map<String, Object>) oAuth2User.getAttributes().get("response")
            );
        }

        // 4. 사용자 정보 추출
        String providerId = oAuth2UserInfo.getProviderId();
        String memberId = provider + "_" + providerId; // kakao_123456
        String memberName = oAuth2UserInfo.getName();
        String imgUrl = oAuth2UserInfo.getProfile_image();

        // 5. DB에서 기존 회원 조회
        Member member = memberRepository.findByMemberId(memberId);

        // 6. 없으면 자동 회원가입
        if (member == null) {
            member = Member.builder()
                    .memberId(memberId)
                    .memberName(memberName)
                    .memberPw(null) // 소셜 로그인은 비밀번호 없음
                    .provider(provider)
                    .providerId(providerId)
                    .roles("ROLE_USER")
                    .imgUrl(imgUrl)
                    .build();
            memberRepository.save(member);
            System.out.println("신규 소셜 회원가입: " + memberId);
        } else {
            System.out.println("기존 소셜 회원 로그인: " + memberId);
        }

        // 7. PrincipalDetails 반환
        return new PrincipalDetails(member, oAuth2User.getAttributes());
    }
}