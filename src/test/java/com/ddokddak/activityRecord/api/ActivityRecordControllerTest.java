package com.ddokddak.activityRecord.api;

import com.ddokddak.activityRecord.dto.CreateActivityRecordRequest;
import com.ddokddak.activityRecord.entity.ActivityRecord;
import com.ddokddak.activityRecord.mapper.ActivityRecordMapper;
import com.ddokddak.category.entity.Category;
import com.ddokddak.common.exception.NotValidRequestException;
import com.ddokddak.common.exception.type.NotValidRequest;
import com.ddokddak.member.Member;
import com.ddokddak.usecase.CreateActivityRecordUsecase;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.IntStream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@AutoConfigureRestDocs
@AutoConfigureMockMvc
@SpringBootTest
class ActivityRecordControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private CreateActivityRecordUsecase createActivityRecordUsecase;

    private Category category;

    @BeforeEach
    void setUp() {
        this.category = Category.builder()
                .id(1L)
                .name("test")
                .color("blue")
                .level(0)
                .mainCategory(null)
                .member(Member.builder().id(1L).build())
                .deleteYn("N")
                .build();
    }

    @AfterEach
    void tearDown() {
    }

    @WithMockUser
    @DisplayName("활동 내역 기록이 잘 수행되는지 확인_201")
    @Test
    void createActivityRecord() throws Exception {
        // given
        var localStartedTime = LocalDateTime.of(2023,1,1,13,0);
        var startedTime = ZonedDateTime.of(localStartedTime, ZoneId.of("Asia/Seoul"));
        var request = CreateActivityRecordRequest.builder()
                .categoryId(1L)
                .startedAt(startedTime)
                .finishedAt(startedTime.plusMinutes(150))
                .timeUnit(30)
                .content("test-activity")
                .build();
        var content = objectMapper.writeValueAsString(request);

        List<ActivityRecord> activityRecords = IntStream.range(0, 150/30)
                .mapToObj(i -> ActivityRecordMapper.toEntityForTest(i, request, this.category))
                .toList();

        var response = activityRecords.stream()
                .map(ActivityRecordMapper::toActivityRecordResponse)
                .toList();

        // when, then
        given(createActivityRecordUsecase.execute(any(), any())).willReturn(response);

        mockMvc.perform(post("/api/v1/activity-records")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(document("create-activity-record",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestParameters(
                                parameterWithName("memberId").optional().description("멤버 아이디(temp)")
                        ),
                        requestFields(
                                fieldWithPath("categoryId").description("카테고리 아이디"),
                                fieldWithPath("startedAt").description("시작 시간 (데이터 예 : 2023-01-01T16:00:00 Asia/Seoul)"),
                                fieldWithPath("finishedAt").description("종료 시간"),
                                fieldWithPath("content").description("활동 내용"),
                                fieldWithPath("timeUnit").description("시간 단위")
                        ),
                        responseFields(
                                fieldWithPath("message").description("응답 메세지"),
                                subsectionWithPath("result").description("결과값"),
                                fieldWithPath("result[].categoryId").description("카테고리 아이디"),
                                fieldWithPath("result[].categoryName").description("카테고리 이름"),
                                fieldWithPath("result[].categoryColor").description("카테고리 색상"),
                                fieldWithPath("result[].startedAt").description("시작 시간"),
                                fieldWithPath("result[].finishedAt").description("종료 시간"),
                                fieldWithPath("result[].timeZone").description("타임존"),
                                fieldWithPath("result[].content").description("활동 내용"),
                                fieldWithPath("result[].timeUnit").description("시간 단위")
                        )
                ));
    }

    @WithMockUser
    @DisplayName("활동 내역 기록 시도 중 잘못된 시간 데이터 전달시 예외 발생_400")
    @Test
    void createActivityRecordWithException() throws Exception {
        // given
        var localStartedTime = LocalDateTime.of(2023,1,1,13,11);
        var startedTime = ZonedDateTime.of(localStartedTime, ZoneId.of("Asia/Seoul"));
        var request = CreateActivityRecordRequest.builder()
                .categoryId(1L)
                .startedAt(startedTime)
                .finishedAt(startedTime.plusMinutes(150))
                .timeUnit(30)
                .content("test-activity")
                .build();
        var content = objectMapper.writeValueAsString(request);

        List<ActivityRecord> activityRecords = IntStream.range(0, 150/30)
                .mapToObj(i -> ActivityRecordMapper.toEntityForTest(i, request, category))
                .toList();

        var response = activityRecords.stream()
                .map(ActivityRecordMapper::toActivityRecordResponse)
                .toList();

        // when, then
        given(createActivityRecordUsecase.execute(any(), any())).willThrow(new NotValidRequestException(NotValidRequest.WRONG_TIME_DATA));

        mockMvc.perform(post("/api/v1/activity-records")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andDo(document("create-activity-record-404",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestParameters(
                                parameterWithName("memberId").optional().description("멤버 아이디(temp)")
                        ),
                        responseFields(
                                fieldWithPath("message").description("응답 메세지")
                        )
                ));
    }
}