package com.ddokddak.category.api;

import com.ddokddak.auth.domain.UserPrincipal;
import com.ddokddak.category.dto.*;
import com.ddokddak.category.service.CategoryReadService;
import com.ddokddak.category.service.CategoryWriteService;
import com.ddokddak.common.dto.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final CategoryReadService categoryReadService;
    private final CategoryWriteService categoryWriteService;

    @PostMapping
    public ResponseEntity<CommonResponse<CategoryAddResponse>> addCategory(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @RequestBody CategoryAddRequest req){

        var res = categoryWriteService.addCategory(userPrincipal.getId(), req);
        return ResponseEntity.ok(new CommonResponse<>("Successfully Created", res));
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<ReadCategoryResponse>>> getCategories(
            @AuthenticationPrincipal UserPrincipal userPrincipal) {

        List<ReadCategoryResponse> res = categoryReadService.readCategoriesByMemberId(userPrincipal.getId());
        return  ResponseEntity.ok(new CommonResponse<>("Successfully loaded", res));
    }

    // 현재 매개변수 Long memberId 를 추후 @AuthenticationalPrincipal 활용하는 것으로 수정
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<CommonResponse<Boolean>> removeCategoryById(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable Long categoryId) {

        categoryWriteService.removeCategoryByIdAndMemberId(categoryId, userPrincipal.getId());
        return ResponseEntity.ok(new CommonResponse<>("Successfully Deleted", Boolean.TRUE));
    }

    @PutMapping("/value")
    public ResponseEntity<CommonResponse<Boolean>> modifyCategoryValue(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @RequestBody ModifyCategoryValueRequest req) {

        categoryWriteService.modifyCategoryValue(req, userPrincipal.getId());
        return ResponseEntity.ok(new CommonResponse<>("Successfully Updated", Boolean.TRUE));
    }

    @PutMapping("/relation")
    public ResponseEntity<CommonResponse<Boolean>> modifyCategoryRelation(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @RequestBody ModifyCategoryRelationRequest req) {

        categoryWriteService.modifyCategoryRelation(req, userPrincipal.getId());
        return ResponseEntity.ok(new CommonResponse<>("Successfully Updated", Boolean.TRUE));
    }

    @PutMapping
    public ResponseEntity<CommonResponse<Boolean>> modifyCategory(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @RequestBody ModifyCategoryRequest req) {

        categoryWriteService.modifyCategory(req, userPrincipal.getId());
        return ResponseEntity.ok(new CommonResponse<>("Successfully Updated", Boolean.TRUE));
    }

//    @PostMapping("/template")
//    public ResponseEntity<CommonResponse<Boolean>> addCategoryTemplate(
//            @AuthenticationPrincipal UserPrincipal userPrincipal,
//            @RequestBody CategoryTemplateRequest req) {
//
//        createCategoryTemplateUsecase.execute(req, userPrincipal.getId());
//        return ResponseEntity.ok(new CommonResponse<>("Successfully Updated", Boolean.TRUE));
//    }
}
