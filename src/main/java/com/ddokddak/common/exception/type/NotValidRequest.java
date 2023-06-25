package com.ddokddak.common.exception.type;

import org.springframework.http.HttpStatus;

public enum NotValidRequest implements ExceptionType {

    ACTIVITYRECORD_ID(HttpStatus.UNPROCESSABLE_ENTITY, "Not Valid ActivityRecord Id"),
    MEMBER_ID(HttpStatus.UNPROCESSABLE_ENTITY, "Not Valid Member Id"),
    CATEGORY_ID(HttpStatus.UNPROCESSABLE_ENTITY, "Not Valid Category Id"),

    CATEGORY_DATA(HttpStatus.NOT_FOUND, "Not Exist Category Data"),
    CATEGORY_NAME(HttpStatus.UNPROCESSABLE_ENTITY, "Not Valid Category Name"),
    MAIN_CATEGORY_ID(HttpStatus.UNPROCESSABLE_ENTITY, "Not Valid Main Category Id"),

    ALREADY_EXISTS(HttpStatus.CONFLICT, "Already CategoryTemplate Exists"),
    USED_NAME_CONFLICTS(HttpStatus.UNPROCESSABLE_ENTITY, "Already Used Name"),
    USED_TIME_PERIOD(HttpStatus.UNPROCESSABLE_ENTITY, "Already Used Time Period"),

    WRONG_TIME_DATA(HttpStatus.BAD_REQUEST, "Not Valid Time Data"),

    NULL_DATA(HttpStatus.BAD_REQUEST, "Null Data Exists When Should Be Not Null"),
    IRONIC_REQUEST(HttpStatus.BAD_REQUEST, "Not Valid Request : Ironic"),
    UNABLE_REQUEST(HttpStatus.BAD_REQUEST, "Not Valid Request : Unable");

    private final HttpStatus status;
    private final String message;

    NotValidRequest(HttpStatus status, String message) {
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