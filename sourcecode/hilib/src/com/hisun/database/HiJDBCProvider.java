package com.hisun.database;


import com.hisun.exception.HiException;
import com.hisun.exception.HiSQLException;
import com.hisun.hilog4j.HiLog;
import com.hisun.hilog4j.Logger;
import com.hisun.util.HiICSProperty;
import com.hisun.util.HiSemaphore;
import com.hisun.util.HiStringUtils;
import org.apache.commons.lang.StringUtils;

import java.sql.*;
import java.util.*;


public abstract class HiJDBCProvider {
    private static final String SQLSTATE_PRIMARY = "23505";
    protected static Logger log = HiLog.getLogger("database.trc");

    private HiSemaphore dbSemaphore = null;

    protected String _dsName = "_DB_NAME";


    public void setDBConnection(String name) {

    }


    protected Logger getLogger() {

        return log;

    }


    public List execQueryBind(String strSql, String[] args) throws HiException {

        if (log.isDebugEnabled()) {

            log.debug("Query[" + strSql + "]");

        }

        Connection conn = null;

        PreparedStatement stmt = null;

        ResultSet rs = null;

        try {

            conn = getConnection();

            stmt = conn.prepareStatement(strSql);

            for (int i = 0; (args != null) && (i < args.length); ++i) {

                stmt.setObject(i + 1, args[i]);

            }

            ArrayList list = new ArrayList();

            rs = stmt.executeQuery();

            ResultSetMetaData meta = rs.getMetaData();

            int cols = meta.getColumnCount();

            while (rs.next()) {

                values = new HashMap();

                for (int i = 0; i < cols; ++i) {

                    String strColName = meta.getColumnName(i + 1);

                    Object value = rs.getObject(i + 1);

                    if (value == null) {

                        value = "";

                    }

                    if (value instanceof Clob) {

                        Clob tmp = (Clob) value;

                        String value1 = tmp.getSubString(1L, (int) tmp.length());

                        values.put(strColName.toUpperCase(), value1);

                    } else {

                        String value1 = rs.getString(i + 1);

                        values.put(strColName.toUpperCase(), StringUtils.trim(value1));

                    }

                }


                list.add(values);

            }


            HashMap values = Collections.unmodifiableList(list);


            return values;

        } catch (Exception e) {

            if (e instanceof HiException) ;

            throw new HiSQLException("215016", e, strSql);

        } finally {

            close(stmt, rs);

        }

    }


    public List execQueryBind(String strSql, List args) throws HiException {

        if (log.isDebugEnabled()) {

            log.debug("Query[" + strSql + "]");

        }

        Connection conn = null;

        PreparedStatement stmt = null;

        ResultSet rs = null;

        try {

            conn = getConnection();

            stmt = conn.prepareStatement(strSql);

            for (int i = 0; (args != null) && (i < args.size()); ++i) {

                stmt.setObject(i + 1, args.get(i));

            }

            ArrayList list = new ArrayList();

            rs = stmt.executeQuery();

            ResultSetMetaData meta = rs.getMetaData();

            int cols = meta.getColumnCount();

            while (rs.next()) {

                values = new HashMap();

                for (int i = 0; i < cols; ++i) {

                    String strColName = meta.getColumnName(i + 1);

                    Object value = rs.getObject(i + 1);

                    if (value == null) {

                        value = "";

                    }

                    if (value instanceof Clob) {

                        Clob tmp = (Clob) value;

                        String value1 = tmp.getSubString(1L, (int) tmp.length());

                        values.put(strColName.toUpperCase(), value1);

                    } else {

                        String value1 = rs.getString(i + 1);

                        values.put(strColName.toUpperCase(), StringUtils.trim(value1));

                    }

                }


                list.add(values);

            }


            HashMap values = Collections.unmodifiableList(list);


            return values;

        } catch (Exception e) {

            if (e instanceof HiException) ;

            throw new HiSQLException("215016", e, strSql);

        } finally {

            close(stmt, rs);

        }

    }


