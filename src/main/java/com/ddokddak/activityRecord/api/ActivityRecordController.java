package com.ddokddak.activityRecord.api;

import com.ddokddak.activityRecord.dto.*;
import com.ddokddak.activityRecord.dto.ActivityRecordResponse;
import com.ddokddak.activityRecord.dto.CreateActivityRecordRequest;
import com.ddokddak.activityRecord.dto.ReadActivityRecordRequest;
import com.ddokddak.activityRecord.service.ActivityRecordReadService;
import com.ddokddak.common.dto.CommonResponse;
import com.ddokddak.usecase.CreateActivityRecordUsecase;
import com.ddokddak.usecase.StatsActivityRecordUsecase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/activity-records")
public class ActivityRecordController {

    private final CreateActivityRecordUsecase createActivityRecordUsecase;
    private final StatsActivityRecordUsecase statsActivityRecordUsecase;
    private final ActivityRecordReadService activityRecordReadService;

    @GetMapping
    public ResponseEntity<CommonResponse<List<ActivityRecordResponse>>> getActivityRecordByDaily(
            @RequestParam(required = false, defaultValue = "1") Long memberId,
            ReadActivityRecordRequest req){
        var response = activityRecordReadService.findByMemberIdAndStartedAtBetween(memberId, req.fromStartedAt(), req.toStartedAt() );
        return ResponseEntity.ok(new CommonResponse<List<ActivityRecordResponse>>("Success", response));
    }

    @PostMapping
    public ResponseEntity<CommonResponse<List<ActivityRecordResponse>>> createActivityRecord(
            @RequestParam(required = false, defaultValue = "1") Long memberId,
            @RequestBody CreateActivityRecordRequest req)
    {
        var response = createActivityRecordUsecase.execute(req, memberId);
        URI location = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .build()
                .toUri();

        return ResponseEntity.created(location)
                .body(new CommonResponse<>("Successfully Created", response));
    }

    @GetMapping("/stats")
    public ResponseEntity<CommonResponse<List<StatsActivityRecordResponse>>> getRecordStatsGroupByCategory(
            @RequestParam(required = false, defaultValue = "1") Long memberId, StatsActivityRecordRequest req)
    {
        var response = statsActivityRecordUsecase.execute(req, memberId);
        return ResponseEntity.ok(new CommonResponse<>("Success", response));
    }
}
