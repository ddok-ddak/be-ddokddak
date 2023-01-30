package com.ddokddak.category.service;


import com.ddokddak.category.dto.CategoryReadResponse;
import com.ddokddak.category.entity.Category;
import com.ddokddak.category.repository.CategoryRepository;
import com.ddokddak.common.exception.NotValidRequestException;
import com.ddokddak.common.exception.type.NotValidRequest;
import com.ddokddak.member.repository.MemberRepository;
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
    private final MemberRepository memberRepository;
    // CategoryRepository 파일에서 db에서 카테고리를 조회해오는 쿼리를 작성한다.

    @Transactional(readOnly = true)
    public CategoryReadResponse readCategoriesByMemberId(Long memberId) {

        List<Category> categories = categoryRepository.findByMemberId(memberId)
                .orElseThrow(() -> new NotValidRequestException(NotValidRequest.CATEGORY_DATA));
        return new CategoryReadResponse(categories);
    }










}
