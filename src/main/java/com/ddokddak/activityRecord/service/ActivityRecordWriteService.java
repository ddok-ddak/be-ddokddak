package com.ddokddak.activityRecord.service;

import com.ddokddak.activityRecord.dto.ModifyActivityRecordRequest;
import com.ddokddak.activityRecord.entity.ActivityRecord;
import com.ddokddak.activityRecord.repository.ActivityRecordJdbcRepository;
import com.ddokddak.activityRecord.repository.ActivityRecordRepository;
import com.ddokddak.auth.domain.UserPrincipal;
import com.ddokddak.category.repository.CategoryRepository;
import com.ddokddak.common.exception.NotValidRequestException;
import com.ddokddak.common.exception.type.NotValidRequest;
import com.ddokddak.activityRecord.repository.ActivityRecordRepository;
import com.ddokddak.activityRecord.repository.ActivityRecordRepository;
import com.ddokddak.auth.domain.UserPrincipal;
import com.ddokddak.category.repository.CategoryRepository;
import com.ddokddak.common.exception.NotValidRequestException;
import com.ddokddak.common.exception.type.NotValidRequest;
import com.ddokddak.activityRecord.repository.ActivityRecordRepository;
import com.ddokddak.auth.domain.UserPrincipal;
import com.ddokddak.category.repository.CategoryRepository;
import com.ddokddak.common.exception.NotValidRequestException;
import com.ddokddak.common.exception.type.NotValidRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ActivityRecordWriteService {

    private final ActivityRecordJdbcRepository activityRecordJdbcRepository;
    private final ActivityRecordRepository activityRecordRepository;
    private final CategoryRepository categoryRepository;
    @Transactional
    public void saveBulkActivityRecords(List<ActivityRecord> activityRecords) {
        activityRecordJdbcRepository.bulkSave(activityRecords);
    }

    @Transactional
    public void saveAll(List<ActivityRecord> activityRecords) {
        activityRecordRepository.saveAll(activityRecords);
    }

    public void removeActivityRecordByMemberIdAndId(Long memberId, Long activityRecordId) {
        activityRecordRepository.softDeleteByMemberIdAndId(memberId, activityRecordId);
    }

    public void modifyActivityRecord(Long memberId, ModifyActivityRecordRequest req) {
        /* TODO 유효성 검증
            1. 사용자 아이디 o
            2. 리뷰 아이디 o
            3. 시작,종료 시간 o
         */

        /* 카테고리 검증 */
        var category = categoryRepository.findByIdAndMemberIdAndIsDeletedFalse(req.categoryId(), memberId)
                .orElseThrow(() -> new NotValidRequestException((NotValidRequest.CATEGORY_ID)));

        /* 리뷰 아이디 검증 */
        var existingActivityRecord = activityRecordRepository.findByIdAndMemberId(req.id(), memberId)
                .orElseThrow(() -> new NotValidRequestException(NotValidRequest.ACTIVITYRECORD_ID));

        /* 시간 검증 */
        // 1. 유효성
        if (req.finishedAt().isBefore(req.startedAt())) {
            throw new NotValidRequestException(NotValidRequest.WRONG_TIME_DATA);
        }

        // 2. 종료 시간이 시작 시간의 일자를 넘어가는 경우, 당일 끝시간으로 지정
        /* TODO
            초를 받음 -> 시간으로 환산해서 그 다음날인 날짜에 채워줘야 하는데 또 다음날로 넘어갈 수 있으니까 날짜가 넘어가지 않을 때까지 시간으로 환산
            같은 일자 내면은 상관이 없고 일을 넘어가는 것부터 체크해야함..
            해당 당일을 지나가면은 23595959초로 설정해주는 작업
         */
        if (Period.between(req.startedAt().toLocalDate(), req.finishedAt().toLocalDate()).getDays() > 1) {
            var localStartedTime = LocalDateTime.of(req.startedAt().getYear(), req.startedAt().getMonth(), req.startedAt().getDayOfMonth(), 23, 59, 59, 59);
            ZonedDateTime newFinishedAt = ZonedDateTime.of(localStartedTime, ZoneId.of("Asia/Seoul"));
            req = ModifyActivityRecordRequest.builder()
                                            .id( req.id() )
                                            .categoryId( req.categoryId() )
                                            .content( req.content() )
                                            .startedAt( req.startedAt() )
                                            .finishedAt( newFinishedAt )
                                            .build();
        }

        existingActivityRecord.modifyCategory(category);
        existingActivityRecord.modifyContent(req.content());
        existingActivityRecord.modifyDate(req.startedAt().toLocalDateTime(), req.finishedAt().toLocalDateTime());
    }
}
