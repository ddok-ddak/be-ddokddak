package com.ddokddak.category.domain.dto;

import lombok.Builder;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public record ModifyCategoryValueRequest(
        @NotNull Long categoryId,
        @NotNull //@Pattern(regexp="[a-zA-Z가-힣0-9]{1,10}", message = "카테고리 명칭 규칙 위반")
        String name,
//        @NotNull //@Pattern(regexp="[a-zA-Z가-힣0-9]{1,10}", message = "카테고리 명칭 규칙 위반")
//        String color,
        @NotNull Long iconId
) {
    @Builder
    public ModifyCategoryValueRequest {}
}
