package com.ddokddak.category.entity;

import com.ddokddak.common.entity.BaseTimeEntity;
import com.ddokddak.member.Member;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql="UPDATE category SET delete_yn = 'Y', modified_at = NOW() WHERE id = ?")
@Where(clause = "delete_yn = 'N'")
@Entity
public class Category extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 하나의 카테고리 그룹 내에서 카테고리의 이름은 중복이 불가능하다.
    // TODO: 한, 영만 가능, 특수문자 금지 - 정규식 추가
    @Column(length = 10, nullable = false)
    private String name;

    @Column(length = 10, nullable = false)
    private String color;

    @Column(length = 1)
    private Integer level;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="PARENT_ID")
    private Category mainCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="MEMBER_ID")
    private Member member;

    @Builder.Default
    @Column(length = 1)
    private String deleteYn = "N";

    @OneToMany(mappedBy="mainCategory")
    private List<Category> subCategories = new ArrayList<>();

    public void modifyColor(String color) {
        this.color = color;
    }

    public void modifyName(String name) {
        this.name = name;
    }

    public void modifyCategoryRelation(Integer level, @Nullable Category mainCategory) {
        this.level = level;
        this.mainCategory = mainCategory;
    }

}
