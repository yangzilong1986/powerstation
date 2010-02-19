package com.hzjbbis.db.resultmap;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class ResultMapper<T> {
    private static final Logger log = Logger.getLogger(ResultMapper.class);
    private String resultClass;
    private List<ColumnMapper> columnMapper = new ArrayList();
    private String columnSequence;
    private Class<T> resultClassObject;

    public T mapOneRow(ResultSet rs) {
        try {
            Object dest = this.resultClassObject.newInstance();
            mapRow2Object(rs, dest);
            return dest;
        } catch (Exception exp) {
            log.error("把记录集行映射到对象异常：" + exp.getLocalizedMessage(), exp);
        }
        return null;
    }

    public List<T> mapAllRows(ResultSet rs) {
        List objList = new ArrayList();
        try {
            while (rs.next()) {
                Object result = mapOneRow(rs);
                if (result != null) objList.add(result);
            }
        } catch (SQLException e) {
            log.error(e.getLocalizedMessage(), e);
        }
        return objList;
    }

    private void mapRow2Object(ResultSet rs, T dest) throws SQLException, IllegalArgumentException, IllegalAccessException {
        try {
            for (Iterator localIterator = this.columnMapper.iterator(); localIterator.hasNext();) {
                Class clz;
                ColumnMapper item = (ColumnMapper) localIterator.next();
                int index = item.getIndex();
                if (index > 0) {
                    clz = item.method.getParameterTypes()[0];
                    if (clz == String.class) {
                        item.method.invoke(dest, new Object[]{rs.getString(index)});
                    } else if ((clz == Integer.TYPE) || (clz == Integer.class)) {
                        item.method.invoke(dest, new Object[]{Integer.valueOf(rs.getInt(index))});
                    } else if ((clz == Long.TYPE) || (clz == Long.class)) {
                        item.method.invoke(dest, new Object[]{Long.valueOf(rs.getLong(index))});
                    } else if (clz == Date.class) {
                        item.method.invoke(dest, new Object[]{rs.getDate(index)});
                    } else if ((clz == Short.TYPE) || (clz == Short.class)) {
                        item.method.invoke(dest, new Object[]{Short.valueOf(rs.getShort(index))});
                    } else if ((clz == Byte.TYPE) || (clz == Byte.class)) {
                        item.method.invoke(dest, new Object[]{Byte.valueOf(rs.getByte(index))});
                    } else if ((clz == Character.TYPE) || (clz == Character.class)) {
                        item.method.invoke(dest, new Object[]{Character.valueOf((char) rs.getByte(index))});
                    } else if ((clz == Boolean.TYPE) || (clz == Boolean.class)) {
                        item.method.invoke(dest, new Object[]{Boolean.valueOf(rs.getBoolean(index))});
                    } else if ((clz == Double.TYPE) || (clz == Double.class))
                        item.method.invoke(dest, new Object[]{Double.valueOf(rs.getDouble(index))});
                    else try {
                            item.method.invoke(dest, new Object[]{rs.getString(index)});
                        } catch (Exception exp) {
                            log.warn("对象属性不能设置[" + item.getProperty() + "]，转换类型错误:" + exp.getLocalizedMessage(), exp);
                        }
                } else {
                    clz = item.method.getParameterTypes()[0];
                    String column = item.getColumn();
                    if (clz == String.class) {
                        item.method.invoke(dest, new Object[]{rs.getString(column)});
                    } else if ((clz == Integer.TYPE) || (clz == Integer.class)) {
                        item.method.invoke(dest, new Object[]{Integer.valueOf(rs.getInt(column))});
                    } else if ((clz == Long.TYPE) || (clz == Long.class)) {
                        item.method.invoke(dest, new Object[]{Long.valueOf(rs.getLong(column))});
                    } else if (clz == Date.class) {
                        item.method.invoke(dest, new Object[]{rs.getDate(column)});
                    } else if ((clz == Short.TYPE) || (clz == Short.class)) {
                        item.method.invoke(dest, new Object[]{Short.valueOf(rs.getShort(column))});
                    } else if ((clz == Byte.TYPE) || (clz == Byte.class)) {
                        item.method.invoke(dest, new Object[]{Byte.valueOf(rs.getByte(column))});
                    } else if ((clz == Character.TYPE) || (clz == Character.class)) {
                        item.method.invoke(dest, new Object[]{Character.valueOf((char) rs.getByte(column))});
                    } else if ((clz == Boolean.TYPE) || (clz == Boolean.class)) {
                        item.method.invoke(dest, new Object[]{Boolean.valueOf(rs.getBoolean(column))});
                    } else if ((clz == Double.TYPE) || (clz == Double.class))
                        item.method.invoke(dest, new Object[]{Double.valueOf(rs.getDouble(column))});
                    else try {
                            item.method.invoke(dest, new Object[]{rs.getString(column)});
                        } catch (Exception exp) {
                            log.warn("对象属性不能设置[" + item.getProperty() + "]，转换类型错误:" + exp.getLocalizedMessage(), exp);
                        }
                }
            }
        } catch (InvocationTargetException exp) {
            log.error(exp.getLocalizedMessage(), exp);
        }
    }

    public String getResultClass() {
        return this.resultClass;
    }

    public void setResultClass(String resultClass) {
        this.resultClass = resultClass;
        if (this.columnMapper != null) populateMethod();
    }

    public List<ColumnMapper> getColumnMapper() {
        return this.columnMapper;
    }

    public void setColumnMapper(List<ColumnMapper> columnMapper) {
        this.columnMapper = columnMapper;
        if ((this.resultClass != null) && (this.resultClass.length() > 1)) populateMethod();
    }

    private void populateMethod() {
        try {
            this.resultClassObject = Class.forName(this.resultClass);
            Method[] allMethods = this.resultClassObject.getMethods();
            Map methods = new HashMap();
            for (Method m : allMethods) {
                if (m.getName().startsWith("set")) methods.put(m.getName(), m);
            }
            for (ColumnMapper column : this.columnMapper) {
                String name = column.getProperty();
                name = "set" + name.substring(0, 1).toUpperCase() + name.substring(1);
                Method method = (Method) methods.get(name);
                if (method == null) {
                    String errInfo = "JdbcBaseDao的对象属性与记录集对应关系配置项错误，属性不存在:" + column.getProperty();
                    log.error(errInfo);
                    throw new RuntimeException(errInfo);
                }
                column.method = method;
            }
        } catch (ClassNotFoundException exp) {
            log.error(exp.getLocalizedMessage(), exp);
        } catch (LinkageError exp) {
            log.error(exp.getLocalizedMessage(), exp);
        }
    }

    public void setColumnSequence(String columnSequence) {
        this.columnSequence = columnSequence;
        this.columnSequence.trim();
        String[] cols = this.columnSequence.split(",");
        int index = 1;
        for (String cstr : cols) {
            cstr = StringUtils.strip(cstr);
            if (cstr.length() == 0) continue;
            ColumnMapper cm = new ColumnMapper();
            cm.setProperty(cstr);
            cm.setIndex(index++);
            this.columnMapper.add(cm);
        }

        if ((this.resultClass != null) && (this.resultClass.length() > 1)) populateMethod();
    }
}