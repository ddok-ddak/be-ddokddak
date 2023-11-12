package com.ddokddak.auth.domain.dto;

import lombok.Builder;
import javax.validation.constraints.NotNull;

public record AuthenticationNumberResponse(
        @NotNull Long id
) {
    @Builder
    public AuthenticationNumberResponse {
    }
}
