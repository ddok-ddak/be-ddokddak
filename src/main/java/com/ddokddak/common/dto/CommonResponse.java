package com.ddokddak.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

@Getter
@Setter
public class CommonResponse<T> {
    private CommonStatus status;
    private String message;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private T result;

    public CommonResponse(String message, @Nullable T result) {
        this.status = CommonStatus.SUCCESS;
        this.message = message;
        this.result = result;
    }
}