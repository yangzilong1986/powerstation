/*    */ package com.hisun.parser.xml;
/*    */ 
/*    */ import java.io.PrintStream;
/*    */ import org.dom4j.DocumentException;
/*    */ import org.dom4j.io.SAXReader;
/*    */ 
/*    */ public class HiTestXML
/*    */ {
/*    */   public static void main(String[] args)
/*    */   {
/* 14 */     if (args.length == 0) {
/* 15 */       System.out.println("USAGE: testxml xmlfiles");
/* 16 */       return;
/*    */     }
/* 18 */     for (int i = 0; i < args.length; ++i)
/*    */       try {
/* 20 */         SAXReader saxReader = new SAXReader();
/* 21 */         saxReader.read(args[i]);
/* 22 */         System.out.println("valid xml file:[" + args[i] + "]");
/*    */       } catch (DocumentException e) {
/* 24 */         System.out.println("invalid xml file:[" + args[i] + "];error:[" + e.getMessage() + "]");
/*    */       }
/*    */   }
/*    */ }