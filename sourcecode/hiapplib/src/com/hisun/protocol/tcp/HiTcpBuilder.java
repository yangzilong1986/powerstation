/*    */ package com.hisun.protocol.tcp;
/*    */ 
/*    */ import com.hisun.protocol.tcp.parser.HiIPXMLParser;
/*    */ 
/*    */ public class HiTcpBuilder
/*    */ {
/*    */   private static HiIPXMLParser parser;
/*    */ 
/*    */   public static HiIPXMLParser getXMLParser()
/*    */   {
/* 31 */     if (parser == null)
/* 32 */       parser = new HiIPXMLParser();
/* 33 */     return parser;
/*    */   }
/*    */ }