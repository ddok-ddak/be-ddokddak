package com.ddokddak.category.dto;

import lombok.Builder;

import javax.validation.constraints.NotNull;

public record CategoryValueModifyRequest(
        @NotNull Long categoryId,
        @NotNull String name,
        @NotNull String color
) {
    @Builder
    public CategoryValueModifyRequest {}
}
