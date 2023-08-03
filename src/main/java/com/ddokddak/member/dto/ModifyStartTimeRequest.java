package com.ddokddak.member.dto;

import com.ddokddak.member.entity.CustomOpt;
import lombok.Builder;

import javax.validation.constraints.NotNull;

public record ModifyStartTimeRequest(
        @NotNull CustomOpt.StartTime startTime
    ) {
        @Builder
        public ModifyStartTimeRequest {}
    }
