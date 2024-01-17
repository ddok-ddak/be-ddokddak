package com.ddokddak.activityRecord.api;

import com.ddokddak.activityRecord.domain.dto.CreateActivityRecordRequest;
import com.ddokddak.activityRecord.domain.dto.ModifyActivityRecordRequest;
import com.ddokddak.activityRecord.domain.dto.StatsActivityRecordResponse;
import com.ddokddak.activityRecord.domain.entity.ActivityRecord;
import com.ddokddak.activityRecord.fixture.ActivityRecordFixture;
import com.ddokddak.activityRecord.mapper.ActivityRecordMapper;
import com.ddokddak.activityRecord.service.ActivityRecordReadService;
import com.ddokddak.activityRecord.service.ActivityRecordWriteService;
import com.ddokddak.auth.filter.JwtAuthenticationFilter;
import com.ddokddak.category.domain.entity.Category;
import com.ddokddak.category.domain.entity.CategoryIcon;
import com.ddokddak.common.exception.NotValidRequestException;
import com.ddokddak.common.exception.type.ActivityException;
import com.ddokddak.member.domain.entity.Member;
import com.ddokddak.usecase.CreateActivityRecordUsecase;
import com.ddokddak.usecase.StatsActivityRecordUsecase;
import com.ddokddak.utils.WebMvcConfigWithoutCsrf;
import com.ddokddak.utils.security.WithMockCustomUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@AutoConfigureRestDocs
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@Import(WebMvcConfigWithoutCsrf.class)
@WebMvcTest(controllers = ActivityRecordController.class,
        excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtAuthenticationFilter.class)})
class ActivityRecordControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CreateActivityRecordUsecase createActivityRecordUsecase;
    @MockBean
    private StatsActivityRecordUsecase statsActivityRecordUsecase;
    @MockBean
    private ActivityRecordWriteService activityRecordWriteService;
    @MockBean
    private ActivityRecordReadService activityRecordReadService;

    private Category category;
    private List<ActivityRecord> activityRecords;
    private Authentication authentication;

    @BeforeEach
    void setUp() {
        var member = Member.builder().id(1L).build();
        var categoryIcon = CategoryIcon.builder()
                .id(1L)
                .iconGroup("base")
                .build();
        this.category = Category.builder()
                .id(1L)
                .name("test")
                .color("blue")
                .iconFile(categoryIcon)
                .level(0)
                .mainCategory(null)
                .member(member)
                .isDeleted(Boolean.FALSE)
                .build();
        this.activityRecords = ActivityRecordFixture.createActivityRecords(0, 4, member, List.of(category));
    }

    @AfterEach
    void tearDown() {
    }

    @WithMockCustomUser
    @DisplayName("활동 기록 조회 확인_200")
    @Test
    void getActivityRecords() throws Exception {

        // given
        var response = this.activityRecords.stream()
                .map(el -> ActivityRecordMapper.toActivityRecordResponse(el))
                .toList();

        given(activityRecordReadService.findByMemberIdAndStartedAtBetween(any(), any(), any())).willReturn(response);
        var params = new LinkedMultiValueMap<String, String>();
        params.add("fromStartedAt", "2023-11-23T04:00:00");
        params.add("toStartedAt", "2023-11-24T03:59:59");

        // when, then
        mockMvc.perform(get("/api/v1/activity-records")
                        .params(params)
                        .header("Authorization", "Bearer {ACCESS_TOKEN}"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(document("read-activity-record",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestParameters(
                                parameterWithName("fromStartedAt").description("조회 시작 시간"),
                                parameterWithName("toStartedAt").description("조회 끝 시간")
                        ),
                        responseFields(
                                fieldWithPath("status").description("응답 상태"),
                                fieldWithPath("message").description("응답 메세지"),
                                subsectionWithPath("result[]").description("결과값"),
                                fieldWithPath("result[].activityRecordId").description("활동기록 아이디"),
                                fieldWithPath("result[].categoryId").description("카테고리 아이디"),
                                fieldWithPath("result[].categoryName").description("카테고리 이름"),
                                fieldWithPath("result[].categoryColor").description("카테고리 색상"),
                                fieldWithPath("result[].content").description("기록 내용"),
                                fieldWithPath("result[].startedAt").description("시작 시간"),
                                fieldWithPath("result[].finishedAt").description("종료 시간"),
                                fieldWithPath("result[].timeUnit").description("기록 시간 단위")
                        )
                ));
    }

    @WithMockCustomUser
    @DisplayName("활동 기록 통계 조회 확인_200")
    @Test
    void getActivityRecordStats() throws Exception {

        // given
        var response = StatsActivityRecordResponse.builder()
                .categoryId(1L)
                .categoryName("category1")
                .categoryColor("blue")
                .parentId(null)
                .timeSum(120)
                .children(List.of(StatsActivityRecordResponse.builder()
                        .categoryId(2L)
                        .categoryName("sub category")
                        .categoryColor("blue")
                        .parentId(1L)
                        .timeSum(120)
                        .children(null)
                        .build()))
                .build();

        given(statsActivityRecordUsecase.execute(any(), any())).willReturn(List.of(response));
        var params = new LinkedMultiValueMap<String, String>();
        params.add("fromStartedAt", "2023-11-01T04:00:00");
        params.add("toStartedAt", "2023-12-01T03:59:59");

        // when, then
        mockMvc.perform(get("/api/v1/activity-records/stats")
                        .params(params)
                        .header("Authorization", "Bearer {ACCESS_TOKEN}"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(document("read-activity-record-stats",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestParameters(
                                parameterWithName("fromStartedAt").description("조회 시작 시간"),
                                parameterWithName("toStartedAt").description("조회 끝 시간")
                        ),
                        responseFields(
                                fieldWithPath("status").description("응답 상태"),
                                fieldWithPath("message").description("응답 메세지"),
                                subsectionWithPath("result[]").description("결과값"),
                                fieldWithPath("result[].categoryId").description("카테고리 아이디"),
                                fieldWithPath("result[].categoryName").description("카테고리 이름"),
                                fieldWithPath("result[].categoryColor").description("카테고리 색상"),
                                fieldWithPath("result[].parentId").description("상위 카테고리"),
                                fieldWithPath("result[].level").description("카테고리 레벨"),
                                fieldWithPath("result[].timeSum").description("총 시간"),
                                fieldWithPath("result[].children[]").description("하위 카테고리 상세")
                        )
                ));
    }

    @WithMockCustomUser
    @DisplayName("활동 내역 기록 api 확인_201")
    @Test
    void createActivityRecord() throws Exception {
        // given
        var localStartedTime = LocalDateTime.of(2023,1,1,13,0);
        var request = CreateActivityRecordRequest.builder()
                .categoryId(1L)
                .startedAt(localStartedTime)
                .finishedAt(localStartedTime.plusMinutes(150))
                .timeUnit(30)
                .content("test-activity")
                .build();
        var content = objectMapper.writeValueAsString(request);

        // when, then
        doNothing().when(createActivityRecordUsecase).execute(any(), any());
        mockMvc.perform(post("/api/v1/activity-records")
                        .header("Authorization", "Bearer {ACCESS_TOKEN}")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(document("create-activity-record",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("categoryId").description("카테고리 아이디"),
                                fieldWithPath("startedAt").description("시작 시간 (데이터 예 : 2023-01-01T16:00:00 Asia/Seoul)"),
                                fieldWithPath("finishedAt").description("종료 시간"),
                                fieldWithPath("content").description("활동 내용"),
                                fieldWithPath("timeUnit").description("시간 단위")
                        ),
                        responseFields(
                                fieldWithPath("status").description("응답 상태"),
                                fieldWithPath("message").description("응답 메세지"),
                                subsectionWithPath("result").description("결과값")
                        )
                ));
    }

    @WithMockCustomUser
    @DisplayName("활동 내역 기록 api 오류_잘못된 시간 데이터 전달 예외_400")
    @Test
    void createActivityRecordWithException() throws Exception {
        // given
        var localStartedTime = LocalDateTime.of(2023,1,1,13,11);
        var request = CreateActivityRecordRequest.builder()
                .categoryId(1L)
                .startedAt(localStartedTime)
                .finishedAt(localStartedTime.plusMinutes(150))
                .timeUnit(30)
                .content("test-activity")
                .build();
        var content = objectMapper.writeValueAsString(request);

        // when, then
        doThrow(new NotValidRequestException(ActivityException.WRONG_TIME_DATA))
                .when(createActivityRecordUsecase)
                .execute(any(), any());

        mockMvc.perform(post("/api/v1/activity-records")
                        .with(csrf())
                        .header("Authorization", "Bearer {ACCESS_TOKEN}")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andDo(document("create-activity-record-400",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("status").description("응답 상태"),
                                fieldWithPath("errorCode").description("응답 코드"),
                                fieldWithPath("message").description("응답 메세지")
                        )
                ));
    }

    @WithMockCustomUser
    @DisplayName("활동 내역 수정 api 확인_200")
    @Test
    void modifyActivityRecord() throws Exception {
        // given
        var localStartedTime = LocalDateTime.of(2023,1,1,13,0);
        var request = ModifyActivityRecordRequest.builder()
                .id(1L)
                .categoryId(1L)
                .startedAt(localStartedTime)
                .finishedAt(localStartedTime.plusMinutes(150))
                .content("test-activity")
                .build();

        var content = objectMapper.writeValueAsString(request);

        // when, then
        doNothing().when(activityRecordWriteService).modifyActivityRecord(any(), any());

        mockMvc.perform(put("/api/v1/activity-records")
                        .header("Authorization", "Bearer {ACCESS_TOKEN}")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(document("modify-activity-record",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("id").description("활동 기록 아이디"),
                                fieldWithPath("categoryId").description("카테고리 아이디"),
                                fieldWithPath("startedAt").description("시작 시간 (데이터 예 : 2023-01-01T16:00:00 Asia/Seoul)"),
                                fieldWithPath("finishedAt").description("종료 시간"),
                                fieldWithPath("content").description("활동 내용")
                        ),
                        responseFields(
                                fieldWithPath("status").description("응답 상태"),
                                fieldWithPath("message").description("응답 메세지"),
                                subsectionWithPath("result").description("결과값")
                        )
                ));
    }

    @WithMockCustomUser
    @DisplayName("활동 내역 삭제 api 확인_200")
    @Test
    void removeActivityRecord() throws Exception {
        // given
        var recordId = 1;

        // when, then
        doNothing().when(activityRecordWriteService).removeActivityRecordByMemberIdAndId(any(), any());

        mockMvc.perform(delete("/api/v1/activity-records/{recordId}", recordId)
                        .header("Authorization", "Bearer {ACCESS_TOKEN}"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(document("remove-activity-record",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("recordId").description("활동기록 아이디")
                        ),
                        responseFields(
                                fieldWithPath("status").description("응답 상태"),
                                fieldWithPath("message").description("응답 메세지"),
                                subsectionWithPath("result").description("결과값")
                        )
                ));
    }
}