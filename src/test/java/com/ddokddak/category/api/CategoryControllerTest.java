package com.ddokddak.category.api;

import com.ddokddak.category.dto.ModifyCategoryRequest;
import com.ddokddak.category.dto.ModifyCategoryRelationRequest;
import com.ddokddak.category.dto.ModifyCategoryValueRequest;
import com.ddokddak.category.service.CategoryWriteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
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


import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
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

    @WithMockUser
    @DisplayName("카테고리 아이디에 따라 해당 카테고리를 비활성화 데이터로 변경_200")
    @Test
    void softDeleteCategoryById() throws Exception {

        var categoryId = 3L;

        // when, then
        mockMvc.perform(delete("/api/v1/categories/{categoryId}", categoryId))
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
                                fieldWithPath("message").description("응답 메세지"),
                                subsectionWithPath("result").description("결과값")
                        )
                ));
    }

    @WithMockUser
    @DisplayName("요청 데이터에 따라 카테고리 이름/색상 값 정보 변경")
    @Test
    void modifyCategoryValue() throws Exception {
        // given
        var dto = ModifyCategoryValueRequest.builder()
                .categoryId(1L)
                .name("sample")
                .color("sample")
                .build();

        String content = objectMapper.writeValueAsString(dto);

        // when, then
        mockMvc.perform(put("/api/v1/categories/value")
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
                                fieldWithPath("color").description("카테고리 색상")
                        ),
                        responseFields(
                                fieldWithPath("message").description("응답 메세지"),
                                subsectionWithPath("result").description("결과값")
                        )
                ));
    }

    @WithMockUser
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
                                fieldWithPath("message").description("응답 메세지"),
                                subsectionWithPath("result").description("결과값")
                        )
                ));
    }

    @WithMockUser
    @DisplayName("요청 데이터에 따라 카테고리 정보 변경")
    @Test
    void modifyCategory() throws Exception {
        // given
        var dto = ModifyCategoryRequest.builder()
                .categoryId(1L)
                .name("sample")
                .color("sample")
                .level(0)
                .build();

        String content = objectMapper.writeValueAsString(dto);

        // when, then
        mockMvc.perform(put("/api/v1/categories")
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
                                fieldWithPath("color").description("카테고리 색상"),
                                fieldWithPath("level").description("카테고리 레벨"),
                                fieldWithPath("mainCategoryId").description("상위 카테고리 아이디 (자신이 대분류인 경우 null)")
                        ),
                        responseFields(
                                fieldWithPath("message").description("응답 메세지"),
                                subsectionWithPath("result").description("결과값")
                        )
                ));
    }
}