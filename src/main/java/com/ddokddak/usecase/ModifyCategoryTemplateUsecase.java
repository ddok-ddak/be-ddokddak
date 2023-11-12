package com.ddokddak.usecase;

import com.ddokddak.category.domain.dto.CategoryTemplateRequest;
import com.ddokddak.category.service.CategoryWriteService;
import com.ddokddak.member.service.MemberWriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ModifyCategoryTemplateUsecase {

    private final MemberWriteService memberWriteService;
    private final CategoryWriteService categoryWriteService;

    @Transactional
    public void execute(CategoryTemplateRequest req, Long memberId) {

        var previousTemplateType = memberWriteService.modifyCategoryTemplate(memberId, req);
        // 카테고리 템플릿 수정 수행 (직장, 학교 카테고리 그룹만 추가 혹은 변경/삭제된다)
        categoryWriteService.modifyCategoryTemplate(req, memberId, previousTemplateType);
    }
}
