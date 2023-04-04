package com.ddokddak.activityRecord.fixture;

import com.ddokddak.activityRecord.entity.ActivityRecord;
import com.ddokddak.category.entity.Category;
import com.ddokddak.member.entity.Member;


import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.IntStream;

public class ActivityRecordFixture {

    public static List<ActivityRecord> createActivityRecords(int start, int end, Member member, List<Category> categories) {

        var startedTime = ZonedDateTime.now();
        var activityRecordList = IntStream.range(start, end)
                .mapToObj(i -> ActivityRecord.builder()
                        .category(categories.get(i))
                        .categoryName(categories.get(i).getName())
                        .categoryColor(categories.get(i).getColor())
                        .startedAt(startedTime.minusMinutes(30*(i+1)).toLocalDateTime())
                        .finishedAt(startedTime.minusMinutes(30*i).toLocalDateTime())
                        .timeZone(startedTime.getZone().toString())
                        .content("content")
                        .timeUnit(30)
                        .member(member)
                        .deleteYn("N")
                        .build()
                ).toList();

        return activityRecordList;
    }
}
