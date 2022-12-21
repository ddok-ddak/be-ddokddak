package com.ddokddak.category.dto;

import lombok.Builder;

public record CategoryModifyRequest(
        Long categoryId,
        String name,
        String color,
        Integer level,
        Long mainCategoryId,
        Long memberId
) {
    @Builder
    public CategoryModifyRequest{}
}
