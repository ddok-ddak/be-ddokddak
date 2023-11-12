package com.ddokddak.category.repository;

import com.ddokddak.category.domain.entity.Category;
import com.ddokddak.member.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("SELECT DISTINCT c FROM Category c JOIN FETCH c.member WHERE c.member = :member and c.level = 0 and c.isDeleted = false")
    List<Category> findCategoryJoinFetch(@Param("member") Member member);

    @Modifying
    @Query("UPDATE Category c SET c.modifiedAt = NOW(), c.isDeleted = true WHERE c = :category")
    void softDelete(Category category);

    @Modifying
    @Query("UPDATE Category c SET c.modifiedAt = NOW(), c.isDeleted = true WHERE c.mainCategory = :category")
    void deleteAllByMainCategory(Category category);

    Boolean existsByLevelAndNameAndMemberIdAndIsDeletedFalse(int level, String name, Long memberId);

    Boolean existsByNameAndMainCategoryIdAndMemberIdAndIsDeletedFalse(String name, Long id, Long memberId);

    List<Category> findAllByMemberAndIsDeletedFalse(Member member);

    List<Category> findByMemberIdAndIsDeletedFalse(Long memberId);

    Optional<Category> findByIdAndMemberIdAndIsDeletedFalse(Long categoryId, Long memberId);

    List<Category> findByMemberIdAndLevelAndIsDeleted(Long id, int level, Boolean isDeleted);

    List<Category> findByMemberIdAndLevel(Long id, int level);

    Optional<Category> findByIdAndIsDeletedFalse(long id);

}
