package com.ddokddak.category.mapper;

import com.ddokddak.category.dto.ReadCategoryResponse;
import com.ddokddak.category.entity.Category;

import java.util.Collections;

public class CategoryMapper {

    public static final ReadCategoryResponse toReadCategoryResponse(Category category, int level) {

        return ReadCategoryResponse.builder()
                .categoryId(category.getId())
                .name(category.getName())
                .color(category.getColor())
                .mainCategoryId(category.getMainCategory() == null ? null : category.getMainCategory().getId())
                .level(category.getLevel())
                .subCategories(category.getLevel() < level
                        ? category.getSubCategories().stream().map(CategoryMapper::toReadCategoryResponse).toList()
                        : Collections.emptyList())
                .build();
    }

    public static final ReadCategoryResponse toReadCategoryResponse(Category category) {

        return CategoryMapper.toReadCategoryResponse(category, 2);
    }
}
