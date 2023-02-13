package com.ddokddak.activityRecord.repository;

import com.ddokddak.activityRecord.entity.ActivityRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

public interface ActivityRecordRepository extends JpaRepository<ActivityRecord, Long> {

    Optional<ActivityRecord> findByIdAndMemberId(Long activityRecordId, Long memberId);
    List<ActivityRecord> findByMemberIdAndCategoryId(Long memberId, Long categoryId);

    Boolean existsByMemberIdAndStartedAtBetween(Long memberId, LocalDateTime fromStartedAt, LocalDateTime toStartedAt);
}
