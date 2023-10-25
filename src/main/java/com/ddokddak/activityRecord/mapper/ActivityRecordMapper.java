package com.ddokddak.activityRecord.mapper;

import com.ddokddak.activityRecord.dto.ActivityRecordResponse;
import com.ddokddak.activityRecord.dto.CreateActivityRecordRequest;
import com.ddokddak.activityRecord.dto.StatsActivityRecordResponse;
import com.ddokddak.activityRecord.entity.ActivityRecord;
import com.ddokddak.category.entity.Category;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class ActivityRecordMapper {

    public static final ActivityRecord toEntity(CreateActivityRecordRequest req, Category category) {

        return ActivityRecord.builder()
                .category(category)
                .member(category.getMember())
                .startedAt(req.startedAt())
                .finishedAt(req.finishedAt())
                .content(req.content())
                .timeUnit(req.timeUnit())
                .build();
    }

    public static final ActivityRecord toEntitySplitedByTimeUnit(Integer cnt, CreateActivityRecordRequest req, Category category) {

        return ActivityRecord.builder()
                .category(category)
                .member(category.getMember())
                .startedAt(req.startedAt().plusMinutes(30*cnt))
                .finishedAt(req.startedAt().plusMinutes(30*(cnt+1)))
                .content(req.content())
                .timeUnit(req.timeUnit())
                .build();
    }

    public static final ActivityRecordResponse toActivityRecordResponse(ActivityRecord activityRecord) {

        return ActivityRecordResponse.builder()
                .activityRecordId(activityRecord.getId())
                .categoryId(activityRecord.getCategory().getId())
                .categoryName(activityRecord.getCategory().getName())
                .categoryColor(activityRecord.getCategory().getColor())
                .startedAt(activityRecord.getStartedAt())
                .finishedAt(activityRecord.getFinishedAt())
                .content(activityRecord.getContent())
                .timeUnit(activityRecord.getTimeUnit())
                .build();
    }

    public static final StatsActivityRecordResponse toStatResponse(Category category, long time, List<StatsActivityRecordResponse> children) {
        return StatsActivityRecordResponse.builder()
                .categoryId(category.getId())
                .categoryColor(category.getColor())
                .categoryName(category.getName())
                .level(category.getLevel())
                .parentId(category.getMainCategory()==null?null:category.getMainCategory().getId())
                .children(children)
                .timeSum(time)
                .build();
    }

    public static final ActivityRecord toEntityForTest(int cnt, CreateActivityRecordRequest req, Category category) {

        return ActivityRecord.builder()
                .id(Long.valueOf(cnt))
                .category(category)
                .member(category.getMember())
                .startedAt(req.startedAt().plusMinutes(30*cnt))
                .finishedAt(req.startedAt().plusMinutes(30*(cnt+1)))
                .content(req.content())
                .timeUnit(req.timeUnit())
                .build();
    }
}
