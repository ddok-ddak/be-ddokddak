package com.ddokddak.common.exception.type;

import org.springframework.http.HttpStatus;

public enum CategoryException implements ExceptionType {

    CATEGORY_ID(HttpStatus.UNPROCESSABLE_ENTITY, "Not Valid Category Id", "E_CATEGORY_ID"),
    CATEGORY_DATA(HttpStatus.NOT_FOUND, "Not Exist Category Data", "E_CATEGORY_ID"),
    CATEGORY_NAME(HttpStatus.UNPROCESSABLE_ENTITY, "Not Valid Category Name", ""),
    MAIN_CATEGORY_ID(HttpStatus.UNPROCESSABLE_ENTITY, "Not Valid Main Category Id", ""),

    ALREADY_EXISTS(HttpStatus.CONFLICT, "Already CategoryTemplate Exists", ""),
    USED_NAME_CONFLICTS(HttpStatus.UNPROCESSABLE_ENTITY, "Already Used Name", "");

    private final HttpStatus status;
    private final String message;
    private final String errorCode;

    CategoryException(HttpStatus status, String message, String errorCode) {
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
