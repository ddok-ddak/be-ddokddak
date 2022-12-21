package com.ddokddak.category.fixture;

import com.ddokddak.category.entity.Category;
import com.ddokddak.member.Member;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.jeasy.random.randomizers.range.LongRangeRandomizer;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class CategoryFixture {

    public static List<Category> createMainCategories(int start, int end, Member member) {

        var categoryList = IntStream.range(start, end)
                .mapToObj(i -> Category.builder()
                        .name("category"+i)
                        .color("color"+i)
                        .level(0)
                        .member(member)
                        .deleteYn("N")
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
                        .deleteYn("N")
                        .build()
                ).toList();
        return categoryList;
    }
}
