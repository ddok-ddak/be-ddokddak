package com.ddokddak.member.api;

import com.ddokddak.category.domain.dto.CategoryTemplateRequest;
import com.ddokddak.member.domain.dto.ModifyStartDayRequest;
import com.ddokddak.member.domain.dto.ModifyStartTimeRequest;
import com.ddokddak.member.domain.enums.CustomOpt;
import com.ddokddak.member.domain.entity.Member;
import com.ddokddak.member.domain.enums.TemplateType;
import com.ddokddak.member.mapper.MemberMapper;
import com.ddokddak.member.service.MemberReadService;
import com.ddokddak.member.service.MemberWriteService;
import com.ddokddak.usecase.CreateCategoryTemplateUsecase;
import com.ddokddak.usecase.ModifyCategoryTemplateUsecase;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@AutoConfigureRestDocs
@AutoConfigureMockMvc
@SpringBootTest
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private MemberReadService memberReadService;
    @MockBean
    private MemberWriteService memberWriteService;
    @MockBean
    private CreateCategoryTemplateUsecase createCategoryTemplateUsecase;
    @MockBean
    private ModifyCategoryTemplateUsecase modifyCategoryTemplateUsecase;

    private Member member;

    @BeforeEach
    void setUp() {
        this.member = Member.builder()
                .id(1L)
                .name("test")
                .nickname("test")
                .email("test@example.com")
                .password("1234")
                .build();
    }

    @AfterEach
    void tearDown() {
    }

    @DisplayName("회원가입 중 중복된 이메일이 있는지 검증_200")
    @Test
    void checkIfDuplicatedEmail() throws Exception {

        // when, then
        doNothing().when(memberReadService).checkIfDuplicatedUserByEmail(any());

        mockMvc.perform(get("/api/v1/members/duplicatedEmail")
                        .param("email", "test@example.com"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(document("check-if-duplicated-email",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestParameters(
                                parameterWithName("email").description("검증할 이메일 주소")
                        ),
                        responseFields(
                                fieldWithPath("status").description("응답 상태"),
                                fieldWithPath("message").description("응답 메세지"),
                                subsectionWithPath("result").description("결과값")
                        )
                ));
    }

    @DisplayName("중복된 이메일이 있는 경우_409")
    @Test
    void return409ExceptionWhenDuplicatedEmail() {
    }

    @DisplayName("회원가입 중 중복된 닉네임이 있는지 검증_200")
    @Test
    void checkIfDuplicatedNickname() throws Exception {

        // when, then
        doNothing().when(memberReadService).checkIfDuplicatedUserByNickname(any());

        mockMvc.perform(get("/api/v1/members/duplicateNickname")
                        .param("nickname", "test"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(document("check-if-duplicated-nickname",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestParameters(
                                parameterWithName("nickname").description("검증할 닉네임명")
                        ),
                        responseFields(
                                fieldWithPath("status").description("응답 상태"),
                                fieldWithPath("message").description("응답 메세지"),
                                subsectionWithPath("result").description("결과값")
                        )
                ));
    }


    @DisplayName("중복된 닉네임이 있는 경우_409")
    @Test
    void return409ExceptionWhenDuplicatedNickname() throws Exception {
    }

    @WithMockUser
    @DisplayName("시작시간 커스텀 수정_200")
    @Test
    void modifyStartTime() throws Exception {

        //given
        var request = ModifyStartTimeRequest.builder()
                .startTime(CustomOpt.StartTime.AM12)
                .build();
        var content = objectMapper.writeValueAsString(request);

        // when, then
        doNothing().when(memberWriteService).updateStartTime(any(), any());

        mockMvc.perform(put("/api/v1/members/custom/start-time")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(document("update-start-time",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("startTime").description("시작 시간")
                        ),
                        responseFields(
                                fieldWithPath("status").description("응답 상태"),
                                fieldWithPath("message").description("응답 메세지"),
                                subsectionWithPath("result").description("결과값")
                        )
                ));
    }

    @WithMockUser
    @DisplayName("시작요일 커스텀 수정_200")
    @Test
    void modifyStartDay() throws Exception {

        //given
        var request = ModifyStartDayRequest.builder()
                .startDay(CustomOpt.StartDay.MONDAY)
                .build();
        var content = objectMapper.writeValueAsString(request);

        // when, then
        doNothing().when(memberWriteService).updateStartDay(any(), any());

        mockMvc.perform(put("/api/v1/members/custom/start-day")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(document("update-start-day",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("startDay").description("시작 요일")
                        ),
                        responseFields(
                                fieldWithPath("status").description("응답 상태"),
                                fieldWithPath("message").description("응답 메세지"),
                                subsectionWithPath("result").description("결과값")
                        )
                ));
    }

    @WithMockUser
    @DisplayName("현재 로그인한 멤버 정보 조회_200")
    @Test
    void getCurrentUserInfo() throws Exception {

        var response = MemberMapper.toMemberResponse(this.member);

        // when, then
        given(memberReadService.getUserDtoByEmail(any())).willReturn(response);

        mockMvc.perform(get("/api/v1/members/me"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(document("get-member-info",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("status").description("응답 상태"),
                                fieldWithPath("message").description("응답 메세지"),
                                subsectionWithPath("result").description("결과값"),
                                fieldWithPath("result.email").description("회원 이메일"),
                                fieldWithPath("result.nickname").description("회원 닉네임"),
                                fieldWithPath("result.role").description("회원 역할"),
                                fieldWithPath("result.status").description("회원 상태"),
                                fieldWithPath("result.authProviderType").description("회원 정보 제공자"),
                                fieldWithPath("result.templateType").description("회원 직업"),
                                fieldWithPath("result.startDay").description("활동 시작 요일"),
                                fieldWithPath("result.startTime").description("활동 시작 시간")
                        )
                ));
    }

    @WithMockUser
    @DisplayName("회원가입 후 최초 카테고리 템플릿 등록_200")
    @Test
    void setCategoryTemplateTypeForTheFirstTime() throws Exception {

        //given
        var request = CategoryTemplateRequest.builder()
                .templateType(TemplateType.WORKER)
                .build();
        var content = objectMapper.writeValueAsString(request);

        // when, then
        doNothing().when(createCategoryTemplateUsecase).execute(any(), any());

        mockMvc.perform(post("/api/v1/members/custom/category-template")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(document("set-category-template",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("templateType").description("템플릿 타입")
                        ),
                        responseFields(
                                fieldWithPath("status").description("응답 상태"),
                                fieldWithPath("message").description("응답 메세지"),
                                subsectionWithPath("result").description("결과값")
                        )
                ));
    }
}