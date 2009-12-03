/*     */ package com.hisun.database;
/*     */ 
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.exception.HiSQLException;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiContext;
/*     */ import com.hisun.util.HiDBSemaphore;
/*     */ import com.hisun.util.HiICSProperty;
/*     */ import com.hisun.util.HiSemaphore;
/*     */ import com.hisun.util.HiServiceLocator;
/*     */ import java.sql.Connection;
/*     */ import java.sql.DriverManager;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Stack;
/*     */ import javax.sql.DataSource;
/*     */ 
/*     */ public class HiDataBaseProvider extends HiJDBCProvider
/*     */   implements DataAccessProvider
/*     */ {
/*     */   private HiSemaphore dbSemaphore;
/*     */   private Connection _connection;
/*     */   private Stack _connections;
/*     */ 
/*     */   public HiDataBaseProvider()
/*     */   {
/*  25 */     this.dbSemaphore = null;
/*     */ 
/*  27 */     this._connection = null;
/*     */ 
/*  29 */     this._connections = null; }
/*     */ 
/*     */   public void close() {
/*     */     try {
/*  33 */       if (this._connection != null) {
/*  34 */         this._connection.commit();
/*  35 */         this._connection.close();
/*  36 */         if (this.dbSemaphore != null) {
/*  37 */           this.dbSemaphore.release();
/*     */         }
/*  39 */         this._connection = null;
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/*     */     }
/*     */   }
/*     */ 
/*     */   public void closeAll() {
/*  47 */     if ((this._connections == null) || (this._connections.isEmpty())) {
/*  48 */       close();
/*  49 */       return;
/*     */     }
/*  51 */     Iterator iter = this._connections.iterator();
/*  52 */     while (iter.hasNext()) {
/*  53 */       HiConnection con = (HiConnection)iter.next();
/*     */       try {
/*  55 */         con.con.commit();
/*  56 */         con.con.close();
/*  57 */         if (this.dbSemaphore != null)
/*  58 */           this.dbSemaphore.release();
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void commit() throws HiException {
/*     */     try {
/*  68 */       if (this._connection != null)
/*  69 */         this._connection.commit();
/*     */     } catch (Exception e) {
/*  71 */       throw new HiSQLException("215015", e, "commit 出错");
/*     */     }
/*     */   }
/*     */ 
/*     */   public Connection getConnection() throws HiException
/*     */   {
/*  77 */     if (this._connection != null)
/*  78 */       return this._connection;
/*  79 */     if (HiICSProperty.isJUnitEnv()) {
/*     */       try {
/*  81 */         String url = System.getProperty("db_url");
/*  82 */         String userId = System.getProperty("userId");
/*  83 */         String password = System.getProperty("password");
/*  84 */         String driver = System.getProperty("db_driver");
/*     */ 
/*  86 */         Class.forName(driver);
/*     */ 
/*  88 */         this._connection = DriverManager.getConnection(url, userId, password);
/*     */ 
/*  90 */         this._connection.setAutoCommit(false);
/*  91 */         return this._connection;
/*     */       } catch (Exception e) {
/*  93 */         throw HiException.makeException(e);
/*     */       }
/*     */     }
/*     */ 
/*  97 */     if (this.dbSemaphore == null) {
/*  98 */       this.dbSemaphore = ((HiDBSemaphore)HiContext.getCurrentContext().getProperty("_SVR_DB_CONN_NUM_CTRL"));
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 103 */       if (this.dbSemaphore != null) {
/* 104 */         this.dbSemaphore.acquire();
/*     */       }
/* 106 */       DataSource ds = HiServiceLocator.getInstance().getDBDataSource(this._dsName);
/*     */ 
/* 108 */       this._connection = ds.getConnection();
/* 109 */       this._connection.setAutoCommit(false);
/* 110 */       return this._connection;
/*     */     } catch (SQLException e) {
/* 112 */       if (this.dbSemaphore != null)
/* 113 */         this.dbSemaphore.release();
/* 114 */       throw new HiSQLException("215022", e, "");
/*     */     }
/*     */   }
/*     */ 
/*     */   public Connection popConnection() {
/* 119 */     if ((this._connections == null) || (this._connections.isEmpty())) {
/* 120 */       return null;
/*     */     }
/* 122 */     HiConnection con = (HiConnection)this._connections.pop();
/* 123 */     this._connection = con.con;
/* 124 */     this._dsName = con.name;
/* 125 */     if (log.isInfoEnabled()) {
/* 126 */       log.info("pop db:[" + this._dsName + "]:[" + this._connection + "]");
/*     */     }
/* 128 */     return this._connection;
/*     */   }
/*     */ 
/*     */   public void pushConnection() {
/* 132 */     if (this._connection == null) {
/* 133 */       return;
/*     */     }
/*     */ 
/* 136 */     if (this._connections == null) {
/* 137 */       this._connections = new Stack();
/*     */     }
/* 139 */     if (log.isInfoEnabled()) {
/* 140 */       log.info("push db:[" + this._dsName + "]:[" + this._connection + "]");
/*     */     }
/* 142 */     this._connections.push(new HiConnection(this._dsName, this._connection));
/* 143 */     this._connection = null;
/*     */   }
/*     */ 
/*     */   public void rollback() throws HiException {
/*     */     try {
/* 148 */       if (this._connection != null)
/* 149 */         this._connection.rollback();
/*     */     }
/*     */     catch (Exception e) {
/* 152 */       throw new HiException("220319", e.getMessage(), e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public int delete(Object o) throws HiException
/*     */   {
/* 158 */     return 0;
/*     */   }
/*     */ 
/*     */   public int delete(List objs) throws HiException {
/* 162 */     return 0;
/*     */   }
/*     */ 
/*     */   public int delete(Object[] objs) throws HiException
/*     */   {
/* 167 */     return 0;
/*     */   }
/*     */ 
/*     */   public Object get(Class clazz, int id) throws HiException
/*     */   {
/* 172 */     return null;
/*     */   }
/*     */ 
/*     */   public List list(Class clazz) throws HiException
/*     */   {
/* 177 */     return null;
/*     */   }
/*     */ 
/*     */   public List list(Class clazz, String sql) throws HiException
/*     */   {
/* 182 */     return null;
/*     */   }
/*     */ 
/*     */   public List list(Class clazz, String sql, List params) throws HiException
/*     */   {
/* 187 */     return null;
/*     */   }
/*     */ 
/*     */   public int save(Object o) throws HiException
/*     */   {
/* 192 */     return 0;
/*     */   }
/*     */ 
/*     */   public int save(List objs) throws HiException
/*     */   {
/* 197 */     return 0;
/*     */   }
/*     */ 
/*     */   public int save(Object[] objs) throws HiException
/*     */   {
/* 202 */     return 0;
/*     */   }
/*     */ 
/*     */   public List list(Class clazz, String sql, Object[] params)
/*     */     throws HiException
/*     */   {
/* 208 */     return null;
/*     */   }
/*     */ 
/*     */   public int delete(String statementid) throws HiException {
/* 212 */     return 0;
/*     */   }
/*     */ 
/*     */   public int delete(String statementid, Object paramObj) throws HiException {
/* 216 */     return 0;
/*     */   }
/*     */ 
/*     */   public int delete(String statementid, Object[] paramObjs) throws HiException
/*     */   {
/* 221 */     return 0;
/*     */   }
/*     */ 
/*     */   public int delete(String statementid, List paramObjs) throws HiException {
/* 225 */     return 0;
/*     */   }
/*     */ 
/*     */   public Object get(String statementid) throws HiException
/*     */   {
/* 230 */     return null;
/*     */   }
/*     */ 
/*     */   public Object get(String statementid, Object paramObj) throws HiException
/*     */   {
/* 235 */     return null;
/*     */   }
/*     */ 
/*     */   public Object get(String statementid, Object paramObj, Object returnObj)
/*     */     throws HiException
/*     */   {
/* 241 */     return null;
/*     */   }
/*     */ 
/*     */   public int insert(String statementid) throws HiException {
/* 245 */     return 0;
/*     */   }
/*     */ 
/*     */   public int insert(String statementid, Object obj) throws HiException {
/* 249 */     return 0;
/*     */   }
/*     */ 
/*     */   public int insert(String statementid, Object[] objs) throws HiException
/*     */   {
/* 254 */     return 0;
/*     */   }
/*     */ 
/*     */   public int insert(String statementid, List objs) throws HiException
/*     */   {
/* 259 */     return 0;
/*     */   }
/*     */ 
/*     */   public List list(String statementid)
/*     */     throws HiException
/*     */   {
/* 265 */     return null;
/*     */   }
/*     */ 
/*     */   public List list(String statementid, Object paramObj) throws HiException
/*     */   {
/* 270 */     return null;
/*     */   }
/*     */ 
/*     */   public List list(String statementid, int skip, int max) throws HiException
/*     */   {
/* 275 */     return null;
/*     */   }
/*     */ 
/*     */   public List list(String statementid, Object paramObj, int skip, int max)
/*     */     throws HiException
/*     */   {
/* 281 */     return null;
/*     */   }
/*     */ 
/*     */   public int update(String statementid) throws HiException {
/* 285 */     return 0;
/*     */   }
/*     */ 
/*     */   public int update(String statementid, Object paramObj) throws HiException
/*     */   {
/* 290 */     return 0;
/*     */   }
/*     */ 
/*     */   public int update(String statementid, Object[] paramObjs)
/*     */     throws HiException
/*     */   {
/* 296 */     return 0;
/*     */   }
/*     */ 
/*     */   public int update(String statementid, List paramObjs) throws HiException {
/* 300 */     return 0;
/*     */   }
/*     */ }