package com.ddokddak.usecase;

import com.ddokddak.activityRecord.domain.dto.CreateActivityRecordRequest;
import com.ddokddak.activityRecord.domain.entity.ActivityRecord;
import com.ddokddak.activityRecord.mapper.ActivityRecordMapper;
import com.ddokddak.activityRecord.service.ActivityRecordReadService;
import com.ddokddak.activityRecord.service.ActivityRecordWriteService;
import com.ddokddak.category.service.CategoryReadService;
import com.ddokddak.common.exception.NotValidRequestException;
import com.ddokddak.common.exception.type.ActivityException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class CreateActivityRecordUsecase {

    private final CategoryReadService categoryReadService;

    private final ActivityRecordReadService activityRecordReadService;
    private final ActivityRecordWriteService activityRecordWriteService;

    public void execute(CreateActivityRecordRequest req, Long memberId) {

        // 시간 차가 음수인 경우, 예외 처리
        var between = (int) ChronoUnit.MINUTES.between(req.startedAt(), req.finishedAt());
        if (between <= 0) throw new NotValidRequestException(ActivityException.WRONG_TIME_DATA);

        // 시간이 특정 분 단위(10, 30, 60)로 요청이 맞는지 확인 후 예외 처리
        if (!Objects.equals(between % req.timeUnit(), 0))
            throw new NotValidRequestException(ActivityException.WRONG_TIME_DATA);

        // 시간 범위 내 이미 기록 데이터가 존재하는 경우, 예외 처리
        var result = activityRecordReadService.existsByMemberIdAndIsDeletedFalseAndBetweenPeriodCondition(
                memberId, req.startedAt(), req.finishedAt());
        if (result) throw new NotValidRequestException(ActivityException.USED_TIME_PERIOD);

        var category = categoryReadService.findByIdAndMemberId(req.categoryId(), memberId);

        ActivityRecord entity = ActivityRecordMapper.toEntity(req, category);
        var saved = activityRecordWriteService.createActivityRecord(entity);
        //return ActivityRecordMapper.toActivityRecordResponse(saved);

//        int num = (int) between / req.timeUnit();
//        List<ActivityRecord> activityRecords = IntStream.range(0, num)
//                .mapToObj(i -> ActivityRecordMapper.toEntitySplitedByTimeUnit(i, req, category))
//                .toList();
//        // 벌크 인서트 수행
//        activityRecordWriteService.saveBulkActivityRecords(activityRecords);
    }
}
