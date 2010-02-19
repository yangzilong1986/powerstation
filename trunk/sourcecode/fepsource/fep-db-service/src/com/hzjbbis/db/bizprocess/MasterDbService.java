package com.hzjbbis.db.bizprocess;

import com.hzjbbis.db.procedure.DbProcedure;
import com.hzjbbis.db.resultmap.ResultMapper;
import com.hzjbbis.fk.model.RtuCmdItem;
import com.hzjbbis.fk.model.RtuSetValue;
import com.hzjbbis.fk.model.RtuSynchronizeItem;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class MasterDbService {
    private static final Logger log = Logger.getLogger(MasterDbService.class);
    private DataSource dataSource;
    private SimpleJdbcTemplate simpleJdbcTemplate;
    private String insertCommandResult;
    private String insertGwCommandResult;
    private String insertRtuComdMag;
    private DbProcedure funcGetRtuCommandSeq;
    private DbProcedure procUpdateCommandStatus;
    private DbProcedure procUpdateParamResult;
    private DbProcedure procPostCreateRtuAlert;
    private DbProcedure procPostCreateRtuData;
    private String sqlGetRtuComdItem;
    private String sqlGetRtuSycItem;
    private String sqlGetGwRtuSetValue;
    private String updateGwRtuSetValue;
    private ResultMapper<RtuCmdItem> mapperGetRtuComdItem;
    private ResultMapper<RtuSetValue> mapperGetRtuSetValue;
    private ResultMapper<RtuSynchronizeItem> mapperGetRtuSycItem;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.simpleJdbcTemplate = new SimpleJdbcTemplate(this.dataSource);
    }

    public int insertCommandResult(Object obj) {
        BeanPropertySqlParameterSource ps = new BeanPropertySqlParameterSource(obj);
        return this.simpleJdbcTemplate.update(this.insertCommandResult, ps);
    }

    public int insertGwCommandResult(Object obj) {
        BeanPropertySqlParameterSource ps = new BeanPropertySqlParameterSource(obj);
        return this.simpleJdbcTemplate.update(this.insertGwCommandResult, ps);
    }

    public void setInsertGwCommandResult(String insertGwCommandResult) {
        this.insertGwCommandResult = insertGwCommandResult;
    }

    public void updateGwRtuSetValue(String sjxzt, long id) {
        this.simpleJdbcTemplate.update(this.updateGwRtuSetValue, new Object[]{sjxzt, Long.valueOf(id)});
    }

    public void setUpdateGwRtuSetValue(String updateGwRtuSetValue) {
        this.updateGwRtuSetValue = updateGwRtuSetValue;
    }

    public void setSqlGetGwRtuSetValue(String sqlGetGwRtuSetValue) {
        this.sqlGetGwRtuSetValue = sqlGetGwRtuSetValue;
    }

    public void setInsertCommandResult(String insertCommandResult) {
        this.insertCommandResult = insertCommandResult;
    }

    public void insertRtuComdMag(Object obj) {
        try {
            BeanPropertySqlParameterSource ps = new BeanPropertySqlParameterSource(obj);
            this.simpleJdbcTemplate.update(this.insertRtuComdMag, ps);
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
    }

    public List<RtuCmdItem> getRtuComdItem(String zdljdz, int mlxh) {
        ParameterizedRowMapper rm = new ParameterizedRowMapper() {
            public RtuCmdItem mapRow(ResultSet rs, int rowNum) throws SQLException {
                return ((RtuCmdItem) MasterDbService.this.mapperGetRtuComdItem.mapOneRow(rs));
            }
        };
        return this.simpleJdbcTemplate.query(this.sqlGetRtuComdItem, rm, new Object[]{zdljdz, Integer.valueOf(mlxh)});
    }

    public List<RtuSetValue> getGwRtuSetValue(long cmdId) {
        ParameterizedRowMapper rm = new ParameterizedRowMapper() {
            public RtuSetValue mapRow(ResultSet rs, int rowNum) throws SQLException {
                return ((RtuSetValue) MasterDbService.this.mapperGetRtuSetValue.mapOneRow(rs));
            }
        };
        return this.simpleJdbcTemplate.query(this.sqlGetGwRtuSetValue, rm, new Object[]{Long.valueOf(cmdId)});
    }

    public List<RtuSynchronizeItem> getRtuSycItem(Date dt) {
        ParameterizedRowMapper rm = new ParameterizedRowMapper() {
            public RtuSynchronizeItem mapRow(ResultSet rs, int rowNum) throws SQLException {
                return ((RtuSynchronizeItem) MasterDbService.this.mapperGetRtuSycItem.mapOneRow(rs));
            }
        };
        return this.simpleJdbcTemplate.query(this.sqlGetRtuSycItem, rm, new Object[]{dt});
    }

    public int getRtuCommandSeq(String strRtua, String rtuType, String rtuPotocType) {
        try {
            return this.funcGetRtuCommandSeq.executeFunctionInt(new Object[]{strRtua, rtuType, rtuPotocType});
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
        return 1;
    }

    public void procUpdateCommandStatus(Object param) {
        try {
            this.procUpdateCommandStatus.execute(new Object[]{param});
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
    }

    public void procUpdateParamResult(Object param) {
        try {
            this.procUpdateParamResult.execute(new Object[]{param});
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
    }

    public void procPostCreateRtuAlert(Object param) {
        try {
            this.procPostCreateRtuAlert.execute(new Object[]{param});
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
    }

    public void procPostCreateRtuData(Object param) {
        try {
            this.procPostCreateRtuData.execute(new Object[]{param});
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
    }

    public void setFuncGetRtuCommandSeq(DbProcedure funcGetRtuCommandSeq) {
        this.funcGetRtuCommandSeq = funcGetRtuCommandSeq;
    }

    public void setProcUpdateCommandStatus(DbProcedure procUpdateCommandStatus) {
        this.procUpdateCommandStatus = procUpdateCommandStatus;
    }

    public void setProcUpdateParamResult(DbProcedure procUpdateParamResult) {
        this.procUpdateParamResult = procUpdateParamResult;
    }

    public void setInsertRtuComdMag(String insertRtuComdMag) {
        this.insertRtuComdMag = insertRtuComdMag;
    }

    public void setSqlGetRtuComdItem(String sqlGetRtuComdItem) {
        this.sqlGetRtuComdItem = sqlGetRtuComdItem;
    }

    public void setMapperGetRtuComdItem(ResultMapper<RtuCmdItem> mapperGetRtuComdItem) {
        this.mapperGetRtuComdItem = mapperGetRtuComdItem;
    }

    public void setProcPostCreateRtuAlert(DbProcedure procPostCreateRtuAlert) {
        this.procPostCreateRtuAlert = procPostCreateRtuAlert;
    }

    public void setProcPostCreateRtuData(DbProcedure procPostCreateRtuData) {
        this.procPostCreateRtuData = procPostCreateRtuData;
    }

    public void setSqlGetRtuSycItem(String sqlGetRtuSycItem) {
        this.sqlGetRtuSycItem = sqlGetRtuSycItem;
    }

    public void setMapperGetRtuSycItem(ResultMapper<RtuSynchronizeItem> mapperGetRtuSycItem) {
        this.mapperGetRtuSycItem = mapperGetRtuSycItem;
    }

    public void setMapperGetRtuSetValue(ResultMapper<RtuSetValue> mapperGetRtuSetValue) {
        this.mapperGetRtuSetValue = mapperGetRtuSetValue;
    }
}