package com.ddokddak.common.exception.type;

import org.springframework.http.HttpStatus;

public enum DbException implements ExceptionType {

    BATCH_INSERTION_FAIL(HttpStatus.BAD_REQUEST, "Batch Insertion Failed", "E_DB_BATCH");

    private final HttpStatus status;
    private final String message;
    private final String errorCode;

    DbException(HttpStatus status, String message, String errorCode) {
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
    public String getErrorCode() { return this.errorCode; }
}
