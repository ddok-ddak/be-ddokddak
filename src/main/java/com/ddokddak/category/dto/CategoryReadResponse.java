package com.ddokddak.category.dto;

import com.ddokddak.category.entity.Category;

import java.util.List;

public record CategoryReadResponse(
        List<Category> result
) {
}
