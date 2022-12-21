package com.ddokddak.category.service;

import com.ddokddak.category.dto.CategoryModifyRequest;
import com.ddokddak.category.entity.Category;
import com.ddokddak.category.repository.CategoryRepository;
import com.ddokddak.common.exception.CustomApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryWriteService {

    // TODO 에러메제지를 관리하는 이넘 혹은 커스텀 예외 추가로 생성하기
    // 에러 메세지 상세화 하기, 상위 카테고리 정보 오류, 카테고리 정보 오류 등
    private static final String MESSAGE_TEMP = "NOT VALID REQUEST";
    private final CategoryRepository categoryRepository;

    @Transactional
    public void removeCategoryById(Long categoryId) {
        var category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CustomApiException(MESSAGE_TEMP, HttpStatus.BAD_REQUEST));

        // 메인 카테고리이면, 서브 카테고리도 삭제 확인
        // 즉, 자식이 있을 경우 함께 삭제
        if (category.getLevel().equals(0)) categoryRepository.deleteAllByMainCategory(category);
        categoryRepository.delete(category);
    }

    @Transactional
    public void modifyCategory(CategoryModifyRequest req) {

        // 카테고리 아이디와 멤버 아이디로 조회
        var category = categoryRepository.findByIdAndMemberId(req.categoryId(), req.memberId())
                .orElseThrow(() -> new CustomApiException(MESSAGE_TEMP, HttpStatus.BAD_REQUEST));

        // 메인 카테고리로 변경하는 경우
        if (Objects.equals(req.level(), 0)) {
            // 부적합한 요청, 상위 카테고리 정보가 존재해서는 안됨
            if (Optional.ofNullable(req.mainCategoryId()).isPresent()) {
                throw new CustomApiException(MESSAGE_TEMP, HttpStatus.BAD_REQUEST);
            }
        }

        // 서브 카테로 변경하는 경우
        Category parentCategory = null;
        if (Objects.equals(req.level(), 1)) {

            // TODO 서브 카테를 갖고 있는 경우 어떻게 처리할지 논의 필요
            //  부적합한 요청, 해당 카테고리의 서브 카테고리가 존재해서는 안됨
            if (category.getSubCategories() != null && category.getSubCategories().size() > 0) {
                throw new CustomApiException(MESSAGE_TEMP, HttpStatus.BAD_REQUEST);
            }
            parentCategory = this.checkParentCategory(req.mainCategoryId(), req.memberId());
        }

        category.modifyCategoryRelation(req, parentCategory);
        category.modifyAttribute(req);
        //categoryRepository.save(category); 변경 감지 동작, 커밋 완료시 저장됨
    }

    // 트랜잭션 전파를 위해 퍼블릭으로 지정
    @Transactional
    public Category checkParentCategory(Long mainCategoryId, Long memberId) {
        // 서브 카테고리로 변경을 원하는데, 상위 카테고리 정보가 없는 경우 예외 발생
        if (Optional.ofNullable(mainCategoryId).isEmpty()) {
            throw new CustomApiException(MESSAGE_TEMP, HttpStatus.BAD_REQUEST);
        }
        return categoryRepository.findByIdAndMemberId(mainCategoryId, memberId)
                .orElseThrow(() -> new CustomApiException("NOT VALID MAIN CATEGORY ID", HttpStatus.BAD_REQUEST));
    }

}
