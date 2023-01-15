package com.ddokddak.category.dto;

import lombok.Builder;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;

public record CategoryRelationModifyRequest(
        @NotNull Long categoryId,
        @NotNull Integer level,
        @Nullable Long mainCategoryId
) {
    @Builder
    public CategoryRelationModifyRequest {}
}
