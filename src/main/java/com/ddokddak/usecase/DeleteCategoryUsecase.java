package com.ddokddak.usecase;

import com.ddokddak.activityRecord.service.ActivityRecordWriteService;
import com.ddokddak.category.entity.Category;
import com.ddokddak.category.service.CategoryReadService;
import com.ddokddak.category.service.CategoryWriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class DeleteCategoryUsecase {
    private final CategoryReadService categoryReadService;
    private final CategoryWriteService categoryWriteService;
    private final ActivityRecordWriteService activityRecordWriteService;

    @Transactional
    public void execute(Long categoryId, Long memberId) {

        var category = categoryReadService.findByIdAndMemberId(categoryId, memberId);

        // 메인 카테고리이면, 서브 카테고리도 삭제 확인
        // 즉, 자식이 있을 경우 함께 삭제
        if (category.getLevel().equals(0)) {
            var subCategories = category.getSubCategories();
            subCategories.stream()
                    .forEach(el -> deleteCategoryAndRecords(el, memberId));
        }
        this.deleteCategoryAndRecords(category, memberId);
    }

    @Transactional
    public void deleteCategoryAndRecords(Category category, Long memberId) {

        categoryWriteService.removeCategoryByIdAndMemberId(category, memberId);
        activityRecordWriteService.removeByMemberIdAndCategory(memberId, category);
    }
}
