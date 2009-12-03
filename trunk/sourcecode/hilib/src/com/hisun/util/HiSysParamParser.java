/*    */ package com.hisun.util;
/*    */ 
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.message.HiContext;
/*    */ import java.util.Iterator;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ import org.dom4j.Element;
/*    */ 
/*    */ public class HiSysParamParser
/*    */ {
/*    */   public static void load(HiContext ctx, String serverName)
/*    */     throws HiException
/*    */   {
/* 19 */     String file = HiICSProperty.getProperty("sys.config");
/* 20 */     Element root = HiXmlHelper.getRootElement(file);
/* 21 */     if (root == null)
/* 22 */       return;
/* 23 */     load(ctx, root, serverName);
/*    */   }
/*    */ 
/*    */   public static void load(HiContext ctx, Element root, String serverName)
/*    */   {
/* 28 */     Element element1 = root.element("Public");
/* 29 */     if (element1 != null) {
/* 30 */       parsePara(ctx, "@PARA", element1);
/*    */     }
/*    */ 
/* 33 */     Iterator iter = root.elementIterator();
/* 34 */     while (iter.hasNext()) {
/* 35 */       element1 = (Element)iter.next();
/* 36 */       if (!(StringUtils.equals(serverName, element1.getName()))) {
/*    */         continue;
/*    */       }
/* 39 */       parsePara(ctx, "@PARA", element1);
/*    */     }
/*    */   }
/*    */ 
/*    */   private static void parsePara(HiContext ctx, String namespace, Element element) {
/* 44 */     Iterator iter = element.elementIterator();
/* 45 */     HiSymbolExpander symbolExpander = new HiSymbolExpander(ctx, namespace);
/* 46 */     while (iter.hasNext()) {
/* 47 */       Element element1 = (Element)iter.next();
/* 48 */       String name = element1.getName();
/* 49 */       String value = element1.getTextTrim();
/* 50 */       if (name == null)
/*    */         continue;
/* 52 */       ctx.setProperty(namespace, name, symbolExpander.expandSymbols(value));
/*    */     }
/*    */   }
/*    */ }