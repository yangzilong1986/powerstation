package com.hisun.database;


import com.hisun.exception.HiException;
import com.hisun.util.HiICSProperty;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class HiDataBaseUtil implements DataAccessProvider {
    private DataAccessProvider provider = null;
    private final String DBSUPPORT = "db.provider";
    private final String HIBERNATE = "HIBERNATE";
    private final String IBATIS = "IBATIS";


    public HiDataBaseUtil() {

        String _support = HiICSProperty.getProperty("db.provider");

        if ((_support != null) && (_support.length() != 0)) {

            _support = _support.toUpperCase();

            if (_support.equals("HIBERNATE")) {

                return;

            }

            if (_support.equals("IBATIS")) {

                this.provider = new HiIbatisProvider();

            } else this.provider = new HiDataBaseProvider();

        } else {

            this.provider = new HiDataBaseProvider();

        }

    }


    private DataAccessProvider getDataAccessProvider() {

        return this.provider;

    }


    public HiDataBaseUtil(DataAccessProvider support) {

        setDataAccessProvider(support);

    }


    public void setDataAccessProvider(DataAccessProvider support) {

        this.provider = support;

    }


    public Map call(String name, Object[] inArgs) throws HiException {

        return getDataAccessProvider().call(name, inArgs);

    }


    public Map call(String name, ArrayList inArgs) throws HiException {

        return getDataAccessProvider().call(name, inArgs);

    }


    public void close() {

        getDataAccessProvider().close();

    }


    public void close(Statement stmt) {

        getDataAccessProvider().close(stmt);

    }


    public void close(Statement stmt, ResultSet rs) {

        getDataAccessProvider().close(stmt, rs);

    }


    public void closeAll() {

        getDataAccessProvider().closeAll();

    }


    public void commit() throws HiException {

        getDataAccessProvider().commit();

    }


    public List execQueryBind(String strSql, String[] args) throws HiException {

        return getDataAccessProvider().execQueryBind(strSql, args);

    }


    public List execQueryBind(String strSql, List args) throws HiException {

        return getDataAccessProvider().execQueryBind(strSql, args);

    }


    public int execUpdateBind(String strSql, String[] args) throws HiException {

        return getDataAccessProvider().execUpdateBind(strSql, args);

    }


    public int execUpdateBind(String strSql, List args) throws HiException {

        return getDataAccessProvider().execUpdateBind(strSql, args);

    }


    public List execQuery(String strSql, String arg1) throws HiException {

        return getDataAccessProvider().execQuery(strSql, arg1);

    }


    public List execQuery(String strSql, String arg1, String arg2) throws HiException {

        return getDataAccessProvider().execQuery(strSql, arg1, arg2);

    }


    public List execQuery(String strSql, String arg1, String arg2, String arg3) throws HiException {

        return getDataAccessProvider().execQuery(strSql, arg1, arg2, arg3);

    }


    public List execQuery(String strSql, String arg1, String arg2, String arg3, String arg4) throws HiException {

        return getDataAccessProvider().execQuery(strSql, arg1, arg2, arg3, arg4);

    }


    public List execQuery(String strSql, String arg1, String arg2, String arg3, String arg4, String arg5) throws HiException {

        return getDataAccessProvider().execQuery(strSql, arg1, arg2, arg3, arg4, arg5);

    }


    public List execQuery(String strSql, String[] args) throws HiException {

        return getDataAccessProvider().execQuery(strSql, args);

    }


    public List execQuery(String strSql, List args) throws HiException {

        return getDataAccessProvider().execQuery(strSql, args);

    }


    public List execQuery(String strSql) throws HiException {

        return getDataAccessProvider().execQuery(strSql);

    }


    public List execQuery(String strSql, int limits) throws HiException {

        return getDataAccessProvider().execQuery(strSql, limits);

    }


    public HiResultSet execQuerySQL(String strSql, String arg1) throws HiException {

        return getDataAccessProvider().execQuerySQL(strSql, arg1);

    }


    public HiResultSet execQuerySQL(String strSql, String arg1, String arg2) throws HiException {

        return getDataAccessProvider().execQuerySQL(strSql, arg1, arg2);

    }


    public HiResultSet execQuerySQL(String strSql, String arg1, String arg2, String arg3) throws HiException {

        return getDataAccessProvider().execQuerySQL(strSql, arg1, arg2, arg3);

    }


    public HiResultSet execQuerySQL(String strSql, String arg1, String arg2, String arg3, String arg4) throws HiException {

        return getDataAccessProvider().execQuerySQL(strSql, arg1, arg2, arg3, arg4);

    }


    public HiResultSet execQuerySQL(String strSql, String arg1, String arg2, String arg3, String arg4, String arg5) throws HiException {

        return getDataAccessProvider().execQuerySQL(strSql, arg1, arg2, arg3, arg4, arg5);

    }


    public HiResultSet execQuerySQL(String strSql, List args) throws HiException {

        return getDataAccessProvider().execQuerySQL(strSql, args);

    }


    public HiResultSet execQuerySQL(String strSql, String[] args) throws HiException {

        return getDataAccessProvider().execQuerySQL(strSql, args);

    }


    public HiResultSet execQuerySQL(String strSql) throws HiException {

        return getDataAccessProvider().execQuerySQL(strSql);

    }


    public int execUpdate(String strSql, String arg1) throws HiException {

        return getDataAccessProvider().execUpdate(strSql, arg1);

    }


    public int execUpdate(String strSql, String arg1, String arg2) throws HiException {

        return getDataAccessProvider().execUpdate(strSql, arg1, arg2);

    }


    public int execUpdate(String strSql, String arg1, String arg2, String arg3) throws HiException {

        return getDataAccessProvider().execUpdate(strSql, arg1, arg2, arg3);

    }


    public int execUpdate(String strSql, String arg1, String arg2, String arg3, String arg4) throws HiException {

        return getDataAccessProvider().execUpdate(strSql, arg1, arg2, arg3, arg4);

    }


    public int execUpdate(String strSql, String arg1, String arg2, String arg3, String arg4, String arg5) throws HiException {

        return getDataAccessProvider().execUpdate(strSql, arg1, arg2, arg3, arg4, arg5);

    }


    public int execUpdate(String strSql, String[] args) throws HiException {

        return getDataAccessProvider().execUpdate(strSql, args);

    }


    public int execUpdate(String strSql, List args) throws HiException {

        return getDataAccessProvider().execUpdate(strSql, args);

    }


    public int execUpdate(String strSql) throws HiException {

        return getDataAccessProvider().execUpdate(strSql);

    }


    public Connection getConnection() throws HiException {

        return getDataAccessProvider().getConnection();

    }


    public ArrayList getProcParams(String name) throws SQLException, HiException {

        return getDataAccessProvider().getProcParams(name);

    }


    public HashMap getTableMetaData(String strTableName, Connection conn) throws HiException {

        return getDataAccessProvider().getTableMetaData(strTableName, conn);

    }


    public Connection popConnection() {

        return getDataAccessProvider().popConnection();

    }


    public void pushConnection() {

        getDataAccessProvider().pushConnection();

    }


    public HashMap readRecord(String strSql, String arg1) throws HiException {

        return getDataAccessProvider().readRecord(strSql, arg1);

    }


    public HashMap readRecord(String strSql, String arg1, String arg2) throws HiException {

        return getDataAccessProvider().readRecord(strSql, arg1, arg2);

    }


    public HashMap readRecord(String strSql, String arg1, String arg2, String arg3) throws HiException {

        return getDataAccessProvider().readRecord(strSql, arg1, arg2, arg3);

    }


    public HashMap readRecord(String strSql, String arg1, String arg2, String arg3, String arg4) throws HiException {

        return getDataAccessProvider().readRecord(strSql, arg1, arg2, arg3, arg4);

    }


    public HashMap readRecord(String strSql, String arg1, String arg2, String arg3, String arg4, String arg5) throws HiException {

        return getDataAccessProvider().readRecord(strSql, arg1, arg2, arg3, arg4, arg5);

    }


    public HashMap readRecord(String strSql, String[] args) throws HiException {

        return getDataAccessProvider().readRecord(strSql, args);

    }


    public HashMap readRecord(String strSql, List args) throws HiException {

        return getDataAccessProvider().readRecord(strSql, args);

    }


    public HashMap readRecord(String strSql) throws HiException {

        return getDataAccessProvider().readRecord(strSql);

    }


    public void rollback() throws HiException {

        getDataAccessProvider().rollback();

    }


    public void setDBConnection(String name) {

        getDataAccessProvider().setDBConnection(name);

    }


    public void setDsName(String dsName) {

        getDataAccessProvider().setDsName(dsName);

    }


    public int delete(String statementid) throws HiException {

        return getDataAccessProvider().delete(statementid);

    }


    public int delete(String statementid, Object paramObj) throws HiException {

        return getDataAccessProvider().delete(statementid, paramObj);

    }


    public int delete(String statementid, Object[] paramObjs) throws HiException {

        return getDataAccessProvider().delete(statementid, paramObjs);

    }


    public int delete(String statementid, List paramObjs) throws HiException {

        return getDataAccessProvider().delete(statementid, paramObjs);

    }


    public Object get(String statementid) throws HiException {

        return getDataAccessProvider().get(statementid);

    }


    public Object get(String statementid, Object paramObj) throws HiException {

        return getDataAccessProvider().get(statementid, paramObj);

    }


    public Object get(String statementid, Object paramObj, Object returnObj) throws HiException {

        return getDataAccessProvider().get(statementid, paramObj, returnObj);

    }


    public int insert(String statementid) throws HiException {

        return getDataAccessProvider().insert(statementid);

    }


    public int insert(String statementid, Object obj) throws HiException {

        return getDataAccessProvider().insert(statementid, obj);

    }


    public int insert(String statementid, Object[] objs) throws HiException {

        return getDataAccessProvider().insert(statementid, objs);

    }


    public int insert(String statementid, List objs) throws HiException {

        return getDataAccessProvider().insert(statementid, objs);

    }


    public List list(String statementid) throws HiException {

        return getDataAccessProvider().list(statementid);

    }


    public List list(String statementid, Object paramObj) throws HiException {

        return getDataAccessProvider().list(statementid, paramObj);

    }


    public List list(String statementid, int skip, int max) throws HiException {

        return getDataAccessProvider().list(statementid, skip, max);

    }


    public List list(String statementid, Object paramObj, int skip, int max) throws HiException {

        return getDataAccessProvider().list(statementid, paramObj, skip, max);

    }


    public int update(String statementid) throws HiException {

        return getDataAccessProvider().update(statementid);

    }


    public int update(String statementid, Object paramObj) throws HiException {

        return getDataAccessProvider().update(statementid, paramObj);

    }


    public int update(String statementid, Object[] paramObjs) throws HiException {

        return getDataAccessProvider().update(statementid, paramObjs);

    }


    public int update(String statementid, List paramObjs) throws HiException {

        return getDataAccessProvider().update(statementid, paramObjs);

    }

}