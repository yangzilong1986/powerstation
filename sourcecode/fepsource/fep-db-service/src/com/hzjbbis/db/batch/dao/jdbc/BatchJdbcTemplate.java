package com.hzjbbis.db.batch.dao.jdbc;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.jdbc.support.nativejdbc.NativeJdbcExtractor;
import org.springframework.util.Assert;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BatchJdbcTemplate extends JdbcTemplate {
    private NativeJdbcExtractor nativeJdbcExtractor;

    public void setNativeJdbcExtractor(NativeJdbcExtractor extractor) {
        super.setNativeJdbcExtractor(extractor);
        this.nativeJdbcExtractor = extractor;
    }

    public NativeJdbcExtractor getNativeJdbcExtractor() {
        return this.nativeJdbcExtractor;
    }

    public BatchJdbcTemplate() {
    }

    public BatchJdbcTemplate(DataSource dataSource, boolean lazyInit) {
        super(dataSource, lazyInit);
    }

    public BatchJdbcTemplate(DataSource dataSource) {
        super(dataSource);
    }

    public Object execute(PreparedStatementCreator psc, PreparedStatementCallback action, String additiveSql) throws DataAccessException {
        Assert.notNull(psc, "PreparedStatementCreator must not be null");
        Assert.notNull(action, "Callback object must not be null");
        if (this.logger.isDebugEnabled()) {
            String sql = getSql(psc);
            this.logger.debug("Executing prepared SQL statement" + ((sql != null) ? " [" + sql + "]" : ""));
        }

        Connection con = DataSourceUtils.getConnection(getDataSource());
        boolean _restore = false;
        boolean _autoCommit = true;
        try {
            _autoCommit = con.getAutoCommit();
        } catch (SQLException localSQLException1) {
        }
        PreparedStatement ps = null;
        try {
            Connection conToUse = con;
            if ((this.nativeJdbcExtractor != null) && (this.nativeJdbcExtractor.isNativeConnectionNecessaryForNativePreparedStatements())) {
                conToUse = this.nativeJdbcExtractor.getNativeConnection(con);
            }

            conToUse.setAutoCommit(false);
            ps = psc.createPreparedStatement(conToUse);
            applyStatementSettings(ps);
            PreparedStatement psToUse = ps;
            if (this.nativeJdbcExtractor != null) {
                psToUse = this.nativeJdbcExtractor.getNativePreparedStatement(ps);
            }
            Object result = action.doInPreparedStatement(psToUse);
            handleWarnings(ps.getWarnings());

            PreparedStatement aps = null;
            try {
                if ((additiveSql != null) && (additiveSql.length() > 5)) {
                    aps = conToUse.prepareStatement(additiveSql);
                    aps.execute(additiveSql);
                }
                conToUse.commit();
            } catch (SQLException ex) {
                if (aps != null) JdbcUtils.closeStatement(aps);
                this.logger.error("批量保存执行附加语句错误，sql=" + additiveSql, ex);
            }

            return result;
        } catch (SQLException ex) {
            if (psc instanceof ParameterDisposer) {
                ((ParameterDisposer) psc).cleanupParameters();
            }
            String sql = getSql(psc);

            throw getExceptionTranslator().translate("PreparedStatementCallback", sql, ex);
        } finally {
            if (psc instanceof ParameterDisposer) {
                ((ParameterDisposer) psc).cleanupParameters();
            }
            JdbcUtils.closeStatement(ps);
            if ((con != null) && (!(_restore))) try {
                con.setAutoCommit(_autoCommit);
            } catch (SQLException localSQLException4) {
            }
            DataSourceUtils.releaseConnection(con, getDataSource());
        }
    }

    public Object execute(String sql, PreparedStatementCallback action, String additiveSql) throws DataAccessException {
        return execute(new SimplePreparedStatementCreator(sql), action, additiveSql);
    }

    public int[] batchUpdate(String sql, BatchPreparedStatementSetter pss, String additiveSql) throws DataAccessException {
        if (this.logger.isDebugEnabled()) {
            this.logger.debug("Executing SQL batch update [" + sql + "]");
        }

        return ((int[]) execute(sql, new PreparedStatementCallback(pss) {
            public Object doInPreparedStatement(PreparedStatement ps) throws SQLException {
                try {
                    int[] arrayOfInt1;
                    int batchSize = this.val$pss.getBatchSize();
                    InterruptibleBatchPreparedStatementSetter ipss = (this.val$pss instanceof InterruptibleBatchPreparedStatementSetter) ? (InterruptibleBatchPreparedStatementSetter) this.val$pss : null;
                    if (JdbcUtils.supportsBatchUpdates(ps.getConnection())) {
                        for (int i = 0; i < batchSize; ++i) {
                            this.val$pss.setValues(ps, i);
                            if ((ipss != null) && (ipss.isBatchExhausted(i))) {
                                break;
                            }
                            ps.addBatch();
                        }
                        arrayOfInt1 = ps.executeBatch();
                        return arrayOfInt1;
                    }

                    List rowsAffected = new ArrayList();
                    for (int i = 0; i < batchSize; ++i) {
                        this.val$pss.setValues(ps, i);
                        if ((ipss != null) && (ipss.isBatchExhausted(i))) {
                            break;
                        }
                        rowsAffected.add(new Integer(ps.executeUpdate()));
                    }
                    int[] rowsAffectedArray = new int[rowsAffected.size()];
                    for (int i = 0; i < rowsAffectedArray.length; ++i) {
                        rowsAffectedArray[i] = ((Integer) rowsAffected.get(i)).intValue();
                    }
                    return rowsAffectedArray;
                } finally {
                    if (this.val$pss instanceof ParameterDisposer)
                        ((ParameterDisposer) this.val$pss).cleanupParameters();
                }
            }
        }, additiveSql));
    }

    private static String getSql(Object sqlProvider) {
        if (sqlProvider instanceof SqlProvider) {
            return ((SqlProvider) sqlProvider).getSql();
        }

        return null;
    }

    private static class SimplePreparedStatementCreator implements PreparedStatementCreator, SqlProvider {
        private final String sql;

        public SimplePreparedStatementCreator(String sql) {
            Assert.notNull(sql, "SQL must not be null");
            this.sql = sql;
        }

        public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
            return con.prepareStatement(this.sql);
        }

        public String getSql() {
            return this.sql;
        }
    }
}