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

    public Boolean existsByMemberIdAndStartedAtBetween(Long memberId, LocalDateTime fromStartedAt, LocalDateTime toStartedAt) {
        var result = activityRecordRepository.existsByMemberIdAndStartedAtBetween(memberId, fromStartedAt, toStartedAt);
        return result;
    }
}
