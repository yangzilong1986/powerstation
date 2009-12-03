/*     */ package com.hisun.database;
/*     */ 
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.util.HiICSProperty;
/*     */ import java.sql.Connection;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Statement;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class HiDataBaseUtil
/*     */   implements DataAccessProvider
/*     */ {
/*  22 */   private DataAccessProvider provider = null;
/*  23 */   private final String DBSUPPORT = "db.provider";
/*  24 */   private final String HIBERNATE = "HIBERNATE";
/*  25 */   private final String IBATIS = "IBATIS";
/*     */ 
/*     */   public HiDataBaseUtil() {
/*  28 */     String _support = HiICSProperty.getProperty("db.provider");
/*  29 */     if ((_support != null) && (_support.length() != 0)) {
/*  30 */       _support = _support.toUpperCase();
/*  31 */       if (_support.equals("HIBERNATE")) {
/*     */         return;
/*     */       }
/*  34 */       if (_support.equals("IBATIS")) {
/*  35 */         this.provider = new HiIbatisProvider();
/*     */       }
/*     */       else
/*  38 */         this.provider = new HiDataBaseProvider();
/*     */     }
/*     */     else {
/*  41 */       this.provider = new HiDataBaseProvider();
/*     */     }
/*     */   }
/*     */ 
/*     */   private DataAccessProvider getDataAccessProvider()
/*     */   {
/*  49 */     return this.provider;
/*     */   }
/*     */ 
/*     */   public HiDataBaseUtil(DataAccessProvider support)
/*     */   {
/*  54 */     setDataAccessProvider(support);
/*     */   }
/*     */ 
/*     */   public void setDataAccessProvider(DataAccessProvider support) {
/*  58 */     this.provider = support;
/*     */   }
/*     */ 
/*     */   public Map call(String name, Object[] inArgs) throws HiException {
/*  62 */     return getDataAccessProvider().call(name, inArgs);
/*     */   }
/*     */ 
/*     */   public Map call(String name, ArrayList inArgs) throws HiException {
/*  66 */     return getDataAccessProvider().call(name, inArgs);
/*     */   }
/*     */ 
/*     */   public void close() {
/*  70 */     getDataAccessProvider().close();
/*     */   }
/*     */ 
/*     */   public void close(Statement stmt)
/*     */   {
/*  75 */     getDataAccessProvider().close(stmt);
/*     */   }
/*     */ 
/*     */   public void close(Statement stmt, ResultSet rs)
/*     */   {
/*  80 */     getDataAccessProvider().close(stmt, rs);
/*     */   }
/*     */ 
/*     */   public void closeAll() {
/*  84 */     getDataAccessProvider().closeAll();
/*     */   }
/*     */ 
/*     */   public void commit() throws HiException
/*     */   {
/*  89 */     getDataAccessProvider().commit();
/*     */   }
/*     */ 
/*     */   public List execQueryBind(String strSql, String[] args) throws HiException
/*     */   {
/*  94 */     return getDataAccessProvider().execQueryBind(strSql, args);
/*     */   }
/*     */ 
/*     */   public List execQueryBind(String strSql, List args) throws HiException {
/*  98 */     return getDataAccessProvider().execQueryBind(strSql, args);
/*     */   }
/*     */ 
/*     */   public int execUpdateBind(String strSql, String[] args) throws HiException {
/* 102 */     return getDataAccessProvider().execUpdateBind(strSql, args);
/*     */   }
/*     */ 
/*     */   public int execUpdateBind(String strSql, List args) throws HiException {
/* 106 */     return getDataAccessProvider().execUpdateBind(strSql, args);
/*     */   }
/*     */ 
/*     */   public List execQuery(String strSql, String arg1) throws HiException {
/* 110 */     return getDataAccessProvider().execQuery(strSql, arg1);
/*     */   }
/*     */ 
/*     */   public List execQuery(String strSql, String arg1, String arg2) throws HiException
/*     */   {
/* 115 */     return getDataAccessProvider().execQuery(strSql, arg1, arg2);
/*     */   }
/*     */ 
/*     */   public List execQuery(String strSql, String arg1, String arg2, String arg3) throws HiException
/*     */   {
/* 120 */     return getDataAccessProvider().execQuery(strSql, arg1, arg2, arg3);
/*     */   }
/*     */ 
/*     */   public List execQuery(String strSql, String arg1, String arg2, String arg3, String arg4) throws HiException
/*     */   {
/* 125 */     return getDataAccessProvider().execQuery(strSql, arg1, arg2, arg3, arg4);
/*     */   }
/*     */ 
/*     */   public List execQuery(String strSql, String arg1, String arg2, String arg3, String arg4, String arg5)
/*     */     throws HiException
/*     */   {
/* 131 */     return getDataAccessProvider().execQuery(strSql, arg1, arg2, arg3, arg4, arg5);
/*     */   }
/*     */ 
/*     */   public List execQuery(String strSql, String[] args) throws HiException
/*     */   {
/* 136 */     return getDataAccessProvider().execQuery(strSql, args);
/*     */   }
/*     */ 
/*     */   public List execQuery(String strSql, List args) throws HiException {
/* 140 */     return getDataAccessProvider().execQuery(strSql, args);
/*     */   }
/*     */ 
/*     */   public List execQuery(String strSql) throws HiException {
/* 144 */     return getDataAccessProvider().execQuery(strSql);
/*     */   }
/*     */ 
/*     */   public List execQuery(String strSql, int limits) throws HiException {
/* 148 */     return getDataAccessProvider().execQuery(strSql, limits);
/*     */   }
/*     */ 
/*     */   public HiResultSet execQuerySQL(String strSql, String arg1) throws HiException
/*     */   {
/* 153 */     return getDataAccessProvider().execQuerySQL(strSql, arg1);
/*     */   }
/*     */ 
/*     */   public HiResultSet execQuerySQL(String strSql, String arg1, String arg2) throws HiException
/*     */   {
/* 158 */     return getDataAccessProvider().execQuerySQL(strSql, arg1, arg2);
/*     */   }
/*     */ 
/*     */   public HiResultSet execQuerySQL(String strSql, String arg1, String arg2, String arg3) throws HiException
/*     */   {
/* 163 */     return getDataAccessProvider().execQuerySQL(strSql, arg1, arg2, arg3);
/*     */   }
/*     */ 
/*     */   public HiResultSet execQuerySQL(String strSql, String arg1, String arg2, String arg3, String arg4) throws HiException
/*     */   {
/* 168 */     return getDataAccessProvider().execQuerySQL(strSql, arg1, arg2, arg3, arg4);
/*     */   }
/*     */ 
/*     */   public HiResultSet execQuerySQL(String strSql, String arg1, String arg2, String arg3, String arg4, String arg5)
/*     */     throws HiException
/*     */   {
/* 175 */     return getDataAccessProvider().execQuerySQL(strSql, arg1, arg2, arg3, arg4, arg5);
/*     */   }
/*     */ 
/*     */   public HiResultSet execQuerySQL(String strSql, List args)
/*     */     throws HiException
/*     */   {
/* 181 */     return getDataAccessProvider().execQuerySQL(strSql, args);
/*     */   }
/*     */ 
/*     */   public HiResultSet execQuerySQL(String strSql, String[] args) throws HiException
/*     */   {
/* 186 */     return getDataAccessProvider().execQuerySQL(strSql, args);
/*     */   }
/*     */ 
/*     */   public HiResultSet execQuerySQL(String strSql) throws HiException {
/* 190 */     return getDataAccessProvider().execQuerySQL(strSql);
/*     */   }
/*     */ 
/*     */   public int execUpdate(String strSql, String arg1) throws HiException {
/* 194 */     return getDataAccessProvider().execUpdate(strSql, arg1);
/*     */   }
/*     */ 
/*     */   public int execUpdate(String strSql, String arg1, String arg2) throws HiException
/*     */   {
/* 199 */     return getDataAccessProvider().execUpdate(strSql, arg1, arg2);
/*     */   }
/*     */ 
/*     */   public int execUpdate(String strSql, String arg1, String arg2, String arg3) throws HiException
/*     */   {
/* 204 */     return getDataAccessProvider().execUpdate(strSql, arg1, arg2, arg3);
/*     */   }
/*     */ 
/*     */   public int execUpdate(String strSql, String arg1, String arg2, String arg3, String arg4) throws HiException
/*     */   {
/* 209 */     return getDataAccessProvider().execUpdate(strSql, arg1, arg2, arg3, arg4);
/*     */   }
/*     */ 
/*     */   public int execUpdate(String strSql, String arg1, String arg2, String arg3, String arg4, String arg5)
/*     */     throws HiException
/*     */   {
/* 215 */     return getDataAccessProvider().execUpdate(strSql, arg1, arg2, arg3, arg4, arg5);
/*     */   }
/*     */ 
/*     */   public int execUpdate(String strSql, String[] args) throws HiException
/*     */   {
/* 220 */     return getDataAccessProvider().execUpdate(strSql, args);
/*     */   }
/*     */ 
/*     */   public int execUpdate(String strSql, List args) throws HiException {
/* 224 */     return getDataAccessProvider().execUpdate(strSql, args);
/*     */   }
/*     */ 
/*     */   public int execUpdate(String strSql) throws HiException {
/* 228 */     return getDataAccessProvider().execUpdate(strSql);
/*     */   }
/*     */ 
/*     */   public Connection getConnection() throws HiException {
/* 232 */     return getDataAccessProvider().getConnection();
/*     */   }
/*     */ 
/*     */   public ArrayList getProcParams(String name) throws SQLException, HiException
/*     */   {
/* 237 */     return getDataAccessProvider().getProcParams(name);
/*     */   }
/*     */ 
/*     */   public HashMap getTableMetaData(String strTableName, Connection conn) throws HiException
/*     */   {
/* 242 */     return getDataAccessProvider().getTableMetaData(strTableName, conn);
/*     */   }
/*     */ 
/*     */   public Connection popConnection() {
/* 246 */     return getDataAccessProvider().popConnection();
/*     */   }
/*     */ 
/*     */   public void pushConnection() {
/* 250 */     getDataAccessProvider().pushConnection();
/*     */   }
/*     */ 
/*     */   public HashMap readRecord(String strSql, String arg1) throws HiException
/*     */   {
/* 255 */     return getDataAccessProvider().readRecord(strSql, arg1);
/*     */   }
/*     */ 
/*     */   public HashMap readRecord(String strSql, String arg1, String arg2) throws HiException
/*     */   {
/* 260 */     return getDataAccessProvider().readRecord(strSql, arg1, arg2);
/*     */   }
/*     */ 
/*     */   public HashMap readRecord(String strSql, String arg1, String arg2, String arg3) throws HiException
/*     */   {
/* 265 */     return getDataAccessProvider().readRecord(strSql, arg1, arg2, arg3);
/*     */   }
/*     */ 
/*     */   public HashMap readRecord(String strSql, String arg1, String arg2, String arg3, String arg4) throws HiException
/*     */   {
/* 270 */     return getDataAccessProvider().readRecord(strSql, arg1, arg2, arg3, arg4);
/*     */   }
/*     */ 
/*     */   public HashMap readRecord(String strSql, String arg1, String arg2, String arg3, String arg4, String arg5)
/*     */     throws HiException
/*     */   {
/* 276 */     return getDataAccessProvider().readRecord(strSql, arg1, arg2, arg3, arg4, arg5);
/*     */   }
/*     */ 
/*     */   public HashMap readRecord(String strSql, String[] args) throws HiException
/*     */   {
/* 281 */     return getDataAccessProvider().readRecord(strSql, args);
/*     */   }
/*     */ 
/*     */   public HashMap readRecord(String strSql, List args) throws HiException {
/* 285 */     return getDataAccessProvider().readRecord(strSql, args);
/*     */   }
/*     */ 
/*     */   public HashMap readRecord(String strSql) throws HiException {
/* 289 */     return getDataAccessProvider().readRecord(strSql);
/*     */   }
/*     */ 
/*     */   public void rollback() throws HiException {
/* 293 */     getDataAccessProvider().rollback();
/*     */   }
/*     */ 
/*     */   public void setDBConnection(String name) {
/* 297 */     getDataAccessProvider().setDBConnection(name);
/*     */   }
/*     */ 
/*     */   public void setDsName(String dsName) {
/* 301 */     getDataAccessProvider().setDsName(dsName);
/*     */   }
/*     */ 
/*     */   public int delete(String statementid) throws HiException {
/* 305 */     return getDataAccessProvider().delete(statementid);
/*     */   }
/*     */ 
/*     */   public int delete(String statementid, Object paramObj) throws HiException {
/* 309 */     return getDataAccessProvider().delete(statementid, paramObj);
/*     */   }
/*     */ 
/*     */   public int delete(String statementid, Object[] paramObjs) throws HiException
/*     */   {
/* 314 */     return getDataAccessProvider().delete(statementid, paramObjs);
/*     */   }
/*     */ 
/*     */   public int delete(String statementid, List paramObjs) throws HiException {
/* 318 */     return getDataAccessProvider().delete(statementid, paramObjs);
/*     */   }
/*     */ 
/*     */   public Object get(String statementid) throws HiException {
/* 322 */     return getDataAccessProvider().get(statementid);
/*     */   }
/*     */ 
/*     */   public Object get(String statementid, Object paramObj) throws HiException {
/* 326 */     return getDataAccessProvider().get(statementid, paramObj);
/*     */   }
/*     */ 
/*     */   public Object get(String statementid, Object paramObj, Object returnObj) throws HiException
/*     */   {
/* 331 */     return getDataAccessProvider().get(statementid, paramObj, returnObj);
/*     */   }
/*     */ 
/*     */   public int insert(String statementid) throws HiException {
/* 335 */     return getDataAccessProvider().insert(statementid);
/*     */   }
/*     */ 
/*     */   public int insert(String statementid, Object obj) throws HiException
/*     */   {
/* 340 */     return getDataAccessProvider().insert(statementid, obj);
/*     */   }
/*     */ 
/*     */   public int insert(String statementid, Object[] objs) throws HiException {
/* 344 */     return getDataAccessProvider().insert(statementid, objs);
/*     */   }
/*     */ 
/*     */   public int insert(String statementid, List objs) throws HiException {
/* 348 */     return getDataAccessProvider().insert(statementid, objs);
/*     */   }
/*     */ 
/*     */   public List list(String statementid) throws HiException {
/* 352 */     return getDataAccessProvider().list(statementid);
/*     */   }
/*     */ 
/*     */   public List list(String statementid, Object paramObj) throws HiException {
/* 356 */     return getDataAccessProvider().list(statementid, paramObj);
/*     */   }
/*     */ 
/*     */   public List list(String statementid, int skip, int max) throws HiException {
/* 360 */     return getDataAccessProvider().list(statementid, skip, max);
/*     */   }
/*     */ 
/*     */   public List list(String statementid, Object paramObj, int skip, int max) throws HiException
/*     */   {
/* 365 */     return getDataAccessProvider().list(statementid, paramObj, skip, max);
/*     */   }
/*     */ 
/*     */   public int update(String statementid) throws HiException {
/* 369 */     return getDataAccessProvider().update(statementid);
/*     */   }
/*     */ 
/*     */   public int update(String statementid, Object paramObj) throws HiException {
/* 373 */     return getDataAccessProvider().update(statementid, paramObj);
/*     */   }
/*     */ 
/*     */   public int update(String statementid, Object[] paramObjs) throws HiException
/*     */   {
/* 378 */     return getDataAccessProvider().update(statementid, paramObjs);
/*     */   }
/*     */ 
/*     */   public int update(String statementid, List paramObjs) throws HiException {
/* 382 */     return getDataAccessProvider().update(statementid, paramObjs);
/*     */   }
/*     */ }