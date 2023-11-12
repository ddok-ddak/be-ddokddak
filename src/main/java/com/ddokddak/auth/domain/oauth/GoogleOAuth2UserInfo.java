package com.ddokddak.auth.domain.oauth;


import com.ddokddak.member.domain.enums.AuthProviderType;

import java.util.Map;

public class GoogleOAuth2UserInfo extends OAuth2UserInfo {

    private static final AuthProviderType authProviderType = AuthProviderType.GOOGLE;

    public GoogleOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public AuthProviderType getProviderType() {
        return authProviderType;
    }
    @Override
    public String getId() {
        return (String) super.getAttributes().get("sub");
    }
    @Override
    public String getName() {
        return (String) super.getAttributes().get("name");
    }
    @Override
    public String getEmail() {
        return (String) super.getAttributes().get("email");
    }
    @Override
    public String getImageUrl() {
        return (String) super.getAttributes().get("picture");
    }

}