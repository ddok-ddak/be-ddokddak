package com.ddokddak.common.exception;

import com.ddokddak.common.exception.type.BaseException;
import com.ddokddak.common.exception.type.ExceptionType;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomApiException extends RuntimeException {

    private final HttpStatus status;
    private ExceptionType exceptionType;

    public CustomApiException(ExceptionType exceptionType) {
        super(exceptionType.getMessage());
        this.exceptionType= exceptionType;
        this.status= exceptionType.getStatus();
    }

    public CustomApiException(String message) {
        super(message);
        this.exceptionType = BaseException.SERVER_ERROR;
        this.status= HttpStatus.BAD_REQUEST;
    }

    public CustomApiException(String message, HttpStatus status) {
        super(message);
        this.exceptionType = BaseException.SERVER_ERROR;
        this.status= status;
    }

    public CustomApiException(String message, Throwable cause) {
        super(message, cause);
        this.exceptionType = BaseException.SERVER_ERROR;
        this.status = exceptionType.getStatus();
    }

    public CustomApiException(String message, Throwable cause, HttpStatus status) {
        super(message, cause);
        this.exceptionType = BaseException.SERVER_ERROR;
        this.status = status;
    }
}
