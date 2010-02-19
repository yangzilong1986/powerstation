package com.hzjbbis.db.batch.dao.jdbc;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.SqlParameterValue;
import org.springframework.jdbc.core.StatementCreatorUtils;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterUtils;
import org.springframework.jdbc.core.namedparam.ParsedSql;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class BatchSimpleJdbcTemplate extends SimpleJdbcTemplate {
    private final BatchNamedParameterJdbcTemplate namedParameterJdbcOperations;

    public BatchSimpleJdbcTemplate(DataSource dataSource) {
        super(dataSource);
        this.namedParameterJdbcOperations = new BatchNamedParameterJdbcTemplate(dataSource);
    }

    public int[] batchUpdate(String sql, List<Object[]> batchArgs, String additiveSql) {
        return doExecuteBatchUpdate(sql, batchArgs, new int[0], additiveSql);
    }

    public int[] batchUpdate(String sql, SqlParameterSource[] batchArgs, String additiveSql) {
        return doExecuteBatchUpdateWithNamedParameters(sql, batchArgs, additiveSql);
    }

    public BatchJdbcTemplate getJdbcOperations() {
        return this.namedParameterJdbcOperations.getJdbcOperations();
    }

    public NamedParameterJdbcOperations getNamedParameterJdbcOperations() {
        return this.namedParameterJdbcOperations;
    }

    private int[] doExecuteBatchUpdateWithNamedParameters(String sql, SqlParameterSource[] batchArgs, String additiveSql) {
        if (batchArgs.length <= 0) {
            return new int[1];
        }
        ParsedSql parsedSql = NamedParameterUtils.parseSqlStatement(sql);
        String sqlToUse = NamedParameterUtils.substituteNamedParameters(parsedSql, batchArgs[0]);
        BatchJdbcTemplate jdbcOperation = getJdbcOperations();
        return jdbcOperation.batchUpdate(sqlToUse, new BatchPreparedStatementSetter(parsedSql, batchArgs) {
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Object[] values = NamedParameterUtils.buildValueArray(this.val$parsedSql, this.val$batchArgs[i], null);
                int[] columnTypes = NamedParameterUtils.buildSqlTypeArray(this.val$parsedSql, this.val$batchArgs[i]);
                BatchSimpleJdbcTemplate.this.doSetStatementParameters(values, ps, columnTypes);
            }

            public int getBatchSize() {
                return this.val$batchArgs.length;
            }
        }, additiveSql);
    }

    private void doSetStatementParameters(Object[] values, PreparedStatement ps, int[] columnTypes) throws SQLException {
        int colIndex = 0;
        for (Object value : values) {
            ++colIndex;
            if (value instanceof SqlParameterValue) {
                SqlParameterValue paramValue = (SqlParameterValue) value;
                StatementCreatorUtils.setParameterValue(ps, colIndex, paramValue, paramValue.getValue());
            } else {
                int colType;
                if ((columnTypes == null) || (columnTypes.length < colIndex)) {
                    colType = -2147483648;
                } else {
                    colType = columnTypes[(colIndex - 1)];
                }
                StatementCreatorUtils.setParameterValue(ps, colIndex, colType, value);
            }
        }
    }

    private int[] doExecuteBatchUpdate(String sql, List<Object[]> batchValues, int[] columnTypes, String additiveSql) {
        return getJdbcOperations().batchUpdate(sql, new BatchPreparedStatementSetter(batchValues, columnTypes) {
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Object[] values = (Object[]) this.val$batchValues.get(i);
                BatchSimpleJdbcTemplate.this.doSetStatementParameters(values, ps, this.val$columnTypes);
            }

            public int getBatchSize() {
                return this.val$batchValues.size();
            }
        }, additiveSql);
    }
}