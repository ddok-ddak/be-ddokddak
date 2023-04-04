package com.ddokddak.category.repository;

import com.ddokddak.category.entity.Category;
import com.ddokddak.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByIdAndMemberId(Long categoryId, Long memberId);

    @Query("select DISTINCT category from Category category join fetch category.member where category.member = :member and level = 0")
    List<Category> findCategoryJoinFetch(@Param("member") Member member);

    @Modifying
    @Query("UPDATE Category c SET modifiedAt = NOW(), deleteYn ='Y' WHERE c.mainCategory = :category")
    void deleteAllByMainCategory(Category category);

    List<Category> findByMemberIdAndLevel(Long id, int level);

    Boolean existsByLevelAndNameAndMemberId(int level, String name, Long memberId);

    Boolean existsByNameAndMainCategoryIdAndMemberId(String name, Long id, Long memberId);

    List<Category> findAllByMember(Member member);

    List<Category> findByMemberId(Long memberId);
}
