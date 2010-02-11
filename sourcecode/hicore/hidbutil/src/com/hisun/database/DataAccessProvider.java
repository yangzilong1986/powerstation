package com.hisun.database;

import com.hisun.exception.HiException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract interface DataAccessProvider {
    public abstract void setDBConnection(String paramString);

    public abstract List execQueryBind(String paramString, String[] paramArrayOfString) throws HiException;

    public abstract List execQueryBind(String paramString, List paramList) throws HiException;

    public abstract int execUpdateBind(String paramString, String[] paramArrayOfString) throws HiException;

    public abstract int execUpdateBind(String paramString, List paramList) throws HiException;

    public abstract List execQuery(String paramString1, String paramString2) throws HiException;

    public abstract List execQuery(String paramString1, String paramString2, String paramString3) throws HiException;

    public abstract List execQuery(String paramString1, String paramString2, String paramString3, String paramString4) throws HiException;

    public abstract List execQuery(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5) throws HiException;

    public abstract List execQuery(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6) throws HiException;

    public abstract List execQuery(String paramString, String[] paramArrayOfString) throws HiException;

    public abstract List execQuery(String paramString, List paramList) throws HiException;

    public abstract List execQuery(String paramString) throws HiException;

    public abstract List execQuery(String paramString, int paramInt) throws HiException;

    public abstract int execUpdate(String paramString1, String paramString2) throws HiException;

    public abstract int execUpdate(String paramString1, String paramString2, String paramString3) throws HiException;

    public abstract int execUpdate(String paramString1, String paramString2, String paramString3, String paramString4) throws HiException;

    public abstract int execUpdate(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5) throws HiException;

    public abstract int execUpdate(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6) throws HiException;

    public abstract int execUpdate(String paramString, String[] paramArrayOfString) throws HiException;

    public abstract int execUpdate(String paramString, List paramList) throws HiException;

    public abstract int execUpdate(String paramString) throws HiException;

    public abstract HashMap readRecord(String paramString1, String paramString2) throws HiException;

    public abstract HashMap readRecord(String paramString1, String paramString2, String paramString3) throws HiException;

    public abstract HashMap readRecord(String paramString1, String paramString2, String paramString3, String paramString4) throws HiException;

    public abstract HashMap readRecord(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5) throws HiException;

    public abstract HashMap readRecord(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6) throws HiException;

    public abstract HashMap readRecord(String paramString, String[] paramArrayOfString) throws HiException;

    public abstract HashMap readRecord(String paramString, List paramList) throws HiException;

    public abstract HashMap readRecord(String paramString) throws HiException;

    public abstract HiResultSet execQuerySQL(String paramString1, String paramString2) throws HiException;

    public abstract HiResultSet execQuerySQL(String paramString1, String paramString2, String paramString3) throws HiException;

    public abstract HiResultSet execQuerySQL(String paramString1, String paramString2, String paramString3, String paramString4) throws HiException;

    public abstract HiResultSet execQuerySQL(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5) throws HiException;

    public abstract HiResultSet execQuerySQL(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6) throws HiException;

    public abstract HiResultSet execQuerySQL(String paramString, List paramList) throws HiException;

    public abstract HiResultSet execQuerySQL(String paramString, String[] paramArrayOfString) throws HiException;

    public abstract HiResultSet execQuerySQL(String paramString) throws HiException;

    public abstract void pushConnection();

    public abstract Connection popConnection();

    public abstract void setDsName(String paramString);

    public abstract Connection getConnection() throws HiException;

    public abstract HashMap getTableMetaData(String paramString, Connection paramConnection) throws HiException;

    public abstract void rollback() throws HiException;

    public abstract void close();

    public abstract void closeAll();

    public abstract void close(Statement paramStatement);

    public abstract void close(Statement paramStatement, ResultSet paramResultSet);

    public abstract void commit() throws HiException;

    public abstract Map call(String paramString, Object[] paramArrayOfObject) throws HiException;

    public abstract Map call(String paramString, ArrayList paramArrayList) throws HiException;

    public abstract ArrayList getProcParams(String paramString) throws SQLException, HiException;

    public abstract int insert(String paramString) throws HiException;

    public abstract int insert(String paramString, Object paramObject) throws HiException;

    public abstract int insert(String paramString, Object[] paramArrayOfObject) throws HiException;

    public abstract int insert(String paramString, List paramList) throws HiException;

    public abstract int update(String paramString) throws HiException;

    public abstract int update(String paramString, Object paramObject) throws HiException;

    public abstract int update(String paramString, Object[] paramArrayOfObject) throws HiException;

    public abstract int update(String paramString, List paramList) throws HiException;

    public abstract int delete(String paramString) throws HiException;

    public abstract int delete(String paramString, Object paramObject) throws HiException;

    public abstract int delete(String paramString, Object[] paramArrayOfObject) throws HiException;

    public abstract int delete(String paramString, List paramList) throws HiException;

    public abstract Object get(String paramString) throws HiException;

    public abstract Object get(String paramString, Object paramObject) throws HiException;

    public abstract Object get(String paramString, Object paramObject1, Object paramObject2) throws HiException;

    public abstract List list(String paramString) throws HiException;

    public abstract List list(String paramString, Object paramObject) throws HiException;

    public abstract List list(String paramString, int paramInt1, int paramInt2) throws HiException;

    public abstract List list(String paramString, Object paramObject, int paramInt1, int paramInt2) throws HiException;
}