package com.ddokddak;

import com.google.common.base.CaseFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Table;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Profile("test")
@Component
@RequiredArgsConstructor
public class DatabaseCleanUp { //implements InitializingBean {

    @PersistenceContext
    private final EntityManager entityManager;
    private List<String> tableNames;

    @PostConstruct
    public void afterPropertiesSet() {
        tableNames = entityManager.getMetamodel()
                .getEntities()
                .stream()
                .filter(entityType -> entityType
                        .getJavaType()
                        .getAnnotation(Entity.class) != null)
                .map(entityType -> {
                    Table table = entityType
                            .getJavaType()
                            .getAnnotation(Table.class);

                    return Objects.nonNull(table) && Objects.nonNull(table.name()) ?
                            table.name() :
                            CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, entityType.getName());
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public void execute() {
        // 쓰기 지연 저장소에 남은 SQL을 마저 수행
        entityManager.flush();
        // 연관 관계 매핑된 테이블이 있는 경우 참조 무결성을 해제해주고, TRUNCATE 수행
        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY FALSE").executeUpdate();

        for (String tableName : tableNames) {
            // 테이블 이름을 순회하면서, TRUNCATE 수행
            entityManager.createNativeQuery("TRUNCATE TABLE " + tableName).executeUpdate();
        }
        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY TRUE").executeUpdate();

   }
}