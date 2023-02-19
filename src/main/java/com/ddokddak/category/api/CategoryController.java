package com.ddokddak.category.api;

import com.ddokddak.category.dto.*;
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

    @PostMapping
    public ResponseEntity<CommonResponse<CategoryAddResponse>> addCategory(@RequestBody CategoryAddRequest req){
        var res = categoryWriteService.addCategory(req);
        return ResponseEntity.ok(new CommonResponse<>("Successfully Created", res));
    }

    @GetMapping("/categories")
    public ResponseEntity<CommonResponse<CategoryReadResponse>> getCategories(@RequestParam Long memberId) {
        CategoryReadResponse res = categoryReadService.readCategoriesByMemberId(memberId);
        return  ResponseEntity.ok(new CommonResponse<>("Successfully loaded", res));
    }

    // 현재 매개변수 Long memberId 를 추후 @AuthenticationalPrincipal 활용하는 것으로 수정
    @DeleteMapping("/categories/{categoryId}")
    public ResponseEntity<CommonResponse<Boolean>> removeCategoryById(@RequestParam(required = false, defaultValue = "1") Long memberId, @PathVariable Long categoryId) {

        categoryWriteService.removeCategoryByIdAndMemberId(categoryId, memberId);
        return ResponseEntity.ok(new CommonResponse<>("Successfully Deleted", Boolean.TRUE));
    }

    @PutMapping("/categories/value")
    public ResponseEntity<CommonResponse<Boolean>> modifyCategoryValue(@RequestParam(required = false, defaultValue = "1") Long memberId, @RequestBody ModifyCategoryValueRequest req) {

        categoryWriteService.modifyCategoryValue(req, memberId);
        return ResponseEntity.ok(new CommonResponse<>("Successfully Updated", Boolean.TRUE));
    }

    @PutMapping("/categories/relation")
    public ResponseEntity<CommonResponse<Boolean>> modifyCategoryRelation(@RequestParam(required = false, defaultValue = "1") Long memberId, @RequestBody ModifyCategoryRelationRequest req) {

        categoryWriteService.modifyCategoryRelation(req, memberId);
        return ResponseEntity.ok(new CommonResponse<>("Successfully Updated", Boolean.TRUE));
    }

    @PutMapping("/categories")
    public ResponseEntity<CommonResponse<Boolean>> modifyCategory(@RequestParam(required = false, defaultValue = "1") Long memberId, @RequestBody ModifyCategoryRequest req) {

        categoryWriteService.modifyCategory(req, memberId);
        return ResponseEntity.ok(new CommonResponse<>("Successfully Updated", Boolean.TRUE));
    }
}
