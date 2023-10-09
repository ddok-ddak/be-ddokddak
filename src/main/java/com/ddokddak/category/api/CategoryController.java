package com.ddokddak.category.api;

import com.ddokddak.auth.domain.UserPrincipal;
import com.ddokddak.category.dto.*;
import com.ddokddak.category.service.CategoryReadService;
import com.ddokddak.category.service.CategoryWriteService;
import com.ddokddak.common.dto.CommonResponse;
import com.ddokddak.usecase.DeleteCategoryUsecase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final CategoryReadService categoryReadService;
    private final CategoryWriteService categoryWriteService;
    private final DeleteCategoryUsecase deleteCategoryUsecase;

    @PostMapping
    public ResponseEntity<CommonResponse<CategoryAddResponse>> addCategory(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @RequestBody CategoryAddRequest req){

        var res = categoryWriteService.addCategory(userPrincipal.getId(), req);
        URI location = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .build()
                .toUri();

        return ResponseEntity.created(location)
                .body(new CommonResponse<>("Successfully Created", res));
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<CategoryReadResponse>>> getCategories(
            @AuthenticationPrincipal UserPrincipal userPrincipal) {

        List<CategoryReadResponse> res = categoryReadService.readCategoriesByMemberId(userPrincipal.getId());
        return  ResponseEntity.ok(new CommonResponse<>("Successfully loaded", res));
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<CommonResponse<Boolean>> removeCategoryById(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable Long categoryId) {

        deleteCategoryUsecase.execute(categoryId, userPrincipal.getId());

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
