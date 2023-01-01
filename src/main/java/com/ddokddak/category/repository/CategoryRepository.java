package com.ddokddak.category.repository;

import com.ddokddak.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByIdAndMemberId(Long categoryId, Long memberId);

    @Transactional
    @Modifying
    @Query("UPDATE Category c SET modifiedAt = NOW(), deleteYn ='Y' WHERE c.mainCategory = :category")
    void deleteAllByMainCategory(Category category);
}
