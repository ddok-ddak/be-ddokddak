package com.ddokddak.common.props;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class OAuth2Properties {

    @Value("${spring.security.oauth2.client.registration.google.clientId}")
    private String googleClientId;
    @Value("${spring.security.oauth2.client.registration.google.clientSecret}")
    private String googleClientSecret;

    @Value("${spring.security.oauth2.client.registration.naver.clientId}")
    private String naverClientId;
    @Value("${spring.security.oauth2.client.registration.naver.clientSecret}")
    private String naverClientSecret;

    @Value("${spring.security.oauth2.client.registration.kakao.clientId}")
    private String kakaoClientId;
    @Value("${spring.security.oauth2.client.registration.kakao.clientSecret}")
    private String kakaoClientSecret;

    private String googleRefreshTokenUrl = "https://oauth2.googleapis.com/token";
    private String kakaoRefreshTokenUrl = "https://kauth.kakao.com/oauth/token";
    private String naverRefreshTokenUrl = "https://nid.naver.com/oauth2.0/token";

    private String googleRevokeUrl = "https://accounts.google.com/o/oauth2/revoke";

}
