package com.ddokddak.common.exception;
import com.ddokddak.common.exception.type.ExceptionType;

public class NotValidRequestException extends CustomApiException {

    public NotValidRequestException(ExceptionType exceptionType) {
        super(exceptionType);
    }
}
