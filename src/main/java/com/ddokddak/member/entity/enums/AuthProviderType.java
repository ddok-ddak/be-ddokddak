package com.ddokddak.member.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AuthProviderType {
    DEFAULT("default"),
    GOOGLE("google"),
    NAVER("naver"),
    KAKAO("kakao");

    private final String code;

    public static AuthProviderType getByCode(String code) {
        for(AuthProviderType authProvider : values()) {
            if(authProvider.code.equals(code)) return authProvider;
        }
        return DEFAULT;
    }
}