/*    */ package com.hisun.database;
/*    */ 
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.message.HiContext;
/*    */ import com.hisun.util.HiICSProperty;
/*    */ import java.util.HashMap;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ import org.hibernate.Session;
/*    */ import org.hibernate.SessionFactory;
/*    */ import org.hibernate.cfg.Configuration;
/*    */ 
/*    */ public class HibernateManager
/*    */ {
/* 18 */   private static final HashMap factorys = new HashMap(3);
/*    */ 
/*    */   public static Session getSession(String dsname)
/*    */     throws HiException
/*    */   {
/*    */     SessionFactory factory;
/* 21 */     SessionFactory _factory = (SessionFactory)factorys.get(dsname);
/* 22 */     if (_factory != null) {
/* 23 */       return _factory.openSession();
/*    */     }
/* 25 */     Configuration cfg = new Configuration();
/* 26 */     String userId = System.getProperty("userId");
/* 27 */     String password = System.getProperty("password");
/* 28 */     if (HiICSProperty.isJUnitEnv())
/*    */     {
/* 30 */       String url = System.getProperty("db_url");
/* 31 */       String driver = System.getProperty("db_driver");
/* 32 */       cfg.setProperty("hibernate.connection.driver_class", driver);
/*    */ 
/* 34 */       cfg.setProperty("hibernate.connection.url", url);
/*    */     }
/*    */     else
/*    */     {
/* 40 */       String dataSourceName = HiContext.getCurrentContext().getStrProp("@PARA", dsname);
/*    */ 
/* 42 */       if (StringUtils.isEmpty(dataSourceName)) {
/* 43 */         throw new HiException("212008", dsname);
/*    */       }
/*    */ 
/* 47 */       cfg.setProperty("hibernate.connection.datasource", dataSourceName);
/*    */     }
/*    */ 
/*    */     try
/*    */     {
/* 63 */       factory = cfg.configure("/conf/hibernate.cfg.xml").buildSessionFactory();
/* 64 */       factorys.put(dsname, factory);
/*    */     } catch (Exception e) {
/* 66 */       throw HiException.makeException("220037", e.getMessage(), e);
/*    */     }
/*    */ 
/* 69 */     return factory.openSession();
/*    */   }
/*    */ }