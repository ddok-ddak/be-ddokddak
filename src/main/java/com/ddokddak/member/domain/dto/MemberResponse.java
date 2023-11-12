package com.ddokddak.member.domain.dto;

import com.ddokddak.member.domain.enums.*;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public record MemberResponse(
        @NotNull @Size(min = 5, max = 100) String email,
        @NotNull @Size(min = 1, max = 50) String nickname,
        RoleType role,
        Status status,
        AuthProviderType authProviderType,
        TemplateType templateType,
        CustomOpt.StartDay startDay,
        CustomOpt.StartTime startTime
) {
    @Builder
    public MemberResponse {}
}
