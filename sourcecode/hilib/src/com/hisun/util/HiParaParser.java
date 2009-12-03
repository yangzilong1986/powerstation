/*    */ package com.hisun.util;
/*    */ 
/*    */ import com.hisun.message.HiContext;
/*    */ import java.util.Iterator;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ import org.dom4j.Element;
/*    */ 
/*    */ public class HiParaParser
/*    */ {
/*    */   public static void setSysParam(HiContext ctx, Element element)
/*    */   {
/* 20 */     Element element1 = element.element("Public");
/* 21 */     if (element1 != null)
/* 22 */       parsePara(ctx, "@SYS", element1);
/*    */   }
/*    */ 
/*    */   public static void setAppParam(HiContext ctx, String appName, Element element)
/*    */   {
/* 27 */     Iterator iter = element.elementIterator("Application");
/*    */     do { if (!(iter.hasNext())) break label75;
/* 29 */       element1 = (Element)iter.next(); }
/* 30 */     while (!(StringUtils.equals(appName, element1.attributeValue("name"))));
/*    */ 
/* 33 */     Element element1 = element1.element("Public");
/* 34 */     if (element1 != null) {
/* 35 */       parsePara(ctx, "@PARA", element1);
/*    */     }
/*    */ 
/* 40 */     label75: element1 = element.element("Public");
/* 41 */     if (element1 != null)
/* 42 */       parsePara(ctx, "@PARA", element1);
/*    */   }
/*    */ 
/*    */   public static void setTrnParam(HiContext ctx, String appName, String txnCode, Element element)
/*    */   {
/* 47 */     Iterator iter = element.elementIterator("Application");
/* 48 */     boolean findFlag = false;
/* 49 */     while (iter.hasNext())
/*    */     {
/*    */       Element element2;
/* 50 */       Element element1 = (Element)iter.next();
/* 51 */       if (!(StringUtils.equals(appName, element1.attributeValue("name")))) {
/*    */         continue;
/*    */       }
/* 54 */       Iterator iter1 = element1.elementIterator("Transaction");
/*    */       do { if (!(iter1.hasNext())) break label120;
/* 56 */         element2 = (Element)iter1.next(); }
/* 57 */       while (!(StringUtils.equals(txnCode, element2.attributeValue("code"))));
/*    */ 
/* 60 */       parsePara(ctx, "@PARA", element2);
/* 61 */       findFlag = true;
/*    */ 
/* 65 */       if (findFlag)
/*    */         label120: return;
/*    */     }
/*    */   }
/*    */ 
/*    */   public static void setSvrParam(HiContext ctx, String svrName, Element element)
/*    */   {
/* 72 */     Iterator iter = element.elementIterator("Server");
/* 73 */     while (iter.hasNext()) {
/* 74 */       element1 = (Element)iter.next();
/* 75 */       if (!(StringUtils.equals(svrName, element1.attributeValue("name")))) {
/*    */         continue;
/*    */       }
/*    */ 
/* 79 */       parsePara(ctx, "@PARA", element1);
/*    */     }
/* 81 */     Element element1 = element.element("Public");
/* 82 */     if (element1 != null)
/* 83 */       parsePara(ctx, "@PARA", element1);
/*    */   }
/*    */ 
/*    */   private static void parsePara(HiContext ctx, String namespace, Element element)
/*    */   {
/* 89 */     Iterator iter = element.elementIterator("Param");
/* 90 */     while (iter.hasNext()) {
/* 91 */       Element element1 = (Element)iter.next();
/* 92 */       String name = element1.attributeValue("name");
/* 93 */       String value = element1.attributeValue("value");
/* 94 */       if (name == null)
/*    */         continue;
/* 96 */       int i1 = value.indexOf("${");
/* 97 */       int i2 = value.indexOf("}");
/* 98 */       if (i1 + 2 < i2) {
/* 99 */         String pattern1 = value.substring(i1 + 2, i2);
/* 100 */         String pattern2 = value.substring(i1, i2 + 1);
/* 101 */         String value1 = ctx.getStrProp(namespace, pattern1);
/* 102 */         if (value1 != null) {
/* 103 */           value = StringUtils.replace(value, pattern2, value1);
/*    */         }
/*    */       }
/* 106 */       ctx.setProperty(namespace, name, value);
/*    */     }
/*    */   }
/*    */ }