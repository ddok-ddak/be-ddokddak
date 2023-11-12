package com.ddokddak.auth.domain.dto;

import lombok.Builder;

import javax.validation.constraints.NotNull;

public record CheckAuthenticationNumberRequest(
        @NotNull String email,
        @NotNull String authenticationNumber,
        @NotNull String authenticationType
) {
    @Builder
    public CheckAuthenticationNumberRequest {}

}