    public List execQuery(String strSql, String arg1) throws HiException {

        return execQuery(strSql, new String[]{arg1});

    }


    public List execQuery(String strSql, String arg1, String arg2) throws HiException {

        return execQuery(strSql, new String[]{arg1, arg2});

    }


    public List execQueryBind(String strSql, String arg1, String arg2) throws HiException {

        return execQueryBind(strSql, new String[]{arg1, arg2});

    }


    public List execQuery(String strSql, String arg1, String arg2, String arg3) throws HiException {

        return execQuery(strSql, new String[]{arg1, arg2, arg3});

    }


    public List execQuery(String strSql, String arg1, String arg2, String arg3, String arg4) throws HiException {

        return execQuery(strSql, new String[]{arg1, arg2, arg3, arg4});

    }


    public List execQuery(String strSql, String arg1, String arg2, String arg3, String arg4, String arg5) throws HiException {

        return execQuery(strSql, new String[]{arg1, arg2, arg3, arg4, arg5});

    }


    public List execQuery(String strSql, String[] args) throws HiException {

        return execQuery(HiStringUtils.format(strSql, args));

    }


    public List execQuery(String strSql, List args) throws HiException {

        return execQuery(HiStringUtils.format(strSql, args));

    }


    public List execQuery(String strSql) throws HiException {

        return HiDBCache.execQuery(strSql, this);

    }


    protected List internal_execQuery(String strSql) throws HiException {

        if (log.isDebugEnabled()) {

            log.debug("Query[" + strSql + "]");

        }

        Connection conn = null;

        PreparedStatement stmt = null;

        ResultSet rs = null;

        try {

            conn = getConnection();

            stmt = conn.prepareStatement(strSql);

            ArrayList list = new ArrayList();

            rs = stmt.executeQuery();

            ResultSetMetaData meta = rs.getMetaData();

            int cols = meta.getColumnCount();

            while (rs.next()) {

                values = new HashMap();

                for (int i = 0; i < cols; ++i) {

                    String strColName = meta.getColumnName(i + 1);

                    Object value = rs.getObject(i + 1);

                    if (value == null) {

                        value = "";

                    }

                    if (value instanceof Clob) {

                        Clob tmp = (Clob) value;

                        String value1 = tmp.getSubString(1L, (int) tmp.length());

                        values.put(strColName.toUpperCase(), value1);

                    } else {

                        String value1 = rs.getString(i + 1);

                        values.put(strColName.toUpperCase(), StringUtils.trim(value1));

                    }


                }


                list.add(values);

            }


            HashMap values = Collections.unmodifiableList(list);


            return values;

        } catch (Exception e) {

            if (e instanceof HiException) ;

            throw new HiSQLException("215016", e, strSql);

        } finally {

            close(stmt, rs);

        }

    }


    public List execQuery(String strSql, int limits) throws HiException {

        return HiDBCache.execQuery(strSql, limits, this);

    }


    protected List internal_execQuery(String strSql, int limits) throws HiException {

        Connection conn = null;

        PreparedStatement stmt = null;

        ResultSet rs = null;

        int count = 0;

        try {

            conn = getConnection();

            stmt = conn.prepareStatement(strSql);

            ArrayList list = new ArrayList();

            rs = stmt.executeQuery();

            ResultSetMetaData meta = rs.getMetaData();

            int cols = meta.getColumnCount();

            while ((rs.next()) && (((limits == -1) || (count < limits)))) {

                ++count;

                values = new HashMap();

                for (int i = 0; i < cols; ++i) {

                    String strColName = meta.getColumnName(i + 1);

                    Object value = rs.getObject(i + 1);

                    if (value == null) {

                        value = "";

                    }

                    values.put(strColName.toUpperCase(), String.valueOf(value).trim());

                }


                list.add(values);

            }

            if (log.isDebugEnabled()) {

                log.debug("execQuery is end......." + strSql + "[Size=" + list.size() + "]");

            }


            HashMap values = Collections.unmodifiableList(list);


            return values;

        } catch (Exception e) {

            if (e instanceof HiException) ;

            throw new HiSQLException("215016", e, strSql);

        } finally {

            close(stmt, rs);

        }

    }


