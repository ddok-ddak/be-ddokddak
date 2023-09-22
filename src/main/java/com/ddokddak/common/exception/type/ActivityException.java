package com.ddokddak.common.exception.type;

import org.springframework.http.HttpStatus;

public enum ActivityException implements ExceptionType {

    ACTIVITYRECORD_ID(HttpStatus.UNPROCESSABLE_ENTITY, "Not Valid ActivityRecord Id", "E_AR_ID"),
    USED_TIME_PERIOD(HttpStatus.UNPROCESSABLE_ENTITY, "Already Used Time Period", "E_AR_PERIOD"),
    WRONG_TIME_DATA(HttpStatus.BAD_REQUEST, "Not Valid Time Data", "E_AR_TIME");

    private final HttpStatus status;
    private final String message;
    private final String errorCode;

    ActivityException(HttpStatus status, String message, String errorCode) {
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