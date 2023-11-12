package com.ddokddak.member.domain.dto;


import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

public record ValidateMemberRequest(
        @Email @Size(min = 5, max = 100) String email,
        @Size(min = 1, max = 100) String nickname)
{
    @Builder
    public ValidateMemberRequest {}
}
