package com.ddokddak.activityRecord.domain.dto;
import lombok.Builder;

public record StatsActivityRecordResult(
        Long categoryId,
        long timeSum
) {
    @Builder
    public StatsActivityRecordResult {}
}
