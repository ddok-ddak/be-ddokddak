package com.ddokddak.member.mapper;

import com.ddokddak.auth.domain.oauth.UserPrincipal;
import com.ddokddak.member.domain.entity.Oauth2Member;
import com.ddokddak.member.domain.enums.AuthProviderType;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Set;

public class Oauth2MemberMapper {
    public static OAuth2AuthorizedClient toOauth2AuthorizedClient(Oauth2Member oauth2Member, ClientRegistration registration) {

        if (registration == null) {
            throw new DataRetrievalFailureException("The ClientRegistration with id '" + oauth2Member.getAuthProvider() + "' exists in the data source, however, it was not found in the ClientRegistrationRepository.");
        }

        OAuth2AccessToken.TokenType tokenType = null;
        if (OAuth2AccessToken.TokenType.BEARER.getValue().equalsIgnoreCase(oauth2Member.getAccessTokenType())) {
            tokenType = OAuth2AccessToken.TokenType.BEARER;
        }
        String tokenValue = oauth2Member.getAccessTokenValue();
        Instant issuedAt = oauth2Member.getAccessTokenIssuedAt().toInstant(ZoneOffset.UTC);
        Instant expiresAt = oauth2Member.getAccessTokenExpiresAt().toInstant(ZoneOffset.UTC);

        Set<String> scopes = oauth2Member.getAccessTokenScope();
        OAuth2AccessToken accessToken = new OAuth2AccessToken(tokenType, tokenValue, issuedAt, expiresAt, scopes);

        OAuth2RefreshToken refreshToken = null;
        String refreshTokenValue = oauth2Member.getRefreshTokenValue();
        if (refreshTokenValue != null) {
            issuedAt = null;
            LocalDateTime refreshTokenIssuedAt = oauth2Member.getRefreshTokenIssuedAt();
            if (refreshTokenIssuedAt != null) {
                issuedAt = refreshTokenIssuedAt.toInstant(ZoneOffset.UTC);
            }
            refreshToken = new OAuth2RefreshToken(tokenValue, issuedAt);
        }

        String principalName = oauth2Member.getOauth2Id();
        return new OAuth2AuthorizedClient(registration, principalName, accessToken, refreshToken);
    }

    public static Oauth2Member fromAuthorizedClientAndPrincipal(OAuth2AuthorizedClient authorizedClient, Authentication principal) {

        ClientRegistration clientRegistration = authorizedClient.getClientRegistration();
        OAuth2AccessToken accessToken = authorizedClient.getAccessToken();
        OAuth2RefreshToken refreshToken = authorizedClient.getRefreshToken();
        UserPrincipal userPrincipal = (UserPrincipal) principal.getPrincipal();
        return Oauth2Member.builder()
                .memberId(userPrincipal.getId())
                .email(principal.getName())
                .oauth2Id(userPrincipal.getAttributes().get("sub").toString())
                .authProvider(AuthProviderType.getByCode(clientRegistration.getRegistrationId()))
                .accessTokenType(accessToken.getTokenType().getValue())
                .accessTokenValue(accessToken.getTokenValue())
                .accessTokenScope(accessToken.getScopes())
                .accessTokenIssuedAt(accessToken.getIssuedAt() == null ? null : accessToken.getIssuedAt().atZone(ZoneOffset.UTC).toLocalDateTime())
                .accessTokenExpiresAt(accessToken.getExpiresAt() == null ? null : accessToken.getExpiresAt().atZone(ZoneOffset.UTC).toLocalDateTime())
                .refreshTokenValue(refreshToken != null ? refreshToken.getTokenValue() : null)
                .refreshTokenIssuedAt(refreshToken != null && refreshToken.getIssuedAt() != null
                        ? LocalDateTime.from(refreshToken.getIssuedAt().atZone(ZoneOffset.UTC).toLocalDateTime()) : null)
                .build();
    }
}
