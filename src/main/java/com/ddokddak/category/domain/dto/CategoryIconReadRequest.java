package com.ddokddak.category.domain.dto;

import lombok.Builder;

import javax.annotation.Nullable;

public record CategoryIconReadRequest(
        @Nullable String iconGroup
) {
    @Builder
    public CategoryIconReadRequest {}
}
