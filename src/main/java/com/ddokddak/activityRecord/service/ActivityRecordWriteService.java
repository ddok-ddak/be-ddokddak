package com.ddokddak.activityRecord.service;

import com.ddokddak.activityRecord.domain.dto.ModifyActivityRecordRequest;
import com.ddokddak.activityRecord.domain.entity.ActivityRecord;
import com.ddokddak.activityRecord.repository.ActivityRecordJdbcRepository;
import com.ddokddak.activityRecord.repository.ActivityRecordRepository;
import com.ddokddak.category.domain.entity.Category;
import com.ddokddak.category.repository.CategoryRepository;
import com.ddokddak.common.exception.CustomApiException;
import com.ddokddak.common.exception.type.ActivityException;
import com.ddokddak.common.exception.type.CategoryException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ActivityRecordWriteService {

    private final ActivityRecordJdbcRepository activityRecordJdbcRepository;
    private final ActivityRecordRepository activityRecordRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public void removeActivityRecordByMemberIdAndId(Long memberId, Long activityRecordId) {
        /* 기록 아이디 검증 */
        var existingActivityRecord = activityRecordRepository.findByIdAndMemberIdAndIsDeletedFalse(activityRecordId, memberId)
                .orElseThrow(() -> new CustomApiException(ActivityException.ACTIVITYRECORD_ID));
        existingActivityRecord.softDelete();
        //activityRecordRepository.softDeleteByMemberIdAndId(memberId, activityRecordId);
    }

    @Transactional
    public void modifyActivityRecord(Long memberId, ModifyActivityRecordRequest req) {
        /* TODO 유효성 검증
            1. 사용자 아이디 o
            2. 리뷰 아이디 o
            3. 시작,종료 시간 o
         */

        /* 시간 검증 */
        // 1. 유효성
        if (req.finishedAt().isBefore(req.startedAt())) {
            throw new CustomApiException(ActivityException.WRONG_TIME_DATA);
        }

        /* 기록 아이디 검증 */
        var existingActivityRecord = activityRecordRepository.findByIdAndMemberIdAndIsDeletedFalse(req.id(), memberId)
                .orElseThrow(() -> new CustomApiException(ActivityException.ACTIVITYRECORD_ID));

        /* 카테고리 검증 */
        if (req.categoryId() != existingActivityRecord.getCategory().getId()) {
            var category = categoryRepository.findByIdAndMemberIdAndIsDeletedFalse(req.categoryId(), memberId)
                    .orElseThrow(() -> new CustomApiException((CategoryException.CATEGORY_ID)));
            existingActivityRecord.modifyCategory(category);
        }

        var activityRecordsByCondition = activityRecordRepository.findByMemberIdAndIsDeletedFalseAndBetweenPeriodConditionExceptSelf(
                req.id(), memberId, req.startedAt(), req.finishedAt());
        if (!activityRecordsByCondition.isEmpty()) {
            throw new CustomApiException(ActivityException.USED_TIME_PERIOD);
        }

        existingActivityRecord.modifyDate(req.startedAt(), req.finishedAt());
        existingActivityRecord.modifyContent(req.content());
    }

    @Transactional
    public void removeByMemberIdAndCategory(Long memberId, Category category) {
        activityRecordRepository.softDeleteByMemberIdAndCategory(memberId, category);
    }

    @Transactional
    public ActivityRecord createActivityRecord(ActivityRecord entity) {
        ActivityRecord saved = activityRecordRepository.save(entity);
        return saved;
    }

    @Transactional
    public void saveBulkActivityRecords(List<ActivityRecord> activityRecords) {
        activityRecordJdbcRepository.bulkSave(activityRecords);
    }

    @Transactional
    public void saveAll(List<ActivityRecord> activityRecords) {
        activityRecordRepository.saveAll(activityRecords);
    }
}
