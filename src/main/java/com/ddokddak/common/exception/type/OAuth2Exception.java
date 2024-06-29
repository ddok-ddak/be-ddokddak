package com.ddokddak.common.exception.type;

import org.springframework.http.HttpStatus;

public enum OAuth2Exception implements ExceptionType {

    NON_EXISTS_OAUTH2_MEMBER(HttpStatus.BAD_REQUEST, "Invalid Refresh Token Request", "E_INVALID_TOKEN"),
    EXPIRED_SOCIAL_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "Social Refresh Token Expired", "E_SR_TOKEN_EXPIRED");

    private final HttpStatus status;
    private final String message;
    private final String errorCode;

    OAuth2Exception(HttpStatus status, String message, String errorCode) {
        this.status = status;
        this.message = message;
        this.errorCode = errorCode;
    }

    @Override
    public HttpStatus getStatus() {
        return this.status;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public String getErrorCode() {
        return this.errorCode;
    }
}