package com.ddokddak.category.mapper;

import com.ddokddak.category.domain.dto.CategoryAddRequest;
import com.ddokddak.category.domain.dto.CategoryReadResponse;
import com.ddokddak.category.domain.entity.Category;
import com.ddokddak.category.domain.entity.CategoryIcon;
import com.ddokddak.member.domain.entity.Member;

import java.util.Collections;
import java.util.Optional;

public class CategoryMapper {

    public static final CategoryReadResponse toReadCategoryResponse(Category category, int level) {

        return CategoryReadResponse.builder()
                .categoryId(category.getId())
                .name(category.getName())
                .color(category.getColor())
                .highlightColor(category.getHighlightColor())
                .iconFile(CategoryIconMapper.toReadResponse(category.getIconFile()))
                .mainCategoryId(category.getMainCategory() == null ? null : category.getMainCategory().getId())
                .level(category.getLevel())
                .subCategories(category.getLevel() < level
                        ? Optional.ofNullable(category.getSubCategories())
                        .orElseGet(Collections::emptyList)
                        .stream()
                        .filter(sub -> sub.getIsDeleted() == Boolean.FALSE)
                        .map(CategoryMapper::toReadCategoryResponse)
                        .toList()
                        : Collections.emptyList())
                .build();
    }

    public static final CategoryReadResponse toReadCategoryResponse(Category category) {

        return CategoryMapper.toReadCategoryResponse(category, 2);
    }

    public static final Category fromCategoryAddRequest(CategoryAddRequest req,
                                                        Member member,
                                                        Category mainCategory,
                                                        CategoryIcon iconFile) {

        return Category.builder()
                .name(req.name())
                .iconFile(iconFile)
                .color(req.color())
                .highlightColor(req.highlightColor())
                .level(req.level())
                .member(member)
                .mainCategory(mainCategory)
                .build();
    }
}
