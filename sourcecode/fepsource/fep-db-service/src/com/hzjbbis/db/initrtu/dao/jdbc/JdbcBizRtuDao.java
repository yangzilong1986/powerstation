package com.hzjbbis.db.initrtu.dao.jdbc;

import com.hzjbbis.db.initrtu.dao.BizRtuDao;
import com.hzjbbis.db.resultmap.ResultMapper;
import com.hzjbbis.fk.model.*;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JdbcBizRtuDao implements BizRtuDao {
    private String sqlLoadRtu;
    private String sqlLoadGwRtu;
    private ResultMapper<BizRtu> mapperLoadRtu;
    private String sqlLoadMeasurePoints;
    private ResultMapper<MeasuredPoint> mapperLoadMeasurePoints;
    private String sqlLoadGwMeasurePoints;
    private ResultMapper<MeasuredPoint> mapperLoadGwMeasurePoints;
    private String sqlLoadAlertCode;
    private ResultMapper<RtuAlertCode> mapperLoadAlertCode;
    private String sqlLoadAlertCodeArgs;
    private ResultMapper<RtuAlertCodeArg> mapperLoadAlertCodeArgs;
    private String sqlLoadRtuTask;
    private String sqlLoadGwRtuTask;
    private ResultMapper<RtuTask> mapperLoadRtuTask;
    private String sqlLoadTaskDbConfig;
    private ResultMapper<TaskDbConfig> mapperLoadTaskDbConfig;
    private String sqlLoadTaskTemplate;
    private ResultMapper<TaskTemplate> mapperLoadTaskTemplate;
    private String sqlLoadTaskTemplateItem;
    private ResultMapper<TaskTemplateItem> mapperLoadTaskTemplateItem;
    private String sqlLoadSysConfig;
    private ResultMapper<SysConfig> mapperLoadSysConfig;
    private SimpleJdbcTemplate simpleJdbcTemplate;

    public void setDataSource(DataSource dataSource) {
        this.simpleJdbcTemplate = new SimpleJdbcTemplate(dataSource);
    }

    public List<BizRtu> loadBizRtu() {
        ParameterizedRowMapper rowMap = new ParameterizedRowMapper() {
            public BizRtu mapRow(ResultSet rs, int rowNum) throws SQLException {
                return ((BizRtu) JdbcBizRtuDao.this.mapperLoadRtu.mapOneRow(rs));
            }
        };
        return this.simpleJdbcTemplate.query(this.sqlLoadRtu, rowMap, new Object[0]);
    }

    public List<BizRtu> loadBizGwRtu() {
        ParameterizedRowMapper rowMap = new ParameterizedRowMapper() {
            public BizRtu mapRow(ResultSet rs, int rowNum) throws SQLException {
                return ((BizRtu) JdbcBizRtuDao.this.mapperLoadRtu.mapOneRow(rs));
            }
        };
        return this.simpleJdbcTemplate.query(this.sqlLoadGwRtu, rowMap, new Object[0]);
    }

    public List<MeasuredPoint> loadMeasuredPoints() {
        ParameterizedRowMapper rowMap = new ParameterizedRowMapper() {
            public MeasuredPoint mapRow(ResultSet rs, int rowNum) throws SQLException {
                return ((MeasuredPoint) JdbcBizRtuDao.this.mapperLoadMeasurePoints.mapOneRow(rs));
            }
        };
        return this.simpleJdbcTemplate.query(this.sqlLoadMeasurePoints, rowMap, new Object[0]);
    }

    public List<MeasuredPoint> loadGwMeasuredPoints() {
        ParameterizedRowMapper rowMap = new ParameterizedRowMapper() {
            public MeasuredPoint mapRow(ResultSet rs, int rowNum) throws SQLException {
                return ((MeasuredPoint) JdbcBizRtuDao.this.mapperLoadGwMeasurePoints.mapOneRow(rs));
            }
        };
        return this.simpleJdbcTemplate.query(this.sqlLoadGwMeasurePoints, rowMap, new Object[0]);
    }

    private List<RtuAlertCodeArg> loadRtuAlertCodeArgs() {
        ParameterizedRowMapper rowMap = new ParameterizedRowMapper() {
            public RtuAlertCodeArg mapRow(ResultSet rs, int rowNum) throws SQLException {
                return ((RtuAlertCodeArg) JdbcBizRtuDao.this.mapperLoadAlertCodeArgs.mapOneRow(rs));
            }
        };
        return this.simpleJdbcTemplate.query(this.sqlLoadAlertCodeArgs, rowMap, new Object[0]);
    }

    public List<RtuAlertCode> loadRtuAlertCodes() {
        ParameterizedRowMapper rowMap = new ParameterizedRowMapper() {
            public RtuAlertCode mapRow(ResultSet rs, int rowNum) throws SQLException {
                return ((RtuAlertCode) JdbcBizRtuDao.this.mapperLoadAlertCode.mapOneRow(rs));
            }
        };
        List alertCodeList = this.simpleJdbcTemplate.query(this.sqlLoadAlertCode, rowMap, new Object[0]);
        HashMap map = new HashMap();
        for (RtuAlertCode acode : alertCodeList) {
            map.put(acode.getCode(), acode);
        }
        List args = loadRtuAlertCodeArgs();
        for (RtuAlertCodeArg arg : args) {
            RtuAlertCode acode = (RtuAlertCode) map.get(arg.getCode());
            if (acode != null) acode.getArgs().add(arg.getSjx());
        }
        map.clear();
        args.clear();
        return alertCodeList;
    }

    public List<RtuTask> loadRtuTasks() {
        ParameterizedRowMapper rowMap = new ParameterizedRowMapper() {
            public RtuTask mapRow(ResultSet rs, int rowNum) throws SQLException {
                return ((RtuTask) JdbcBizRtuDao.this.mapperLoadRtuTask.mapOneRow(rs));
            }
        };
        return this.simpleJdbcTemplate.query(this.sqlLoadRtuTask, rowMap, new Object[0]);
    }

    public List<RtuTask> loadGwRtuTasks() {
        ParameterizedRowMapper rowMap = new ParameterizedRowMapper() {
            public RtuTask mapRow(ResultSet rs, int rowNum) throws SQLException {
                return ((RtuTask) JdbcBizRtuDao.this.mapperLoadRtuTask.mapOneRow(rs));
            }
        };
        return this.simpleJdbcTemplate.query(this.sqlLoadGwRtuTask, rowMap, new Object[0]);
    }

    public List<TaskDbConfig> loadTaskDbConfig() {
        ParameterizedRowMapper rowMap = new ParameterizedRowMapper() {
            public TaskDbConfig mapRow(ResultSet rs, int rowNum) throws SQLException {
                return ((TaskDbConfig) JdbcBizRtuDao.this.mapperLoadTaskDbConfig.mapOneRow(rs));
            }
        };
        return this.simpleJdbcTemplate.query(this.sqlLoadTaskDbConfig, rowMap, new Object[0]);
    }

    public List<TaskTemplate> loadTaskTemplate() {
        ParameterizedRowMapper rowMap = new ParameterizedRowMapper() {
            public TaskTemplate mapRow(ResultSet rs, int rowNum) throws SQLException {
                return ((TaskTemplate) JdbcBizRtuDao.this.mapperLoadTaskTemplate.mapOneRow(rs));
            }
        };
        List taskTemps = this.simpleJdbcTemplate.query(this.sqlLoadTaskTemplate, rowMap, new Object[0]);
        Map map = new HashMap();
        for (TaskTemplate tt : taskTemps) {
            map.put(tt.getTaskTemplateID(), tt);
        }
        List taskTempItems = loadTaskTemplateItem();
        for (TaskTemplateItem ttItem : taskTempItems) {
            TaskTemplate tt = (TaskTemplate) map.get(ttItem.getTaskTemplateID());
            if (tt != null) tt.addDataCode(ttItem.getCode());
        }
        return taskTemps;
    }

    public void setMapperLoadSysConfig(ResultMapper<SysConfig> mapperLoadSysConfig) {
        this.mapperLoadSysConfig = mapperLoadSysConfig;
    }

    public void setSqlLoadSysConfig(String sqlLoadSysConfig) {
        this.sqlLoadSysConfig = sqlLoadSysConfig;
    }

    private List<TaskTemplateItem> loadTaskTemplateItem() {
        ParameterizedRowMapper rowMap = new ParameterizedRowMapper() {
            public TaskTemplateItem mapRow(ResultSet rs, int rowNum) throws SQLException {
                return ((TaskTemplateItem) JdbcBizRtuDao.this.mapperLoadTaskTemplateItem.mapOneRow(rs));
            }
        };
        return this.simpleJdbcTemplate.query(this.sqlLoadTaskTemplateItem, rowMap, new Object[0]);
    }

    public List<SysConfig> loadSysConfig() {
        ParameterizedRowMapper rowMap = new ParameterizedRowMapper() {
            public SysConfig mapRow(ResultSet rs, int rowNum) throws SQLException {
                return ((SysConfig) JdbcBizRtuDao.this.mapperLoadSysConfig.mapOneRow(rs));
            }
        };
        return this.simpleJdbcTemplate.query(this.sqlLoadSysConfig, rowMap, new Object[0]);
    }

    public void setSqlLoadRtu(String sqlLoadRtu) {
        this.sqlLoadRtu = sqlLoadRtu;
    }

    public void setSqlLoadMeasurePoints(String sqlLoadMeasurePoints) {
        this.sqlLoadMeasurePoints = sqlLoadMeasurePoints;
    }

    public void setSqlLoadAlertCode(String sqlLoadAlertCode) {
        this.sqlLoadAlertCode = sqlLoadAlertCode;
    }

    public void setSqlLoadRtuTask(String sqlLoadRtuTask) {
        this.sqlLoadRtuTask = sqlLoadRtuTask;
    }

    public void setSqlLoadTaskDbConfig(String sqlLoadTaskDbConfig) {
        this.sqlLoadTaskDbConfig = sqlLoadTaskDbConfig;
    }

    public void setSqlLoadTaskTemplate(String sqlLoadTaskTemplate) {
        this.sqlLoadTaskTemplate = sqlLoadTaskTemplate;
    }

    public void setSqlLoadTaskTemplateItem(String sqlLoadTaskTemplateItem) {
        this.sqlLoadTaskTemplateItem = sqlLoadTaskTemplateItem;
    }

    public void setSqlLoadAlertCodeArgs(String sqlLoadAlertCodeArgs) {
        this.sqlLoadAlertCodeArgs = sqlLoadAlertCodeArgs;
    }

    public void setMapperLoadRtu(ResultMapper<BizRtu> mapperLoadRtu) {
        this.mapperLoadRtu = mapperLoadRtu;
    }

    public void setMapperLoadMeasurePoints(ResultMapper<MeasuredPoint> mapperLoadMeasurePoints) {
        this.mapperLoadMeasurePoints = mapperLoadMeasurePoints;
    }

    public void setMapperLoadAlertCode(ResultMapper<RtuAlertCode> mapperLoadAlertCode) {
        this.mapperLoadAlertCode = mapperLoadAlertCode;
    }

    public void setMapperLoadAlertCodeArgs(ResultMapper<RtuAlertCodeArg> mapperLoadAlertCodeArgs) {
        this.mapperLoadAlertCodeArgs = mapperLoadAlertCodeArgs;
    }

    public void setMapperLoadRtuTask(ResultMapper<RtuTask> mapperLoadRtuTask) {
        this.mapperLoadRtuTask = mapperLoadRtuTask;
    }

    public void setMapperLoadTaskDbConfig(ResultMapper<TaskDbConfig> mapperLoadTaskDbConfig) {
        this.mapperLoadTaskDbConfig = mapperLoadTaskDbConfig;
    }

    public void setMapperLoadTaskTemplate(ResultMapper<TaskTemplate> mapperLoadTaskTemplate) {
        this.mapperLoadTaskTemplate = mapperLoadTaskTemplate;
    }

    public void setMapperLoadTaskTemplateItem(ResultMapper<TaskTemplateItem> mapperLoadTaskTemplateItem) {
        this.mapperLoadTaskTemplateItem = mapperLoadTaskTemplateItem;
    }

    public void setSqlLoadGwRtu(String sqlLoadGwRtu) {
        this.sqlLoadGwRtu = sqlLoadGwRtu;
    }

    public void setSqlLoadGwRtuTask(String sqlLoadGwRtuTask) {
        this.sqlLoadGwRtuTask = sqlLoadGwRtuTask;
    }

    public void setSqlLoadGwMeasurePoints(String sqlLoadGwMeasurePoints) {
        this.sqlLoadGwMeasurePoints = sqlLoadGwMeasurePoints;
    }

    public void setMapperLoadGwMeasurePoints(ResultMapper<MeasuredPoint> mapperLoadGwMeasurePoints) {
        this.mapperLoadGwMeasurePoints = mapperLoadGwMeasurePoints;
    }
}