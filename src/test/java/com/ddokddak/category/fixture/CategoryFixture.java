package com.ddokddak.category.fixture;

import com.ddokddak.category.domain.entity.Category;
import com.ddokddak.category.domain.entity.CategoryIcon;
import com.ddokddak.member.domain.entity.Member;
import java.util.List;
import java.util.stream.IntStream;

public class CategoryFixture {

    public static List<Category> createMainCategories(int start,
                                                      int end,
                                                      Member member,
                                                      CategoryIcon categoryIcon) {

        var categoryList = IntStream.range(start, end)
                .mapToObj(i -> Category.builder()
                        .name("category"+i)
                        .color("color"+i)
                        .iconFile(categoryIcon)
                        .level(0)
                        .member(member)
                        .isDeleted(Boolean.FALSE)
                        .build()
                ).toList();
        return categoryList;
    }

    public static List<Category> createSubCategories(int start, int end,
                                                     Member member,
                                                     Category mainCategory,
                                                     CategoryIcon categoryIcon) {

        var categoryList = IntStream.range(start, end)
                .mapToObj(i -> Category.builder()
                        .name("category"+i)
                        .color("color"+i)
                        .iconFile(categoryIcon)
                        .level(1)
                        .mainCategory(mainCategory)
                        .member(member)
                        .isDeleted(Boolean.FALSE)
                        .build()
                ).toList();
        return categoryList;
    }
}
