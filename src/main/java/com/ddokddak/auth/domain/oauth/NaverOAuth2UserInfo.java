package com.ddokddak.auth.domain.oauth;

import com.ddokddak.member.domain.enums.AuthProviderType;

import java.util.Map;

public class NaverOAuth2UserInfo extends OAuth2UserInfo {

    private static final AuthProviderType authProviderType = AuthProviderType.NAVER;
    private Map<String, Object> attributesResponse;


    public NaverOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
        this.attributesResponse = ((Map<String, Object>) super.getAttributes().get("response"));
    }
    @Override
    public AuthProviderType getProviderType() {
        return authProviderType;
    }

    @Override
    public String getOauth2Id() {
        if (attributesResponse == null) {
            return null;
        }
        return this.attributesResponse.get("id").toString();
    }

    @Override
    public String getEmail() {
        if (attributesResponse == null) {
            return null;
        }
        return (String) attributesResponse.get("email");
    }

    @Override
    public String getName() {
        if (attributesResponse == null) {
            return null;
        }
        return (String) attributesResponse.get("nickname");
    }

    @Override
    public String getImageUrl() {
        if (attributesResponse == null) {
            return null;
        }
        return (String) attributesResponse.get("profile_image");
    }
}
