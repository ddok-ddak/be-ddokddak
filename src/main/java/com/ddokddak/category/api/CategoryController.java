package com.ddokddak.category.api;

import com.ddokddak.category.service.CategoryReadService;
import com.ddokddak.category.service.CategoryWriteService;
import com.ddokddak.common.dto.CommonResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class CategoryController {

    private final CategoryReadService categoryReadService;
    private final CategoryWriteService categoryWriteService;

    @DeleteMapping("/categories/{categoryId}")
    public ResponseEntity<CommonResponse> deleteCategoryById(@PathVariable Long categoryId) {
        // enum으로 기본 응답 통일하기
        return ResponseEntity.ok(new CommonResponse("Successfully Deleted", Boolean.TRUE));
    }

}
