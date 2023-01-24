package com.ddokddak.usecase;

import com.ddokddak.activityRecord.dto.CreateActivityRecordRequest;
import com.ddokddak.activityRecord.service.ActivityRecordReadService;
import com.ddokddak.activityRecord.service.ActivityRecordWriteService;
import com.ddokddak.category.entity.Category;
import com.ddokddak.category.service.CategoryReadService;
import com.ddokddak.common.exception.NotValidRequestException;
import com.ddokddak.member.Member;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
class CreateActivityRecordUsecaseTest {

    @Mock
    private CategoryReadService categoryReadService;
    @Mock
    private ActivityRecordWriteService activityRecordWriteService;
    @Mock
    private ActivityRecordReadService activityRecordReadService;
    @InjectMocks
    private CreateActivityRecordUsecase createActivityRecordUsecase;

    private Member member;
    private Category category;
    @BeforeEach
    void setUp() {
        this.member  = Member.builder()
                .id(1L)
                .build();
        this.category = Category.builder()
                .id(1L)
                .name("name")
                .color("color")
                .level(0)
                .mainCategory(null)
                .subCategories(Collections.EMPTY_LIST)
                .member(member)
                .build();
    }

    @AfterEach
    void tearDown() {
    }

    @DisplayName("활동 기록이 잘 저장되는지 검증")
    @Test
    void execute() {
        // given
        var localStartedTime = LocalDateTime.of(2023, 1, 1, 13, 0);
        var startedTime = ZonedDateTime.of(localStartedTime, ZoneId.of("Asia/Seoul"));

        var activityRecordRequest = CreateActivityRecordRequest.builder()
                .categoryId(1L)
                .startedAt(startedTime)
                .finishedAt(startedTime.plusMinutes(150))
                .timeUnit(30)
                .content("test content")
                .build();

        given(categoryReadService.findByIdAndMemberId(any(), any())).willReturn(this.category);

        // when
        var executed = createActivityRecordUsecase.execute(activityRecordRequest, this.member.getId());

        // then
        Assertions.assertEquals(150/30, executed.size());
    }

    @DisplayName("활동 기록이 잘 저장 실행 중 시간 데이터 검증 실패 오류 발생")
    @Test
    void occurExceptionWhileExecuting() {
        // given
        var localStartedTime = LocalDateTime.of(2023, 1, 1, 13, 11);
        var startedTime = ZonedDateTime.of(localStartedTime, ZoneId.of("Asia/Seoul"));

        var activityRecordRequest = CreateActivityRecordRequest.builder()
                .categoryId(1L)
                .startedAt(startedTime)
                .finishedAt(startedTime.plusMinutes(150))
                .timeUnit(30)
                .content("test content")
                .build();

        given(categoryReadService.findByIdAndMemberId(any(), any())).willReturn(this.category);

        // when, then
        assertThatThrownBy(()->createActivityRecordUsecase.execute(activityRecordRequest, this.member.getId()))
                .isInstanceOf(NotValidRequestException.class)
                .hasMessage("Not Valid Time Data");
    }
}