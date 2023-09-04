package com.ddokddak.category.mapper;

import com.ddokddak.category.dto.CategoryAddRequest;
import com.ddokddak.category.dto.ReadCategoryResponse;
import com.ddokddak.category.entity.Category;
import com.ddokddak.member.entity.Member;

import java.util.Collections;

public class CategoryMapper {

    public static final ReadCategoryResponse toReadCategoryResponse(Category category, int level) {

        return ReadCategoryResponse.builder()
                .categoryId(category.getId())
                .name(category.getName())
                .color(category.getColor())
                .iconName(category.getIconName())
                .mainCategoryId(category.getMainCategory() == null ? null : category.getMainCategory().getId())
                .level(category.getLevel())
                .subCategories(category.getLevel() < level
                        ? category.getSubCategories()
                        .stream()
                        .filter(sub -> sub.getIsDeleted() == Boolean.FALSE)
                        .map(CategoryMapper::toReadCategoryResponse)
                        .toList()
                        : Collections.emptyList())
                .build();
    }

    public static final ReadCategoryResponse toReadCategoryResponse(Category category) {

        return CategoryMapper.toReadCategoryResponse(category, 2);
    }

    public static final Category fromCategoryAddRequest(CategoryAddRequest req, Member member, Category mainCategory) {

        return Category.builder()
                .name( req.name() )
                .iconName( req.iconName() )
                .color( req.color() )
                .level( req.level() )
                .member( member )
                .mainCategory( mainCategory )
                .build();
    }
}
