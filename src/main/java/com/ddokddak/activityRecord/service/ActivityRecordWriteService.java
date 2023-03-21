package com.ddokddak.activityRecord.service;

import com.ddokddak.activityRecord.entity.ActivityRecord;
import com.ddokddak.activityRecord.repository.ActivityRecordJdbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ActivityRecordWriteService {

    private final ActivityRecordJdbcRepository activityRecordJdbcRepository;

    @Transactional
    public void saveBulkActivityRecords(List<ActivityRecord> activityRecords) {
        activityRecordJdbcRepository.bulkSave(activityRecords);
    }
}
