package com.ddokddak.member.dto;

import lombok.Builder;

import javax.validation.constraints.NotNull;

public record CheckEmailAuthenticationRequest(
        @NotNull String addressee,
        @NotNull String authenticationNumber,
        @NotNull String authenticationType

) {
    @Builder
    public CheckEmailAuthenticationRequest {}

}
