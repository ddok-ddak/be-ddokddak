package com.ddokddak.auth.service;

import com.ddokddak.common.props.OAuth2Properties;
import com.ddokddak.member.domain.enums.AuthProviderType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@RequiredArgsConstructor
@Service
public class OAuth2RevokeService {

    private final OAuth2Properties oAuth2Properties;

    public void requestRevokeUser(String accessToken, AuthProviderType authProviderType) {

        switch(authProviderType) {
            case GOOGLE -> deleteGoogleAccount(accessToken);
            case KAKAO -> deleteKakaoAccount(accessToken);
            case NAVER -> deleteNaverAccount(accessToken);
        }
    }

    public void deleteGoogleAccount(String accessToken) {

        String uriString = UriComponentsBuilder.fromHttpUrl(oAuth2Properties.getGoogleRevokeUrl())
                .queryParam("token", accessToken)
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        sendRevokeRequest(uriString,headers);
    }

    public void deleteNaverAccount(String accessToken) {

        String uriString = UriComponentsBuilder.fromHttpUrl(oAuth2Properties.getNaverRevokeUrl())
                .queryParam("client_id", oAuth2Properties.getNaverClientId())
                .queryParam("client_secret", oAuth2Properties.getNaverClientSecret())
                .queryParam("access_token", accessToken)
                .queryParam("service_provider", "NAVER")
                .queryParam("grant_type", "delete")
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        sendRevokeRequest(uriString, headers);
    }

    public void deleteKakaoAccount(String accessToken) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBearerAuth(accessToken);

        sendRevokeRequest(oAuth2Properties.getKakaoRevokeUrl(), headers);
    }

    private void sendRevokeRequest(String revokeUrl, HttpHeaders headers) {

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> responseEntity = restTemplate
                .exchange(revokeUrl, HttpMethod.POST, entity, String.class);
        HttpStatus statusCode = (HttpStatus) responseEntity.getStatusCode();
        String responseBody = responseEntity.getBody();
    }
}