    public int execUpdateBind(String strSql, String[] args) throws HiException {

        if (log.isDebugEnabled()) {

            log.debug("Update[" + strSql + "]");

        }


        Connection conn = null;

        PreparedStatement stmt = null;

        try {

            conn = getConnection();

            stmt = conn.prepareStatement(strSql);

            for (int i = 0; (args != null) && (i < args.length); ++i) {

                stmt.setObject(i + 1, args[i]);

            }

            int nRow = stmt.executeUpdate();


            int i = nRow;


            return i;

        } catch (SQLException e) {

            int j;

            SQLException ex = e;

            String code = HiICSProperty.getProperty("sql.duppk");

            log.error("SQLState: [" + code + "][" + ex.getSQLState() + "]");

            if (StringUtils.equals(ex.getSQLState(), code)) {

                log.error(strSql, e);

                j = 0;


                close(stmt);

            }

            throw new HiSQLException("215021", e, strSql);

        } finally {

            close(stmt);

        }

    }


    public int execUpdateBind(String strSql, List args) throws HiException {

        if (log.isDebugEnabled()) {

            log.debug("Update[" + strSql + "]");

        }


        Connection conn = null;

        PreparedStatement stmt = null;

        try {

            conn = getConnection();

            stmt = conn.prepareStatement(strSql);

            for (int i = 0; (args != null) && (i < args.size()); ++i) {

                stmt.setObject(i + 1, args.get(i));

            }

            int nRow = stmt.executeUpdate();


            int i = nRow;


            return i;

        } catch (SQLException e) {

            int j;

            SQLException ex = e;

            String code = HiICSProperty.getProperty("sql.duppk");

            log.error("SQLState: [" + code + "][" + ex.getSQLState() + "]");

            if (StringUtils.equals(ex.getSQLState(), code)) {

                log.error(strSql, e);

                j = 0;


                close(stmt);

            }

            throw new HiSQLException("215021", e, strSql);

        } finally {

            close(stmt);

        }

    }


    public int execUpdate(String strSql, String arg1) throws HiException {

        return execUpdate(strSql, new String[]{arg1});

    }


    public int execUpdate(String strSql, String arg1, String arg2) throws HiException {

        return execUpdate(strSql, new String[]{arg1, arg2});

    }


    public int execUpdate(String strSql, String arg1, String arg2, String arg3) throws HiException {

        return execUpdate(strSql, new String[]{arg1, arg2, arg3});

    }


    public int execUpdate(String strSql, String arg1, String arg2, String arg3, String arg4) throws HiException {

        return execUpdate(strSql, new String[]{arg1, arg2, arg3, arg4});

    }


    public int execUpdate(String strSql, String arg1, String arg2, String arg3, String arg4, String arg5) throws HiException {

        return execUpdate(strSql, new String[]{arg1, arg2, arg3, arg4, arg5});

    }


    public int execUpdate(String strSql, String[] args) throws HiException {

        return execUpdate(HiStringUtils.format(strSql, args));

    }


    public int execUpdate(String strSql, List args) throws HiException {

        if (log.isDebugEnabled()) {

            log.debug("Update[" + strSql + "]");

        }


        Connection conn = null;

        PreparedStatement stmt = null;

        try {

            conn = getConnection();

            stmt = conn.prepareStatement(strSql);

            for (int i = 0; i < args.size(); ++i) {

                o = args.get(i);

                if (o instanceof byte[]) {

                    byte[] buf = (byte[]) (byte[]) args.get(i);

                    stmt.setString(i + 1, new String(buf, 0, buf.length));

                } else {

                    stmt.setString(i + 1, args.get(i).toString());

                }

            }

            int nRow = stmt.executeUpdate();

            Object o = nRow;


            return o;

        } catch (SQLException e) {

            int i;

            SQLException ex = e;

            String code = HiICSProperty.getProperty("sql.duppk");

            if (StringUtils.equals(ex.getSQLState(), code)) {

                log.error(strSql, e);

                i = 0;


                close(stmt);

            }

            throw new HiSQLException("215021", e, strSql);

        } finally {

            close(stmt);

        }

    }


