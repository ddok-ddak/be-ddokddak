package com.ddokddak.category.fixture;

import com.ddokddak.category.entity.Category;
import com.ddokddak.member.entity.Member;
import java.util.List;
import java.util.stream.IntStream;

public class CategoryFixture {

    public static List<Category> createMainCategories(int start, int end, Member member) {

        var categoryList = IntStream.range(start, end)
                .mapToObj(i -> Category.builder()
                        .name("category"+i)
                        .color("color"+i)
                        .level(0)
                        .member(member)
                        .isDeleted(Boolean.FALSE)
                        .build()
                ).toList();
        return categoryList;
    }

    public static List<Category> createSubCategories(int start, int end,
                                                Member member, Category mainCategory) {

        var categoryList = IntStream.range(start, end)
                .mapToObj(i -> Category.builder()
                        .name("category"+i)
                        .color("color"+i)
                        .level(1)
                        .mainCategory(mainCategory)
                        .member(member)
                        .isDeleted(Boolean.FALSE)
                        .build()
                ).toList();
        return categoryList;
    }
}
