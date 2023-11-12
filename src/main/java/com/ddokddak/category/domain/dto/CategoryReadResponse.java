package com.ddokddak.category.domain.dto;
import lombok.Builder;
import java.util.List;

public record CategoryReadResponse(
        Long categoryId,
        String name,
        String color,
        String highlightColor,
        String iconName,
        Integer level,
        Long mainCategoryId,
        List<CategoryReadResponse> subCategories
) {
    @Builder
    public CategoryReadResponse {}
}
