package com.ddokddak.common.exception.type;

import org.springframework.http.HttpStatus;

public enum FailedJdbcExec implements ExceptionType {

    BATCH_INSERTION_FAIL(HttpStatus.BAD_REQUEST, "Batch Insertion Failed");

    private final HttpStatus status;
    private final String message;

    FailedJdbcExec(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    @Override
    public HttpStatus status() {
        return this.status;
    }
    @Override
    public String message() {
        return this.message;
    }
}
