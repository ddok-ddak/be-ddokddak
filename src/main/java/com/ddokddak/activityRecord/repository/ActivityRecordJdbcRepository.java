package com.ddokddak.activityRecord.repository;

import com.ddokddak.activityRecord.entity.ActivityRecord;
import com.ddokddak.common.exception.JdbcExecException;

import com.ddokddak.common.exception.type.FailedJdbcExec;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.LongStream;

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

        Long latestId;
        try {
            latestId = jdbcTemplate.queryForObject("SELECT id FROM activity_record ORDER BY id DESC LIMIT 1", Long.class);
        } catch (DataAccessException e) {
            e.printStackTrace();
            latestId = 0L;
        }

        batchInsert(batchSize, list);

        Long finalId = latestId;
        if (list.size() < batchSize) { // TODO 리팩토링 필수
            LongStream.range(0, list.size())
                    .forEach(i -> list.get((int) i).assignId(i + finalId + 1L));
        }
    }

    /*
    * DataAccessException 을 던진다. 체크해보고, 커스텀 익셉션 던지는 걸로 처리
    * */
    @Transactional
    public void batchInsert(int batchSize, List<ActivityRecord> list) {

        var sql = "INSERT INTO ACTIVITY_RECORD (MEMBER_ID, CATEGORY_ID, CATEGORY_NAME, CATEGORY_COLOR, " +
                "STARTED_AT, FINISHED_AT, TIME_ZONE, CONTENT, TIME_UNIT, DELETE_YN) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            jdbcTemplate.batchUpdate(
                    sql,
                    list,
                    batchSize,
                    (ps, arg) -> {
                        ps.setLong(1, arg.getMember().getId());
                        ps.setLong(2, arg.getCategory().getId());
                        ps.setString(3, arg.getCategoryName());
                        ps.setString(4, arg.getCategoryColor());
                        ps.setTimestamp(5, Timestamp.valueOf(arg.getStartedAt())); // datetime 타입으로 저장!
                        ps.setTimestamp(6, Timestamp.valueOf(arg.getFinishedAt()));
                        ps.setString(7, arg.getTimeZone());
                        ps.setString(8, arg.getContent());
                        ps.setInt(9, arg.getTimeUnit());
                        ps.setString(10, arg.getDeleteYn());
                    });
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new JdbcExecException(FailedJdbcExec.BATCH_INSERTION_FAIL);
        }
    }
}