    public int execUpdate(String strSql) throws HiException {

        return HiDBCache.execUpdate(strSql, this);

    }


    protected int internal_execUpdate(String strSql) throws HiException {

        if (log.isDebugEnabled()) {

            log.debug("Update[" + strSql + "]");

        }


        Connection conn = null;

        PreparedStatement stmt = null;

        try {

            conn = getConnection();

            stmt = conn.prepareStatement(strSql);

            int nRow = stmt.executeUpdate();


            int i = nRow;


            return i;

        } catch (SQLException e) {

            int j;

            SQLException ex = e;

            String code = HiICSProperty.getProperty("sql.duppk");

            log.error("SQLState: [" + code + "][" + ex.getSQLState() + "]");

            if (StringUtils.equals(ex.getSQLState(), code)) {

                log.error(strSql, e);

                j = 0;


                close(stmt);

            }

            throw new HiSQLException("215021", e, strSql);

        } finally {

            close(stmt);

        }

    }


    public HashMap readRecord(String strSql, String arg1) throws HiException {

        return readRecord(strSql, new String[]{arg1});

    }


    public HashMap readRecord(String strSql, String arg1, String arg2) throws HiException {

        return readRecord(strSql, new String[]{arg1, arg2});

    }


    public HashMap readRecord(String strSql, String arg1, String arg2, String arg3) throws HiException {

        return readRecord(strSql, new String[]{arg1, arg2, arg3});

    }


    public HashMap readRecord(String strSql, String arg1, String arg2, String arg3, String arg4) throws HiException {

        return readRecord(strSql, new String[]{arg1, arg2, arg3, arg4});

    }


    public HashMap readRecord(String strSql, String arg1, String arg2, String arg3, String arg4, String arg5) throws HiException {

        return readRecord(strSql, new String[]{arg1, arg2, arg3, arg4, arg5});

    }


    public HashMap readRecord(String strSql, String[] args) throws HiException {

        return readRecord(HiStringUtils.format(strSql, args));

    }


    public HashMap readRecord(String strSql, List args) throws HiException {

        return readRecord(HiStringUtils.format(strSql, args));

    }


    public HashMap readRecord(String strSql) throws HiException {

        return HiDBCache.readRecord(strSql, this);

    }


    protected HashMap internal_readRecord(String strSql) throws HiException {

        Connection conn = null;

        PreparedStatement stmt = null;

        ResultSet rs = null;

        try {

            conn = getConnection();

            stmt = conn.prepareStatement(strSql);

            HashMap values = new HashMap();

            rs = stmt.executeQuery();

            ResultSetMetaData meta = rs.getMetaData();

            int cols = meta.getColumnCount();

            int index = 0;

            while (rs.next()) {

                for (i = 0; i < cols; ++i) {

                    String strColName = meta.getColumnName(i + 1);

                    Object value = rs.getObject(i + 1);

                    values.put(strColName.toUpperCase(), String.valueOf(value).trim());

                }


                ++index;

            }

            if (index > 1) {

                throw new HiException("215025", "检索数据超出范围");

            }


            int i = values;


            return i;

        } catch (Exception e) {

            if (e instanceof HiException) ;

            throw new HiSQLException("215025", e, strSql);

        } finally {

            close(stmt, rs);

        }

    }


    public HiResultSet execQuerySQL(String strSql, String arg1) throws HiException {

        return execQuerySQL(strSql, new String[]{arg1});

    }


    public HiResultSet execQuerySQL(String strSql, String arg1, String arg2) throws HiException {

        return execQuerySQL(strSql, new String[]{arg1, arg2});

    }


