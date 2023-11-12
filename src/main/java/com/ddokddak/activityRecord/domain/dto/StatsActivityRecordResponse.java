package com.ddokddak.activityRecord.domain.dto;

import lombok.Builder;
import java.util.List;

public record StatsActivityRecordResponse(
        Long categoryId,
        String categoryName,
        String categoryColor,
        Long parentId,
        Integer level,
        long timeSum,
        List<StatsActivityRecordResponse> children
) {
    @Builder
    public StatsActivityRecordResponse {}

}
