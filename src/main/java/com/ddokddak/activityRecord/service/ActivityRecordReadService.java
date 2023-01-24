package com.ddokddak.activityRecord.service;

import com.ddokddak.activityRecord.repository.ActivityRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@RequiredArgsConstructor
@Service
public class ActivityRecordReadService {

    private final ActivityRecordRepository activityRecordRepository;

    public Boolean existsByMemberIdAndStartedAtBetween(Long memberId, LocalDateTime startedAt, LocalDateTime finishedAt) {
        var result = activityRecordRepository.existsByMemberIdAndStartedAtBetween(memberId, startedAt, finishedAt);
        return result;
    }
}
