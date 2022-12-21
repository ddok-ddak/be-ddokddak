package com.ddokddak.category.entity;

import com.ddokddak.category.dto.CategoryModifyRequest;
import com.ddokddak.common.entity.BaseTimeEntity;
import com.ddokddak.member.Member;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
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

    // TODO: 한 사람이 가질 수 있는 카테고리의 이름은 중복이 불가능하다.
    // 한, 영만 가능, 특수문자 금지 - 정규식 추가
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

    @Column(length = 1)
    private String deleteYn = "N";

    // 이하 선택 사항
    @OneToMany(mappedBy="mainCategory")
    private List<Category> subCategories;

    public void modifyAttribute(CategoryModifyRequest req) {
        this.color = req.color();
        this.name = req.name();
    }

    public void modifyCategoryRelation(CategoryModifyRequest req, Category mainCategory) {
        this.level = req.level();
        this.mainCategory = mainCategory;
    }
}
