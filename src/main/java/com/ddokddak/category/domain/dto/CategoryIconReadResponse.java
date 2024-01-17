package com.ddokddak.category.domain.dto;

import lombok.Builder;

public record CategoryIconReadResponse(
        Long id,
        String iconGroup,
        String filename,
        String originalFilename,
        String path
) {
    @Builder
    public CategoryIconReadResponse {}
}