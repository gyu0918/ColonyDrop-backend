package com.example.colonydrop.config.security.oauth2.provider;

import java.util.Map;

public class NaverUserInfo implements OAuth2UserInfo {

    private Map<String, Object> attributes;    //oauth2User.getAttributes() 여기 나오는 리턴값을 받기 위해

    public NaverUserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String getProviderId() {
        return (String) attributes.get("id");
    }

    @Override
    public String getProvider() {
        return "naver";
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }

//    @Override
//    public String getName() {
//        return (String) attributes.get("name");
//    }
//
//    @Override
//    public String getProfile_image() {
//        return "";
//    }

    @Override
    public String getName() {
        // 네이버도 nickname으로 오는 경우 있음, null 체크 추가
        Object name = attributes.get("nickname");
        if (name != null) return name.toString();
        Object nameAttr = attributes.get("name");
        return nameAttr != null ? nameAttr.toString() : null;
    }

    @Override
    public String getProfile_image() {
        Object img = attributes.get("profile_image");
        return img != null ? img.toString() : null;
    }
}
