/*    */ package com.hisun.database;
/*    */ 
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.message.HiContext;
/*    */ import com.hisun.util.HiICSProperty;
/*    */ import com.ibatis.common.resources.Resources;
/*    */ import com.ibatis.sqlmap.client.SqlMapClient;
/*    */ import com.ibatis.sqlmap.client.SqlMapClientBuilder;
/*    */ import com.ibatis.sqlmap.client.SqlMapSession;
/*    */ import java.io.IOException;
/*    */ import java.io.Reader;
/*    */ import java.util.HashMap;
/*    */ import java.util.Properties;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ 
/*    */ public class HiIbatisManager
/*    */ {
/* 21 */   private static final HashMap clients = new HashMap(3);
/*    */   private static final String PRODUCT_CONFIG = "conf/SQLMAP_CFG.XML";
/*    */   private static final String JUNIT_CONFIG = "sqlMapConfig_junit.xml";
/*    */ 
/*    */   public static SqlMapSession getSession(String name)
/*    */     throws HiException
/*    */   {
/*    */     Reader reader;
/* 26 */     SqlMapClient client = null;
/* 27 */     synchronized (clients) {
/* 28 */       client = (SqlMapClient)clients.get(name);
/*    */     }
/* 30 */     if (client != null) {
/* 31 */       return client.openSession();
/*    */     }
/* 33 */     Properties properties = new Properties();
/*    */ 
/* 35 */     if (!(HiICSProperty.isJUnitEnv())) {
/* 36 */       String dataSourceName = HiContext.getCurrentContext().getStrProp("@PARA", name);
/*    */ 
/* 38 */       if (StringUtils.isEmpty(dataSourceName)) {
/* 39 */         throw new HiException("212008", name);
/*    */       }
/*    */ 
/* 42 */       properties.setProperty("dsname", dataSourceName);
/*    */       try {
/* 44 */         reader = Resources.getResourceAsReader("conf/SQLMAP_CFG.XML");
/*    */       }
/*    */       catch (Exception e) {
/* 47 */         throw HiException.makeException("220037", e.getMessage(), e);
/*    */       }
/*    */     }
/*    */     else {
/* 51 */       properties = System.getProperties();
/*    */       try {
/* 53 */         reader = Resources.getResourceAsReader("sqlMapConfig_junit.xml");
/*    */       } catch (Exception e) {
/* 55 */         throw HiException.makeException("220037", e.getMessage(), e);
/*    */       }
/*    */     }
/*    */     try
/*    */     {
/* 60 */       client = SqlMapClientBuilder.buildSqlMapClient(reader, properties);
/*    */     }
/*    */     catch (RuntimeException e)
/*    */     {
/*    */     }
/*    */     finally
/*    */     {
/* 67 */       if (reader != null) {
/*    */         try {
/* 69 */           reader.close();
/*    */         }
/*    */         catch (IOException e) {
/* 72 */           e.printStackTrace();
/*    */         }
/* 74 */         reader = null;
/*    */       }
/*    */     }
/* 77 */     synchronized (clients) {
/* 78 */       clients.put(name, client);
/*    */     }
/*    */ 
/* 81 */     return client.openSession();
/*    */   }
/*    */ }