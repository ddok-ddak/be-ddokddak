package com.ddokddak.member.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public record SigninResponse(
        @NotNull @Size(min = 3, max = 100) String email,
        @NotNull @Size(min = 3, max = 100) String password,
        String authorization) {
    @Builder
    public SigninResponse {}
}
