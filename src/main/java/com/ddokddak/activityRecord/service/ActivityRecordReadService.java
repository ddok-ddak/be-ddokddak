package com.ddokddak.activityRecord.service;

import com.ddokddak.activityRecord.dto.ActivityRecordResponse;
import com.ddokddak.activityRecord.dto.ReadActivityRecordRequest;
import com.ddokddak.activityRecord.entity.ActivityRecord;
import com.ddokddak.activityRecord.mapper.ActivityRecordMapper;
import com.ddokddak.activityRecord.repository.ActivityRecordRepository;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.mapper.Mapper;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ActivityRecordReadService {

    private final ActivityRecordRepository activityRecordRepository;

    public Boolean existsByMemberIdAndStartedAtBetween(Long memberId, LocalDateTime fromStartedAt, LocalDateTime toStartedAt) {
        var result = activityRecordRepository.existsByMemberIdAndStartedAtBetween( memberId, fromStartedAt, toStartedAt );
        return result;
    }

    public List<ActivityRecordResponse> findByMemberIdAndStartedAtBetween(Long memberId, LocalDateTime fromStartedAt, LocalDateTime toStartedAt) {
        var activityRecords = activityRecordRepository.findByMemberIdAndStartedAtBetween( memberId, fromStartedAt, toStartedAt );
        return activityRecords.stream().map(ActivityRecordMapper::toActivityRecordResponse).collect(Collectors.toList());
    }
}
