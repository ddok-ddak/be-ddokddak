package com.ddokddak.common.repository;

import java.util.List;

public interface JdbcRepository<T> {
    void batchInsert(int batchSize, List<T> list);
}
