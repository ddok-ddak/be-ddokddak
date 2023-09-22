package com.ddokddak.common.exception.type;

import org.springframework.http.HttpStatus;

public enum MemberException implements ExceptionType{
    MEMBER_ID(HttpStatus.UNPROCESSABLE_ENTITY, "Not Valid Member Id", "E_MEMBER_ID_N"),
    ALREADY_EXISTS_EMAIL(HttpStatus.UNPROCESSABLE_ENTITY, "Already Exists Email", "E_MEMBER_EMAIL_C"),
    ALREADY_EXISTS_NAME(HttpStatus.UNPROCESSABLE_ENTITY, "Already Exists Nickname", "E_MEMBER_NAME_C");

    private final HttpStatus status;
    private final String message;
    private final String errorCode;

    MemberException(HttpStatus status, String message, String errorCode) {
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
