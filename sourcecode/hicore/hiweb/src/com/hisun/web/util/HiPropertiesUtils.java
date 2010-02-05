 package com.hisun.web.util;

 import org.apache.commons.lang.StringUtils;

 import javax.servlet.ServletContext;
 import java.io.IOException;
 import java.io.InputStream;
 import java.net.URISyntaxException;
 import java.util.Properties;
 
 public class HiPropertiesUtils
 {
   private static Properties loadProperties(ServletContext context)
     throws URISyntaxException, IOException
   {
     InputStream in = context.getResourceAsStream("/conf/conf.properties");
     Properties properties = new Properties();
     properties.load(in);
     return properties;
   }
 
   public static String getProperties(String name, ServletContext context) throws URISyntaxException, IOException {
     Properties properties = loadProperties(context);
     String val = properties.getProperty(name);
     return StringUtils.trim(val);
   }
 }