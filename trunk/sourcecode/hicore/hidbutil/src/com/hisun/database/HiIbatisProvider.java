 package com.hisun.database;
 
 import com.hisun.exception.HiException;
 import com.hisun.exception.HiSQLException;
 import com.hisun.hilog4j.Logger;
 import com.hisun.util.HiICSProperty;
 import com.ibatis.sqlmap.client.SqlMapSession;
 import java.sql.Connection;
 import java.sql.SQLException;
 import java.util.List;
 import java.util.Stack;
 import javax.sql.DataSource;
 
 public class HiIbatisProvider extends HiJDBCProvider
   implements DataAccessProvider
 {
   private SqlMapSession session;
   private Connection currConnection;
   private Stack _sessions;
 
   public HiIbatisProvider()
   {
     this.session = null;
 
     this.currConnection = null;
 
     this._sessions = null; }
 
   private SqlMapSession getSession() throws HiException {
     if (this.session != null) {
       return this.session;
     }
     log.debug("get new Session");
     this.session = HiIbatisManager.getSession(this._dsName);
     if (this.session == null) {
       throw HiException.makeException(new NullPointerException("can't found datasource :" + this._dsName));
     }
 
     try
     {
       this.currConnection = this.session.getDataSource().getConnection();
       this.currConnection.setAutoCommit(false);
       this.session.setUserConnection(this.currConnection);
     }
     catch (RuntimeException re) {
       throw new HiSQLException("220037", re, "");
     }
     catch (SQLException e)
     {
       throw new HiSQLException("220037", e, e.getSQLState());
     }
 
     return this.session;
   }
 
   public void close() {
     log.debug("close session :" + this._dsName);
     if (this.session != null)
       this.session.close();
     try {
       if (this.currConnection != null) {
         this.currConnection.commit();
         this.currConnection.close();
       }
     } catch (SQLException e) {
     }
     this.session = null;
     this.currConnection = null;
     if ((this._sessions != null) && (!(this._sessions.isEmpty())))
       this._sessions.remove(this._dsName);
   }
 
   public void closeAll()
   {
     if ((this._sessions == null) || (this._sessions.isEmpty())) {
       close();
       return;
     }
     close();
     for (int i = 0; i < this._sessions.size(); ++i) {
       HiIbatisSession hSession = (HiIbatisSession)this._sessions.remove(i);
       SqlMapSession session = hSession.session;
       try {
         session.getCurrentConnection().commit();
         session.getCurrentConnection().close();
       }
       catch (SQLException e) {
       }
       session.close();
       session = null;
     }
   }
 
   public void commit() throws HiException
   {
     log.debug("session commit:" + this._dsName);
     try {
       log.debug("COMMIt:" + getSession().getCurrentConnection().hashCode());
 
       getSession().getCurrentConnection().commit();
     }
     catch (SQLException e) {
       throw new HiSQLException("220037", e, e.getSQLState());
     }
   }
 
   public Connection getConnection()
     throws HiException
   {
     SqlMapSession tmpSession = getSession();
     if (this.currConnection == null) {
       try {
         this.currConnection = tmpSession.getCurrentConnection();
       } catch (SQLException e) {
         throw new HiSQLException("220037", e, e.getSQLState());
       }
     }
 
     return this.currConnection;
   }
 
   public Connection popConnection() {
     if ((this._sessions == null) || (this._sessions.isEmpty())) {
       return null;
     }
     HiIbatisSession tmpSession = (HiIbatisSession)this._sessions.pop();
 
     this._dsName = tmpSession.dsname;
     try {
       this.currConnection = this.session.getCurrentConnection();
     } catch (SQLException e) {
       log.fatal(e.getLocalizedMessage());
       return null;
     }
     this.session = tmpSession.session;
     if (log.isDebugEnabled()) {
       log.debug("pop db:[" + this._dsName + "]:[" + this.session + "]");
     }
     return this.currConnection;
   }
 
   public void pushConnection() {
     if (this.session == null) {
       return;
     }
     if (this._sessions == null) {
       this._sessions = new Stack();
       this._sessions.push(new HiIbatisSession(this._dsName, this.session));
     }
     if (log.isDebugEnabled()) {
       log.debug("push db:[" + this._dsName + "]:[" + this.session + "]");
     }
     this.session = null;
     this.currConnection = null;
   }
 
   public void rollback() throws HiException
   {
     log.debug("session rollback:" + this._dsName);
     try
     {
       getSession().getCurrentConnection().rollback();
     } catch (SQLException e) {
       throw new HiSQLException("220037", e, e.getSQLState());
     }
   }
 
   public int delete(String statementid)
     throws HiException
   {
     try
     {
       return getSession().delete(statementid);
     } catch (SQLException e) {
       throw new HiSQLException("220042", e, e.getSQLState());
     }
   }
 
   public int delete(String statementid, Object paramObj) throws HiException
   {
     return delete(statementid, new Object[] { paramObj });
   }
 
   public int delete(String statementid, Object[] paramObjs)
     throws HiException
   {
     SqlMapSession tmpSession = getSession();
     int p = 0;
     for (int i = 0; i < paramObjs.length; ++i) {
       try {
         p += tmpSession.delete(statementid, paramObjs[i]);
       } catch (SQLException e) {
         SQLException se = getException(e);
         throw new HiSQLException("220042", se, se.getSQLState());
       }
     }
 
     return p;
   }
 
   public int delete(String statementid, List paramObjs) throws HiException {
     Object[] objs = paramObjs.toArray();
     return delete(statementid, objs);
   }
 
   public Object get(String statementid) throws HiException
   {
     try
     {
       return getSession().queryForObject(statementid);
     } catch (SQLException e) {
       SQLException se = getException(e);
       throw new HiSQLException("220038", se, se.getSQLState());
     }
   }
 
   public Object get(String statementid, Object paramObj) throws HiException
   {
     try
     {
       return getSession().queryForObject(statementid, paramObj);
     } catch (SQLException e) {
       SQLException se = getException(e);
       throw new HiSQLException("220038", se, se.getSQLState());
     }
   }
 
   public Object get(String statementid, Object paramObj, Object returnObj)
     throws HiException
   {
     try
     {
       return getSession().queryForObject(statementid, paramObj, returnObj);
     }
     catch (SQLException e) {
       SQLException se = getException(e);
       throw new HiSQLException("220038", se, se.getSQLState());
     }
   }
 
   public int insert(String statementid) throws HiException
   {
     try
     {
       getSession().insert(statementid);
       return 1;
     } catch (SQLException e) {
       SQLException se = getException(e);
       if (se.getSQLState().equals(HiICSProperty.getProperty("sql.duppk"))) {
         log.error("220042", se.getMessage(), se);
         return 0;
       }
       throw new HiSQLException("220042", se, se.getSQLState());
     }
   }
 
   public int insert(String statementid, Object obj) throws HiException
   {
     return insert(statementid, new Object[] { obj });
   }
 
   public int insert(String statementid, Object[] objs)
     throws HiException
   {
     SqlMapSession tmpSession = getSession();
     int p = 0;
     for (int i = 0; i < objs.length; ++i) {
       try {
         tmpSession.insert(statementid, objs[i]);
         ++p;
       } catch (SQLException e) {
         SQLException se = getException(e);
         if (se.getSQLState().equals(HiICSProperty.getProperty("sql.duppk"))) {
           log.error("220042", e.getMessage());
           return 0;
         }
         throw new HiSQLException("220042", se, se.getSQLState());
       }
     }
 
     return p;
   }
 
   public int insert(String statementid, List objs) throws HiException {
     Object[] _objs = objs.toArray();
     return insert(statementid, _objs);
   }
 
   public List list(String statementid) throws HiException
   {
     try {
       return getSession().queryForList(statementid);
     } catch (SQLException e) {
       SQLException se = getException(e);
       throw new HiSQLException("220038", se, se.getSQLState());
     }
   }
 
   public List list(String statementid, Object paramObj) throws HiException
   {
     try
     {
       return getSession().queryForList(statementid, paramObj);
     } catch (SQLException e) {
       SQLException se = getException(e);
       throw new HiSQLException("220038", se, se.getSQLState());
     }
   }
 
   public List list(String statementid, int skip, int max) throws HiException
   {
     try
     {
       return getSession().queryForList(statementid, skip, max);
     } catch (SQLException e) {
       SQLException se = getException(e);
       throw new HiSQLException("220038", se, se.getSQLState());
     }
   }
 
   public List list(String statementid, Object paramObj, int skip, int max)
     throws HiException
   {
     try
     {
       return getSession().queryForList(statementid, paramObj, skip, max);
     } catch (SQLException e) {
       SQLException se = getException(e);
       throw new HiSQLException("220038", se, se.getSQLState());
     }
   }
 
   public int update(String statementid) throws HiException
   {
     try
     {
       return getSession().update(statementid);
     } catch (SQLException e) {
       SQLException se = getException(e);
       throw new HiSQLException("220038", se, se.getSQLState());
     }
   }
 
   public int update(String statementid, Object paramObj)
     throws HiException
   {
     return update(statementid, new Object[] { paramObj });
   }
 
   public int update(String statementid, Object[] paramObjs)
     throws HiException
   {
     int p = 0;
     SqlMapSession tmpSession = getSession();
     for (int i = 0; i < paramObjs.length; ++i) {
       try {
         p += tmpSession.update(statementid, paramObjs[i]);
       } catch (SQLException e) {
         SQLException se = getException(e);
         throw new HiSQLException("220042", se, se.getSQLState());
       }
     }
 
     return p;
   }
 
   public int update(String statementid, List paramObjs) throws HiException
   {
     Object[] _objs = paramObjs.toArray();
     return update(statementid, _objs);
   }
 
   public SQLException getException(SQLException se)
   {
     SQLException se1 = se;
 
     return se1;
   }
 }