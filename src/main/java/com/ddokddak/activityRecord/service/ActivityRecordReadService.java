package com.ddokddak.activityRecord.service;

import com.ddokddak.activityRecord.domain.dto.StatsActivityRecordRequest;
import com.ddokddak.activityRecord.domain.dto.StatsActivityRecordResult;
import com.ddokddak.activityRecord.domain.dto.ActivityRecordResponse;
import com.ddokddak.activityRecord.mapper.ActivityRecordMapper;
import com.ddokddak.activityRecord.repository.ActivityRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ActivityRecordReadService {

    private final ActivityRecordRepository activityRecordRepository;

    public Boolean existsByMemberIdAndStartedAtBetween(Long memberId, LocalDateTime fromStartedAt, LocalDateTime toStartedAt) {
        var result = activityRecordRepository.existsByMemberIdAndStartedAtBetweenAndIsDeletedFalse(memberId, fromStartedAt, toStartedAt);
        return result;
    }

    public List<StatsActivityRecordResult> statsByMemberIdAndPeriod(StatsActivityRecordRequest request, Long memberId) {
        List<StatsActivityRecordResult> result = activityRecordRepository.statsByMemberIdAndPeriodGroupByCategory(request, memberId);
        return result;
    }

    public List<ActivityRecordResponse> findByMemberIdAndStartedAtBetween(
            Long memberId, LocalDateTime fromStartedAt, LocalDateTime toStartedAt)
    {
        var activityRecords = activityRecordRepository.findByMemberIdAndStartedAtBetweenAndIsDeletedFalse(
                memberId, fromStartedAt, toStartedAt );
        return activityRecords.stream()
                .map(ActivityRecordMapper::toActivityRecordResponse)
                .collect(Collectors.toList());
    }
}
