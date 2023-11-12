package com.ddokddak.auth.domain.dto;

import com.ddokddak.auth.domain.enums.EmailAuthenticationType;
import lombok.Builder;

import javax.validation.constraints.NotNull;

public record AuthenticationNumberRequest(
        @NotNull String email,
        @NotNull EmailAuthenticationType authenticationType
){
    @Builder
    public AuthenticationNumberRequest {}
}