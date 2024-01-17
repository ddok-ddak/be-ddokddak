package com.ddokddak.category.api;

import com.ddokddak.category.domain.dto.*;
import com.ddokddak.category.domain.entity.Category;
import com.ddokddak.category.domain.entity.CategoryIcon;
import com.ddokddak.category.fixture.CategoryFixture;
import com.ddokddak.category.mapper.CategoryMapper;
import com.ddokddak.category.service.CategoryWriteService;
import com.ddokddak.member.domain.entity.Member;
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
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@AutoConfigureMockMvc
@SpringBootTest
public class CategoryIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private CategoryWriteService categoryWriteService;

    private Member member;
    private CategoryIcon iconFile;
    private List<Category> mainCategories;
    private List<Category> subCategories;

    @BeforeEach
    void setUp() {
        this.member = Member.builder().build();
        this.iconFile = CategoryIcon.builder()
                .filename("icon.jpg")
                .path("/")
                .originalFilename("1234.jpg")
                .build();

        // 전달하는 숫자 파라미터에 따라 이름이 정해짐
        this.mainCategories = CategoryFixture.createMainCategories(0, 4, member, iconFile);
        this.subCategories = CategoryFixture.createSubCategories(4, 8, member, mainCategories.get(2), iconFile);
    }

    @WithMockCustomUser
    @DisplayName("카테고리 조회 확인_200")
    @Test
    void getCategories() throws Exception {

        // given
        var response = this.mainCategories.stream()
                .map(el -> CategoryMapper.toReadCategoryResponse(el))
                .toList();

        // when, then
        mockMvc.perform(get("/api/v1/categories")
                        .header("Authorization", "Bearer {ACCESS_TOKEN}"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print());
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
                .iconId(1L)
                .level(1)
                .mainCategoryId(1L)
                .build();

        var content = objectMapper.writeValueAsString(request);

        // when, then
        mockMvc.perform(post("/api/v1/categories")
                        .header("Authorization", "Bearer {ACCESS_TOKEN}")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print());
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
                .andDo(print());
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
                .iconId(1L)
                .build();

        String content = objectMapper.writeValueAsString(dto);

        // when, then
        mockMvc.perform(put("/api/v1/categories/value")
                        .header("Authorization", "Bearer {ACCESS_TOKEN}")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    @WithMockCustomUser
    @DisplayName("요청 데이터에 따라 카테고리 관계 정보 변경")
    @Test
    void modifyCategoryRelation() throws Exception {
        // given
        var dto = ModifyCategoryRelationRequest.builder()
                .categoryId(14L) // data.sql 에서 인서트된 데이터 활용
                .level(1)
                .mainCategoryId(6L)
                .build();
        String content = objectMapper.writeValueAsString(dto);

        // when, then
        mockMvc.perform(put("/api/v1/categories/relation")
                        .header("Authorization", "Bearer {ACCESS_TOKEN}")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print());
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
                .iconId(1L)
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
                .andDo(print());
    }
}
