package com.ddokddak.member.dto;

import lombok.Builder;

import javax.validation.constraints.NotNull;

public record RequestAuthenticationNumberRequest(
        @NotNull String email,
        @NotNull String authenticationType
) {
    @Builder
    public RequestAuthenticationNumberRequest {
    }
}