package com.ddokddak.usecase;

import com.ddokddak.activityRecord.service.ActivityRecordWriteService;
import com.ddokddak.category.domain.dto.CategoryTemplateRequest;
import com.ddokddak.category.domain.entity.Category;
import com.ddokddak.category.service.CategoryReadService;
import com.ddokddak.category.service.CategoryWriteService;
import com.ddokddak.common.exception.CustomApiException;
import com.ddokddak.common.exception.type.BaseException;
import com.ddokddak.member.domain.enums.TemplateType;
import com.ddokddak.member.service.MemberWriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ModifyCategoryTemplateUsecase {

    private final MemberWriteService memberWriteService;
    private final CategoryWriteService categoryWriteService;
    private final CategoryReadService categoryReadService;
    private final ActivityRecordWriteService activityRecordWriteService;

    @Transactional
    public void execute(CategoryTemplateRequest req, Long memberId) {

        var previousTemplateType = memberWriteService.modifyCategoryTemplate(memberId, req);

        // 카테고리 템플릿 수정 수행 (직장, 학교 카테고리 그룹만 추가 혹은 변경/삭제된다)
        // 전체 카테고리를 조회해와서 확인 후 제거 및 업데이트 수행
        var categories = categoryReadService.findByMemberIdAndLevel(memberId, 0);

        // 기존 템플릿에서 학생이면 학업, 직장이면 업무 카테고리 그룹 제거
        if (!previousTemplateType.equals(TemplateType.UNEMPLOYED)) {
            var pValues = previousTemplateType.getSpecificTemplates();
            var mainCategory = pValues.stream()
                    .filter(v->v.getParentName()==null)
                    .findFirst()
                    .orElseThrow(() -> new CustomApiException(BaseException.NULL_DATA));

            categories.stream()
                    .filter(category -> !category.getIsDeleted() && category.getName().equals(mainCategory.getName()))
                    .forEach(category-> {
                        category.getSubCategories().stream()
                                .forEach(el -> deleteCategoryAndRecords(el, memberId));
                        this.deleteCategoryAndRecords(category, memberId);
                    });
        }

        // 학생이면 학업, 직장이면 업무 카테고리 그룹 추가
        if (!req.templateType().equals(TemplateType.UNEMPLOYED)) {
            var newValues = req.templateType().getSpecificTemplates();
            var newMainCategory = newValues.stream()
                    .filter(v->v.getParentName()==null)
                    .findFirst()
                    .orElseThrow(() -> new CustomApiException(BaseException.NULL_DATA));

            // 기존에 대분류가 존재했었다면(삭제 상태라면)
            var alreadyExistsCategory = categories.stream()
                    .filter(category -> category.getName().equals(newMainCategory.getName()))
                    .findFirst();
            if (alreadyExistsCategory.isPresent()) {
                // 카테고리만 재활성화
                categoryWriteService.undeleteCategoryGroup(alreadyExistsCategory.get());
                return;
            }

            // 대분류 카테 갯수 제한 (삭제 포함 8개까지 가능)
            if (categories.size() > 7) {
                throw new CustomApiException(BaseException.UNABLE_REQUEST);
            }
            categoryWriteService.batchInsert(newValues, memberId);
        }
    }

    @Transactional
    public void deleteCategoryAndRecords(Category category, Long memberId) {

        activityRecordWriteService.removeByMemberIdAndCategory(memberId, category);
        categoryWriteService.removeCategoryByIdAndMemberId(category, memberId);
    }
}
