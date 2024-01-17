package com.ddokddak.category.domain.dto;

import lombok.Builder;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;

public record CategoryAddRequest(
        @NotNull String name,
        @NotNull String color,
        @NotNull String highlightColor,
        @NotNull Long iconId,
        @NotNull Integer level,
        @Nullable Long mainCategoryId
){
    @Builder
    public CategoryAddRequest {}

}
