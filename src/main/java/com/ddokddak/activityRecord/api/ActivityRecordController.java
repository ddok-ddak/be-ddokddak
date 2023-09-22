package com.ddokddak.activityRecord.api;

import com.ddokddak.activityRecord.dto.*;
import com.ddokddak.activityRecord.dto.ActivityRecordResponse;
import com.ddokddak.activityRecord.dto.CreateActivityRecordRequest;
import com.ddokddak.activityRecord.dto.ReadActivityRecordRequest;
import com.ddokddak.activityRecord.service.ActivityRecordReadService;
import com.ddokddak.auth.domain.UserPrincipal;
import com.ddokddak.activityRecord.service.ActivityRecordWriteService;
import com.ddokddak.auth.domain.UserPrincipal;
import com.ddokddak.activityRecord.service.ActivityRecordWriteService;
import com.ddokddak.auth.domain.UserPrincipal;
import com.ddokddak.auth.domain.UserPrincipal;
import com.ddokddak.activityRecord.service.ActivityRecordWriteService;
import com.ddokddak.auth.domain.UserPrincipal;
import com.ddokddak.common.dto.CommonResponse;
import com.ddokddak.usecase.CreateActivityRecordUsecase;
import com.ddokddak.usecase.StatsActivityRecordUsecase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    private final ActivityRecordWriteService activityRecordWriteService;

    @PutMapping
    public ResponseEntity<CommonResponse<Boolean>> modifyActivityRecord(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody ModifyActivityRecordRequest req){
        activityRecordWriteService.modifyActivityRecord( userPrincipal.getId(), req );
        return ResponseEntity.ok( new CommonResponse<>("Successfully Modified", Boolean.TRUE) );
    }

    @DeleteMapping
    public ResponseEntity<CommonResponse<Boolean>> removeActivityRecordById(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
        @PathVariable Long activityRecordId
    ) {
        activityRecordWriteService.removeActivityRecordByMemberIdAndId( userPrincipal.getId(), activityRecordId );
        return  ResponseEntity.ok( new CommonResponse<>( "Successfully Deleted", Boolean.TRUE) );
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<ActivityRecordResponse>>> getActivityRecord(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            ReadActivityRecordRequest req){
        var response =
                activityRecordReadService.findByMemberIdAndStartedAtBetween(userPrincipal.getId(), req.fromStartedAt(), req.toStartedAt());
        return ResponseEntity.ok(new CommonResponse<>("Success", response));
    }

    @GetMapping("/stats")
    public ResponseEntity<CommonResponse<List<StatsActivityRecordResponse>>> getRecordStatsGroupByCategory(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            StatsActivityRecordRequest req)
    {
        var response = statsActivityRecordUsecase.execute(req, userPrincipal.getId());
        return ResponseEntity.ok(new CommonResponse<>("Success", response));
    }

    @PostMapping
    public ResponseEntity<CommonResponse<Boolean>> createActivityRecord(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @RequestBody CreateActivityRecordRequest req)
    {
        createActivityRecordUsecase.execute(req, userPrincipal.getId());
        URI location = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .build()
                .toUri();

        return ResponseEntity.created(location)
                .body(new CommonResponse<>("Successfully Created", Boolean.TRUE));
    }
}
