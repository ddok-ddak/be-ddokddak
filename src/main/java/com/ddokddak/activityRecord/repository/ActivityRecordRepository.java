package com.ddokddak.activityRecord.repository;

import com.ddokddak.activityRecord.domain.dto.StatsActivityRecordRequest;
import com.ddokddak.activityRecord.domain.dto.StatsActivityRecordResult;
import com.ddokddak.activityRecord.domain.entity.ActivityRecord;
import com.ddokddak.category.domain.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ActivityRecordRepository extends JpaRepository<ActivityRecord, Long> {

    Optional<ActivityRecord> findByIdAndMemberIdAndIsDeletedFalse(Long activityRecordId, Long memberId);

    Optional<ActivityRecord> findByIdAndIsDeletedFalse(Long targetId);

    List<ActivityRecord> findByMemberIdAndCategoryIdAndIsDeletedFalse(Long memberId, Long categoryId);

    Boolean existsByMemberIdAndStartedAtBetweenAndIsDeletedFalse(
            Long memberId, LocalDateTime fromStartedAt, LocalDateTime toStartedAt);

    List<ActivityRecord> findByMemberIdAndStartedAtAfterAndStartedAtBeforeAndIsDeletedFalse(
            Long memberId, LocalDateTime fromStartedAt, LocalDateTime minusSeconds);

    @Query("SELECT ar FROM ActivityRecord ar " +
            "WHERE ar.member.id = :memberId " +
            "AND ar.isDeleted = false " +
            "AND ar.id != :id " +
            "AND ((ar.startedAt < :startedAt AND ar.finishedAt > :startedAt) " +
            "OR (ar.startedAt < :finishedAt AND ar.finishedAt > :finishedAt))")
    List<ActivityRecord> findByMemberIdAndIsDeletedFalseAndBetweenPeriodConditionExceptSelf(
            Long id, Long memberId, LocalDateTime startedAt, LocalDateTime finishedAt);

    @Query("SELECT count(ar) > 0 FROM ActivityRecord ar " +
            "WHERE ar.member.id = :memberId " +
            "AND ar.isDeleted = false " +
            "AND ((ar.startedAt <= :startedAt AND ar.finishedAt > :startedAt) " +
            "OR (ar.startedAt < :finishedAt AND ar.finishedAt >= :finishedAt)" +
            "OR (ar.startedAt >= :startedAt AND ar.finishedAt <= :finishedAt))")
    Boolean existByMemberIdAndIsDeletedFalseAndBetweenPeriodCondition(
            Long memberId, LocalDateTime startedAt, LocalDateTime finishedAt);

    List<ActivityRecord> findByMemberIdAndStartedAtBetweenAndIsDeletedFalse(
            Long memberId, LocalDateTime fromStartedAt, LocalDateTime toStartedAt);

    @Query("SELECT new com.ddokddak.activityRecord.domain.dto.StatsActivityRecordResult(" +
            "ar.category.id, " +
            "sum(TIME_TO_SEC(" +
                "CASE WHEN (ar.startedAt >= :#{#request.fromStartedAt()} AND ar.finishedAt <= :#{#request.toFinishedAt()}) " +
                    "THEN TIMEDIFF(ar.finishedAt, ar.startedAt) " +
                    "WHEN (ar.startedAt <= :#{#request.fromStartedAt()} AND ar.finishedAt >= :#{#request.fromStartedAt()}) " +
                    "THEN TIMEDIFF(ar.finishedAt, :#{#request.fromStartedAt()}) " +
                    "ELSE TIMEDIFF(:#{#request.toFinishedAt()}, ar.startedAt) " +
                    "END" +
            ") / 60)) " + // case when 로직 - 활동기록이 시작 시간, 종료 시간 조건에 겹치는 경우
            "FROM ActivityRecord ar JOIN ar.category " +
            "WHERE ar.member.id = :memberId " +
            "AND ar.isDeleted = false " +
            "AND ar.category.isDeleted = false " +
            "AND ((ar.startedAt >= :#{#request.fromStartedAt()} AND ar.finishedAt <= :#{#request.toFinishedAt()}) " +
            "OR (ar.startedAt <= :#{#request.toFinishedAt()} AND ar.finishedAt >= :#{#request.toFinishedAt()}) " +
            "OR (ar.finishedAt >= :#{#request.fromStartedAt()} AND ar.startedAt <= :#{#request.fromStartedAt()})) " +
            "GROUP BY ar.category")
    List<StatsActivityRecordResult> statsByMemberIdAndPeriodGroupByCategory(StatsActivityRecordRequest request, Long memberId); //LocalDateTime fromStartedAt, LocalDateTime toFinishedAt

    @Modifying
    @Query("UPDATE ActivityRecord ar SET ar.isDeleted = 1, ar.modifiedAt = NOW() WHERE ar.member.id=:id AND ar.id = :activityRecordId")
    void softDeleteByMemberIdAndId(Long id, Long activityRecordId);

    @Modifying
    @Query("UPDATE ActivityRecord ar SET ar.isDeleted = 1, ar.modifiedAt = NOW() WHERE ar.member.id=:memberId AND ar.category = :category")
    void softDeleteByMemberIdAndCategory(Long memberId, Category category);

}
