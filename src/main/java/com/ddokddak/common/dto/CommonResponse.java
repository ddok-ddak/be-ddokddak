package com.ddokddak.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

@Getter
@Setter
public class CommonResponse<T> {

    private String message;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    T result;

    public CommonResponse(String message, @Nullable T result) {
        this.message = message;
        this.result = result;
    }
}