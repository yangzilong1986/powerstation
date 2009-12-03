/*    */ package com.hisun.resource;
/*    */ 
/*    */ import com.hisun.message.HiContext;
/*    */ import java.util.Iterator;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ import org.dom4j.Attribute;
/*    */ import org.dom4j.Document;
/*    */ import org.dom4j.Element;
/*    */ 
/*    */ public class HiParam
/*    */ {
/*    */   public static void process(Document doc)
/*    */   {
/* 24 */     Element element = doc.getRootElement();
/* 25 */     String name = element.attributeValue("name");
/* 26 */     walkTree(name, element);
/*    */   }
/*    */ 
/*    */   private static void walkTree(String name, Element element)
/*    */   {
/* 35 */     replaceParam(name, element);
/*    */ 
/* 37 */     Iterator iter = element.elementIterator();
/* 38 */     while (iter.hasNext())
/* 39 */       walkTree(name, (Element)iter.next());
/*    */   }
/*    */ 
/*    */   private static void replaceParam(String name, Element element)
/*    */   {
/*    */     String value;
/* 49 */     Iterator iter = element.attributeIterator();
/*    */ 
/* 51 */     if (!(StringUtils.isEmpty(element.getTextTrim()))) {
/* 52 */       value = element.getTextTrim();
/* 53 */       if (value.startsWith("_")) {
/* 54 */         element.setText(getParamValue(name, value));
/*    */       }
/*    */     }
/*    */ 
/* 58 */     while (iter.hasNext()) {
/* 59 */       Attribute attr = (Attribute)iter.next();
/* 60 */       value = attr.getValue();
/* 61 */       if ((!(StringUtils.isEmpty(value))) && 
/* 62 */         (value.startsWith("_")))
/* 63 */         attr.setValue(getParamValue(name, value));
/*    */     }
/*    */   }
/*    */ 
/*    */   private static String getParamValue(String name, String param)
/*    */   {
/* 76 */     name = "ParaList." + name + "." + param;
/*    */ 
/* 78 */     String value = HiContext.getRootContext().getStrProp(name);
/* 79 */     if (StringUtils.isEmpty(value)) {
/* 80 */       value = param;
/*    */     }
/* 82 */     return value;
/*    */   }
/*    */ }