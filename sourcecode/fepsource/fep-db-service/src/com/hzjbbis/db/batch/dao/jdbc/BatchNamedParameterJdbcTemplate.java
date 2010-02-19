package com.hzjbbis.db.batch.dao.jdbc;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

public class BatchNamedParameterJdbcTemplate extends NamedParameterJdbcTemplate {
    private final BatchJdbcTemplate batchJdbcTemplate;

    public BatchNamedParameterJdbcTemplate(DataSource dataSource) {
        super(dataSource);
        this.batchJdbcTemplate = new BatchJdbcTemplate(dataSource);
    }

    public BatchJdbcTemplate getJdbcOperations() {
        return this.batchJdbcTemplate;
    }
}