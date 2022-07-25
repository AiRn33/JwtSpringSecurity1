package com.cos.security1.config.oauth.provider;

import java.util.Map;

public class GoogleUserInfo implements OAuth2Userinfo{

    //  생성자
    private Map<String, Object> attributes; // oauth2User.getAttributes()를 받아옴

    public GoogleUserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String getProviderId() {
        return (String) attributes.get("sub"); // 구글은 sub, 페이스북은 id
    }

    @Override
    public String getProvider() {
        return "google";
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }

    @Override
    public String getName() {
        return  (String) attributes.get("name");
    }
}
