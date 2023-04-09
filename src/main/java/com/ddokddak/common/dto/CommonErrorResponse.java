package com.ddokddak.common.dto;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CommonErrorResponse {
    private CommonStatus status;
    private String message;
    private int statusCode;

    public CommonErrorResponse(String message, HttpStatus status) {
        this.status = CommonStatus.FAIL;
        this.message = message;
        this.statusCode = status.value();
    }
}