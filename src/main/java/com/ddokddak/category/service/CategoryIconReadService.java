package com.ddokddak.category.service;

import com.ddokddak.category.domain.dto.CategoryIconReadResponse;
import com.ddokddak.category.mapper.CategoryIconMapper;
import com.ddokddak.category.repository.CategoryIconRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TableGenerator;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryIconReadService {
    private final CategoryIconRepository categoryIconRepository;

    @Transactional(readOnly = true)
    public List<CategoryIconReadResponse> readCategoryIconByGroup(String iconGroup) {

        if (Objects.isNull(iconGroup)) iconGroup = "base";
        var resultList = this.categoryIconRepository.findByIconGroupAndIsDeletedFalse(iconGroup);

        return resultList.stream()
                .map(el -> CategoryIconMapper.toReadResponse(el))
                .toList();
    }
}
