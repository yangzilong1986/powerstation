/*    */ package com.hisun.server.manage.servlet;
/*    */ 
/*    */ import com.hisun.util.HiICSProperty;
/*    */ import com.hisun.util.HiResource;
/*    */ import java.net.URL;
/*    */ import java.util.Iterator;
/*    */ import java.util.Properties;
/*    */ import org.dom4j.Document;
/*    */ import org.dom4j.DocumentException;
/*    */ import org.dom4j.Element;
/*    */ import org.dom4j.io.SAXReader;
/*    */ 
/*    */ public class HiSYSCFG
/*    */ {
/*    */   private Properties property;
/*    */   private static HiSYSCFG instance;
/*    */ 
/*    */   public HiSYSCFG()
/*    */   {
/* 21 */     this.property = new Properties();
/*    */   }
/*    */ 
/*    */   public static synchronized HiSYSCFG getInstance()
/*    */   {
/* 26 */     instance = new HiSYSCFG();
/* 27 */     instance.parse(HiICSProperty.getProperty("sys.config"));
/* 28 */     return instance;
/*    */   }
/*    */ 
/*    */   public String getProperty(String key) {
/* 32 */     return this.property.getProperty("Public." + key);
/*    */   }
/*    */ 
/*    */   public String getProperty(String serverName, String key) {
/* 36 */     if (!(this.property.contains(serverName + "." + key))) {
/* 37 */       return this.property.getProperty("Public." + key);
/*    */     }
/* 39 */     return this.property.getProperty(serverName + "." + key);
/*    */   }
/*    */ 
/*    */   public void parse(String file)
/*    */   {
/* 44 */     SAXReader reader = new SAXReader();
/* 45 */     URL url = HiResource.getResource(file);
/* 46 */     if (url == null);
/* 49 */     Document doc = null;
/*    */     try {
/* 51 */       doc = reader.read(url);
/*    */     } catch (DocumentException e) {
/* 53 */       e.printStackTrace();
/*    */     }
/* 55 */     Element root = doc.getRootElement();
/* 56 */     Iterator iter = root.elementIterator();
/* 57 */     while (iter.hasNext()) {
/* 58 */       Element element = (Element)iter.next();
/* 59 */       String serverName = element.getName();
/* 60 */       Iterator iter1 = element.elementIterator();
/* 61 */       while (iter1.hasNext()) {
/* 62 */         Element element1 = (Element)iter1.next();
/* 63 */         this.property.setProperty(serverName + "." + element1.getName(), element1.getTextTrim());
/*    */       }
/*    */     }
/*    */   }
/*    */ }