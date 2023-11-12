package com.ddokddak.member.dto;

import lombok.Builder;

import javax.validation.constraints.NotNull;

public record CheckEmailAuthenticationRequest(
        @NotNull Long authenticationRequestId,
        @NotNull String authenticationNumber

) {
    @Builder
    public CheckEmailAuthenticationRequest {}

}
