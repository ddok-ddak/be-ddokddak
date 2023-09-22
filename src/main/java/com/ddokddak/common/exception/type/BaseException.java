package com.ddokddak.common.exception.type;

import org.springframework.http.HttpStatus;

public enum BaseException implements ExceptionType {

    SERVER_ERROR(HttpStatus.BAD_REQUEST, "Server Error", "E_NOT_DEFINED"),
    INVALID_INPUT(HttpStatus.BAD_REQUEST, "Validation Failed", "E_VALIDATION"),
    NULL_DATA(HttpStatus.BAD_REQUEST, "Null Data Exists When Should Be Not Null", "E_NULL"),
    IRONIC_REQUEST(HttpStatus.BAD_REQUEST, "Not Valid Request : Ironic", "E_IRONIC"),
    UNABLE_REQUEST(HttpStatus.BAD_REQUEST, "Not Valid Request : Unable", "E_UNABLE");

    private final HttpStatus status;
    private final String message;
    private final String errorCode;

    BaseException(HttpStatus status, String message, String errorCode) {
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
