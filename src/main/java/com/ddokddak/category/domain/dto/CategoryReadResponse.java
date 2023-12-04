package com.ddokddak.category.domain.dto;
import com.ddokddak.category.domain.entity.CategoryIcon;
import lombok.Builder;
import java.util.List;

public record CategoryReadResponse(
        Long categoryId,
        String name,
        String color,
        String highlightColor,
        CategoryIconReadResponse iconFile,
        Integer level,
        Long mainCategoryId,
        List<CategoryReadResponse> subCategories
) {
    @Builder
    public CategoryReadResponse {}
}
