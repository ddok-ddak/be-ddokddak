package com.ddokddak.category.service;


import com.ddokddak.category.dto.ReadCategoryResponse;
import com.ddokddak.category.entity.Category;
import com.ddokddak.category.mapper.CategoryMapper;
import com.ddokddak.category.repository.CategoryRepository;
import com.ddokddak.common.exception.NotValidRequestException;
import com.ddokddak.common.exception.type.NotValidRequest;
import com.ddokddak.member.entity.Member;
import com.ddokddak.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryReadService {

    private final CategoryRepository categoryRepository;
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public List<ReadCategoryResponse> readCategoriesByMemberId(Long memberId) {

        Member member = memberRepository.findMemberById(memberId)
                .orElseThrow(() -> new NotValidRequestException(NotValidRequest.MEMBER_ID));
        return categoryRepository.findCategoryJoinFetch(member)
                .stream()
                .map(CategoryMapper::toReadCategoryResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public Category findByIdAndMemberId(Long categoryId, Long memberId) {
        return categoryRepository.findByIdAndMemberIdAndIsDeletedFalse(categoryId, memberId)
                .orElseThrow(() -> new NotValidRequestException(NotValidRequest.UNABLE_REQUEST));
    }

    @Transactional(readOnly = true)
    public List<Category> findByRefMember(Member member) {
        var allByMember = categoryRepository.findAllByMemberAndIsDeletedFalse(member);
        if (allByMember.size() < 1) throw new NotValidRequestException(NotValidRequest.UNABLE_REQUEST);
        return allByMember;
    }

    @Transactional(readOnly = true)
    public List<Category> findByMemberId(Long memberId) {
        var allByMember = categoryRepository.findByMemberIdAndIsDeletedFalse(memberId);
        if (allByMember.size() < 1) throw new NotValidRequestException(NotValidRequest.UNABLE_REQUEST);
        return allByMember;
    }
}
