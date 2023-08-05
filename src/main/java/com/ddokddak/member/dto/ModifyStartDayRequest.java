package com.ddokddak.member.dto;

import com.ddokddak.member.entity.CustomOpt;
import lombok.Builder;

import javax.validation.constraints.NotNull;

public record ModifyStartDayRequest(
        @NotNull CustomOpt.StartDay startDay
) {
    @Builder
    public ModifyStartDayRequest {}
}