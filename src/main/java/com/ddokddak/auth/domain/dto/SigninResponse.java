package com.ddokddak.auth.domain.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public record SigninResponse(
        @NotNull @Size(min = 3, max = 100) String email,
        String accessToken) {
    @Builder
    public SigninResponse {}
}
