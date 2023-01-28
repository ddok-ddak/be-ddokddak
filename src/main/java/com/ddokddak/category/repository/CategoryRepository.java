package com.ddokddak.category.repository;

import com.ddokddak.category.entity.Category;
import com.ddokddak.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByIdAndMemberId(Long categoryId, Long memberId);
    Optional<List<Category>> findByMemberId(Long memberId);

    @Modifying
    @Query("UPDATE Category c SET modifiedAt = NOW(), deleteYn ='Y' WHERE c.mainCategory = :category")
    void deleteAllByMainCategory(Category category);

    List<Category> findByMemberIdAndLevel(Long id, int level);

    Boolean existsByLevelAndNameAndMemberId(int level, String name, Long memberId);

    Boolean existsByNameAndMainCategoryIdAndMemberId(String name, Long id, Long memberId);
}
