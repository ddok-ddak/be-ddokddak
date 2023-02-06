package com.ddokddak.category.service;


import com.ddokddak.category.dto.CategoryReadResponse;
import com.ddokddak.category.entity.Category;
import com.ddokddak.category.repository.CategoryRepository;
import com.ddokddak.common.exception.NotValidRequestException;
import com.ddokddak.common.exception.type.NotValidRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryReadService {

    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public CategoryReadResponse readCategoriesByMemberId(Long memberId) {

        List<Category> categories = categoryRepository.findByMemberId(memberId)
                .orElseThrow(() -> new NotValidRequestException(NotValidRequest.CATEGORY_DATA));
        return new CategoryReadResponse(categories);
    }

    @Transactional(readOnly = true)
    public Category findByIdAndMemberId(Long categoryId, Long memberId) {
        return categoryRepository.findByIdAndMemberId(categoryId, memberId)
                .orElseThrow(() -> new NotValidRequestException(NotValidRequest.UNABLE_REQUEST));
    }
}
