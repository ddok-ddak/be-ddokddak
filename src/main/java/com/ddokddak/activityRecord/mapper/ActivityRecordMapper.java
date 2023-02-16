package com.ddokddak.activityRecord.mapper;

import com.ddokddak.activityRecord.dto.ActivityRecordResponse;
import com.ddokddak.activityRecord.dto.CreateActivityRecordRequest;
import com.ddokddak.activityRecord.entity.ActivityRecord;
import com.ddokddak.category.entity.Category;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Component
public class ActivityRecordMapper {

    public static ActivityRecord toEntity(Integer cnt, CreateActivityRecordRequest req, Category category) {

        return ActivityRecord.builder()
                .category(category)
                .member(category.getMember())
                .categoryColor(category.getColor())
                .categoryName(category.getName())
                .startedAt(req.startedAt().toLocalDateTime().plusMinutes(30*cnt))
                .finishedAt(req.startedAt().toLocalDateTime().plusMinutes(30*(cnt+1)))
                .timeZone(req.startedAt().getZone().toString())
                .content(req.content())
                .timeUnit(req.timeUnit())
                .build();
    }

    public static ActivityRecordResponse toActivityRecordResponse(ActivityRecord activityRecord) {

        return ActivityRecordResponse.builder()
                .activityRecordId(activityRecord.getId())
                .categoryId(activityRecord.getCategory().getId())
                .categoryName(activityRecord.getCategoryName())
                .categoryColor(activityRecord.getCategoryColor())
                .startedAt(ZonedDateTime.of(activityRecord.getStartedAt(), ZoneId.of(activityRecord.getTimeZone())))
                .finishedAt(ZonedDateTime.of(activityRecord.getFinishedAt(), ZoneId.of(activityRecord.getTimeZone())))
                .timeZone(activityRecord.getTimeZone())
                .content(activityRecord.getContent())
                .timeUnit(activityRecord.getTimeUnit())
                .build();
    }

    public static ActivityRecord toEntityForTest(int cnt, CreateActivityRecordRequest req, Category category) {

        return ActivityRecord.builder()
                .id(Long.valueOf(cnt))
                .category(category)
                .member(category.getMember())
                .categoryColor(category.getColor())
                .categoryName(category.getName())
                .startedAt(req.startedAt().toLocalDateTime().plusMinutes(30*cnt))
                .finishedAt(req.startedAt().toLocalDateTime().plusMinutes(30*(cnt+1)))
                .timeZone(req.startedAt().getZone().toString())
                .content(req.content())
                .timeUnit(req.timeUnit())
                .build();
    }
}
