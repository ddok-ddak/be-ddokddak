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

import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class CategoryController {

    private final CategoryReadService categoryReadService;
    private final CategoryWriteService categoryWriteService;

    // 현재 매개변수 Long memberId 를 추후 @AuthenticationalPrincipal 활용하는 것으로 수정
    @DeleteMapping("/categories/{categoryId}")
    public ResponseEntity<CommonResponse<Boolean>> removeCategoryById(@RequestParam(required = false) Long memberId, @PathVariable Long categoryId) {

        if (Objects.isNull(memberId)) memberId =1L;
        categoryWriteService.removeCategoryByIdAndMemberId(categoryId, memberId);

        // enum으로 기본 응답 통일하기
        return ResponseEntity.ok(new CommonResponse<>("Successfully Deleted", Boolean.TRUE));
    }

    @PutMapping("/categories/value")
    public ResponseEntity<CommonResponse<Boolean>> modifyCategoryValue(@RequestParam(required = false) Long memberId, @RequestBody CategoryValueModifyRequest req) {

        if (Objects.isNull(memberId)) memberId =1L;
        categoryWriteService.modifyCategoryValue(req, memberId);

        return ResponseEntity.ok(new CommonResponse<>("Successfully Updated", Boolean.TRUE));
    }

    @PutMapping("/categories/relation")
    public ResponseEntity<CommonResponse<Boolean>> modifyCategoryRelation(@RequestParam(required = false) Long memberId, @RequestBody CategoryRelationModifyRequest req) {

        if (Objects.isNull(memberId)) memberId =1L;
        categoryWriteService.modifyCategoryRelation(req, memberId);

        return ResponseEntity.ok(new CommonResponse<>("Successfully Updated", Boolean.TRUE));
    }

    @PutMapping("/categories")
    public ResponseEntity<CommonResponse<Boolean>> modifyCategory(@RequestParam(required = false) Long memberId, @RequestBody CategoryModifyRequest req) {

        if (Objects.isNull(memberId)) memberId =1L;
        categoryWriteService.modifyCategory(req, memberId);

        return ResponseEntity.ok(new CommonResponse<>("Successfully Updated", Boolean.TRUE));
    }
}
