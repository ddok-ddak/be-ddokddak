package com.ddokddak.activityRecord.repository;

import com.ddokddak.activityRecord.domain.entity.ActivityRecord;
import com.ddokddak.common.exception.JdbcExecException;

import com.ddokddak.common.exception.type.DbException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class ActivityRecordJdbcRepository {

    @Value("${jdbc.batch.size}")
    private int batchSize;
    private final JdbcTemplate jdbcTemplate;

    /**
     * saveAll() 수행시 매 엔티티마다 쿼리가 발생하는 것을 개선하기 위한 메서드
     * 설정파일에 지정해둔 size 값에 따라 인서트 쿼리를 나눠서 수행합니다.
     * @param list 인서트를 수행할 엔티티 리스트
     */
    @Transactional // TODO 리팩토링 - 트랜잭션, id 체크 여부
    public void bulkSave(List<ActivityRecord> list) {

//        Long latestId;
//        try {
//            latestId = jdbcTemplate.queryForObject("SELECT id FROM activity_record ORDER BY id DESC LIMIT 1", Long.class);
//        } catch (DataAccessException e) {
//            e.printStackTrace();
//            latestId = 0L;
//        }

        // 배치 인서트 수행
        batchInsert(batchSize, list);

//        Long finalId = latestId;
//        if (list.size() < batchSize) {
//            LongStream.range(0, list.size())
//                    .forEach(i -> list.get((int) i).assignId(i + finalId + 1L));
//        }
    }

    /**
     * 주어진 배치 사이즈 크기만큼 벌크 인서트를 나누어서 수행한다.
     *
     * @param batchSize 배치 인서트 수행 단위
     * @param list 인서트를 수행할 엔티티 리스트
     * @throws JdbcExecException 커스텀 예외로 대체 (DataAccessException이 발생하면, 대신 해당 예외를 던진다.)
     */
    @Transactional
    public void batchInsert(int batchSize, List<ActivityRecord> list) {

        var sql = "INSERT INTO activity_record (MEMBER_ID, CATEGORY_ID, " +
                "STARTED_AT, FINISHED_AT, CONTENT, TIME_UNIT, IS_DELETED, CREATED_AT) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, NOW())";
        try {
            jdbcTemplate.batchUpdate(
                    sql,
                    list,
                    batchSize,
                    (ps, arg) -> {
                        ps.setLong(1, arg.getMember().getId());
                        ps.setLong(2, arg.getCategory().getId());
                        ps.setTimestamp(3, Timestamp.valueOf(arg.getStartedAt())); // datetime 타입으로 저장!
                        ps.setTimestamp(4, Timestamp.valueOf(arg.getFinishedAt()));
                        ps.setString(5, arg.getContent());
                        ps.setInt(6, arg.getTimeUnit());
                        ps.setBoolean(7, arg.getIsDeleted().booleanValue());
                    });
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new JdbcExecException(DbException.BATCH_INSERTION_FAIL);
        }
    }
}
