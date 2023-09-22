package com.ddokddak.category.repository;

import com.ddokddak.category.enums.CategoryTemplate;
import com.ddokddak.common.exception.CustomApiException;
import com.ddokddak.common.exception.type.DbException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class CategoryJdbcRepository {

    @Value("${jdbc.batch.size}")
    private int batchSize;
    private final JdbcTemplate jdbcTemplate;

    @Transactional
    public void batchInsert(List<CategoryTemplate> list, Long memberId) {
        var sql = "INSERT INTO category (MEMBER_ID, NAME, COLOR, ICON_NAME, LEVEL, PARENT_ID, IS_DELETED, CREATED_AT) " +
                "VALUES " +
                "( ?, ?, ?, ?, (SELECT ID FROM (SELECT ID FROM CATEGORY WHERE MEMBER_ID=? AND NAME=?) AS SUB), 0, NOW())";
        try {
            jdbcTemplate.batchUpdate(
                    sql,
                    list,
                    batchSize,
                    (ps, arg) -> {
                        ps.setLong(1, memberId);
                        ps.setString(2, arg.getName());
                        ps.setString(3, arg.getColor());
                        ps.setString(4, arg.getIconName());
                        ps.setInt(5, arg.getParentName()==null?0:1);
                        ps.setLong(6, memberId);
                        ps.setString(7, arg.getParentName());
                        //ps.setString(7, "N");
                    });
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomApiException(DbException.BATCH_INSERTION_FAIL);
        }
    }
}
