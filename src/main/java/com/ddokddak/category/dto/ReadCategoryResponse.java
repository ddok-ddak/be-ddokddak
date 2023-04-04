package com.ddokddak.category.dto;
import lombok.Builder;
import java.util.List;

public record ReadCategoryResponse(
        Long categoryId,
        String name,
        String color,
        Integer level,
        Long mainCategoryId,
        List<ReadCategoryResponse> subCategories
) {
    @Builder
    public ReadCategoryResponse {}
}
