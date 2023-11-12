package com.ddokddak.auth.domain.dto;

import lombok.Builder;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public record RegisterMemberRequest(
        @NotNull @Size(min = 5, max = 100) String email,
        @NotNull @Size(min = 3, max = 100) String password,
        @NotNull @Size(min = 1, max = 50) String nickname
) {
    @Builder
    public RegisterMemberRequest {}
}
