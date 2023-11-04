package com.ddokddak.activityRecord.repository;

import com.ddokddak.activityRecord.dto.StatsActivityRecordRequest;
import com.ddokddak.activityRecord.dto.StatsActivityRecordResult;
import com.ddokddak.activityRecord.entity.ActivityRecord;
import com.ddokddak.category.entity.Category;
import org.hibernate.annotations.SQLDelete;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Collection;
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

    List<ActivityRecord> findByMemberIdAndStartedAtBetweenAndIsDeletedFalse(
            Long memberId, LocalDateTime fromStartedAt, LocalDateTime toStartedAt);

    @Query("SELECT new com.ddokddak.activityRecord.dto.StatsActivityRecordResult(" +
            "ar.category.id, sum(TIME_TO_SEC(TIMEDIFF(ar.finishedAt, ar.startedAt)) / 60)) " + //, function('date_format', ar.startedAt, '%Y-%m')) " +
            "FROM ActivityRecord ar JOIN ar.category " +
            "WHERE ar.member.id = :memberId " +
            "AND ar.isDeleted = false " +
            "AND ar.category.isDeleted = false " +
            "AND ar.startedAt >= :#{#request.fromStartedAt} " +
            "AND ar.finishedAt <= :#{#request.toFinishedAt} " +
            "GROUP BY ar.category") //, function('date_format', ar.startedAt, '%Y-%m')" )
    List<StatsActivityRecordResult> statsByMemberIdAndPeriodGroupByCategory(StatsActivityRecordRequest request, Long memberId); //LocalDateTime fromStartedAt, LocalDateTime toFinishedAt

    @Modifying
    @Transactional
    @Query("UPDATE ActivityRecord ar SET ar.isDeleted = 1, ar.modifiedAt = NOW() WHERE ar.member.id=:id AND ar.id = :activityRecordId")
    void softDeleteByMemberIdAndId(Long id, Long activityRecordId);

    @Modifying
    @Query("UPDATE ActivityRecord ar SET ar.isDeleted = 1, ar.modifiedAt = NOW() WHERE ar.member.id=:memberId AND ar.category = :category")
    void softDeleteByMemberIdAndCategory(Long memberId, Category category);

}
