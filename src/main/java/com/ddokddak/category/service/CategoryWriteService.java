package com.ddokddak.category.service;

import com.ddokddak.category.dto.CategoryModifyRequest;
import com.ddokddak.category.dto.CategoryRelationModifyRequest;
import com.ddokddak.category.dto.CategoryValueModifyRequest;
import com.ddokddak.category.entity.Category;
import com.ddokddak.category.repository.CategoryRepository;
import com.ddokddak.common.exception.NotValidRequestException;
import com.ddokddak.common.exception.type.NotValidRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CategoryWriteService {

    private final CategoryRepository categoryRepository;

    @Transactional
    public void removeCategoryById(Long categoryId) {
        var category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotValidRequestException(NotValidRequest.CATEGORY_ID.message(), NotValidRequest.CATEGORY_ID.status()));

        // 메인 카테고리이면, 서브 카테고리도 삭제 확인
        // 즉, 자식이 있을 경우 함께 삭제
        if (category.getLevel().equals(0)) categoryRepository.deleteAllByMainCategory(category);
        categoryRepository.delete(category);
    }

    /**
     * 카테고리의 이름, 색상 값만을 변경하고자 하는 경우
     */
    @Transactional
    public void modifyCategoryValue(CategoryValueModifyRequest req) {

        // 카테고리 아이디와 멤버 아이디로 조회
        var category = categoryRepository.findByIdAndMemberId(req.categoryId(), req.memberId())
                .orElseThrow(() -> new NotValidRequestException(NotValidRequest.CATEGORY_ID.message(), NotValidRequest.CATEGORY_ID.status()));

        // 이름 검증 후 변경
        this.checkCategoryNameAbleToUse(req.name(), category, category.getMainCategory(), Boolean.FALSE);
        category.modifyName(req.name());
        category.modifyColor(req.color());

        // 더티 체킹으로 업데이트 수행
    }

    /**
     * 카테고리의 관계, 레벨만을 변경하고자 하는 경우
     */
    @Transactional
    public void modifyCategoryRelation(CategoryRelationModifyRequest req) {
        // 카테고리 아이디와 멤버 아이디로 조회
        var category = categoryRepository.findByIdAndMemberId(req.categoryId(), req.memberId())
                .orElseThrow(() -> new NotValidRequestException(NotValidRequest.CATEGORY_ID.message(), NotValidRequest.CATEGORY_ID.status()));
        Category mainCategory = null;

        // 1. 메인 카테고리로 변경하는 경우
        // - 변경이 없다면, 요청이 올바르지 않아 예외 발생
        if (Objects.equals(req.level(), 0) &&
                (Objects.equals(category.getLevel(), 0) ||
                        (Objects.nonNull(req.mainCategoryId()) && !Objects.equals(req.mainCategoryId(), 0L)))) {
            throw new NotValidRequestException(NotValidRequest.IRONIC_REQUEST.message(), NotValidRequest.IRONIC_REQUEST.status());
        }

        // 2. 서브 카테고리로 변경 및 유지
        // - 메인 카테고리가 달라지는 경우
        if (Objects.equals(req.level(), 1)) {

            this.validateSubCategoryRelation(category, req.mainCategoryId());
            mainCategory = this.checkMainCategoryToChange(req.mainCategoryId(), req.memberId());

            // 이동할 카테고리 그룹에 중복되는 이름이 있는지 검증
            this.checkCategoryNameAbleToUse(category.getName(), category, mainCategory, Boolean.TRUE);
        }

        category.modifyCategoryRelation(req.level(), mainCategory);
    }

    @Transactional
    public void modifyCategory(CategoryModifyRequest req) {
        // 카테고리 아이디와 멤버 아이디로 조회
        var category = categoryRepository.findByIdAndMemberId(req.categoryId(), req.memberId())
                .orElseThrow(() -> new NotValidRequestException(NotValidRequest.CATEGORY_ID.message(), NotValidRequest.CATEGORY_ID.status()));
        Category mainCategory = null;

        // 1. 관계가 변경되지 않는 경우
        if (Objects.equals(category.getLevel(), req.level()) && Objects.equals(category.getMainCategory(), req.mainCategoryId())) {

            // - 메인 카테고리 : 이름 검증 없음
            // - 서브 카테고리 : 이름 검증 수행
            this.checkCategoryNameAbleToUse(req.name(), category, mainCategory, Boolean.FALSE);
        }

        // 2. 관계가 변경되는 경우
        // 2-1. 서브 카테고리가 메인 카테고리가 되는 경우 (req.level() == 0)
        // req.mainCategoryId() 값을 갖는 경우(not Null && not 0L) 예외 발생
        if (req.level() == 0 && (Objects.nonNull(req.mainCategoryId()) && !Objects.equals(req.mainCategoryId(), 0L))) {
            throw new NotValidRequestException(NotValidRequest.IRONIC_REQUEST.message(), NotValidRequest.IRONIC_REQUEST.status());
        }

        // 2-2. 메인 카데고리가 서브 카테고리가 되는 경우 (category.getLevel() == 0 && req.level() == 1 )
        // 2-3. 서브 카테고리를 유지 (category.getLevel() == 1 && req.level() == 1)
        // - 부모가 바뀌는 경우 (category.getMainCategoryId() != req.mainCategoryId())
        if (req.level() == 1) {

            this.validateSubCategoryRelation(category, req.mainCategoryId());
            mainCategory = this.checkMainCategoryToChange(req.mainCategoryId(), req.memberId());
            this.checkCategoryNameAbleToUse(req.name(), category, mainCategory, Boolean.TRUE);
        }

        category.modifyCategoryRelation(req.level(), mainCategory);
        category.modifyName(req.name());
        category.modifyColor(req.color());
    }

    /**
     * 메인 카테고리 검증
     * - 서브 카테고리 유지 및 메인 카테고리 변경시
     * - 서브 카테고리로 변경시
     */
    private Category checkMainCategoryToChange(Long reqMainCategoryId, Long memberId) {

        return categoryRepository.findByIdAndMemberId(reqMainCategoryId, memberId) // memberId 는 해당 요청 유저 아이디와 매칭을 미리 할 예정
                .orElseThrow(() ->
                        new NotValidRequestException(NotValidRequest.MAIN_CATEGORY_ID.message(), NotValidRequest.MAIN_CATEGORY_ID.status()));
    }

    /**
     * 카테고리 이름 변경 작업 시,
     * 같은 카테고리 그룹 내 (자기 자신은 제외) 에 동일한 이름이 있는지 확인한다.
     * 그룹의 높이가 1인 경우만 고려한다. (즉, 메인과 서브 레벨만 존재한다.)
     * @param reqName 변경할 카테고리의 새 이름
     * @param category 변경할 대상 카테고리
     * @param relationChangeFlag
     */
    private void checkCategoryNameAbleToUse(String reqName, Category category, Category mainCategory, Boolean relationChangeFlag) {

        // 현재 메인 카테고리이거나, 부모 변화가 없고 이름 변화도 없는 경우 pass
        if (Objects.isNull(mainCategory) || (!relationChangeFlag && Objects.equals(reqName, category.getName()))) return;

        // 한 그룹의 모든 카테고리
        var categories = mainCategory.getSubCategories();
        categories.add(mainCategory);

        var result = categories
                .stream()
                .filter(el -> !el.getId().equals(category.getId()))
                .map(Category::getName)
                .anyMatch(nameCompared -> nameCompared.equals(reqName));

        if (result) {
            throw new NotValidRequestException(NotValidRequest.USED_NAME_CONFLICTS.message(), NotValidRequest.USED_NAME_CONFLICTS.status());
        }
    }

    private void validateSubCategoryRelation(Category category, Long reqMainCategoryId) {

        // 1. 서브 카테고리인 경우의 검증 수행
        // - 상위 카테고리 정보가 없는 경우 예외 발생
        if (Objects.isNull(reqMainCategoryId)) {
            throw new NotValidRequestException(NotValidRequest.NULL_DATA.message(), NotValidRequest.NULL_DATA.status());
        }

        // 1-1. 서브 카테고리로 변경
        // - 기존에 메인 카테고리 && 서브 카테고리를 갖고 있는 경우 변경 불가하므로 예외 발생
        if (Objects.equals(category.getLevel(), 0) && !category.getSubCategories().isEmpty()) {
            throw new NotValidRequestException(NotValidRequest.UNABLE_REQUEST.message(), NotValidRequest.UNABLE_REQUEST.status());
        }

        // 1-2. 서브 카테고리로 유지
        // - 메인 카테고리 아이디가 이전과 동일한 경우
        if (Objects.equals(category.getLevel(), 1) && Objects.equals(category.getMainCategory().getId(), reqMainCategoryId)) {
            throw new NotValidRequestException(NotValidRequest.MAIN_CATEGORY_ID.message(), NotValidRequest.MAIN_CATEGORY_ID.status());
        }
    }
}
