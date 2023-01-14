package com.ddokddak.common.exception.type;

import org.springframework.http.HttpStatus;

public enum NotValidRequest implements ExceptionType {

    MEMBER_ID(HttpStatus.UNPROCESSABLE_ENTITY, "Not Valid Member Id"),

    CATEGORY_ID(HttpStatus.UNPROCESSABLE_ENTITY, "Not Valid Category Id"),
    CATEGORY_NAME(HttpStatus.UNPROCESSABLE_ENTITY, "Not Valid Category Name"),
    MAIN_CATEGORY_ID(HttpStatus.UNPROCESSABLE_ENTITY, "Not Valid Main Category Id"),

    USED_NAME_CONFLICTS(HttpStatus.UNPROCESSABLE_ENTITY, "Already Used Name"),

    NULL_DATA(HttpStatus.BAD_REQUEST, "Null Data Exists When Should Be Not Null"),
    IRONIC_REQUEST(HttpStatus.BAD_REQUEST, "Not Valid Request : Ironic"),
    UNABLE_REQUEST(HttpStatus.BAD_REQUEST, "Not Valid Request : Unable");

    private final HttpStatus status;
    private final String message;

    NotValidRequest(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public HttpStatus status() {
        return this.status;
    }
    public String message() {
        return this.message;
    }
}