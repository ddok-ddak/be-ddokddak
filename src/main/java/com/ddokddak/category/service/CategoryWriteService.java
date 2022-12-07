package com.ddokddak.category.service;


import com.ddokddak.category.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class CategoryWriteService {

    private final CategoryRepository categoryRepository;

}
