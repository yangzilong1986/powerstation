package com.hzjbbis.db.rtu.jdbc;

import com.hzjbbis.db.resultmap.ResultMapper;
import com.hzjbbis.db.rtu.RtuRefreshDao;
import com.hzjbbis.fk.model.*;
import com.hzjbbis.fk.utils.HexDump;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

public class JdbcRtuRefreshDao implements RtuRefreshDao {
    private String sqlGetRtuByRtuId;
    private String sqlGetRtuByRtua;
    private String sqlGetGwRtuByRtuId;
    private String sqlGetGwRtuByRtua;
    private ResultMapper<BizRtu> mapperGetRtu;
    private String sqlGetMeasurePoints;
    private ResultMapper<MeasuredPoint> mapperGetMeasurePoints;
    private String sqlGetGwMeasurePoints;
    private ResultMapper<MeasuredPoint> mapperGetGwMeasurePoints;
    private String sqlGetComRtuByRtua;
    private String sqlGetComGwRtuByRtua;
    private ResultMapper<ComRtu> mapperGetComRtu;
    private String sqlGetRtuTask;
    private String sqlGetGwRtuTask;
    private ResultMapper<RtuTask> mapperGetRtuTask;
    private String sqlGetTaskTemplate;
    private ResultMapper<TaskTemplate> mapperGetTaskTemplate;
    private String sqlGetTaskTemplateItem;
    private ResultMapper<TaskTemplateItem> mapperGetTaskTemplateItem;
    private SimpleJdbcTemplate simpleJdbcTemplate;

    public void setDataSource(DataSource dataSource) {
        this.simpleJdbcTemplate = new SimpleJdbcTemplate(dataSource);
    }

    public List<MeasuredPoint> getMeasurePoints(String zdjh) {
        ParameterizedRowMapper rm = new ParameterizedRowMapper() {
            public MeasuredPoint mapRow(ResultSet rs, int rowNum) throws SQLException {
                return ((MeasuredPoint) JdbcRtuRefreshDao.this.mapperGetMeasurePoints.mapOneRow(rs));
            }
        };
        return this.simpleJdbcTemplate.query(this.sqlGetMeasurePoints, rm, new Object[]{zdjh});
    }

    public List<MeasuredPoint> getGwMeasurePoints(String zdjh) {
        ParameterizedRowMapper rm = new ParameterizedRowMapper() {
            public MeasuredPoint mapRow(ResultSet rs, int rowNum) throws SQLException {
                return ((MeasuredPoint) JdbcRtuRefreshDao.this.mapperGetGwMeasurePoints.mapOneRow(rs));
            }
        };
        return this.simpleJdbcTemplate.query(this.sqlGetGwMeasurePoints, rm, new Object[]{zdjh});
    }

    public BizRtu getRtu(String zdjh) {
        List mps;
        Iterator i$;
        MeasuredPoint mp;
        ParameterizedRowMapper rm = new ParameterizedRowMapper() {
            public BizRtu mapRow(ResultSet rs, int rowNum) throws SQLException {
                return ((BizRtu) JdbcRtuRefreshDao.this.mapperGetRtu.mapOneRow(rs));
            }
        };
        BizRtu rtu = null;
        try {
            rtu = (BizRtu) this.simpleJdbcTemplate.queryForObject(this.sqlGetRtuByRtuId, rm, new Object[]{zdjh});
        } catch (Exception ex) {
        }
        if (rtu == null) {
            rtu = (BizRtu) this.simpleJdbcTemplate.queryForObject(this.sqlGetGwRtuByRtuId, rm, new Object[]{zdjh});
            if (rtu != null) {
                rtu.setRtuType("02");

                mps = getGwMeasurePoints(zdjh);
                for (i$ = mps.iterator(); i$.hasNext();) {
                    mp = (MeasuredPoint) i$.next();
                    rtu.addMeasuredPoint(mp);
                }
            }
            List tasks = getGwRtuTasks(zdjh);
            for (RtuTask task : tasks)
                rtu.addRtuTask(task);
        } else {
            rtu.setRtuType("01");

            mps = getMeasurePoints(zdjh);
            for (i$ = mps.iterator(); i$.hasNext();) {
                mp = (MeasuredPoint) i$.next();
                rtu.addMeasuredPoint(mp);
            }
            List tasks = getRtuTasks(zdjh);
            for (RtuTask task : tasks)
                rtu.addRtuTask(task);
        }
        if ((rtu != null) && (rtu.getRtuProtocol() == null)) rtu.setRtuProtocol("01");
        return rtu;
    }

