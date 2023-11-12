package com.ddokddak.member.domain.dto;

import com.ddokddak.member.domain.enums.CustomOpt;
import lombok.Builder;

import javax.validation.constraints.NotNull;

public record ModifyStartTimeRequest(
        @NotNull CustomOpt.StartTime startTime
    ) {
        @Builder
        public ModifyStartTimeRequest {}
    }
