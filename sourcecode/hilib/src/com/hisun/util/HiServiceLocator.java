/*    */ package com.hisun.util;
/*    */ 
/*    */ import com.hisun.exception.HiException;
/*    */ import java.io.Serializable;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Hashtable;
/*    */ import javax.ejb.EJBHome;
/*    */ import javax.ejb.EJBLocalHome;
/*    */ import javax.jms.ConnectionFactory;
/*    */ import javax.jms.Queue;
/*    */ import javax.jms.QueueConnectionFactory;
/*    */ import javax.jms.Topic;
/*    */ import javax.jms.TopicConnectionFactory;
/*    */ import javax.naming.InitialContext;
/*    */ import javax.sql.DataSource;
/*    */ 
/*    */ public abstract class HiServiceLocator
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = -6022385922541611335L;
/*    */   private static HiServiceLocator me;
/*    */ 
/*    */   public static synchronized HiServiceLocator getInstance()
/*    */     throws HiException
/*    */   {
/* 47 */     if (me == null) {
/* 48 */       String tmp = HiICSProperty.getProperty("framework");
/* 49 */       if ("EJB".equalsIgnoreCase(tmp))
/* 50 */         me = new HiServiceLocatorEJBImpl();
/*    */       else {
/* 52 */         me = new HiServiceLocatorPOJOImpl();
/*    */       }
/*    */     }
/* 55 */     return me;
/*    */   }
/*    */ 
/*    */   public static synchronized HiServiceLocator getInstance(Hashtable environment)
/*    */     throws HiException
/*    */   {
/* 61 */     if (me == null)
/*    */     {
/* 63 */       me = new HiServiceLocatorPOJOImpl(environment);
/*    */     }
/* 65 */     return me;
/*    */   }
/*    */ 
/*    */   public abstract InitialContext getInitalContext();
/*    */ 
/*    */   public abstract Object lookup(String paramString)
/*    */     throws HiException;
/*    */ 
/*    */   public abstract EJBLocalHome getLocalHome(String paramString)
/*    */     throws HiException;
/*    */ 
/*    */   public abstract EJBHome getRemoteHome(String paramString, Class paramClass)
/*    */     throws HiException;
/*    */ 
/*    */   public abstract Object getRemoteObject(String paramString, Class paramClass)
/*    */     throws HiException;
/*    */ 
/*    */   public abstract QueueConnectionFactory getQueueConnectionFactory(String paramString)
/*    */     throws HiException;
/*    */ 
/*    */   public abstract Queue getQueue(String paramString)
/*    */     throws HiException;
/*    */ 
/*    */   public abstract TopicConnectionFactory getTopicConnectionFactory(String paramString)
/*    */     throws HiException;
/*    */ 
/*    */   public abstract ConnectionFactory getConnectionFactory(String paramString)
/*    */     throws HiException;
/*    */ 
/*    */   public abstract Topic getTopic(String paramString)
/*    */     throws HiException;
/*    */ 
/*    */   public abstract DataSource getDataSource(String paramString)
/*    */     throws HiException;
/*    */ 
/*    */   public abstract DataSource getDBDataSource(String paramString)
/*    */     throws HiException;
/*    */ 
/*    */   public abstract void bind(String paramString, Object paramObject)
/*    */     throws HiException;
/*    */ 
/*    */   public abstract void unbind(String paramString)
/*    */     throws HiException;
/*    */ 
/*    */   public abstract void rebind(String paramString, Object paramObject)
/*    */     throws HiException;
/*    */ 
/*    */   public abstract ArrayList list(String paramString);
/*    */ }