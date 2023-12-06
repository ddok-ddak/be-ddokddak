package com.ddokddak.activityRecord.fixture;

import com.ddokddak.activityRecord.domain.entity.ActivityRecord;
import com.ddokddak.category.domain.entity.Category;
import com.ddokddak.member.domain.entity.Member;


import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.IntStream;

public class ActivityRecordFixture {

    public static List<ActivityRecord> createActivityRecords(int start, int end, Member member, List<Category> categories) {

        var startedTime = LocalDateTime.now();
        var categoreisLength = categories.size();
        var activityRecordList = IntStream.range(start, end)
                .mapToObj(i -> ActivityRecord.builder()
                        .category(categories.get(i % categoreisLength))
                        .startedAt(startedTime.minusMinutes(30*(i+1)))
                        .finishedAt(startedTime.minusMinutes(30*i))
                        .content("content")
                        .timeUnit(30)
                        .member(member)
                        .isDeleted(Boolean.FALSE)
                        .build()
                ).toList();

        return activityRecordList;
    }
}
