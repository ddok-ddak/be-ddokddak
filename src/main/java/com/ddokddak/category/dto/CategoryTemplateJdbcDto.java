package com.ddokddak.category.dto;

import lombok.Builder;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;

public record CategoryTemplateJdbcDto(
        @NotNull Long memberId,
        @NotNull String name,
        @NotNull String color,
        @NotNull String iconName,
        @NotNull Integer level,
        @Nullable String mainCategoryName
) {
    @Builder
    public CategoryTemplateJdbcDto{}
}
