package com.ddokddak.common.props;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@ConfigurationProperties(prefix = "auth")
public class AuthProperties {

    private Token token = new Token();
    private OAuth2 oauth2 = new OAuth2();

    @Getter
    @Setter
    public static class Token {

        private String tokenSecret;
        private long tokenExpirationMS;
    }

    public static class OAuth2 {

        private List<String> authorizedRedirectUris = new ArrayList<>();

        public OAuth2 authorizedRedirectUris(List<String> authorizedRedirectUris) {
            this.authorizedRedirectUris = authorizedRedirectUris;
            return this;
        }
        public List<String> getAuthorizedRedirectUris() {
            return authorizedRedirectUris;
        }
    }

    public Token getToken() {
        return token;
    }
    public OAuth2 getOauth2() {
        return oauth2;
    }

}