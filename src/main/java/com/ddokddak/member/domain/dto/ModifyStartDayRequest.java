package com.ddokddak.member.domain.dto;

import com.ddokddak.member.domain.enums.CustomOpt;
import lombok.Builder;

import javax.validation.constraints.NotNull;

public record ModifyStartDayRequest(
        @NotNull CustomOpt.StartDay startDay
) {
    @Builder
    public ModifyStartDayRequest {}
}