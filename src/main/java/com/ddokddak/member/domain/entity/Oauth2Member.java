package com.ddokddak.member.domain.entity;

import com.ddokddak.common.utils.SetStringConverter;
import com.ddokddak.member.domain.enums.AuthProviderType;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Set;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "oauth2_member")
public class Oauth2Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "member_id")
    private Long memberId;

    @Column(length = 100)
    private String email;

    @Column(name = "oauth2_id", unique = true, length = 200)
    private String oauth2Id;

    @Enumerated(EnumType.STRING)
    @ColumnDefault("'DEFAULT'")
    @Column(length = 100)
    @Builder.Default
    private AuthProviderType authProvider = AuthProviderType.DEFAULT;

    @NotNull
    @Column(length = 100)
    private String accessTokenType;

    @NotNull
    @Column(length = 256)
    private String accessTokenValue;

    @NotNull
    @Column
    private LocalDateTime accessTokenIssuedAt;

    @NotNull
    @Column
    private LocalDateTime accessTokenExpiresAt;

    @Column(length = 1000)
    @Convert(converter = SetStringConverter.class)
    private Set<String> accessTokenScope;

    @Size(max = 256)
    private String refreshTokenValue;

    @Column
    private LocalDateTime refreshTokenIssuedAt;

    @Builder.Default
    @ColumnDefault("0")
    private Boolean isDeleted = Boolean.FALSE;

    public void modifyByOAuth2AuthorizedClientAndAuthentication(OAuth2AuthorizedClient authorizedClient, Authentication principal) {

        ClientRegistration clientRegistration = authorizedClient.getClientRegistration();
        OAuth2AccessToken accessToken = authorizedClient.getAccessToken();
        OAuth2RefreshToken refreshToken = authorizedClient.getRefreshToken();

        //this.authProvider =
        AuthProviderType.getByCode(clientRegistration.getRegistrationId());
        //this.oauth2Id = principal.getName();
        this.accessTokenType = accessToken.getTokenType().getValue();
        this.accessTokenValue = accessToken.getTokenValue();
        this.accessTokenScope = accessToken.getScopes();
        this.accessTokenIssuedAt = accessToken.getIssuedAt() == null ? null : accessToken.getIssuedAt().atZone(ZoneOffset.UTC).toLocalDateTime();
        this.accessTokenExpiresAt = accessToken.getExpiresAt() == null ? null : accessToken.getExpiresAt().atZone(ZoneOffset.UTC).toLocalDateTime();
        this.refreshTokenValue = refreshToken != null ? refreshToken.getTokenValue() : null;
        this.refreshTokenIssuedAt = refreshToken != null && refreshToken.getIssuedAt() != null
                ? LocalDateTime.from(refreshToken.getIssuedAt()) : null;
    }

    public void modifyForDeletingAuthentication() {
        this.accessTokenType = null;
        this.accessTokenValue = null;
        this.accessTokenScope = null;
        this.accessTokenIssuedAt = null;
        this.accessTokenExpiresAt = null;
        this.refreshTokenValue = null;
        this.refreshTokenIssuedAt = null;
        this.isDeleted = Boolean.TRUE;
    }
}
