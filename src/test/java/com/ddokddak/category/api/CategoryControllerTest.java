package com.ddokddak.category.api;

import com.ddokddak.category.dto.CategoryModifyRequest;
import com.ddokddak.category.dto.CategoryRelationModifyRequest;
import com.ddokddak.category.dto.CategoryValueModifyRequest;
import com.ddokddak.category.service.CategoryWriteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
@AutoConfigureMockMvc
@SpringBootTest
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    private CategoryWriteService categoryWriteService;

    @WithMockUser
    @DisplayName("카테고리 아이디에 따라 해당 카테고리를 비활성화 데이터로 변경_200")
    @Test
    void softDeleteCategoryById() throws Exception {

        // when, then
        mockMvc.perform(delete("/api/v1/categories/3"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    @WithMockUser
    @DisplayName("요청 데이터에 따라 카테고리 이름/색상 값 정보 변경")
    @Test
    void modifyCategoryValue() throws Exception {
        // given
        var dto = CategoryValueModifyRequest.builder()
                .categoryId(1L)
                .name("sample")
                .color("sample")
                .memberId(1L)
                .build();

        String content = objectMapper.writeValueAsString(dto);

        // when, then
        mockMvc.perform(put("/api/v1/categories/value")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    @WithMockUser
    @DisplayName("요청 데이터에 따라 카테고리 관계 정보 변경")
    @Test
    void modifyCategoryRelation() throws Exception {
        // given
        var dto = CategoryRelationModifyRequest.builder()
                .categoryId(1L)
                .level(0)
                .mainCategoryId(3L)
                .memberId(1L)
                .build();

        String content = objectMapper.writeValueAsString(dto);

        // when, then
        mockMvc.perform(put("/api/v1/categories/relation")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    @WithMockUser
    @DisplayName("요청 데이터에 따라 카테고리 정보 변경")
    @Test
    void modifyCategory() throws Exception {
        // given
        var dto = CategoryModifyRequest.builder()
                .categoryId(1L)
                .name("sample")
                .color("sample")
                .level(0)
                .memberId(1L)
                .build();

        String content = objectMapper.writeValueAsString(dto);

        // when, then
        mockMvc.perform(put("/api/v1/categories")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print());
    }
}