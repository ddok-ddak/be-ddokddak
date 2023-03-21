package com.ddokddak.usecase;

import com.ddokddak.activityRecord.dto.StatsActivityRecordRequest;
import com.ddokddak.activityRecord.dto.StatsActivityRecordResponse;
import com.ddokddak.activityRecord.mapper.ActivityRecordMapper;
import com.ddokddak.activityRecord.service.ActivityRecordReadService;
import com.ddokddak.category.entity.Category;
import com.ddokddak.category.service.CategoryReadService;
import com.ddokddak.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class StatsActivityRecordUsecase {
    private final ActivityRecordReadService activityRecordReadService;
    private final CategoryReadService categoryReadService;
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public List<StatsActivityRecordResponse> execute(StatsActivityRecordRequest req, Long memberId) {
        var categories = categoryReadService.findByMemberId(memberId); //categoryReadService.findByRefMember(memberRepository.getReferenceById(memberId));
        var statsActivityRecordResponses = activityRecordReadService.statsByMemberIdAndPeriod(req, memberId);

        // 부모 카테고리 기준 그루핑
        Map<Long, List<Category>> categoriesMap = categories.stream()
                .collect(Collectors.groupingBy(item -> item.getMainCategory() == null ? 0L : item.getMainCategory().getId()));
        // 카테고리 아이디 정보만 가지고 있는 통계 데이터, 아이디 정보로 매핑
        var statsTempResult = statsActivityRecordResponses.stream()
                .collect(Collectors.toMap(item->item.categoryId(), Function.identity()));

        // 카테고리 아이디를 가지고 순회 시작
        var responseList= categoriesMap.entrySet()
                .stream()
                .filter(item-> item.getKey()==0 || item.getKey()==null) // 대분류 기준 자식(subRecordsStats)에 소분류 담기
                .flatMap(item -> item.getValue()
                        .stream()
                        .map(main -> {
                            long sum = statsTempResult.get(main.getId()) == null ? 0L : statsTempResult.get(main.getId()).timeSum();
                            List<StatsActivityRecordResponse> tempChildren = new ArrayList<>();
                            for (Category child :  categoriesMap.get(main.getId())) {
                                var time = statsTempResult.get(child.getId()) == null ? 0L : statsTempResult.get(child.getId()).timeSum();
                                sum += time;
                                tempChildren.add(
                                        ActivityRecordMapper.toStatResponse(child, time, null));
                            }
                            return ActivityRecordMapper.toStatResponse(main, sum, tempChildren);
                        })
                )
                .toList();

        return responseList;
    }
}
