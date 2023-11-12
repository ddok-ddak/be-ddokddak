package com.ddokddak.auth.domain.oauth;

import com.ddokddak.member.domain.enums.AuthProviderType;

import java.util.Map;

public class OAuth2UserInfoFactory {

    public static final OAuth2UserInfo getOAuth2UserInfo(AuthProviderType authProvider, Map<String, Object> attributes) {
        switch (authProvider) {
            case GOOGLE: return new GoogleOAuth2UserInfo(attributes);
            case KAKAO: return new KakaoOAuth2UserInfo(attributes);
            case NAVER: return new NaverOAuth2UserInfo(attributes);
            //case GITHUB: return new GithubOAuth2UserInfo(attributes);
            default: throw new IllegalArgumentException("Invalid Provider Type.");
        }
    }
}
