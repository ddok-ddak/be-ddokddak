package com.ddokddak.category.domain.dto;

import lombok.Builder;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;

public record ModifyCategoryRelationRequest(
        @NotNull Long categoryId,
        @NotNull Integer level,
        @Nullable Long mainCategoryId
) {
    @Builder
    public ModifyCategoryRelationRequest {}
}
