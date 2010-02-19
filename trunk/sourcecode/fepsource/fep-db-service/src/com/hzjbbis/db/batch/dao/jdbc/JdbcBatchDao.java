package com.hzjbbis.db.batch.dao.jdbc;

import com.hzjbbis.db.DbMonitor;
import com.hzjbbis.db.batch.dao.IBatchDao;
import com.hzjbbis.fk.tracelog.TraceLog;
import org.apache.log4j.Logger;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class JdbcBatchDao implements IBatchDao {
    private static final Logger log = Logger.getLogger(JdbcBatchDao.class);

    private static final TraceLog tracer = TraceLog.getTracer("jdbcBatchdao");
    private SimpleJdbcTemplate simpleJdbcTemplate;
    private DataSource dataSource;
    private String sql;
    private String sqlAlt;
    private String additiveSql;
    private Object additiveParameter;
    private int key = 0;
    private int batchSize = 2000;
    private long delay = 5000L;

    private List<Object> objList = new ArrayList();
    private List<Object[]> paramArrayList = new ArrayList();
    private Object batchDaoLock = new Object();
    private long lastIoTime = System.currentTimeMillis();
    private String executeThreadName = null;
    private boolean executing = false;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.simpleJdbcTemplate = new SimpleJdbcTemplate(this.dataSource);
    }

    private int[] batchUpdateByPojo(String sqlStr, List<Object> pojoList) {
        SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(pojoList.toArray());
        int[] updateCounts = this.simpleJdbcTemplate.batchUpdate(sqlStr, batch);
        if (this.additiveSql != null) this.simpleJdbcTemplate.update(this.additiveSql, new Object[0]);
        return updateCounts;
    }

    private int[] batchUpdateByParams(String sqlStr, List<Object[]> arrayList) {
        int[] updateCounts = this.simpleJdbcTemplate.batchUpdate(sqlStr, arrayList);
        if (this.additiveSql != null) this.simpleJdbcTemplate.update(this.additiveSql, new Object[0]);
        return updateCounts;
    }

    private void _doBatchUpdate() {
        long timeTake;
        List listAlt;
        int i;
        if (log.isDebugEnabled()) log.debug("开始执行Dao，key=" + this.key + ",sql=" + this.sql);
        int[] result = (int[]) null;
        long time0 = System.currentTimeMillis();
        if (this.objList.size() > 0) {
            result = batchUpdateByPojo(this.sql, this.objList);
            timeTake = System.currentTimeMillis() - time0;
            if (timeTake > 1000L) tracer.trace("batchUpdate takes(milliseconds):" + timeTake);
            if (log.isDebugEnabled()) {
                log.debug("key=" + this.key + ",成功条数=" + result.length + ",花费毫秒=" + timeTake);
            }
            if (this.sqlAlt != null) {
                listAlt = new ArrayList();
                for (i = 0; i < result.length; ++i) {
                    if (result[i] <= 0) {
                        listAlt.add(this.objList.get(i));
                    }
                }
                if (listAlt.size() > 0) batchUpdateByPojo(this.sqlAlt, listAlt);
            }
            this.lastIoTime = System.currentTimeMillis();
        } else if (this.paramArrayList.size() > 0) {
            result = batchUpdateByParams(this.sql, this.paramArrayList);
            timeTake = System.currentTimeMillis() - time0;
            if (log.isDebugEnabled()) {
                log.debug("key=" + this.key + ",成功条数=" + result.length + ",花费毫秒=" + timeTake);
            }
            if (this.sqlAlt != null) {
                listAlt = new ArrayList();
                for (i = 0; i < result.length; ++i) {
                    if (result[i] <= 0) {
                        listAlt.add((Object[]) this.paramArrayList.get(i));
                    }
                }
                if (listAlt.size() > 0) batchUpdateByParams(this.sqlAlt, listAlt);
            }
            this.lastIoTime = System.currentTimeMillis();
        }
    }

    public void batchUpdate() {
        if (this.executing) return;
        synchronized (this.batchDaoLock) {
            DbMonitor dm = DbMonitor.getMonitor(this.dataSource);
            if ((dm != null) && (!(dm.isAvailable()))) return;
            if (this.executeThreadName != null) {
                log.error("BatchDao[key=" + this.key + "] has already been executed by : " + this.executeThreadName);
            }
            this.executeThreadName = Thread.currentThread().getName();
            try {
                this.executing = true;
                _doBatchUpdate();
            } catch (CannotGetJdbcConnectionException e) {
                if (dm != null) dm.setAvailable(false);
            } catch (BadSqlGrammarException e) {
                tracer.trace(e.getLocalizedMessage(), e);
            } catch (Exception e) {
                tracer.trace("batch dao exception:" + e.getLocalizedMessage(), e);
                log.warn("batch dao exception:" + e.getLocalizedMessage(), e);
            } finally {
                this.executing = false;

                this.objList.clear();
                this.paramArrayList.clear();
            }

            this.executeThreadName = null;
        }
    }

    public int getKey() {
        return this.key;
    }

    public void setKey(int k) {
        this.key = k;
    }

    public boolean add(Object pojo) {
        if (pojo != null) {
            synchronized (this.batchDaoLock) {
                int above = size() - this.batchSize;
                if ((above > this.batchSize) || (above > 3000)) {
                    tracer.trace("batchDao can not add object,size=" + size() + ",batchSize=" + this.batchSize);
                    return false;
                }
                this.objList.add(pojo);
            }
            if (size() >= this.batchSize) batchUpdate();
        } else {
            delayExec();
        }
        return true;
    }

    public boolean add(Object[] params) {
        if (params != null) {
            synchronized (this.batchDaoLock) {
                int above = size() - this.batchSize;
                if ((above > this.batchSize) || (above > 3000)) {
                    tracer.trace("batchDao can not add object,size=" + size() + ",batchSize=" + this.batchSize);
                    return false;
                }
                this.paramArrayList.add(params);
            }
            if (size() >= this.batchSize) batchUpdate();
        } else {
            delayExec();
        }
        return true;
    }

    public void setSql(String sql) {
        this.sql = sql.trim();
    }

    public void setSqlAlt(String sqlAlt) {
        this.sqlAlt = sqlAlt.trim();
    }

    public void setAdditiveSql(String adSql) {
        this.additiveSql = adSql;
    }

    public int size() {
        return Math.max(this.objList.size(), this.paramArrayList.size());
    }

    public void setBatchSize(int batchSize) {
        this.batchSize = batchSize;
    }

    public long getLastIoTime() {
        return this.lastIoTime;
    }

    public void setDelaySecond(int delaySec) {
        this.delay = (delaySec * 1000);
    }

    public long getDelayMilliSeconds() {
        return this.delay;
    }

    public boolean hasDelayData() {
        DbMonitor dm = DbMonitor.getMonitor(this.dataSource);
        boolean result = (System.currentTimeMillis() - this.lastIoTime >= this.delay) && (size() > 0);
        if (dm != null) result = (result) && (dm.isAvailable());
        return result;
    }

    private void delayExec() {
        if (hasDelayData()) batchUpdate();
    }

    public void setAdditiveParameter(Object additiveParameter) {
        this.additiveParameter = additiveParameter;
    }

    public Object getAdditiveParameter() {
        return this.additiveParameter;
    }
}