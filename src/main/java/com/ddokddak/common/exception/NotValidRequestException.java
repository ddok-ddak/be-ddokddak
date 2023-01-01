package com.ddokddak.common.exception;

import org.springframework.http.HttpStatus;

public class NotValidRequestException extends CustomApiException {

    public NotValidRequestException(String message) {
        super(message);
    }

    public NotValidRequestException(String message, HttpStatus status) {
        super(message, status);
    }

}
