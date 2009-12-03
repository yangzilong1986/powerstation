/*    */ package com.hisun.common;
/*    */ 
/*    */ import com.hisun.exception.HiException;
/*    */ import java.io.InputStream;
/*    */ import java.net.URI;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import javax.servlet.ServletContext;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ import org.dom4j.Document;
/*    */ import org.dom4j.DocumentException;
/*    */ import org.dom4j.Element;
/*    */ import org.dom4j.io.SAXReader;
/*    */ 
/*    */ public class HiConvertorUtils
/*    */ {
/*    */   private static final String cfgfile = "convert.xml";
/*    */   private static final String TOIMAGE = "TOIMAGE";
/*    */   private static final String TOSTRING = "TOSTRING";
/*    */ 
/*    */   public static String getConfigValue(String convertId, String srcValue, ServletContext context)
/*    */     throws HiException
/*    */   {
/* 36 */     if (StringUtils.isEmpty(convertId)) return "";
/*    */ 
/* 39 */     if (StringUtils.isEmpty(srcValue)) srcValue = "null";
/* 40 */     List list = getComponentNodes("convert.xml", "/Root/CONVERT", context);
/*    */ 
/* 42 */     for (Iterator i = list.iterator(); i.hasNext(); ) {
/* 43 */       Element el = (Element)i.next();
/* 44 */       if (el.attributeValue("id").equals(convertId)) {
/* 45 */         String type = el.attributeValue("type");
/* 46 */         Iterator iter = el.elementIterator();
/* 47 */         String val = "";
/* 48 */         while (iter.hasNext()) {
/* 49 */           Element child = (Element)iter.next();
/*    */ 
/* 51 */           if (srcValue.equals(child.attributeValue("name"))) {
/* 52 */             val = child.attributeValue("value");
/*    */ 
/* 54 */             break;
/*    */           }
/*    */         }
/* 57 */         return dispatch(type, val);
/*    */       }
/*    */     }
/*    */ 
/* 61 */     return "";
/*    */   }
/*    */ 
/*    */   private static String dispatch(String type, String value)
/*    */   {
/* 66 */     return value;
/*    */   }
/*    */ 
/*    */   public static List getComponentNodes(String arg, String node, ServletContext context)
/*    */   {
/* 79 */     URI uri = null;
/*    */ 
/* 81 */     Document doc = null;
/* 82 */     SAXReader reader = new SAXReader();
/*    */     try {
/* 84 */       InputStream in = context.getResourceAsStream("/conf/convert.xml");
/* 85 */       doc = reader.read(in);
/*    */     } catch (DocumentException e) {
/* 87 */       e.printStackTrace();
/*    */     }
/*    */ 
/* 90 */     if (doc == null) {
/* 91 */       return null;
/*    */     }
/* 93 */     return doc.selectNodes(node);
/*    */   }
/*    */ }