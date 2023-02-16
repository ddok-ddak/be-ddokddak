package com.ddokddak.common.exception;

import com.ddokddak.common.exception.type.ExceptionType;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomApiException extends RuntimeException{

    private final HttpStatus status;

    public CustomApiException(String message) {
        super(message);
        this.status= HttpStatus.BAD_REQUEST;
    }

    public CustomApiException(String message, HttpStatus status) {
        super(message);
        this.status= status;
    }

    public CustomApiException(ExceptionType exceptionType) {
        super(exceptionType.message());
        this.status= exceptionType.status();
    }

    public CustomApiException(String message, Throwable cause) {
        super(message, cause);
        this.status = HttpStatus.BAD_REQUEST;
    }

    public CustomApiException(String message, Throwable cause, HttpStatus status) {
        super(message, cause);
        this.status = status;
    }
}
