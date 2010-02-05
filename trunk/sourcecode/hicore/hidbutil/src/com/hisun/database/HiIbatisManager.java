 package com.hisun.database;
 
 import com.hisun.exception.HiException;
 import com.hisun.message.HiContext;
 import com.hisun.util.HiICSProperty;
 import com.ibatis.common.resources.Resources;
 import com.ibatis.sqlmap.client.SqlMapClient;
 import com.ibatis.sqlmap.client.SqlMapClientBuilder;
 import com.ibatis.sqlmap.client.SqlMapSession;
 import java.io.IOException;
 import java.io.Reader;
 import java.util.HashMap;
 import java.util.Properties;
 import org.apache.commons.lang.StringUtils;
 
 public class HiIbatisManager
 {
   private static final HashMap clients = new HashMap(3);
   private static final String PRODUCT_CONFIG = "conf/SQLMAP_CFG.XML";
   private static final String JUNIT_CONFIG = "sqlMapConfig_junit.xml";
 
   public static SqlMapSession getSession(String name)
     throws HiException
   {
     Reader reader;
     SqlMapClient client = null;
     synchronized (clients) {
       client = (SqlMapClient)clients.get(name);
     }
     if (client != null) {
       return client.openSession();
     }
     Properties properties = new Properties();
 
     if (!(HiICSProperty.isJUnitEnv())) {
       String dataSourceName = HiContext.getCurrentContext().getStrProp("@PARA", name);
 
       if (StringUtils.isEmpty(dataSourceName)) {
         throw new HiException("212008", name);
       }
 
       properties.setProperty("dsname", dataSourceName);
       try {
         reader = Resources.getResourceAsReader("conf/SQLMAP_CFG.XML");
       }
       catch (Exception e) {
         throw HiException.makeException("220037", e.getMessage(), e);
       }
     }
     else {
       properties = System.getProperties();
       try {
         reader = Resources.getResourceAsReader("sqlMapConfig_junit.xml");
       } catch (Exception e) {
         throw HiException.makeException("220037", e.getMessage(), e);
       }
     }
     try
     {
       client = SqlMapClientBuilder.buildSqlMapClient(reader, properties);
     }
     catch (RuntimeException e)
     {
     }
     finally
     {
       if (reader != null) {
         try {
           reader.close();
         }
         catch (IOException e) {
           e.printStackTrace();
         }
         reader = null;
       }
     }
     synchronized (clients) {
       clients.put(name, client);
     }
 
     return client.openSession();
   }
 }