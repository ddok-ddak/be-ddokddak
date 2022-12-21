package com.ddokddak.category.api;

import com.ddokddak.category.dto.CategoryModifyRequest;
import com.ddokddak.category.dto.CategoryRelationModifyRequest;
import com.ddokddak.category.dto.CategoryValueModifyRequest;
import com.ddokddak.category.service.CategoryReadService;
import com.ddokddak.category.service.CategoryWriteService;
import com.ddokddak.common.dto.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class CategoryController {

    private final CategoryReadService categoryReadService;
    private final CategoryWriteService categoryWriteService;

    @DeleteMapping("/categories/{categoryId}")
    public ResponseEntity<CommonResponse<Boolean>> removeCategoryById(@PathVariable Long categoryId) {

        categoryWriteService.removeCategoryById(categoryId);

        // enum으로 기본 응답 통일하기
        return ResponseEntity.ok(new CommonResponse<>("Successfully Deleted", Boolean.TRUE));
    }

    @PutMapping("/categories/value")
    public ResponseEntity<CommonResponse<Boolean>> modifyCategoryValue(@RequestBody CategoryValueModifyRequest req) {

        categoryWriteService.modifyCategoryValue(req);

        return ResponseEntity.ok(new CommonResponse<>("Successfully Updated", Boolean.TRUE));
    }

    @PutMapping("/categories/relation")
    public ResponseEntity<CommonResponse<Boolean>> modifyCategoryRelation(@RequestBody CategoryRelationModifyRequest req) {

        categoryWriteService.modifyCategoryRelation(req);

        return ResponseEntity.ok(new CommonResponse<>("Successfully Updated", Boolean.TRUE));
    }

    @PutMapping("/categories")
    public ResponseEntity<CommonResponse<Boolean>> modifyCategory(@RequestBody CategoryModifyRequest req) {

        categoryWriteService.modifyCategory(req);

        return ResponseEntity.ok(new CommonResponse<>("Successfully Updated", Boolean.TRUE));
    }
}
