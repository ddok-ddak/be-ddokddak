package com.ddokddak.activityRecord.repository;

import com.ddokddak.activityRecord.dto.StatsActivityRecordRequest;
import com.ddokddak.activityRecord.dto.StatsActivityRecordResult;
import com.ddokddak.activityRecord.entity.ActivityRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ActivityRecordRepository extends JpaRepository<ActivityRecord, Long> {

    Optional<ActivityRecord> findByIdAndMemberId(Long activityRecordId, Long memberId);
    List<ActivityRecord> findByMemberIdAndCategoryId(Long memberId, Long categoryId);

    Boolean existsByMemberIdAndStartedAtBetween(Long memberId, LocalDateTime fromStartedAt, LocalDateTime toStartedAt);

    List<ActivityRecord> findByMemberIdAndStartedAtBetween(Long memberId,LocalDateTime fromStartedAt, LocalDateTime toStartedAt );
    //@Query("SELECT ar FROM ActivityRecord ar WHERE ar.member_id=:memberId AND ar.category_id=:categoryId AND ar.started_at >= :fromStartedAt AND ar.finished_at <= :toStartedAt" )

    @Query("SELECT new com.ddokddak.activityRecord.dto.StatsActivityRecordResult(ar.category.id, sum(ar.timeUnit))" + //, function('date_format', ar.startedAt, '%Y-%m')) " +
            "FROM ActivityRecord ar JOIN ar.category " +
            "WHERE ar.member.id = :memberId " +
            "AND ar.startedAt >= :#{#request.fromStartedAt} AND ar.finishedAt <= :#{#request.toFinishedAt} " +
            "GROUP BY ar.category") //, function('date_format', ar.startedAt, '%Y-%m')" )
    List<StatsActivityRecordResult> statsByMemberIdAndPeriodGroupByCategory(StatsActivityRecordRequest request, Long memberId); //LocalDateTime fromStartedAt, LocalDateTime toFinishedAt
}
