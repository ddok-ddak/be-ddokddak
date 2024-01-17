package com.ddokddak.category.domain.dto;

import lombok.Builder;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public record ModifyCategoryRequest(
        @NotNull Long categoryId,
        @NotNull //@Pattern(regexp="[a-zA-Z가-힣0-9]{1,10}", message = "카테고리 명칭 규칙 위반")
        String name,
        @NotNull String color,
        @NotNull String highlightColor,
        @NotNull Long iconId,
        @NotNull Integer level,
        @Nullable Long mainCategoryId
) {
    @Builder // 빌더 어노테이션이 적용되지 않는 이슈로 현재 코드로 작성
    public ModifyCategoryRequest {}
}