    public BizRtu getRtu(int rtua) {
        List mps;
        Iterator i$;
        List tasks;
        MeasuredPoint mp;
        Iterator i$;
        RtuTask task;
        ParameterizedRowMapper rm = new ParameterizedRowMapper() {
            public BizRtu mapRow(ResultSet rs, int rowNum) throws SQLException {
                return ((BizRtu) JdbcRtuRefreshDao.this.mapperGetRtu.mapOneRow(rs));
            }
        };
        BizRtu rtu = null;
        try {
            rtu = (BizRtu) this.simpleJdbcTemplate.queryForObject(this.sqlGetRtuByRtua, rm, new Object[]{HexDump.toHex(rtua)});
        } catch (Exception ex) {
        }
        if (rtu == null) {
            rtu = (BizRtu) this.simpleJdbcTemplate.queryForObject(this.sqlGetGwRtuByRtua, rm, new Object[]{HexDump.toHex(rtua)});
            if (rtu != null) {
                rtu.setRtuType("02");

                mps = getMeasurePoints(rtu.getRtuId());
                for (i$ = mps.iterator(); i$.hasNext();) {
                    mp = (MeasuredPoint) i$.next();
                    rtu.addMeasuredPoint(mp);
                }
                tasks = getGwRtuTasks(rtu.getRtuId());
                for (i$ = tasks.iterator(); i$.hasNext();) {
                    task = (RtuTask) i$.next();
                    rtu.addRtuTask(task);
                }
            }
        } else {
            rtu.setRtuType("01");

            mps = getMeasurePoints(rtu.getRtuId());
            for (tasks = mps.iterator(); tasks.hasNext();) {
                i$ = (MeasuredPoint) tasks.next();
                rtu.addMeasuredPoint(i$);
            }
            tasks = getRtuTasks(rtu.getRtuId());
            for (i$ = tasks.iterator(); i$.hasNext();) {
                task = (RtuTask) i$.next();
                rtu.addRtuTask(task);
            }
        }
        if ((rtu != null) && (rtu.getRtuProtocol() == null)) rtu.setRtuProtocol("01");
        return rtu;
    }

    public ComRtu getComRtu(int rtua) {
        ParameterizedRowMapper rm = new ParameterizedRowMapper() {
            public ComRtu mapRow(ResultSet rs, int rowNum) throws SQLException {
                return ((ComRtu) JdbcRtuRefreshDao.this.mapperGetComRtu.mapOneRow(rs));
            }
        };
        ComRtu rtu = null;
        try {
            rtu = (ComRtu) this.simpleJdbcTemplate.queryForObject(this.sqlGetComRtuByRtua, rm, new Object[]{HexDump.toHex(rtua)});
        } catch (Exception ex) {
        }
        if (rtu == null)
            rtu = (ComRtu) this.simpleJdbcTemplate.queryForObject(this.sqlGetComGwRtuByRtua, rm, new Object[]{HexDump.toHex(rtua)});
        return rtu;
    }

    public List<RtuTask> getRtuTasks(String zdjh) {
        ParameterizedRowMapper rm = new ParameterizedRowMapper() {
            public RtuTask mapRow(ResultSet rs, int rowNum) throws SQLException {
                return ((RtuTask) JdbcRtuRefreshDao.this.mapperGetRtuTask.mapOneRow(rs));
            }
        };
        return this.simpleJdbcTemplate.query(this.sqlGetRtuTask, rm, new Object[]{zdjh});
    }

    public List<RtuTask> getGwRtuTasks(String zdjh) {
        ParameterizedRowMapper rm = new ParameterizedRowMapper() {
            public RtuTask mapRow(ResultSet rs, int rowNum) throws SQLException {
                return ((RtuTask) JdbcRtuRefreshDao.this.mapperGetRtuTask.mapOneRow(rs));
            }
        };
        return this.simpleJdbcTemplate.query(this.sqlGetGwRtuTask, rm, new Object[]{zdjh});
    }

    public TaskTemplate getTaskTemplate(String templID) {
        ParameterizedRowMapper rm = new ParameterizedRowMapper() {
            public TaskTemplate mapRow(ResultSet rs, int rowNum) throws SQLException {
                return ((TaskTemplate) JdbcRtuRefreshDao.this.mapperGetTaskTemplate.mapOneRow(rs));
            }
        };
        return ((TaskTemplate) this.simpleJdbcTemplate.queryForObject(this.sqlGetTaskTemplate, rm, new Object[]{templID}));
    }

