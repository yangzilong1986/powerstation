/*    */ package com.hisun.util;
/*    */ 
/*    */ import com.hisun.message.HiContext;
/*    */ import java.util.HashMap;
/*    */ import java.util.Iterator;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ import org.dom4j.Element;
/*    */ 
/*    */ public class HiComponentParaParser
/*    */ {
/*    */   public static void setAppParam(HiContext ctx, String appName, Element element)
/*    */   {
/* 20 */     Iterator iter = element.elementIterator("Application");
/*    */     do { if (!(iter.hasNext())) break label75;
/* 22 */       element1 = (Element)iter.next(); }
/* 23 */     while (!(StringUtils.equals(appName, element1.attributeValue("name"))));
/*    */ 
/* 26 */     Element element1 = element1.element("Public");
/* 27 */     if (element1 != null) {
/* 28 */       parsePara(ctx, "@CMP", element1);
/*    */     }
/*    */ 
/* 33 */     label75: element1 = element.element("Public");
/* 34 */     if (element1 != null)
/* 35 */       parsePara(ctx, "@CMP", element1);
/*    */   }
/*    */ 
/*    */   public static void setTrnParam(HiContext ctx, String appName, String txnCode, Element element)
/*    */   {
/* 40 */     Iterator iter = element.elementIterator("Application");
/* 41 */     boolean findFlag = false;
/* 42 */     while (iter.hasNext())
/*    */     {
/*    */       Element element2;
/* 43 */       Element element1 = (Element)iter.next();
/* 44 */       if (!(StringUtils.equals(appName, element1.attributeValue("name")))) {
/*    */         continue;
/*    */       }
/* 47 */       Iterator iter1 = element1.elementIterator("Transaction");
/*    */       do { if (!(iter1.hasNext())) break label120;
/* 49 */         element2 = (Element)iter1.next(); }
/* 50 */       while (!(StringUtils.equals(txnCode, element2.attributeValue("code"))));
/*    */ 
/* 53 */       parsePara(ctx, "@CMP", element2);
/* 54 */       findFlag = true;
/*    */ 
/* 58 */       if (findFlag)
/*    */         label120: return;
/*    */     }
/*    */   }
/*    */ 
/*    */   private static void parsePara(HiContext ctx, String namespace, Element element)
/*    */   {
/* 65 */     Iterator iter = element.elementIterator("Component");
/* 66 */     while (iter.hasNext()) {
/* 67 */       Element element1 = (Element)iter.next();
/* 68 */       String name = element1.attributeValue("name");
/* 69 */       if (name == null)
/*    */         continue;
/* 71 */       Iterator iter1 = element1.elementIterator("Param");
/* 72 */       HashMap map = new HashMap(2);
/* 73 */       while (iter1.hasNext()) {
/* 74 */         Element element2 = (Element)iter1.next();
/* 75 */         String name1 = element2.attributeValue("name");
/* 76 */         if (name1 == null)
/*    */           continue;
/* 78 */         map.put(name1.toUpperCase(), element2.attributeValue("value"));
/*    */       }
/* 80 */       ctx.setProperty(namespace, name, map);
/*    */     }
/*    */   }
/*    */ }