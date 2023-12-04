package com.ddokddak.category.domain.entity;

import com.ddokddak.common.entity.BaseTimeEntity;
import com.ddokddak.member.domain.entity.Member;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.SQLDelete;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Category extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 하나의 카테고리 그룹 내에서 카테고리의 이름은 중복이 불가능하다.
    //@Pattern(regexp="[a-zA-Z가-힣0-9]{1,10}", message = "카테고리 명칭 규칙 위반")
    @Column(length = 10, nullable = false)
    private String name;

    @Column(length = 10, nullable = false)
    private String color;

    @Column(length = 10)
    private String highlightColor;

    @Column(length = 1)
    private Integer level;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="PARENT_ID")
    private Category mainCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="MEMBER_ID")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @ColumnDefault("1")
    @JoinColumn(name="CATEGORY_ICON_ID")
    private CategoryIcon iconFile;


    @Builder.Default
    @ColumnDefault("0")
    private Boolean isDeleted = Boolean.FALSE;

    @Builder.Default
    @OneToMany(mappedBy="mainCategory")
    private List<Category> subCategories = new ArrayList<>();

    public void modifyColor(String color) {
        this.color = color;
    }

    public void modifyName(String name) {
        this.name = name;
    }

    public void modifyIconFile(CategoryIcon iconFile) {
        this.iconFile = iconFile;
    }

    public void modifyCategoryRelation(Integer level, @Nullable Category mainCategory) {
        this.level = level;
        this.mainCategory = mainCategory;
    }

    public void delete() {
        this.isDeleted = Boolean.TRUE;
    }

    public void deleteGroup() {
        this.isDeleted = Boolean.TRUE;
        this.getSubCategories()
                .forEach(category -> category.delete());
    }

    private void undelete() {
        this.isDeleted = Boolean.FALSE;
    }

    public void undeleteGroup() {
        this.isDeleted = Boolean.FALSE;
        this.getSubCategories()
                .forEach(category -> category.undelete());
    }
}
