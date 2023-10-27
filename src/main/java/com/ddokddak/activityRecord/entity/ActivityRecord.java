package com.ddokddak.activityRecord.entity;

import com.ddokddak.category.entity.Category;
import com.ddokddak.common.entity.BaseTimeEntity;
import com.ddokddak.member.entity.Member;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;


@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ActivityRecord extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="CATEGORY_ID")
    private Category category;

//    @Column(nullable = false)
//    private String categoryId;

//    @Column(length = 10, nullable = false)
//    private String categoryName;
//
//    @Column(length = 10, nullable = false)
//    private String categoryColor;

    private LocalDateTime startedAt;

    private LocalDateTime finishedAt;


    private String content;

    @Builder.Default
    @Column
    private Integer timeUnit = 30; // 기본 30 mins (10/30/60)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="MEMBER_ID")
    private Member member;

    @Builder.Default
    @ColumnDefault("0")
    private Boolean isDeleted = Boolean.FALSE;

    public void assignId(Long id) {
        this.id = id;
    }

    public void modifyCategory(Category newCategory) {
        this.category = newCategory;
    }

    public void modifyContent(String newContent) {
        this.content = newContent;
    }

    public void modifyDate(LocalDateTime newStartAt, LocalDateTime newFinishedAt) {
        this.startedAt = newStartAt;
        this.finishedAt = newFinishedAt;
    }
    public void softDelete() {
        this.isDeleted = Boolean.TRUE;
    }
}
