package com.ddokddak.common.dto;

import org.springframework.http.HttpStatus;

public class CommonErrorResponse {
    private String message;
    private int statusCode;

    public CommonErrorResponse(String message, HttpStatus status) {
        this.message = message;
        this.statusCode = status.value();
    }
}
