package com.ddokddak.category.dto;

import lombok.Builder;

public record CategoryAddResponse(
    Long id
){
    @Builder
    public CategoryAddResponse {}
}
