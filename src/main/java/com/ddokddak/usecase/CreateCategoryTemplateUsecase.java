package com.ddokddak.usecase;

import com.ddokddak.category.dto.CategoryTemplateRequest;
import com.ddokddak.category.service.CategoryWriteService;
import com.ddokddak.member.service.MemberWriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CreateCategoryTemplateUsecase {

    private final MemberWriteService memberWriteService;
    private final CategoryWriteService categoryWriteService;

    @Transactional
    public void execute(CategoryTemplateRequest req, Long memberId) {
        memberWriteService.setCategoryTemplate(memberId, req);
        // 벌크 인서트 수행
        categoryWriteService.addCategoryTemplate(req, memberId);
    }
}
