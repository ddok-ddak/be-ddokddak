package com.ddokddak.category.repository;

import com.ddokddak.category.domain.enums.CategoryTemplate;
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
        var sql = "INSERT INTO category (MEMBER_ID, NAME, COLOR, HIGHLIGHT_COLOR, CATEGORY_ICON_ID, LEVEL, PARENT_ID, IS_DELETED, CREATED_AT) " +
                "VALUES " +
                "( ?, ?, ?, ?, " +
                "(SELECT ID FROM category_icon WHERE ICON_GROUP=? AND FILENAME=?), ?, " +
                "(SELECT ID FROM (SELECT ID FROM category WHERE MEMBER_ID=? AND NAME=? LIMIT 1) AS SUB), " +
                "0, NOW())";
        try {
            jdbcTemplate.batchUpdate(
                    sql,
                    list,
                    batchSize,
                    (ps, arg) -> {
                        ps.setLong(1, memberId);
                        ps.setString(2, arg.getName());
                        ps.setString(3, arg.getColor());
                        ps.setString(4, arg.getHighlightColor());
                        ps.setString(5, arg.getIconGroup());
                        ps.setString(6, arg.getIconFilename());
                        ps.setInt(7, arg.getParentName()==null?0:1);
                        ps.setLong(8, memberId);
                        ps.setString(9, arg.getParentName());
                    });
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomApiException(DbException.BATCH_INSERTION_FAIL);
        }
    }
}
