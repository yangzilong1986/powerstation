/*    */ package com.hisun.ccb.atc.multi;
/*    */ 
/*    */ public class HiQueryFactory
/*    */ {
/*    */   public static HiIMultiQuery getProcessor(HiMultiDTO md)
/*    */   {
/* 24 */     if (md.getDataSourceType().equalsIgnoreCase("fileSource"))
/*    */     {
/* 26 */       return new HiMultiFileQueryImp(md); }
/* 27 */     if (md.getDataSourceType().equalsIgnoreCase("dbSource"));
/* 32 */     return null;
/*    */   }
/*    */ }