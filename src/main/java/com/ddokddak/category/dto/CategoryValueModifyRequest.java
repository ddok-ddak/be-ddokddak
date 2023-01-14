package com.ddokddak.category.dto;

import lombok.Builder;

import javax.validation.constraints.NotNull;

public record CategoryValueModifyRequest(
        @NotNull Long categoryId,
        @NotNull String name,
        @NotNull String color
) {
    @Builder // 빌더 어노테이션이 적용되지 않는 이슈로 현재 코드로 작성
    public CategoryValueModifyRequest {}
}
