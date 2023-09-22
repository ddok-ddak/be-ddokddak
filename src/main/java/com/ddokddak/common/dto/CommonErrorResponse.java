package com.ddokddak.common.dto;

import com.ddokddak.common.exception.type.ExceptionType;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CommonErrorResponse {
    private CommonStatus status;
    private String message;
    private String errorCode;

    public CommonErrorResponse(String message, ExceptionType exceptionType) {
        this.status = CommonStatus.FAIL;
        this.message = message;
        this.errorCode = exceptionType.getErrorCode();
    }
}