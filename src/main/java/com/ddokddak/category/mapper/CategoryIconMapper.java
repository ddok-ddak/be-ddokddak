package com.ddokddak.category.mapper;

import com.ddokddak.category.domain.dto.CategoryIconReadResponse;
import com.ddokddak.category.domain.entity.CategoryIcon;

import java.util.List;

public class CategoryIconMapper {
    public static CategoryIconReadResponse toReadResponse(CategoryIcon categoryIcon) {

        if (categoryIcon == null) return CategoryIconReadResponse.builder().build();

        return CategoryIconReadResponse.builder()
                .id(categoryIcon.getId())
                .iconGroup(categoryIcon.getIconGroup())
                .filename(categoryIcon.getFilename())
                .path(categoryIcon.getPath())
                .originalFilename(categoryIcon.getOriginalFilename())
                .build();
    }
}
