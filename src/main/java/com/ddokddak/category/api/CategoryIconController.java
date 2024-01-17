package com.ddokddak.category.api;

import com.ddokddak.auth.domain.oauth.UserPrincipal;
import com.ddokddak.category.domain.dto.CategoryIconReadRequest;
import com.ddokddak.category.domain.dto.CategoryIconReadResponse;
import com.ddokddak.category.service.CategoryIconReadService;
import com.ddokddak.common.dto.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/category/icon")
public class CategoryIconController {

    private final CategoryIconReadService categoryIconReadService;
    @GetMapping
    public ResponseEntity<CommonResponse<List<CategoryIconReadResponse>>> getCategories(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            CategoryIconReadRequest req
    ) {
        List<CategoryIconReadResponse> res = this.categoryIconReadService.readCategoryIconByGroup(req.iconGroup());
        return  ResponseEntity.ok(new CommonResponse<>("Successfully loaded", res));
    }
}
