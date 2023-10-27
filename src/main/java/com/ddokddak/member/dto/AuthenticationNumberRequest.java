package com.ddokddak.member.dto;

import lombok.Builder;

import javax.validation.constraints.NotNull;

public record AuthenticationNumberRequest(
        @NotNull String addressee,
        @NotNull String authenticationType
){
    @Builder
    public AuthenticationNumberRequest {}
}