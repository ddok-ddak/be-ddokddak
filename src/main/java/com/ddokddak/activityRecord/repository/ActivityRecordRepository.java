package com.ddokddak.activityRecord.repository;

import com.ddokddak.activityRecord.dto.ActivityRecordResponse;
import com.ddokddak.activityRecord.dto.ReadActivityRecordResponse;
import com.ddokddak.activityRecord.entity.ActivityRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ActivityRecordRepository extends JpaRepository<ActivityRecord, Long> {

    Optional<ActivityRecord> findByIdAndMemberId(Long activityRecordId, Long memberId);
    List<ActivityRecord> findByMemberIdAndCategoryId(Long memberId, Long categoryId);

    Boolean existsByMemberIdAndStartedAtBetween(Long memberId, LocalDateTime fromStartedAt, LocalDateTime toStartedAt);
    List<ActivityRecord> findByMemberIdAndStartedAtBetween(Long memberId,LocalDateTime fromStartedAt, LocalDateTime toStartedAt );
    //@Query("SELECT ar FROM ActivityRecord ar WHERE ar.member_id=:memberId AND ar.category_id=:categoryId AND ar.started_at >= :fromStartedAt AND ar.finished_at <= :toStartedAt" )
}
