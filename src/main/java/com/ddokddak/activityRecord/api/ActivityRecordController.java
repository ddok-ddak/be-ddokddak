package com.ddokddak.activityRecord.api;

import com.ddokddak.activityRecord.dto.ActivityRecordResponse;
import com.ddokddak.activityRecord.dto.CreateActivityRecordRequest;
import com.ddokddak.activityRecord.dto.ReadActivityRecordRequest;
import com.ddokddak.activityRecord.dto.ReadActivityRecordResponse;
import com.ddokddak.activityRecord.service.ActivityRecordReadService;
import com.ddokddak.common.dto.CommonResponse;
import com.ddokddak.usecase.CreateActivityRecordUsecase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalTime;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/activity-records") // TODO URI 카멜케이스 혹은 대쉬
public class ActivityRecordController {

    private final CreateActivityRecordUsecase createActivityRecordUsecase;
    private final ActivityRecordReadService activityRecordReadService;

    @GetMapping
    public ResponseEntity<CommonResponse<List<ReadActivityRecordResponse>>> getActivityRecordByDaily(ReadActivityRecordRequest req){
        var response = activityRecordReadService.findByMemberIdAndStartedAtBetween( req.memberId(), req.fromStartedAt(), req.toStartedAt() );
        return null;
    }


    @PostMapping
    public ResponseEntity<CommonResponse<List<ActivityRecordResponse>>> createCategory(@RequestParam(required = false, defaultValue = "1") Long memberId,
                                                                  @RequestBody CreateActivityRecordRequest req) {

        var response = createActivityRecordUsecase.execute(req, memberId);
        URI location = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .build()
                .toUri();

        return ResponseEntity.created(location)
                .body(new CommonResponse<>("Successfully Created", response));
    }
}
