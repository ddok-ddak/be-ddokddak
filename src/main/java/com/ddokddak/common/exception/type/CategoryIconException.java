package com.ddokddak.common.exception.type;

import org.springframework.http.HttpStatus;

public enum CategoryIconException implements ExceptionType {

    ICON_ID(HttpStatus.UNPROCESSABLE_ENTITY, "Not Valid Category Icon Id", "E_CATEGORY_ICON_ID");

    private final HttpStatus status;
    private final String message;
    private final String errorCode;

    CategoryIconException(HttpStatus status, String message, String errorCode) {
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
