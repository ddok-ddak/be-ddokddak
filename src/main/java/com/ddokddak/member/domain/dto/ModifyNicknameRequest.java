package com.ddokddak.member.domain.dto;

import lombok.Builder;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public record ModifyNicknameRequest(
        @NotNull @Size(min = 1, max = 50) String nickname
) {
    @Builder
    public ModifyNicknameRequest {}
}
