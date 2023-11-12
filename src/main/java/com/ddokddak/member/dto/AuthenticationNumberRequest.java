package com.ddokddak.member.dto;

import lombok.Builder;

import javax.validation.constraints.NotNull;

public record AuthenticationNumberRequest(
        @NotNull String email,
        @NotNull String authenticationType
){
    @Builder
    public AuthenticationNumberRequest {}
}