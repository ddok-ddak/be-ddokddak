package com.ddokddak.auth.domain.oauth;


import com.ddokddak.member.entity.AuthProviderType;

import java.util.Map;

public class KakaoOAuth2UserInfo extends OAuth2UserInfo {

    private static final AuthProviderType authProviderType = AuthProviderType.KAKAO;
    private Map<String, Object> attributesAccount;
    private Map<String, Object> attributesProfile;

    public KakaoOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
        // 카카오 응답값 기준 파싱
        this.attributesAccount = (Map<String, Object>) super.getAttributes().get("kakao_account");
        this.attributesProfile = (Map<String, Object>) attributesAccount.get("profile");
    }

    @Override
    public AuthProviderType getProviderType() {
        return authProviderType;
    }

    @Override
    public String getId() {
        return super.getAttributes().get("id").toString();
    }

    @Override
    public String getEmail() {
        if (attributesAccount == null) {
            return null;
        }
        return (String) attributesAccount.get("email");
    }

    @Override
    public String getName() {
        if (attributesProfile == null) {
            return null;
        }
        return (String) attributesProfile.get("nickname");
    }

    @Override
    public String getImageUrl() {
        if (attributesProfile == null) {
            return null;
        }
        return (String) attributesProfile.get("profile_image");
    }

}