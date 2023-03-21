package com.ddokddak.activityRecord.dto;
import lombok.Builder;

public record StatsActivityRecordResult(
        Long categoryId,
        long timeSum
) {
    @Builder
    public StatsActivityRecordResult {}
}