    public List<TaskTemplateItem> getTaskTemplateItems(String templID) {
        ParameterizedRowMapper rm = new ParameterizedRowMapper() {
            public TaskTemplateItem mapRow(ResultSet rs, int rowNum) throws SQLException {
                return ((TaskTemplateItem) JdbcRtuRefreshDao.this.mapperGetTaskTemplateItem.mapOneRow(rs));
            }
        };
        return this.simpleJdbcTemplate.query(this.sqlGetTaskTemplateItem, rm, new Object[]{templID});
    }

    public void setMapperGetRtu(ResultMapper<BizRtu> mapperGetRtu) {
        this.mapperGetRtu = mapperGetRtu;
    }

    public void setSqlGetMeasurePoints(String sqlGetMeasurePoints) {
        this.sqlGetMeasurePoints = sqlGetMeasurePoints;
    }

    public void setMapperGetMeasurePoints(ResultMapper<MeasuredPoint> mapperGetMeasurePoints) {
        this.mapperGetMeasurePoints = mapperGetMeasurePoints;
    }

    public void setSqlGetRtuTask(String sqlGetRtuTask) {
        this.sqlGetRtuTask = sqlGetRtuTask;
    }

    public void setMapperGetRtuTask(ResultMapper<RtuTask> mapperGetRtuTask) {
        this.mapperGetRtuTask = mapperGetRtuTask;
    }

    public void setSqlGetTaskTemplate(String sqlGetTaskTemplate) {
        this.sqlGetTaskTemplate = sqlGetTaskTemplate;
    }

    public void setMapperGetTaskTemplate(ResultMapper<TaskTemplate> mapperGetTaskTemplate) {
        this.mapperGetTaskTemplate = mapperGetTaskTemplate;
    }

    public void setSqlGetTaskTemplateItem(String sqlGetTaskTemplateItem) {
        this.sqlGetTaskTemplateItem = sqlGetTaskTemplateItem;
    }

    public void setMapperGetTaskTemplateItem(ResultMapper<TaskTemplateItem> mapperGetTaskTemplateItem) {
        this.mapperGetTaskTemplateItem = mapperGetTaskTemplateItem;
    }

    public void setSimpleJdbcTemplate(SimpleJdbcTemplate simpleJdbcTemplate) {
        this.simpleJdbcTemplate = simpleJdbcTemplate;
    }

    public void setSqlGetRtuByRtuId(String sqlGetRtuByRtuId) {
        this.sqlGetRtuByRtuId = sqlGetRtuByRtuId;
    }

    public void setSqlGetRtuByRtua(String sqlGetRtuByRtua) {
        this.sqlGetRtuByRtua = sqlGetRtuByRtua;
    }

    public void setSqlGetComRtuByRtua(String sqlGetComRtuByRtua) {
        this.sqlGetComRtuByRtua = sqlGetComRtuByRtua;
    }

    public void setMapperGetComRtu(ResultMapper<ComRtu> mapperGetComRtu) {
        this.mapperGetComRtu = mapperGetComRtu;
    }

    public void setSqlGetGwRtuByRtuId(String sqlGetGwRtuByRtuId) {
        this.sqlGetGwRtuByRtuId = sqlGetGwRtuByRtuId;
    }

    public void setSqlGetGwRtuByRtua(String sqlGetGwRtuByRtua) {
        this.sqlGetGwRtuByRtua = sqlGetGwRtuByRtua;
    }

    public void setSqlGetGwRtuTask(String sqlGetGwRtuTask) {
        this.sqlGetGwRtuTask = sqlGetGwRtuTask;
    }

    public void setSqlGetComGwRtuByRtua(String sqlGetComGwRtuByRtua) {
        this.sqlGetComGwRtuByRtua = sqlGetComGwRtuByRtua;
    }

    public void setSqlGetGwMeasurePoints(String sqlGetGwMeasurePoints) {
        this.sqlGetGwMeasurePoints = sqlGetGwMeasurePoints;
    }

    public void setMapperGetGwMeasurePoints(ResultMapper<MeasuredPoint> mapperGetGwMeasurePoints) {
        this.mapperGetGwMeasurePoints = mapperGetGwMeasurePoints;
    }
}