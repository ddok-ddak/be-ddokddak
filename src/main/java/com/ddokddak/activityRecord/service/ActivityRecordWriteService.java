package com.ddokddak.activityRecord.service;

import com.ddokddak.activityRecord.entity.ActivityRecord;
import com.ddokddak.activityRecord.repository.ActivityRecordJdbcRepository;
import com.ddokddak.activityRecord.repository.ActivityRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ActivityRecordWriteService {

    private final ActivityRecordJdbcRepository activityRecordJdbcRepository;
    private final ActivityRecordRepository activityRecordRepository;

    @Transactional
    public void saveBulkActivityRecords(List<ActivityRecord> activityRecords) {
        activityRecordJdbcRepository.bulkSave(activityRecords);
    }

    @Transactional
    public void saveAll(List<ActivityRecord> activityRecords) {
        activityRecordRepository.saveAll(activityRecords);
    }
}
