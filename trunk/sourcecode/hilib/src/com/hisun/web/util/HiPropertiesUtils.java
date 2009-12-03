/*    */ package com.hisun.web.util;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.net.URISyntaxException;
/*    */ import java.util.Properties;
/*    */ import javax.servlet.ServletContext;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ 
/*    */ public class HiPropertiesUtils
/*    */ {
/*    */   private static Properties loadProperties(ServletContext context)
/*    */     throws URISyntaxException, IOException
/*    */   {
/* 15 */     InputStream in = context.getResourceAsStream("/conf/conf.properties");
/* 16 */     Properties properties = new Properties();
/* 17 */     properties.load(in);
/* 18 */     return properties;
/*    */   }
/*    */ 
/*    */   public static String getProperties(String name, ServletContext context) throws URISyntaxException, IOException {
/* 22 */     Properties properties = loadProperties(context);
/* 23 */     String val = properties.getProperty(name);
/* 24 */     return StringUtils.trim(val);
/*    */   }
/*    */ }