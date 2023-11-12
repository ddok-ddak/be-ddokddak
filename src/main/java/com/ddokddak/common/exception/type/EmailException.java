package com.ddokddak.common.exception.type;

import org.springframework.http.HttpStatus;

public enum EmailException implements ExceptionType {
    NOT_VALID_ID(HttpStatus.BAD_REQUEST, "Not Valid Authentication ID", "E_NOT_VALID_ID"),
    EXCEEDED_TIME_LIMIT(HttpStatus.BAD_REQUEST, "Exceeded Limit Time(3 min) For Email Of Authentication", "E_TIME_LIMIT"),
    EXCEEDED_TRANSMISSION_LIMIT_COUNT(HttpStatus.TOO_MANY_REQUESTS, "Exceeded Transmission Limit Count For Email Of Authentication", "E_EXCEED_TRANSMISSION_LIMIT_COUNT"),
    EXCEEDED_RETRY_LIMIT_COUNT(HttpStatus.TOO_MANY_REQUESTS, "Exceeded Retry Limit Count For Email Of Authentication", "E_EXCEED_RETRY_LIMIT_COUNT"),
    NOT_VALID_AUTHENTICATION_NUMBER(HttpStatus.BAD_REQUEST, "Not Valid Authentication Number", "E_NOT_VALID_AUTHENTICATION_NUMBER"),
    WRONG_MATCH_AUTHENTICATION_PROVIDER(HttpStatus.INTERNAL_SERVER_ERROR, "Wrong Match Auth Provider", "E_WRONG_MATCH_AUTHENTICATION_PROVIDER"),
    FAIL_TO_CREATING_MAIL_FORM(HttpStatus.BAD_REQUEST, "Fail To Creating Mail Form", "E_FAIL_TO_CREATING_MAIL_FORM"),
    FAIL_TO_MAIL_AUTH(HttpStatus.BAD_REQUEST, "Fail To Mail Authentication", "E_FAIL_TO_MAIL_AUTH"),
    FAIL_TO_MAIL(HttpStatus.BAD_REQUEST, "Fail To Mail", "E_FAIL_TO_MAIL");

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
