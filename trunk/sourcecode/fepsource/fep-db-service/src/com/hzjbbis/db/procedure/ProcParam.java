package com.hzjbbis.db.procedure;

import org.apache.commons.beanutils.PropertyUtils;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ProcParam {
    public static final int MODE_IN = 0;
    public static final int MODE_OUT = 1;
    public static final int MODE_INOUT = 2;
    private static final Map<String, Integer> typeMap = new HashMap();
    private String pname;
    private int jdbcType = 12;
    private int index;
    private int mode = 0;

    static {
        typeMap.put("CHAR", Integer.valueOf(1));
        typeMap.put("NUMERIC", Integer.valueOf(2));
        typeMap.put("DECIMAL", Integer.valueOf(3));
        typeMap.put("INTEGER", Integer.valueOf(4));
        typeMap.put("SMALLINT", Integer.valueOf(5));
        typeMap.put("FLOAT", Integer.valueOf(6));
        typeMap.put("REAL", Integer.valueOf(7));
        typeMap.put("DOUBLE", Integer.valueOf(8));
        typeMap.put("VARCHAR", Integer.valueOf(12));
        typeMap.put("TINYINT", Integer.valueOf(-6));
        typeMap.put("BIGINT", Integer.valueOf(-5));
        typeMap.put("DATE", Integer.valueOf(91));
        typeMap.put("TIME", Integer.valueOf(92));
        typeMap.put("TIMESTAMP", Integer.valueOf(93));
        typeMap.put("BOOLEAN", Integer.valueOf(16));
    }

    public ProcParam(String paramName, String jtype, String pmode, int index) {
        this.pname = paramName;
        Integer t = (Integer) typeMap.get(jtype.toUpperCase());
        if (t != null) this.jdbcType = t.intValue();
        pmode = pmode.toUpperCase();
        if (pmode.equals("IN")) this.mode = 0;
        else if (pmode.equals("OUT")) this.mode = 1;
        else if (pmode.equals("INOUT")) this.mode = 2;
        this.index = index;
    }

    public String getParamName() {
        return this.pname;
    }

    public int getJdbcType() {
        return this.jdbcType;
    }

    public int getParamIndex() {
        return this.index;
    }

    public void setParamIndex(int i) {
        this.index = i;
    }

    public int getParamMode() {
        return this.mode;
    }

    public void setInputValueByBean(CallableStatement stmt, Object bean) throws SQLException {
        try {
            Object pval = PropertyUtils.getProperty(bean, this.pname);
            setInputValue(stmt, pval);
        } catch (Exception e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    public void setInputValue(CallableStatement stmt, Object val) throws SQLException {
        Calendar cal;
        long lo;
        SimpleDateFormat sdf;
        java.util.Date udate;
        String err;
        switch (this.jdbcType) {
            case 1:
            case 12:
                stmt.setString(this.index, val.toString());
                break;
            case 2:
            case 3:
            case 4:
            case 5:
                if (val instanceof Integer) {
                    stmt.setInt(this.index, ((Integer) val).intValue());
                    return;
                }
                if (val instanceof Long) {
                    stmt.setLong(this.index, ((Long) val).longValue());
                    return;
                }
                if (val instanceof Short) {
                    stmt.setShort(this.index, ((Short) val).shortValue());
                    return;
                }
                if (val instanceof Byte) {
                    stmt.setByte(this.index, ((Byte) val).byteValue());
                    return;
                }
                if (val instanceof String) {
                    stmt.setInt(this.index, Long.valueOf((String) val).intValue());
                    return;
                }
                throw new RuntimeException("传入的参数，与配置不兼容。参数名称[name=" + this.pname + ",index=" + this.index + "],value type=" + val.getClass().getName());
            case 6:
            case 7:
            case 8:
                if (val instanceof Float) {
                    stmt.setFloat(this.index, ((Float) val).floatValue());
                    return;
                }
                if (val instanceof Double) {
                    stmt.setDouble(this.index, ((Double) val).doubleValue());
                    return;
                }
                if (val instanceof Integer) {
                    int v = ((Integer) val).intValue();
                    stmt.setDouble(this.index, v);
                    return;
                }
                if (val instanceof Long) {
                    long v = ((Long) val).longValue();
                    stmt.setDouble(this.index, v);
                    return;
                }
                if (val instanceof String) {
                    double d = Double.parseDouble((String) val);
                    stmt.setDouble(this.index, d);
                    return;
                }

                throw new RuntimeException("传入的参数，与配置不兼容。参数名称[name=" + this.pname + ",index=" + this.index + "],value type=" + val.getClass().getName());
            case -6:
                if (val instanceof Byte) {
                    stmt.setByte(this.index, ((Byte) val).byteValue());
                    return;
                }
                if (val instanceof Integer) {
                    stmt.setByte(this.index, ((Integer) val).byteValue());
                    return;
                }
                if (val instanceof Short) {
                    stmt.setByte(this.index, ((Short) val).byteValue());
                    return;
                }
                if (val instanceof String) {
                    Integer ib = Integer.valueOf(Integer.parseInt((String) val));
                    stmt.setByte(this.index, ib.byteValue());
                    return;
                }

                throw new RuntimeException("传入的参数，与配置不兼容。参数名称[name=" + this.pname + ",index=" + this.index + "],value type=" + val.getClass().getName());
            case -5:
                if (val instanceof Integer) {
                    stmt.setInt(this.index, ((Integer) val).intValue());
                    return;
                }
                if (val instanceof Long) {
                    stmt.setLong(this.index, ((Long) val).longValue());
                    return;
                }
                if (val instanceof String) {
                    Long l = Long.valueOf(Long.parseLong((String) val));
                    stmt.setLong(this.index, l.longValue());
                    return;
                }

                throw new RuntimeException("传入的参数，与配置不兼容。参数名称[name=" + this.pname + ",index=" + this.index + "],value type=" + val.getClass().getName());
            case 91:
                if (val instanceof java.util.Date) {
                    if (val instanceof java.sql.Date) {
                        stmt.setDate(this.index, (java.sql.Date) val);
                        return;
                    }
                    stmt.setDate(this.index, new java.sql.Date(((java.util.Date) val).getTime()));
                    return;
                }
                if (val instanceof Calendar) {
                    cal = (Calendar) val;
                    stmt.setDate(this.index, new java.sql.Date(cal.getTimeInMillis()));
                    return;
                }
                if ((val instanceof Long) || (val.getClass() == Long.TYPE)) {
                    lo = ((Long) val).longValue();
                    stmt.setDate(this.index, new java.sql.Date(lo));
                    return;
                }
                if (!(val instanceof String)) return;
                sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    udate = sdf.parse((String) val);
                    java.sql.Date sqlDate = new java.sql.Date(udate.getTime());
                    stmt.setDate(this.index, sqlDate);
                } catch (ParseException e) {
                    err = "字符串[" + ((String) val) + "]转换为Date错误。参数字段:name=" + this.pname + ",index=" + this.index;
                    throw new RuntimeException(err);
                }
            case 92:
                if (val instanceof java.util.Date) {
                    if (val instanceof Time) {
                        stmt.setTime(this.index, (Time) val);
                        return;
                    }
                    stmt.setTime(this.index, new Time(((java.util.Date) val).getTime()));
                    return;
                }
                if (val instanceof Calendar) {
                    sdf = (Calendar) val;
                    stmt.setTime(this.index, new Time(sdf.getTimeInMillis()));
                    return;
                }
                if ((val instanceof Long) || (val.getClass() == Long.TYPE)) {
                    sdf = ((Long) val).longValue();
                    stmt.setTime(this.index, new Time(sdf));
                    return;
                }
                if (!(val instanceof String)) return;
                sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    e = sdf.parse((String) val);
                    Time sqlDate = new Time(e.getTime());
                    stmt.setTime(this.index, sqlDate);
                } catch (ParseException e) {
                    err = "字符串[" + ((String) val) + "]转换为Date错误。参数字段:name=" + this.pname + ",index=" + this.index;
                    throw new RuntimeException(err);
                }
            case 93:
                if (val instanceof java.util.Date) {
                    if (val instanceof Timestamp) {
                        stmt.setTimestamp(this.index, (Timestamp) val);
                        return;
                    }
                    stmt.setTimestamp(this.index, new Timestamp(((java.util.Date) val).getTime()));
                    return;
                }
                if (val instanceof Calendar) {
                    sdf = (Calendar) val;
                    stmt.setTimestamp(this.index, new Timestamp(sdf.getTimeInMillis()));
                    return;
                }
                if ((val instanceof Long) || (val.getClass() == Long.TYPE)) {
                    sdf = ((Long) val).longValue();
                    stmt.setTimestamp(this.index, new Timestamp(sdf));
                    return;
                }
                if (val instanceof String) {
                    sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    try {
                        e = sdf.parse((String) val);
                        Timestamp sqlDate = new Timestamp(e.getTime());
                        stmt.setTimestamp(this.index, sqlDate);
                    } catch (ParseException e) {
                        err = "字符串[" + ((String) val) + "]转换为Date错误。参数字段:name=" + this.pname + ",index=" + this.index;
                        throw new RuntimeException(err);
                    }
                }

                throw new RuntimeException("传入的参数，与配置不兼容。参数名称[name=" + this.pname + ",index=" + this.index + "],value type=" + val.getClass().getName());
            case 16:
                if (val instanceof Boolean) {
                    stmt.setBoolean(this.index, ((Boolean) val).booleanValue());
                    return;
                }
                if (val instanceof Integer) {
                    int i = ((Integer) val).intValue();
                    stmt.setBoolean(this.index, i != 0);
                    return;
                }
                if (val instanceof String) {
                    String s = ((String) val).toLowerCase();
                    stmt.setBoolean(this.index, s == "true");
                    return;
                }

                throw new RuntimeException("传入的参数，与配置不兼容。参数名称[name=" + this.pname + ",index=" + this.index + "],value type=" + val.getClass().getName());
            default:
                throw new RuntimeException("传入的参数，没有对应的jdbcType。参数名称[name=" + this.pname + ",index=" + this.index + "],value type=" + val.getClass().getName());
        }
    }
}