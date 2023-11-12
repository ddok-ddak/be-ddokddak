package com.ddokddak.category.domain.dto;

import lombok.Builder;

public record CategoryAddResponse(
    Long id
){
    @Builder
    public CategoryAddResponse {}
}
