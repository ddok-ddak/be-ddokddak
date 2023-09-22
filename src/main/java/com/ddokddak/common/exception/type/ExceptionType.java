package com.ddokddak.common.exception.type;

import org.springframework.http.HttpStatus;

public interface ExceptionType {
    HttpStatus getStatus();
    String getMessage();
    String getErrorCode();
}
