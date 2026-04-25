package com.example.colonydrop.config.security.oauth2.provider;

import java.util.Map;

public  class FacebookUserInfo implements OAuth2UserInfo {

    private Map<String, Object> attributes;    //oauth2User.getAttributes() 여기 나오는 리턴값을 받기 위해

    public FacebookUserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String getProviderId() {
        return (String) attributes.get("id");
    }

    @Override
    public String getProvider() {
        return "facebook";
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }

    @Override
    public String getName() {
        return (String) attributes.get("name");
    }

    @Override
    public String getProfile_image() {
        Map<String, Object> profile = (Map<String, Object>) attributes.get("picture");
        Map<String, Object> data = (Map<String, Object>) profile.get("data");
        String url = (String) data.get("url");
        System.out.println("Facebook profile image: " + url);
        return url;
    }
}
