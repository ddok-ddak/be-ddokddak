package com.ddokddak.auth.service;

import com.ddokddak.auth.domain.oauth.CustomOAuth2Token;
import com.ddokddak.common.exception.CustomApiException;
import com.ddokddak.common.exception.type.OAuth2Exception;
import com.ddokddak.common.props.OAuth2Properties;
import com.ddokddak.member.domain.entity.OAuth2Member;
import com.ddokddak.member.domain.enums.AuthProviderType;
import com.ddokddak.member.repository.OAuth2MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class OAuth2RefreshService {
    private final OAuth2Properties oAuth2Properties;
    private final OAuth2MemberRepository oauth2MemberRepository;

    @Transactional
    public String refreshOAuth2AccessToken(Long memberId, AuthProviderType authProviderType) {

        OAuth2Member oAuth2Member = oauth2MemberRepository.findByMemberId(memberId).orElse(null);
        if (oAuth2Member == null) {
            throw new CustomApiException(OAuth2Exception.NON_EXISTS_OAUTH2_MEMBER);
        } else if (oAuth2Member.getRefreshTokenExpiredAt()!=null &&
                oAuth2Member.getRefreshTokenExpiredAt().isAfter(LocalDateTime.now())) {
            throw new CustomApiException(OAuth2Exception.EXPIRED_SOCIAL_REFRESH_TOKEN);
        }
        var refreshToken = oAuth2Member.getRefreshTokenValue();
        String resolvedUrl = "";
        switch(authProviderType) {
            case GOOGLE -> resolvedUrl = resolveRequestUrl(oAuth2Properties.getGoogleRefreshTokenUrl(),
                    oAuth2Properties.getGoogleClientId(),
                    oAuth2Properties.getGoogleClientSecret(),
                    refreshToken);

            case KAKAO -> resolvedUrl = resolveRequestUrl(oAuth2Properties.getKakaoRefreshTokenUrl(),
                    oAuth2Properties.getKakaoClientId(),
                    oAuth2Properties.getKakaoClientSecret(),
                    refreshToken);

            case NAVER -> resolvedUrl = resolveRequestUrl(oAuth2Properties.getNaverRefreshTokenUrl(),
                    oAuth2Properties.getNaverClientId(),
                    oAuth2Properties.getNaverClientSecret(),
                    refreshToken);
        }

        CustomOAuth2Token oAuth2Token = getAccessToken(resolvedUrl);
        // OAuth2 토큰 데이터 업데이트 수행
        oAuth2Member.modifyForRefreshProc(oAuth2Token);

        return oAuth2Token.getAccessToken();
    }

    private String resolveRequestUrl(String url, String clientId, String clientSecret, String refreshToken) {
        return UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("client_id", clientId)
                .queryParam("client_secret", clientSecret)
                .queryParam("refresh_token", refreshToken)
                .queryParam("grant_type", "refresh_token")
                .toUriString();
    }

    private CustomOAuth2Token getAccessToken(String refreshUrl) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<CustomOAuth2Token> responseEntity = restTemplate
                .exchange(refreshUrl, HttpMethod.POST, entity, CustomOAuth2Token.class);

        HttpStatus statusCode = (HttpStatus) responseEntity.getStatusCode();

        return responseEntity.getBody();
    }
}
