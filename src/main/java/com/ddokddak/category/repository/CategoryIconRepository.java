package com.ddokddak.category.repository;

import com.ddokddak.category.domain.entity.CategoryIcon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CategoryIconRepository extends JpaRepository<CategoryIcon, Long> {

    List<CategoryIcon> findByIconGroupAndIsDeletedFalse(@Param("iconGroup") String iconGroup);

    Optional<CategoryIcon> findByIdAndIsDeletedFalse(Long iconId);
}