    public HiResultSet execQuerySQL(String strSql, String arg1, String arg2, String arg3) throws HiException {

        return execQuerySQL(strSql, new String[]{arg1, arg2, arg3});

    }


    public HiResultSet execQuerySQL(String strSql, String arg1, String arg2, String arg3, String arg4) throws HiException {

        return execQuerySQL(strSql, new String[]{arg1, arg2, arg3, arg4});

    }


    public HiResultSet execQuerySQL(String strSql, String arg1, String arg2, String arg3, String arg4, String arg5) throws HiException {

        return execQuerySQL(strSql, new String[]{arg1, arg2, arg3, arg4, arg5});

    }


    public HiResultSet execQuerySQL(String strSql, List args) throws HiException {

        return execQuerySQL(HiStringUtils.format(strSql, args));

    }


    public HiResultSet execQuerySQL(String strSql, String[] args) throws HiException {

        return execQuerySQL(HiStringUtils.format(strSql, args));

    }


    public HiResultSet execQuerySQL(String strSql) throws HiException {

        return HiDBCache.execQuerySQL(strSql, this);

    }


    protected HiResultSet internal_execQuerySQL(String strSql) throws HiException {

        if (log.isDebugEnabled()) {

            log.debug("Query[" + strSql + "]");

        }

        Connection conn = null;

        PreparedStatement stmt = null;

        ResultSet rs = null;

        HiResultSet hrs = null;

        try {

            conn = getConnection();

            stmt = conn.prepareStatement(strSql);

            rs = stmt.executeQuery();

            hrs = new HiResultSet(rs);

        } catch (SQLException e) {

        } finally {

            close(stmt, rs);

        }

        return hrs;

    }


    public abstract void pushConnection();


    public abstract Connection popConnection();


    public void setDsName(String dsName) {

        this._dsName = dsName;

    }


    public abstract Connection getConnection() throws HiException;


    public HashMap getTableMetaData(String strTableName, Connection conn) throws HiException {

        Statement stmt = null;

        ResultSet rs = null;

        try {

            strTableName = strTableName.toUpperCase();


            stmt = conn.createStatement();

            rs = stmt.executeQuery("SELECT * FROM " + strTableName + " WHERE 1=0");


            ResultSetMetaData me = rs.getMetaData();

            int nColumns = me.getColumnCount();

            HashMap colInfos = new HashMap();

            for (int i = 0; i < nColumns; ++i) {

                String strColumnName = me.getColumnName(i + 1);

                colInfos.put(strColumnName, me.getColumnTypeName(i + 1));

            }


            i = colInfos;


            return i;

        } catch (Exception e) {

        } finally {

            try {

                if (rs != null) rs.close();

                if (stmt != null) stmt.close();

            } catch (Exception e) {

            }

        }

    }


    public abstract void rollback() throws HiException;


    public abstract void close();


    public abstract void closeAll();


    public void close(Statement stmt) {

        try {

            if (stmt != null) stmt.close();

        } catch (Exception e) {

        }

    }


    public void close(Statement stmt, ResultSet rs) {

        try {

            if (rs != null) rs.close();

            if (stmt != null) stmt.close();

        } catch (Exception e) {

        }

    }


    public abstract void commit() throws HiException;


