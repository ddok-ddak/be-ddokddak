package com.ddokddak.activityRecord.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QActivityRecord is a Querydsl query type for ActivityRecord
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QActivityRecord extends EntityPathBase<ActivityRecord> {

    private static final long serialVersionUID = -451600168L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QActivityRecord activityRecord = new QActivityRecord("activityRecord");

    public final com.ddokddak.common.entity.QBaseTimeEntity _super = new com.ddokddak.common.entity.QBaseTimeEntity(this);

    public final com.ddokddak.category.entity.QCategory category;

    public final StringPath categoryColor = createString("categoryColor");

    public final StringPath categoryName = createString("categoryName");

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath deleteYn = createString("deleteYn");

    public final DateTimePath<java.time.LocalDateTime> finishedAt = createDateTime("finishedAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.ddokddak.member.QMember member;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final DateTimePath<java.time.LocalDateTime> startedAt = createDateTime("startedAt", java.time.LocalDateTime.class);

    public final NumberPath<Integer> timeUnit = createNumber("timeUnit", Integer.class);

    public final StringPath timeZone = createString("timeZone");

    public QActivityRecord(String variable) {
        this(ActivityRecord.class, forVariable(variable), INITS);
    }

    public QActivityRecord(Path<? extends ActivityRecord> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QActivityRecord(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QActivityRecord(PathMetadata metadata, PathInits inits) {
        this(ActivityRecord.class, metadata, inits);
    }

    public QActivityRecord(Class<? extends ActivityRecord> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.category = inits.isInitialized("category") ? new com.ddokddak.category.entity.QCategory(forProperty("category"), inits.get("category")) : null;
        this.member = inits.isInitialized("member") ? new com.ddokddak.member.QMember(forProperty("member")) : null;
    }

}

