package com.ddokddak.category.service;

import com.ddokddak.category.domain.dto.CategoryReadResponse;
import com.ddokddak.category.domain.entity.Category;
import com.ddokddak.category.repository.CategoryRepository;
import com.ddokddak.common.exception.NotValidRequestException;
import com.ddokddak.member.domain.entity.Member;
import com.ddokddak.member.repository.MemberRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class CategoryReadServiceTest {

    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private MemberRepository memberRepository;
    @InjectMocks
    private CategoryReadService categoryReadService;

    @DisplayName("카테고리 조회")
    @Test
    public void readCategoriesByMemberId() {
        Long memberId = 1L;
        Member member = Member.builder().id(memberId).build();
        List<Category> categories = new ArrayList<>();
        categories.add(Category.builder()
                .id(111L)
                .name("category11")
                .color("color11")
                .level(0)
                .member(member)
                .isDeleted(Boolean.FALSE)
                .subCategories(Collections.EMPTY_LIST)
                .build()
        );
        categories.add(Category.builder()
                .id(222L)
                .name("category22")
                .color("color22")
                .level(0)
                .member(member)
                .isDeleted(Boolean.FALSE)
                .subCategories(Collections.EMPTY_LIST)
                .build()
        );
        Mockito.when(memberRepository.findMemberById(memberId)).thenReturn(Optional.of(member));
        Mockito.when(categoryRepository.findCategoryJoinFetch(Mockito.any())).thenReturn(categories);
        List<CategoryReadResponse> response = categoryReadService.readCategoriesByMemberId(memberId);
        assertEquals(categories.size(), response.size());
    }

    @DisplayName("Member가 존재하지 않을 경우, NotValidRequestException 발생 테스트")
    @Test
    public void readCategoriesByMemberId_memberNotFound() {
        Long memberId = 1L;
        Mockito.when(memberRepository.findMemberById(memberId)).thenReturn(Optional.empty());

        assertThrows(NotValidRequestException.class, () -> {
            categoryReadService.readCategoriesByMemberId(memberId);
        });
    }

//    @DisplayName("Categories가 존재하지 않을 경우, NotValidRequestException 발생 테스트")
//    @Test
//    public void readCategoriesByMemberId_categoryNotFound() {

//        Long memberId = 1L;
//        Member member = Member.builder().id(memberId).build();
//        Mockito.when(memberRepository.findMemberById(memberId)).thenReturn(Optional.of(member));
//        Mockito.when(categoryRepository.findCategoryJoinFetch(member)).thenReturn(Collections.EMPTY_LIST);
//    }

    @DisplayName("Id와 MemberId 탐색 성공 테스트")
    @Test
    public void findByIdAndMemberId() {
        Long categoryId = 1L;
        Long memberId = 1L;
        Member member = Member.builder().id(memberId).build();
        Category mockCategory = Category.builder()
                .id(categoryId)
                .name("category11")
                .color("color11")
                .level(0)
                .member(member)
                .isDeleted(Boolean.FALSE)
                .build();

        Mockito.when(categoryRepository.findByIdAndMemberIdAndIsDeletedFalse(categoryId, memberId))
                .thenReturn(Optional.of(mockCategory));
        Category category = categoryReadService.findByIdAndMemberId(categoryId, memberId);
        assertEquals(category.getId(), mockCategory.getId());
        assertEquals(category.getMember().getId(), mockCategory.getMember().getId());
    }

    @DisplayName("category가 존재하지 않을 경우, NotValidRequestException 발생 테스트")
    @Test
    public void findByIdAndMemberId_categoryNotFound() {
        Long categoryId = 1L;
        Long memberId = 1L;
        Mockito.when(categoryRepository.findByIdAndMemberIdAndIsDeletedFalse(categoryId, memberId)).thenReturn(Optional.empty());

        assertThrows(NotValidRequestException.class, () -> {
            categoryReadService.findByIdAndMemberId(categoryId, memberId);
        });
    }
}