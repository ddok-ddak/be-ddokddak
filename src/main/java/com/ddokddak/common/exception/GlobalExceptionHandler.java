package com.ddokddak.common.exception;

import com.ddokddak.common.dto.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    // 공통 커스텀 예외를 기준으로 오류를 제어한다.
    @ExceptionHandler(CustomApiException.class)
    protected ResponseEntity<Object> handleCustomApiException(CustomApiException e, WebRequest request) {

        HttpHeaders headers = new HttpHeaders();
        return handleExceptionInternal(e, new CommonResponse<>(e.getMessage(), null), headers, e.getStatus(), request);
    }

    // 나머지 예외 처리는 오버라이드해서 커스텀할 수 있다.
}
