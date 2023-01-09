package com.ddokddak.category.service;


import com.ddokddak.category.entity.Category;
import com.ddokddak.category.repository.CategoryRepository;
import com.ddokddak.common.exception.NotValidRequestException;
import com.ddokddak.common.exception.type.NotValidRequest;
import com.ddokddak.member.Member;
import com.ddokddak.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryReadService {
    // 사용중인 카테고리 중 delete=y or use=y 인 분류들을 모두 조회한다. (일단 써봄)
    // 대분류와 소분류가 매핑되어 list 형태로 조회된다. (아직)

    private final CategoryRepository categoryRepository;
    private final MemberRepository memberRepository;
    // CategoryRepository 파일에서 db에서 카테고리를 조회해오는 쿼리를 작성한다.

    @Transactional
    public List<Category> readCategoriesByMemberId(Long memberId) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotValidRequestException(NotValidRequest.MEMBER_ID.message(), NotValidRequest.MEMBER_ID.status()));
        List<Category> categories = categoryRepository.findByMember(member)
                .orElseThrow(() -> new NotValidRequestException(NotValidRequest.CATEGORY_ID.message(), NotValidRequest.CATEGORY_ID.status()));

        return categories;
    }










}
