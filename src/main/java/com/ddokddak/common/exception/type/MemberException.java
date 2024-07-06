package com.ddokddak.common.exception.type;

import org.springframework.http.HttpStatus;

public enum MemberException implements ExceptionType{
    MEMBER_ID(HttpStatus.UNPROCESSABLE_ENTITY, "Not Valid Member Id", "E_MEMBER_ID_N"),
    ALREADY_EXISTS_EMAIL(HttpStatus.UNPROCESSABLE_ENTITY, "Already Exists Email", "E_MEMBER_EMAIL_C"),
    ALREADY_EXISTS_NAME(HttpStatus.UNPROCESSABLE_ENTITY, "Already Exists Nickname", "E_MEMBER_NAME_C"),
    WRONG_AUTH_PROVIDER(HttpStatus.BAD_REQUEST, "Wrong Match Auth Provider", "E_WRONG_PROVIDER"),
    EMPTY_OAUTH2_EMAIL(HttpStatus.BAD_REQUEST, "Email not found from OAuth2 provider", "E_EMPTY_EMAIL"),
    MEMBER_WITHDREW(HttpStatus.UNPROCESSABLE_ENTITY, "Withdrew within 1 month", "E_MEMBER_WITHDREW"),
    FAILED_ID_PASSWORD(HttpStatus.UNPROCESSABLE_ENTITY, "Failed to authenticate with ID & PW", "E_FAILED_AUTH"),
    LOCKED_MEMBER(HttpStatus.UNPROCESSABLE_ENTITY, "More than 5 times failure with ID & PW", "E_LOCKED_MEMBER"),
    TEST_ACCOUNT(HttpStatus.BAD_REQUEST, "Impossible to withdraw test account", "E_TEST_ACCOUNT"),
    DISABLED_MEMBER(HttpStatus.BAD_REQUEST, "Disabled member account, Not accessible", "E_DISABLED_MEMBER");

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
