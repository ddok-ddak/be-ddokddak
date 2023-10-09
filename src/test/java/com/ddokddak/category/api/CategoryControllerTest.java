package com.ddokddak.category.api;

import com.ddokddak.activityRecord.dto.CreateActivityRecordRequest;
import com.ddokddak.category.dto.*;
import com.ddokddak.category.entity.Category;
import com.ddokddak.category.fixture.CategoryFixture;
import com.ddokddak.category.mapper.CategoryMapper;
import com.ddokddak.category.service.CategoryReadService;
import com.ddokddak.category.service.CategoryWriteService;
import com.ddokddak.member.entity.Member;
import com.ddokddak.usecase.DeleteCategoryUsecase;
import com.ddokddak.utils.security.WithMockCustomUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
@AutoConfigureRestDocs
@AutoConfigureMockMvc
@SpringBootTest
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private CategoryWriteService categoryWriteService;
    @MockBean
    private CategoryReadService categoryReadService;
    @MockBean
    private DeleteCategoryUsecase deleteCategoryUsecase;

    private Member member;
    private List<Category> mainCategories;
    private List<Category> subCategories;

    @BeforeEach
    void setUp() {
        this.member = Member.builder().build();
        // 전달하는 숫자 파라미터에 따라 이름이 정해짐
        this.mainCategories = CategoryFixture.createMainCategories(0, 4, member);
        this.subCategories = CategoryFixture.createSubCategories(4, 8, member, mainCategories.get(2));

    }

    @WithMockCustomUser
    @DisplayName("카테고리 조회 확인_200")
    @Test
    void getCategories() throws Exception {

        // given
        var response = this.mainCategories.stream()
                .map(el -> CategoryMapper.toReadCategoryResponse(el))
                .toList();

        given(categoryReadService.readCategoriesByMemberId(any())).willReturn(response);

        // when, then
        mockMvc.perform(get("/api/v1/categories")
                        .header("Authorization", "Bearer {ACCESS_TOKEN}"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(document("read-category",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("status").description("응답 상태"),
                                fieldWithPath("message").description("응답 메세지"),
                                subsectionWithPath("result[]").description("결과값"),
                                fieldWithPath("result[].categoryId").description("아이디"),
                                fieldWithPath("result[].name").description("카테고리 이름"),
                                fieldWithPath("result[].color").description("색상"),
                                fieldWithPath("result[].iconName").description("아이콘 파일명"),
                                fieldWithPath("result[].level").description("레벨"),
                                fieldWithPath("result[].mainCategoryId").description("상위 카테고리 아이디")
                        )
                ));
    }

    @WithMockCustomUser
    @DisplayName("카테고리 등록이 잘 수행되는지 확인_201")
    @Test
    void addCategory() throws Exception {
        // given
        var request = CategoryAddRequest.builder()
                .name("test")
                .color("sample")
                .highlightColor("sample")
                .iconName("sample")
                .level(1)
                .mainCategoryId(1L)
                .build();
        var response = CategoryAddResponse.builder()
                .id(1L)
                .build();

        var content = objectMapper.writeValueAsString(request);
        given(categoryWriteService.addCategory(any(), any())).willReturn(response);

        // when, then
        mockMvc.perform(post("/api/v1/categories")
                        .header("Authorization", "Bearer {ACCESS_TOKEN}")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(document("create-category",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("name").description("카테고리 이름"),
                                fieldWithPath("color").description("색상"),
                                fieldWithPath("highlightColor").description("하이라이트 색상"),
                                fieldWithPath("iconName").description("아이콘 파일명"),
                                fieldWithPath("level").description("레벨"),
                                fieldWithPath("mainCategoryId").description("상위 카테고리 아이디")
                        ),
                        responseFields(
                                fieldWithPath("status").description("응답 상태"),
                                fieldWithPath("message").description("응답 메세지"),
                                subsectionWithPath("result").description("결과값"),
                                fieldWithPath("result.id").description("카테고리 아이디")
                        )
                ));
    }

    @WithMockCustomUser
    @DisplayName("카테고리 아이디에 따라 해당 카테고리를 비활성화 데이터로 변경_200")
    @Test
    void softDeleteCategoryById() throws Exception {
        // given
        var categoryId = 3L;

        // when, then
        mockMvc.perform(delete("/api/v1/categories/{categoryId}", categoryId)
                        .header("Authorization", "Bearer {ACCESS_TOKEN}"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(document("delete-category",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestParameters(
                                parameterWithName("memberId").optional().description("멤버 아이디(temp)")
                        ),
                        pathParameters(
                                parameterWithName("categoryId").description("카테고리 아이디")
                        ),
                        responseFields(
                                fieldWithPath("status").description("응답 상태"),
                                fieldWithPath("message").description("응답 메세지"),
                                subsectionWithPath("result").description("결과값")
                        )
                ));
    }

    @WithMockCustomUser
    @DisplayName("요청 데이터에 따라 카테고리 이름/아이콘 값 정보 변경")
    @Test
    void modifyCategoryValue() throws Exception {
        // given
        var dto = ModifyCategoryValueRequest.builder()
                .categoryId(1L)
                .name("sample")
                //.color("sample")
                .iconName("sample")
                .build();

        String content = objectMapper.writeValueAsString(dto);

        // when, then
        mockMvc.perform(put("/api/v1/categories/value")
                        .header("Authorization", "Bearer {ACCESS_TOKEN}")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(document("modify-category-value",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestParameters(
                                parameterWithName("memberId").optional().description("멤버 아이디(temp)")
                        ),
                        requestFields(
                                fieldWithPath("categoryId").description("카테고리 아이디"),
                                fieldWithPath("name").description("카테고리명"),
                                fieldWithPath("iconName").description("아이콘 파일명")
                        ),
                        responseFields(
                                fieldWithPath("status").description("응답 상태"),
                                fieldWithPath("message").description("응답 메세지"),
                                subsectionWithPath("result").description("결과값")
                        )
                ));
    }

    @WithMockCustomUser
    @DisplayName("요청 데이터에 따라 카테고리 관계 정보 변경")
    @Test
    void modifyCategoryRelation() throws Exception {
        // given
        var dto = ModifyCategoryRelationRequest.builder()
                .categoryId(1L)
                .level(0)
                .mainCategoryId(3L)
                .build();

        String content = objectMapper.writeValueAsString(dto);

        // when, then
        mockMvc.perform(put("/api/v1/categories/relation")
                        .header("Authorization", "Bearer {ACCESS_TOKEN}")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(document("modify-category-relation",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestParameters(
                                parameterWithName("memberId").optional().description("멤버 아이디(temp)")
                        ),
                        requestFields(
                                fieldWithPath("categoryId").description("카테고리 아이디"),
                                fieldWithPath("level").description("카테고리 레벨"),
                                fieldWithPath("mainCategoryId").description("상위 카테고리 아이디 (자신이 대분류인 경우 null)")
                        ),
                        responseFields(
                                fieldWithPath("status").description("응답 상태"),
                                fieldWithPath("message").description("응답 메세지"),
                                subsectionWithPath("result").description("결과값")
                        )
                ));
    }

    @WithMockCustomUser()
    @DisplayName("요청 데이터에 따라 카테고리 정보 변경")
    @Test
    void modifyCategory() throws Exception {
        // given
        var dto = ModifyCategoryRequest.builder()
                .categoryId(1L)
                .name("sample")
                .color("sample")
                .highlightColor("sample")
                .iconName("sample")
                .level(0)
                .build();

        String content = objectMapper.writeValueAsString(dto);

        // when, then
        mockMvc.perform(put("/api/v1/categories")
                        .header("Authorization", "Bearer {ACCESS_TOKEN}")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(document("modify-category",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestParameters(
                                parameterWithName("memberId").optional().description("멤버 아이디(temp)")
                        ),
                        requestFields(
                                fieldWithPath("categoryId").description("카테고리 아이디"),
                                fieldWithPath("name").description("카테고리명"),
                                fieldWithPath("iconName").description("카테고리 아이콘 파일명"),
                                fieldWithPath("color").description("카테고리 색상"),
                                fieldWithPath("highlightColor").description("카테고리 하이라이트 색상"),
                                fieldWithPath("level").description("카테고리 레벨"),
                                fieldWithPath("mainCategoryId").description("상위 카테고리 아이디 (자신이 대분류인 경우 null)")
                        ),
                        responseFields(
                                fieldWithPath("status").description("응답 상태"),
                                fieldWithPath("message").description("응답 메세지"),
                                subsectionWithPath("result").description("결과값")
                        )
                ));
    }
}