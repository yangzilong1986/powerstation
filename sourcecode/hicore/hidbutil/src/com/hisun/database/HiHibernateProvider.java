 package com.hisun.database;
 
 import com.hisun.exception.HiException;
 import com.hisun.hilog4j.Logger;
 import java.sql.Connection;
 import java.util.Iterator;
 import java.util.List;
 import java.util.Stack;
 import org.hibernate.Criteria;
 import org.hibernate.HibernateException;
 import org.hibernate.Query;
 import org.hibernate.SQLQuery;
 import org.hibernate.Session;
 import org.hibernate.StaleStateException;
 import org.hibernate.Transaction;
 import org.hibernate.exception.ConstraintViolationException;
 
 public abstract class HiHibernateProvider extends HiJDBCProvider
   implements DataAccessProvider
 {
   private Session session;
   private Connection _connection;
   private Stack _sessions;
 
   public HiHibernateProvider()
   {
     this.session = null;
 
     this._connection = null;
   }
 
   private Session getSession() throws HiException
   {
     if (this.session != null) {
       return this.session;
     }
     log.debug("get new Session");
     this.session = HibernateManager.getSession(this._dsName);
     if (this.session == null) {
       throw HiException.makeException(new NullPointerException("can't found datasource :" + this._dsName));
     }
     try
     {
       this._connection = this.session.connection();
       this.session.beginTransaction();
     } catch (RuntimeException re) {
       throw HiException.makeException(re);
     }
 
     return this.session;
   }
 
   public void close()
   {
     try {
       if (this.session == null) {
         return;
       }
       log.debug("close session");
 
       this.session.getTransaction().commit();
       this.session.close();
       this.session = null;
     }
     catch (HibernateException e) {
     }
   }
 
   public void closeAll() {
     if ((this._sessions == null) || (this._sessions.isEmpty())) {
       close();
       return;
     }
     Iterator iter = this._sessions.iterator();
     while (iter.hasNext()) {
       HiSession tmpSession = (HiSession)iter.next();
       try {
         tmpSession.session.getTransaction().commit();
         tmpSession.session.close();
         tmpSession.session = null;
       } catch (Exception e) {
       }
     }
   }
 
   public void commit() throws HiException {
     Transaction _transaction = getSession().getTransaction();
     if (!(_transaction.isActive())) {
       return;
     }
     log.debug("Commit");
     try {
       _transaction.commit();
       getSession().beginTransaction();
     } catch (RuntimeException re) {
       throw new HiException("220319", re.getMessage(), re);
     }
   }
 
   public Connection getConnection() throws HiException
   {
     if (this._connection == null)
       getSession();
     return this._connection;
   }
 
   public Connection popConnection() {
     if ((this._sessions == null) || (this._sessions.isEmpty())) {
       return null;
     }
     HiSession tmpSession = (HiSession)this._sessions.pop();
 
     this._dsName = tmpSession.dsname;
     this._connection = this.session.connection();
     this.session = tmpSession.session;
     if (log.isDebugEnabled()) {
       log.debug("pop db:[" + this._dsName + "]:[" + this.session + "]");
     }
     return this._connection;
   }
 
   public void pushConnection() {
     if (this.session == null) {
       return;
     }
     if (this._sessions == null) {
       this._sessions = new Stack();
       this._sessions.push(new HiSession(this._dsName, this.session));
     }
     if (log.isDebugEnabled()) {
       log.debug("push db:[" + this._dsName + "]:[" + this.session + "]");
     }
     this.session = null;
     this._connection = null;
   }
 
   public void rollback() throws HiException {
     Transaction _transaction = getSession().getTransaction();
     if (!(_transaction.isActive())) {
       return;
     }
     log.debug("Rollback");
     try {
       _transaction.rollback();
       getSession().clear();
 
       getSession().beginTransaction();
     } catch (RuntimeException re) {
       throw new HiException("220319", re.getMessage(), re);
     }
   }
 
   public void setDBConnection(String name)
   {
     super.setDBConnection(name);
   }
 
   public int delete(Object o) throws HiException {
     return delete(new Object[] { o });
   }
 
   public int delete(List objs) throws HiException {
     return delete(objs.toArray());
   }
 
   public int delete(Object[] objs) throws HiException {
     Session tmpSession = getSession();
     try {
       for (int i = 0; i < objs.length; ++i) {
         tmpSession.delete(objs[i]);
         if (log.isDebugEnabled()) {
           log.debug("delete object:[" + objs[i] + "]");
         }
       }
       tmpSession.flush();
       return objs.length;
     } catch (RuntimeException re) {
       if (re instanceof StaleStateException) {
         return 0;
       }
       throw new HiException("215021", re.getMessage(), re);
     }
   }
 
   public List list(Class clazz) throws HiException
   {
     try {
       return getSession().createCriteria(clazz).list();
     } catch (RuntimeException re) {
       throw new HiException("215016", re.getMessage(), re);
     }
   }
 
   public List list(Class clazz, String sql) throws HiException
   {
     return list(clazz, sql, (List)null);
   }
 
   public List list(Class clazz, String sql, Object[] params) throws HiException
   {
     try {
       Query query = null;
       if (sql != null)
         query = getSession().createSQLQuery(sql).addEntity(clazz);
       else {
         return null;
       }
       if (log.isDebugEnabled()) {
         log.debug("query object:[sql:" + sql + "]param:" + params);
       }
       if ((params != null) && (params.length != 0)) {
         for (int i = 0; i < params.length; ++i) {
           query.setParameter(i, params[i]);
         }
       }
 
       return query.list();
     } catch (RuntimeException re) {
       throw new HiException("215016", re.getMessage(), re);
     }
   }
 
   public List list(Class clazz, String sql, List params) throws HiException
   {
     try {
       Query query = null;
       if (sql != null)
         query = getSession().createSQLQuery(sql).addEntity(clazz);
       else {
         return null;
       }
       if (log.isDebugEnabled()) {
         log.debug("query object:[sql:" + sql + "]param:" + params);
       }
       if ((params != null) && (params.size() != 0)) {
         for (int i = 0; i < params.size(); ++i) {
           query.setParameter(i, params.get(i));
         }
       }
 
       return query.list();
     } catch (RuntimeException re) {
       throw new HiException("215016", re.getMessage(), re);
     }
   }
 
   public int save(Object o) throws HiException
   {
     return save(new Object[] { o });
   }
 
   public boolean insert(Object o) throws HiException {
     return (getSession().save(o) != null);
   }
 
   public int save(List objs) throws HiException
   {
     return save(objs.toArray());
   }
 
   public int save(Object[] objs) throws HiException {
     int i = 0;
     Object obj = null;
     try {
       for (; i < objs.length; ++i) {
         obj = objs[i];
         getSession().update(obj);
         if (log.isDebugEnabled()) {
           log.debug("save object:[" + objs[i] + "]");
         }
       }
       getSession().flush();
 
       return objs.length;
     } catch (HibernateException re) {
       if (re instanceof ConstraintViolationException) {
         ConstraintViolationException cve = (ConstraintViolationException)re;
         log.error(cve.getSQL(), objs[0], re.getCause());
         return 0;
       }
       throw new HiException("215021", re.getMessage(), re);
     }
   }
 
   public Object get(Class clazz, int id) throws HiException
   {
     try {
       if (log.isDebugEnabled()) {
         log.debug("query object:[" + clazz.getSimpleName() + "][id:" + id + "]");
       }
 
       return getSession().get(clazz, new Integer(id));
     } catch (RuntimeException re) {
       throw new HiException("215016", re.getMessage(), re);
     }
   }
 
   public Object get(String statementid)
     throws HiException
   {
     return null;
   }
 
   public Object get(String statementid, Object paramObj) throws HiException
   {
     return null;
   }
 
   public Object get(String statementid, Object paramObj, Object returnObj)
     throws HiException
   {
     return null;
   }
 
   public List list(String statementid) throws HiException
   {
     return null;
   }
 
   public List list(String statementid, Object paramObj) throws HiException
   {
     return null;
   }
 
   public List list(String statementid, int skip, int max) throws HiException
   {
     return null;
   }
 
   public List list(String statementid, Object paramObj, int skip, int max)
     throws HiException
   {
     return null;
   }
 }