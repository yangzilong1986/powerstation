/*     */ package com.hisun.database;
/*     */ 
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.exception.HiSQLException;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.util.HiICSProperty;
/*     */ import com.ibatis.sqlmap.client.SqlMapSession;
/*     */ import java.sql.Connection;
/*     */ import java.sql.SQLException;
/*     */ import java.util.List;
/*     */ import java.util.Stack;
/*     */ import javax.sql.DataSource;
/*     */ 
/*     */ public class HiIbatisProvider extends HiJDBCProvider
/*     */   implements DataAccessProvider
/*     */ {
/*     */   private SqlMapSession session;
/*     */   private Connection currConnection;
/*     */   private Stack _sessions;
/*     */ 
/*     */   public HiIbatisProvider()
/*     */   {
/*  17 */     this.session = null;
/*     */ 
/*  19 */     this.currConnection = null;
/*     */ 
/*  23 */     this._sessions = null; }
/*     */ 
/*     */   private SqlMapSession getSession() throws HiException {
/*  26 */     if (this.session != null) {
/*  27 */       return this.session;
/*     */     }
/*  29 */     log.debug("get new Session");
/*  30 */     this.session = HiIbatisManager.getSession(this._dsName);
/*  31 */     if (this.session == null) {
/*  32 */       throw HiException.makeException(new NullPointerException("can't found datasource :" + this._dsName));
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/*  37 */       this.currConnection = this.session.getDataSource().getConnection();
/*  38 */       this.currConnection.setAutoCommit(false);
/*  39 */       this.session.setUserConnection(this.currConnection);
/*     */     }
/*     */     catch (RuntimeException re) {
/*  42 */       throw new HiSQLException("220037", re, "");
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/*  46 */       throw new HiSQLException("220037", e, e.getSQLState());
/*     */     }
/*     */ 
/*  50 */     return this.session;
/*     */   }
/*     */ 
/*     */   public void close() {
/*  54 */     log.debug("close session :" + this._dsName);
/*  55 */     if (this.session != null)
/*  56 */       this.session.close();
/*     */     try {
/*  58 */       if (this.currConnection != null) {
/*  59 */         this.currConnection.commit();
/*  60 */         this.currConnection.close();
/*     */       }
/*     */     } catch (SQLException e) {
/*     */     }
/*  64 */     this.session = null;
/*  65 */     this.currConnection = null;
/*  66 */     if ((this._sessions != null) && (!(this._sessions.isEmpty())))
/*  67 */       this._sessions.remove(this._dsName);
/*     */   }
/*     */ 
/*     */   public void closeAll()
/*     */   {
/*  73 */     if ((this._sessions == null) || (this._sessions.isEmpty())) {
/*  74 */       close();
/*  75 */       return;
/*     */     }
/*  77 */     close();
/*  78 */     for (int i = 0; i < this._sessions.size(); ++i) {
/*  79 */       HiIbatisSession hSession = (HiIbatisSession)this._sessions.remove(i);
/*  80 */       SqlMapSession session = hSession.session;
/*     */       try {
/*  82 */         session.getCurrentConnection().commit();
/*  83 */         session.getCurrentConnection().close();
/*     */       }
/*     */       catch (SQLException e) {
/*     */       }
/*  87 */       session.close();
/*  88 */       session = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void commit() throws HiException
/*     */   {
/*  94 */     log.debug("session commit:" + this._dsName);
/*     */     try {
/*  96 */       log.debug("COMMIt:" + getSession().getCurrentConnection().hashCode());
/*     */ 
/*  98 */       getSession().getCurrentConnection().commit();
/*     */     }
/*     */     catch (SQLException e) {
/* 101 */       throw new HiSQLException("220037", e, e.getSQLState());
/*     */     }
/*     */   }
/*     */ 
/*     */   public Connection getConnection()
/*     */     throws HiException
/*     */   {
/* 109 */     SqlMapSession tmpSession = getSession();
/* 110 */     if (this.currConnection == null) {
/*     */       try {
/* 112 */         this.currConnection = tmpSession.getCurrentConnection();
/*     */       } catch (SQLException e) {
/* 114 */         throw new HiSQLException("220037", e, e.getSQLState());
/*     */       }
/*     */     }
/*     */ 
/* 118 */     return this.currConnection;
/*     */   }
/*     */ 
/*     */   public Connection popConnection() {
/* 122 */     if ((this._sessions == null) || (this._sessions.isEmpty())) {
/* 123 */       return null;
/*     */     }
/* 125 */     HiIbatisSession tmpSession = (HiIbatisSession)this._sessions.pop();
/*     */ 
/* 127 */     this._dsName = tmpSession.dsname;
/*     */     try {
/* 129 */       this.currConnection = this.session.getCurrentConnection();
/*     */     } catch (SQLException e) {
/* 131 */       log.fatal(e.getLocalizedMessage());
/* 132 */       return null;
/*     */     }
/* 134 */     this.session = tmpSession.session;
/* 135 */     if (log.isDebugEnabled()) {
/* 136 */       log.debug("pop db:[" + this._dsName + "]:[" + this.session + "]");
/*     */     }
/* 138 */     return this.currConnection;
/*     */   }
/*     */ 
/*     */   public void pushConnection() {
/* 142 */     if (this.session == null) {
/* 143 */       return;
/*     */     }
/* 145 */     if (this._sessions == null) {
/* 146 */       this._sessions = new Stack();
/* 147 */       this._sessions.push(new HiIbatisSession(this._dsName, this.session));
/*     */     }
/* 149 */     if (log.isDebugEnabled()) {
/* 150 */       log.debug("push db:[" + this._dsName + "]:[" + this.session + "]");
/*     */     }
/* 152 */     this.session = null;
/* 153 */     this.currConnection = null;
/*     */   }
/*     */ 
/*     */   public void rollback() throws HiException
/*     */   {
/* 158 */     log.debug("session rollback:" + this._dsName);
/*     */     try
/*     */     {
/* 167 */       getSession().getCurrentConnection().rollback();
/*     */     } catch (SQLException e) {
/* 169 */       throw new HiSQLException("220037", e, e.getSQLState());
/*     */     }
/*     */   }
/*     */ 
/*     */   public int delete(String statementid)
/*     */     throws HiException
/*     */   {
/*     */     try
/*     */     {
/* 178 */       return getSession().delete(statementid);
/*     */     } catch (SQLException e) {
/* 180 */       throw new HiSQLException("220042", e, e.getSQLState());
/*     */     }
/*     */   }
/*     */ 
/*     */   public int delete(String statementid, Object paramObj) throws HiException
/*     */   {
/* 186 */     return delete(statementid, new Object[] { paramObj });
/*     */   }
/*     */ 
/*     */   public int delete(String statementid, Object[] paramObjs)
/*     */     throws HiException
/*     */   {
/* 193 */     SqlMapSession tmpSession = getSession();
/* 194 */     int p = 0;
/* 195 */     for (int i = 0; i < paramObjs.length; ++i) {
/*     */       try {
/* 197 */         p += tmpSession.delete(statementid, paramObjs[i]);
/*     */       } catch (SQLException e) {
/* 199 */         SQLException se = getException(e);
/* 200 */         throw new HiSQLException("220042", se, se.getSQLState());
/*     */       }
/*     */     }
/*     */ 
/* 204 */     return p;
/*     */   }
/*     */ 
/*     */   public int delete(String statementid, List paramObjs) throws HiException {
/* 208 */     Object[] objs = paramObjs.toArray();
/* 209 */     return delete(statementid, objs);
/*     */   }
/*     */ 
/*     */   public Object get(String statementid) throws HiException
/*     */   {
/*     */     try
/*     */     {
/* 216 */       return getSession().queryForObject(statementid);
/*     */     } catch (SQLException e) {
/* 218 */       SQLException se = getException(e);
/* 219 */       throw new HiSQLException("220038", se, se.getSQLState());
/*     */     }
/*     */   }
/*     */ 
/*     */   public Object get(String statementid, Object paramObj) throws HiException
/*     */   {
/*     */     try
/*     */     {
/* 227 */       return getSession().queryForObject(statementid, paramObj);
/*     */     } catch (SQLException e) {
/* 229 */       SQLException se = getException(e);
/* 230 */       throw new HiSQLException("220038", se, se.getSQLState());
/*     */     }
/*     */   }
/*     */ 
/*     */   public Object get(String statementid, Object paramObj, Object returnObj)
/*     */     throws HiException
/*     */   {
/*     */     try
/*     */     {
/* 239 */       return getSession().queryForObject(statementid, paramObj, returnObj);
/*     */     }
/*     */     catch (SQLException e) {
/* 242 */       SQLException se = getException(e);
/* 243 */       throw new HiSQLException("220038", se, se.getSQLState());
/*     */     }
/*     */   }
/*     */ 
/*     */   public int insert(String statementid) throws HiException
/*     */   {
/*     */     try
/*     */     {
/* 251 */       getSession().insert(statementid);
/* 252 */       return 1;
/*     */     } catch (SQLException e) {
/* 254 */       SQLException se = getException(e);
/* 255 */       if (se.getSQLState().equals(HiICSProperty.getProperty("sql.duppk"))) {
/* 256 */         log.error("220042", se.getMessage(), se);
/* 257 */         return 0;
/*     */       }
/* 259 */       throw new HiSQLException("220042", se, se.getSQLState());
/*     */     }
/*     */   }
/*     */ 
/*     */   public int insert(String statementid, Object obj) throws HiException
/*     */   {
/* 265 */     return insert(statementid, new Object[] { obj });
/*     */   }
/*     */ 
/*     */   public int insert(String statementid, Object[] objs)
/*     */     throws HiException
/*     */   {
/* 271 */     SqlMapSession tmpSession = getSession();
/* 272 */     int p = 0;
/* 273 */     for (int i = 0; i < objs.length; ++i) {
/*     */       try {
/* 275 */         tmpSession.insert(statementid, objs[i]);
/* 276 */         ++p;
/*     */       } catch (SQLException e) {
/* 278 */         SQLException se = getException(e);
/* 279 */         if (se.getSQLState().equals(HiICSProperty.getProperty("sql.duppk"))) {
/* 280 */           log.error("220042", e.getMessage());
/* 281 */           return 0;
/*     */         }
/* 283 */         throw new HiSQLException("220042", se, se.getSQLState());
/*     */       }
/*     */     }
/*     */ 
/* 287 */     return p;
/*     */   }
/*     */ 
/*     */   public int insert(String statementid, List objs) throws HiException {
/* 291 */     Object[] _objs = objs.toArray();
/* 292 */     return insert(statementid, _objs);
/*     */   }
/*     */ 
/*     */   public List list(String statementid) throws HiException
/*     */   {
/*     */     try {
/* 298 */       return getSession().queryForList(statementid);
/*     */     } catch (SQLException e) {
/* 300 */       SQLException se = getException(e);
/* 301 */       throw new HiSQLException("220038", se, se.getSQLState());
/*     */     }
/*     */   }
/*     */ 
/*     */   public List list(String statementid, Object paramObj) throws HiException
/*     */   {
/*     */     try
/*     */     {
/* 309 */       return getSession().queryForList(statementid, paramObj);
/*     */     } catch (SQLException e) {
/* 311 */       SQLException se = getException(e);
/* 312 */       throw new HiSQLException("220038", se, se.getSQLState());
/*     */     }
/*     */   }
/*     */ 
/*     */   public List list(String statementid, int skip, int max) throws HiException
/*     */   {
/*     */     try
/*     */     {
/* 320 */       return getSession().queryForList(statementid, skip, max);
/*     */     } catch (SQLException e) {
/* 322 */       SQLException se = getException(e);
/* 323 */       throw new HiSQLException("220038", se, se.getSQLState());
/*     */     }
/*     */   }
/*     */ 
/*     */   public List list(String statementid, Object paramObj, int skip, int max)
/*     */     throws HiException
/*     */   {
/*     */     try
/*     */     {
/* 332 */       return getSession().queryForList(statementid, paramObj, skip, max);
/*     */     } catch (SQLException e) {
/* 334 */       SQLException se = getException(e);
/* 335 */       throw new HiSQLException("220038", se, se.getSQLState());
/*     */     }
/*     */   }
/*     */ 
/*     */   public int update(String statementid) throws HiException
/*     */   {
/*     */     try
/*     */     {
/* 343 */       return getSession().update(statementid);
/*     */     } catch (SQLException e) {
/* 345 */       SQLException se = getException(e);
/* 346 */       throw new HiSQLException("220038", se, se.getSQLState());
/*     */     }
/*     */   }
/*     */ 
/*     */   public int update(String statementid, Object paramObj)
/*     */     throws HiException
/*     */   {
/* 353 */     return update(statementid, new Object[] { paramObj });
/*     */   }
/*     */ 
/*     */   public int update(String statementid, Object[] paramObjs)
/*     */     throws HiException
/*     */   {
/* 360 */     int p = 0;
/* 361 */     SqlMapSession tmpSession = getSession();
/* 362 */     for (int i = 0; i < paramObjs.length; ++i) {
/*     */       try {
/* 364 */         p += tmpSession.update(statementid, paramObjs[i]);
/*     */       } catch (SQLException e) {
/* 366 */         SQLException se = getException(e);
/* 367 */         throw new HiSQLException("220042", se, se.getSQLState());
/*     */       }
/*     */     }
/*     */ 
/* 371 */     return p;
/*     */   }
/*     */ 
/*     */   public int update(String statementid, List paramObjs) throws HiException
/*     */   {
/* 376 */     Object[] _objs = paramObjs.toArray();
/* 377 */     return update(statementid, _objs);
/*     */   }
/*     */ 
/*     */   public SQLException getException(SQLException se)
/*     */   {
/* 382 */     SQLException se1 = se;
/*     */ 
/* 387 */     return se1;
/*     */   }
/*     */ }