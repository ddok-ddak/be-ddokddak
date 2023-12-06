package com.ddokddak.auth.service;

import com.ddokddak.auth.domain.oauth.UserPrincipal;
import com.ddokddak.auth.domain.oauth.OAuth2UserInfo;
import com.ddokddak.auth.domain.oauth.OAuth2UserInfoFactory;
import com.ddokddak.common.exception.CustomApiException;
import com.ddokddak.member.domain.enums.AuthProviderType;
import com.ddokddak.member.domain.entity.Member;
import com.ddokddak.member.service.MemberReadService;
import com.ddokddak.member.service.MemberWriteService;
import com.google.common.collect.ImmutableMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RequiredArgsConstructor
@Slf4j
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final MemberReadService memberReadService;
    private final MemberWriteService memberWriteService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {

        DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();
        // 써드파티에 OAuth2UserRequest 를 보내고 받은 응답값에 있는 Access Token으로 유저정보 get
        OAuth2User oAuth2User = delegate.loadUser(oAuth2UserRequest);
        return process(oAuth2UserRequest, oAuth2User);
    }

    // 획득한 유저정보를 Java Model과 매핑하고 프로세스 진행
    @Transactional
    public OAuth2User process(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {

        AuthProviderType authProviderType = AuthProviderType.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId().toUpperCase());
        OAuth2UserInfo userInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(authProviderType, oAuth2User.getAttributes());

        if (Objects.isNull(userInfo.getEmail()) || userInfo.getEmail().isEmpty()) {
            throw new CustomApiException("Email not found from OAuth2 provider");
        }
        // 유저 정보 조회
        Member member;
        if (memberReadService.existsUserByEmail(userInfo.getEmail())) {	// 이미 가입된 경우
            member = memberReadService.findUserByEmail(userInfo.getEmail());
            if (!authProviderType.equals(member.getAuthProvider())) {
                throw new CustomApiException("Wrong Match Auth Provider");
            }
        } else { // 가입되지 않은 경우
            member = memberWriteService.registerForOauth2(userInfo);
        }

        var attributes = this.buildNewAttributesMap(oAuth2User.getAttributes(), oAuth2UserRequest.getAdditionalParameters());
        return UserPrincipal.create(member, userInfo.getOauth2Id(), attributes);
    }

    private Map<String, Object> buildNewAttributesMap(Map<String, Object> attributes, Map<String, Object> additionalParameters) {

        var newAttributes = ImmutableMap.<String, Object> builder()
                .putAll(attributes)
                .putAll(additionalParameters)
                .build();

        return newAttributes;
    }

}