package com.ddokddak.common.exception;
import com.ddokddak.common.exception.type.ExceptionType;

public class NoRollbackCustomApiException extends CustomApiException {

    public NoRollbackCustomApiException(ExceptionType exceptionType) {
        super(exceptionType);
    }
}
