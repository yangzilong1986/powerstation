/*     */ package com.hisun.database;
/*     */ 
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import java.sql.Connection;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Stack;
/*     */ import org.hibernate.Criteria;
/*     */ import org.hibernate.HibernateException;
/*     */ import org.hibernate.Query;
/*     */ import org.hibernate.SQLQuery;
/*     */ import org.hibernate.Session;
/*     */ import org.hibernate.StaleStateException;
/*     */ import org.hibernate.Transaction;
/*     */ import org.hibernate.exception.ConstraintViolationException;
/*     */ 
/*     */ public abstract class HiHibernateProvider extends HiJDBCProvider
/*     */   implements DataAccessProvider
/*     */ {
/*     */   private Session session;
/*     */   private Connection _connection;
/*     */   private Stack _sessions;
/*     */ 
/*     */   public HiHibernateProvider()
/*     */   {
/*  20 */     this.session = null;
/*     */ 
/*  22 */     this._connection = null;
/*     */   }
/*     */ 
/*     */   private Session getSession() throws HiException
/*     */   {
/*  27 */     if (this.session != null) {
/*  28 */       return this.session;
/*     */     }
/*  30 */     log.debug("get new Session");
/*  31 */     this.session = HibernateManager.getSession(this._dsName);
/*  32 */     if (this.session == null) {
/*  33 */       throw HiException.makeException(new NullPointerException("can't found datasource :" + this._dsName));
/*     */     }
/*     */     try
/*     */     {
/*  37 */       this._connection = this.session.connection();
/*  38 */       this.session.beginTransaction();
/*     */     } catch (RuntimeException re) {
/*  40 */       throw HiException.makeException(re);
/*     */     }
/*     */ 
/*  43 */     return this.session;
/*     */   }
/*     */ 
/*     */   public void close()
/*     */   {
/*     */     try {
/*  49 */       if (this.session == null) {
/*  50 */         return;
/*     */       }
/*  52 */       log.debug("close session");
/*     */ 
/*  54 */       this.session.getTransaction().commit();
/*  55 */       this.session.close();
/*  56 */       this.session = null;
/*     */     }
/*     */     catch (HibernateException e) {
/*     */     }
/*     */   }
/*     */ 
/*     */   public void closeAll() {
/*  63 */     if ((this._sessions == null) || (this._sessions.isEmpty())) {
/*  64 */       close();
/*  65 */       return;
/*     */     }
/*  67 */     Iterator iter = this._sessions.iterator();
/*  68 */     while (iter.hasNext()) {
/*  69 */       HiSession tmpSession = (HiSession)iter.next();
/*     */       try {
/*  71 */         tmpSession.session.getTransaction().commit();
/*  72 */         tmpSession.session.close();
/*  73 */         tmpSession.session = null;
/*     */       } catch (Exception e) {
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void commit() throws HiException {
/*  80 */     Transaction _transaction = getSession().getTransaction();
/*  81 */     if (!(_transaction.isActive())) {
/*  82 */       return;
/*     */     }
/*  84 */     log.debug("Commit");
/*     */     try {
/*  86 */       _transaction.commit();
/*  87 */       getSession().beginTransaction();
/*     */     } catch (RuntimeException re) {
/*  89 */       throw new HiException("220319", re.getMessage(), re);
/*     */     }
/*     */   }
/*     */ 
/*     */   public Connection getConnection() throws HiException
/*     */   {
/*  95 */     if (this._connection == null)
/*  96 */       getSession();
/*  97 */     return this._connection;
/*     */   }
/*     */ 
/*     */   public Connection popConnection() {
/* 101 */     if ((this._sessions == null) || (this._sessions.isEmpty())) {
/* 102 */       return null;
/*     */     }
/* 104 */     HiSession tmpSession = (HiSession)this._sessions.pop();
/*     */ 
/* 106 */     this._dsName = tmpSession.dsname;
/* 107 */     this._connection = this.session.connection();
/* 108 */     this.session = tmpSession.session;
/* 109 */     if (log.isDebugEnabled()) {
/* 110 */       log.debug("pop db:[" + this._dsName + "]:[" + this.session + "]");
/*     */     }
/* 112 */     return this._connection;
/*     */   }
/*     */ 
/*     */   public void pushConnection() {
/* 116 */     if (this.session == null) {
/* 117 */       return;
/*     */     }
/* 119 */     if (this._sessions == null) {
/* 120 */       this._sessions = new Stack();
/* 121 */       this._sessions.push(new HiSession(this._dsName, this.session));
/*     */     }
/* 123 */     if (log.isDebugEnabled()) {
/* 124 */       log.debug("push db:[" + this._dsName + "]:[" + this.session + "]");
/*     */     }
/* 126 */     this.session = null;
/* 127 */     this._connection = null;
/*     */   }
/*     */ 
/*     */   public void rollback() throws HiException {
/* 131 */     Transaction _transaction = getSession().getTransaction();
/* 132 */     if (!(_transaction.isActive())) {
/* 133 */       return;
/*     */     }
/* 135 */     log.debug("Rollback");
/*     */     try {
/* 137 */       _transaction.rollback();
/* 138 */       getSession().clear();
/*     */ 
/* 141 */       getSession().beginTransaction();
/*     */     } catch (RuntimeException re) {
/* 143 */       throw new HiException("220319", re.getMessage(), re);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setDBConnection(String name)
/*     */   {
/* 150 */     super.setDBConnection(name);
/*     */   }
/*     */ 
/*     */   public int delete(Object o) throws HiException {
/* 154 */     return delete(new Object[] { o });
/*     */   }
/*     */ 
/*     */   public int delete(List objs) throws HiException {
/* 158 */     return delete(objs.toArray());
/*     */   }
/*     */ 
/*     */   public int delete(Object[] objs) throws HiException {
/* 162 */     Session tmpSession = getSession();
/*     */     try {
/* 164 */       for (int i = 0; i < objs.length; ++i) {
/* 165 */         tmpSession.delete(objs[i]);
/* 166 */         if (log.isDebugEnabled()) {
/* 167 */           log.debug("delete object:[" + objs[i] + "]");
/*     */         }
/*     */       }
/* 170 */       tmpSession.flush();
/* 171 */       return objs.length;
/*     */     } catch (RuntimeException re) {
/* 173 */       if (re instanceof StaleStateException) {
/* 174 */         return 0;
/*     */       }
/* 176 */       throw new HiException("215021", re.getMessage(), re);
/*     */     }
/*     */   }
/*     */ 
/*     */   public List list(Class clazz) throws HiException
/*     */   {
/*     */     try {
/* 183 */       return getSession().createCriteria(clazz).list();
/*     */     } catch (RuntimeException re) {
/* 185 */       throw new HiException("215016", re.getMessage(), re);
/*     */     }
/*     */   }
/*     */ 
/*     */   public List list(Class clazz, String sql) throws HiException
/*     */   {
/* 191 */     return list(clazz, sql, (List)null);
/*     */   }
/*     */ 
/*     */   public List list(Class clazz, String sql, Object[] params) throws HiException
/*     */   {
/*     */     try {
/* 197 */       Query query = null;
/* 198 */       if (sql != null)
/* 199 */         query = getSession().createSQLQuery(sql).addEntity(clazz);
/*     */       else {
/* 201 */         return null;
/*     */       }
/* 203 */       if (log.isDebugEnabled()) {
/* 204 */         log.debug("query object:[sql:" + sql + "]param:" + params);
/*     */       }
/* 206 */       if ((params != null) && (params.length != 0)) {
/* 207 */         for (int i = 0; i < params.length; ++i) {
/* 208 */           query.setParameter(i, params[i]);
/*     */         }
/*     */       }
/*     */ 
/* 212 */       return query.list();
/*     */     } catch (RuntimeException re) {
/* 214 */       throw new HiException("215016", re.getMessage(), re);
/*     */     }
/*     */   }
/*     */ 
/*     */   public List list(Class clazz, String sql, List params) throws HiException
/*     */   {
/*     */     try {
/* 221 */       Query query = null;
/* 222 */       if (sql != null)
/* 223 */         query = getSession().createSQLQuery(sql).addEntity(clazz);
/*     */       else {
/* 225 */         return null;
/*     */       }
/* 227 */       if (log.isDebugEnabled()) {
/* 228 */         log.debug("query object:[sql:" + sql + "]param:" + params);
/*     */       }
/* 230 */       if ((params != null) && (params.size() != 0)) {
/* 231 */         for (int i = 0; i < params.size(); ++i) {
/* 232 */           query.setParameter(i, params.get(i));
/*     */         }
/*     */       }
/*     */ 
/* 236 */       return query.list();
/*     */     } catch (RuntimeException re) {
/* 238 */       throw new HiException("215016", re.getMessage(), re);
/*     */     }
/*     */   }
/*     */ 
/*     */   public int save(Object o) throws HiException
/*     */   {
/* 244 */     return save(new Object[] { o });
/*     */   }
/*     */ 
/*     */   public boolean insert(Object o) throws HiException {
/* 248 */     return (getSession().save(o) != null);
/*     */   }
/*     */ 
/*     */   public int save(List objs) throws HiException
/*     */   {
/* 253 */     return save(objs.toArray());
/*     */   }
/*     */ 
/*     */   public int save(Object[] objs) throws HiException {
/* 257 */     int i = 0;
/* 258 */     Object obj = null;
/*     */     try {
/* 260 */       for (; i < objs.length; ++i) {
/* 261 */         obj = objs[i];
/* 262 */         getSession().update(obj);
/* 263 */         if (log.isDebugEnabled()) {
/* 264 */           log.debug("save object:[" + objs[i] + "]");
/*     */         }
/*     */       }
/* 267 */       getSession().flush();
/*     */ 
/* 269 */       return objs.length;
/*     */     } catch (HibernateException re) {
/* 271 */       if (re instanceof ConstraintViolationException) {
/* 272 */         ConstraintViolationException cve = (ConstraintViolationException)re;
/* 273 */         log.error(cve.getSQL(), objs[0], re.getCause());
/* 274 */         return 0;
/*     */       }
/* 276 */       throw new HiException("215021", re.getMessage(), re);
/*     */     }
/*     */   }
/*     */ 
/*     */   public Object get(Class clazz, int id) throws HiException
/*     */   {
/*     */     try {
/* 283 */       if (log.isDebugEnabled()) {
/* 284 */         log.debug("query object:[" + clazz.getSimpleName() + "][id:" + id + "]");
/*     */       }
/*     */ 
/* 287 */       return getSession().get(clazz, new Integer(id));
/*     */     } catch (RuntimeException re) {
/* 289 */       throw new HiException("215016", re.getMessage(), re);
/*     */     }
/*     */   }
/*     */ 
/*     */   public Object get(String statementid)
/*     */     throws HiException
/*     */   {
/* 297 */     return null;
/*     */   }
/*     */ 
/*     */   public Object get(String statementid, Object paramObj) throws HiException
/*     */   {
/* 302 */     return null;
/*     */   }
/*     */ 
/*     */   public Object get(String statementid, Object paramObj, Object returnObj)
/*     */     throws HiException
/*     */   {
/* 308 */     return null;
/*     */   }
/*     */ 
/*     */   public List list(String statementid) throws HiException
/*     */   {
/* 313 */     return null;
/*     */   }
/*     */ 
/*     */   public List list(String statementid, Object paramObj) throws HiException
/*     */   {
/* 318 */     return null;
/*     */   }
/*     */ 
/*     */   public List list(String statementid, int skip, int max) throws HiException
/*     */   {
/* 323 */     return null;
/*     */   }
/*     */ 
/*     */   public List list(String statementid, Object paramObj, int skip, int max)
/*     */     throws HiException
/*     */   {
/* 329 */     return null;
/*     */   }
/*     */ }