package com.ddokddak.auth.domain.oauth;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class CustomOAuth2Token {
    private String accessToken;
    private String tokenType;
    private int expiresIn;
    private String refreshToken;
    private int refreshTokenExpiresIn;
}
