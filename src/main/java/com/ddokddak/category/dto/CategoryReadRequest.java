package com.ddokddak.category.dto;

import lombok.Builder;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;

public record CategoryReadRequest (
        @NotNull Long categoryId,
        @NotNull String name,
        @NotNull String color,
        @NotNull Integer level,
        @Nullable
        Long mainCategoryId,
        @NotNull Long memberId
) {
    @Builder // 빌더 어노테이션이 적용되지 않는 이슈로 현재 코드로 작성
    public CategoryReadRequest {}
}