    public Map call(String name, Object[] inArgs) throws HiException {

        CallableStatement stmt = null;

        ResultSet rs = null;

        LinkedHashMap outValue = null;

        name = name.toUpperCase();

        try {

            HiProcedureParam param;

            Connection conn = getConnection();


            ArrayList params = getProcParams(name);

            String cmd = buildCallStatment(name, params.size());


            if (log.isDebugEnabled()) {

                log.debug("CallProc:[" + cmd + "]");

            }

            stmt = conn.prepareCall(cmd);


            int inNum = (inArgs != null) ? inArgs.length : 0;

            for (int i = 0; i < inNum; ++i) {

                if (log.isDebugEnabled()) {

                    log.debug("arg[" + i + "]:[" + inArgs[i] + "]");

                }

                stmt.setObject(i + 1, inArgs[i]);

            }


            for (i = 0; i < params.size(); ++i) {

                param = (HiProcedureParam) params.get(i);

                if (log.isDebugEnabled()) {

                    log.debug("CallProc:[" + param.name + "]:[" + param.type + "]:[" + param.out + "]");

                }


                if (!(param.out)) {

                    continue;

                }


                if (param.isCursor()) stmt.registerOutParameter(i + 1, -10);

                else {

                    stmt.registerOutParameter(i + 1, 12);

                }

            }


            stmt.execute();


            if (params.size() > 0) {

                outValue = new LinkedHashMap();

            }


            for (i = 0; i < params.size(); ++i) {

                param = (HiProcedureParam) params.get(i);

                if (!(param.out)) {

                    continue;

                }

                Object o = stmt.getObject(i + 1);

                if (o == null) {

                    outValue.put(param.name, "");

                } else {

                    if (o instanceof ResultSet) {

                        log.debug("CURSOR:[" + param.name + "]");

                        o = resultSetToHashMap((ResultSet) o);

                    }

                    log.debug("[" + param.name + "]:[" + o + "]");

                    outValue.put(param.name, o);
                }

            }

            i = Collections.unmodifiableMap(outValue);


            return i;
        } catch (Exception e) {
        } finally {
            close(stmt, rs);

        }

    }


    public Map call(String name, ArrayList inArgs) throws HiException {

        return call(name, (inArgs != null) ? inArgs.toArray() : null);

    }


    private ArrayList resultSetToHashMap(ResultSet rs) throws SQLException {

        ArrayList list = new ArrayList();

        while (rs.next()) {

            log.debug("resultSetToHashMap01");

            ResultSetMetaData rsmt = rs.getMetaData();

            HashMap map = new HashMap();

            for (int j = 0; j < rsmt.getColumnCount(); ++j) {

                String name1 = rsmt.getColumnName(j + 1);

                Object o1 = rs.getObject(name1);

                log.debug("resultSetToHashMap:[" + name1.toUpperCase() + "]:[" + o1 + "]");


                if (o1 == null) map.put(name1.toUpperCase(), "");

                else {

                    map.put(name1.toUpperCase(), o1);

                }

            }

            list.add(map);

        }

        return list;

    }


    public ArrayList getProcParams(String name) throws SQLException, HiException {

        ResultSet rs = null;

        try {

            DatabaseMetaData dbmd = getConnection().getMetaData();

            rs = dbmd.getProcedureColumns(null, null, name.toUpperCase(), null);

            ArrayList params = new ArrayList();

            while (rs.next()) {

                param = new HiProcedureParam(rs.getString("COLUMN_NAME"), rs.getInt("COLUMN_TYPE"), rs.getString("TYPE_NAME"));


                params.add(new HiProcedureParam(rs.getString("COLUMN_NAME"), rs.getInt("COLUMN_TYPE"), rs.getString("TYPE_NAME")));


                if (log.isDebugEnabled()) {

                    log.debug("store procedure column:[" + param.name + "][" + param.type + "][" + param.out + "]");

                }

            }


            HiProcedureParam param = params;


            return param;
        } finally {
            rs.close();

        }

    }


    public static boolean isOutParam(String type) {

        return ((type.equals("4")) || (type.equals("3")));

    }


    public static boolean isOutParam(int type) {

        return ((type == 4) || (type == 3));

    }


    private String buildCallStatment(String name, int paramNum) {

        StringBuffer buf = new StringBuffer();

        buf.append("{ call ");

        buf.append(name);

        buf.append("(");

        for (int i = 0; i < paramNum; ++i) {

            buf.append("?");

            if (i < paramNum - 1) {

                buf.append(",");

            }

        }

        buf.append(")}");

        return buf.toString();

    }

}