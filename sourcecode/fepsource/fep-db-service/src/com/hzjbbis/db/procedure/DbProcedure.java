package com.hzjbbis.db.procedure;

import com.hzjbbis.db.resultmap.ResultMapper;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DbProcedure {
    private static final Logger log = Logger.getLogger(DbProcedure.class);
    private DataSource dataSource;
    private List<ProcParam> outParams = new ArrayList();
    private List<ProcParam> inParams = new ArrayList();
    private String callString;
    private boolean initialized = false;
    private final Object lock = new Object();

    public DbProcedure() {
    }

    public DbProcedure(DataSource ds, String sqlConfig) {
        this.dataSource = ds;
        this.callString = sqlConfig;
        checkInitialize();
    }

    private void initialize(String sqlConfig) {
        try {
            this.callString = compile(sqlConfig);
            if (log.isDebugEnabled()) log.debug("call string=" + this.callString);
        } catch (Exception e) {
            log.error("sql编译错误:" + sqlConfig, e);
        }
    }

    private void checkInitialize() {
        synchronized (this.lock) {
            if (!(this.initialized)) {
                this.initialized = true;
                initialize(this.callString);
            }
        }
    }

    private boolean isPrimitive(Object obj) {
        Class clz = obj.getClass();
        if (clz.isPrimitive()) return true;
        if ((clz == String.class) || (clz == Integer.class) || (clz == Long.class) || (clz == Short.class) || (clz == Character.class) || (clz == Byte.class) || (clz == Double.class) || (clz == Float.class)) {
            return true;
        }
        return (!(obj instanceof Date));
    }

    public Object executeFunction(Object[] args) throws SQLException {
        checkInitialize();
        long time = System.currentTimeMillis();
        Connection con = DataSourceUtils.getConnection(this.dataSource);
        long time1 = System.currentTimeMillis();
        if (log.isDebugEnabled()) log.debug("executeFunction取连接时间=" + (time1 - time));
        try {
            int i;
            CallableStatement procStmt = con.prepareCall(this.callString);
            for (ProcParam pout : this.outParams) {
                procStmt.registerOutParameter(pout.getParamIndex(), pout.getJdbcType());
            }

            int i = 0;
            if (args.length == 1) {
                i = (isPrimitive(args[0])) ? 0 : 1;
            }
            if ((i == 0) && (this.inParams.size() > args.length)) {
                String msg = "存储过程需要参数个数=" + this.inParams.size() + ",实际传入个数=" + args.length;
                log.error(msg);
                throw new RuntimeException(msg);
            }

            for (int i = 0; i < this.inParams.size(); ++i) {
                ProcParam pin = (ProcParam) this.inParams.get(i);
                if (i != 0) pin.setInputValueByBean(procStmt, args[0]);
                else pin.setInputValue(procStmt, args[i]);
            }
            procStmt.execute();
            Object ret = procStmt.getObject(1);
            procStmt.close();
            return ret;
        } catch (SQLException e) {
            throw e;
        } finally {
            DataSourceUtils.releaseConnection(con, this.dataSource);
        }
    }

    public boolean execute(Object[] args) throws SQLException {
        checkInitialize();
        long time = System.currentTimeMillis();
        Connection con = DataSourceUtils.getConnection(this.dataSource);
        long time1 = System.currentTimeMillis();
        if (log.isDebugEnabled()) log.debug("execute取连接时间=" + (time1 - time));
        try {
            int i;
            CallableStatement procStmt = con.prepareCall(this.callString);
            for (ProcParam pout : this.outParams) {
                procStmt.registerOutParameter(pout.getParamIndex(), pout.getJdbcType());
            }

            int i = 0;
            if (args.length == 1) {
                i = (isPrimitive(args[0])) ? 0 : 1;
            }
            if ((i == 0) && (this.inParams.size() > args.length)) {
                String msg = "存储过程需要参数个数=" + this.inParams.size() + ",实际传入个数=" + args.length;
                log.error(msg);
                throw new RuntimeException(msg);
            }

            for (int i = 0; i < this.inParams.size(); ++i) {
                ProcParam pin = (ProcParam) this.inParams.get(i);
                if (i != 0) pin.setInputValueByBean(procStmt, args[0]);
                else pin.setInputValue(procStmt, args[i]);
            }
            boolean ret = procStmt.execute();
            procStmt.close();
            return ret;
        } catch (SQLException e) {
        } finally {
            DataSourceUtils.releaseConnection(con, this.dataSource);
            if (log.isDebugEnabled()) log.debug("存储过程执行完毕");
        }
    }

    private ResultSet executeResultSet(Object[] args) throws SQLException {
        checkInitialize();
        long time = System.currentTimeMillis();
        Connection con = DataSourceUtils.getConnection(this.dataSource);
        long time1 = System.currentTimeMillis();
        if (log.isDebugEnabled()) log.debug("executeResultSet取连接时间=" + (time1 - time));
        try {
            int i;
            CallableStatement procStmt = con.prepareCall(this.callString);
            for (ProcParam pout : this.outParams) {
                procStmt.registerOutParameter(pout.getParamIndex(), pout.getJdbcType());
            }

            int i = 0;
            if (args.length == 1) {
                i = (isPrimitive(args[0])) ? 0 : 1;
            }
            if ((i == 0) && (this.inParams.size() > args.length)) {
                String msg = "存储过程需要参数个数=" + this.inParams.size() + ",实际传入个数=" + args.length;
                log.error(msg);
                throw new RuntimeException(msg);
            }

            for (int i = 0; i < this.inParams.size(); ++i) {
                ProcParam pin = (ProcParam) this.inParams.get(i);
                pin.setInputValue(procStmt, args[i]);
            }
            procStmt.execute();
            ResultSet rs = procStmt.getResultSet();
            return rs;
        } catch (SQLException e) {
            throw e;
        } finally {
            DataSourceUtils.releaseConnection(con, this.dataSource);
        }
    }

    public List<?> executeList(ResultMapper<?> rm, Object[] args) throws SQLException {
        ResultSet rs = executeResultSet(args);
        return rm.mapAllRows(rs);
    }

    public int executeFunctionInt(Object[] args) throws SQLException {
        Object ret = executeFunction(args);
        if (ret instanceof String) {
            Long lv = Long.valueOf(Long.parseLong((String) ret));
            return lv.intValue();
        }
        if (ret instanceof Integer) return ((Integer) ret).intValue();
        if (ret instanceof Long) return ((Long) ret).intValue();
        if (ret instanceof Short) {
            return ((Short) ret).shortValue();
        }
        throw new RuntimeException("返回类型不能转换到int");
    }

    public long executeFunctionLong(Object[] args) throws SQLException {
        Object ret = executeFunction(args);
        if (ret instanceof String) return Long.parseLong((String) ret);
        if (ret instanceof Integer) return ((Integer) ret).intValue();
        if (ret instanceof Long) return ((Long) ret).longValue();
        if (ret instanceof Short) {
            return ((Short) ret).shortValue();
        }
        throw new RuntimeException("返回类型不能转换到long");
    }

    public String executeFunctionString(Object[] args) throws SQLException {
        Object ret = executeFunction(args);
        if (ret instanceof String) {
            return ((String) ret);
        }
        return ret.toString();
    }

    private String compile(String sqlConfig) {
        sqlConfig = StringUtils.strip(sqlConfig);
        int index = sqlConfig.indexOf("{");
        if (index > 0) sqlConfig = sqlConfig.substring(index);
        index = sqlConfig.indexOf("#");
        if (index > 0) {
            int lp = sqlConfig.indexOf("(");
            int rp = sqlConfig.indexOf(")");
            StringBuffer sb = new StringBuffer();

            String lsql = sqlConfig.substring(0, lp + 1);
            String sparam = sqlConfig.substring(lp + 1, rp);

            int iq = lsql.indexOf("?");
            int pindex = 1;
            if (iq > 0) {
                sb.append("{?");
                int ieq = lsql.indexOf("=");
                sb.append(lsql.substring(ieq));
                String jtype = lsql.substring(iq + 1, ieq);
                int escapteIndex = 0;
                for (escapteIndex = 0; escapteIndex < jtype.length(); ++escapteIndex) {
                    char c = lsql.charAt(escapteIndex);
                    if ((c != ':') && (c != '#')) {
                        jtype = jtype.substring(escapteIndex);
                        break;
                    }
                }
                ProcParam retParam = new ProcParam("", jtype, "OUT", pindex++);
                this.outParams.add(retParam);
            } else {
                sb.append(lsql);
            }
            String[] parts = sparam.split(",");
            for (int i = 0; i < parts.length; i += 3) {
                String name = parts[i].substring(1);
                String jtype = parts[(i + 1)].substring("jdbcType=".length());
                String pmode = parts[(i + 2)].substring(0, parts[(i + 2)].length() - 1);
                ProcParam param = new ProcParam(name, jtype, pmode, pindex++);
                if (param.getParamMode() == 0) {
                    if (this.inParams.size() > 0) sb.append(",?");
                    else sb.append("?");
                    this.inParams.add(param);
                } else if (param.getParamMode() == 1) {
                    this.outParams.add(param);
                } else if (param.getParamMode() == 2) {
                    if (this.inParams.size() > 0) sb.append(",?");
                    else sb.append("?");
                    this.inParams.add(param);
                    ProcParam paramOut = new ProcParam(name, jtype, pmode, param.getParamIndex());
                    this.outParams.add(paramOut);
                } else {
                    String errinfo = "Ibatis格式存储过程配置错误，mode=" + pmode;
                    log.error(errinfo);
                    throw new RuntimeException(errinfo);
                }
            }

            sb.append(")}");
            return sb.toString();
        }
        return sqlConfig;
    }

    public void setOutParams(List<ProcParam> outParams) {
        this.outParams = outParams;
    }

    public void setInParams(List<ProcParam> inParams) {
        this.inParams = inParams;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setCallString(String callString) {
        this.callString = callString;
    }
}