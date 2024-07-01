package com.ddokddak.auth.domain.oauth;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Map;

public abstract class AbstractOAuth2Request {
    private URI requestUri;
    private Map<String, String> params;
    private RestTemplate restTemplate;

    public abstract URI resolveRequestUri();

    public ResponseEntity<String> requestOAuth2Withdrawal(URI requestUri, String data) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<String> entity = new HttpEntity<>(data, headers);
        return restTemplate.exchange(requestUri, HttpMethod.POST, entity, String.class);
    }
}
