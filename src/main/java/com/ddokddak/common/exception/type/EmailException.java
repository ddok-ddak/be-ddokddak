package com.ddokddak.common.exception.type;

import org.springframework.http.HttpStatus;

public enum EmailException implements ExceptionType {

    EXCEEDED_TRANSMISSION_LIMIT_COUNT(HttpStatus.TOO_MANY_REQUESTS, "Exceeded Transmission Limit Count For Email Of Authentication", "E_EXCEED_TRANSMISSION_LIMIT_COUNT"),
    EXCEEDED_RETRY_LIMIT_COUNT(HttpStatus.TOO_MANY_REQUESTS, "Exceeded Retry Limit Count For Email Of Authentication", "E_EXCEED_RETRY_LIMIT_COUNT"),
    NOT_VALID_AUTHENTICATION_NUMBER(HttpStatus.BAD_REQUEST, "Not Valid Authentication Number", "E_NOT_VALID_AUTHENTICATION_NUMBER"),
    WRONG_MATCH_AUTHENTICATION_PROVIDER(HttpStatus.INTERNAL_SERVER_ERROR, "Wrong Match Auth Provider", "E_WRONG_MATCH_AUTHENTICATION_PROVIDER"),
    FAIL_TO_CREATING_MAIL_FORM(HttpStatus.INTERNAL_SERVER_ERROR, "Fail To Creating Mail Form", "E_FAIL_TO_CREATING_MAIL_FORM"),
    FAIL_TO_MAIL(HttpStatus.INTERNAL_SERVER_ERROR, "Fail To Mail", "E_FAIL_TO_MAIL");

    private final HttpStatus status;
    private final String message;
    private final String errorCode;

    EmailException(HttpStatus status, String message, String errorCode) {
        this.status = status;
        this.message = message;
        this.errorCode = errorCode;
    }

    @Override
    public HttpStatus getStatus() { return this.status; }
    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public String getErrorCode() { return this.errorCode; }
}
