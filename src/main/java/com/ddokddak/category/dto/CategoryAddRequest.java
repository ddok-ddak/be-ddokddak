package com.ddokddak.category.dto;

import lombok.Builder;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;

public record CategoryAddRequest(
        @NotNull String name,
        @NotNull String color,
        @NotNull Integer level,
        @Nullable Long mainCategoryId,
        @NotNull Long memberId
){
    @Builder
    public CategoryAddRequest {}

}
