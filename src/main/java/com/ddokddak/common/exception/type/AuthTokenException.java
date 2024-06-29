package com.ddokddak.common.exception.type;

import org.springframework.http.HttpStatus;

public enum AuthTokenException implements ExceptionType {

    INVALID_REFRESH_TOKEN(HttpStatus.BAD_REQUEST, "Invalid Refresh Token Request", "E_INVALID_TOKEN"),
    EXPIRED_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "Default Refresh Token Expired", "E_DR_TOKEN_EXPIRED");

    private final HttpStatus status;
    private final String message;
    private final String errorCode;

    AuthTokenException(HttpStatus status, String message, String errorCode) {
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
