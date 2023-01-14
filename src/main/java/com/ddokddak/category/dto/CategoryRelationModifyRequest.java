package com.ddokddak.category.dto;

import lombok.Builder;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;

public record CategoryRelationModifyRequest(
        @NotNull Long categoryId,
        @NotNull Integer level,
        @Nullable Long mainCategoryId
) {
    @Builder // 빌더 어노테이션이 적용되지 않는 이슈로 현재 코드로 작성
    public CategoryRelationModifyRequest {}
}
