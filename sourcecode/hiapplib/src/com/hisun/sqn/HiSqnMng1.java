/*    */ package com.hisun.sqn;
/*    */ 
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.hilog4j.HiLog;
/*    */ import com.hisun.hilog4j.Logger;
/*    */ import com.hisun.message.HiETF;
/*    */ import com.hisun.message.HiMessage;
/*    */ import com.hisun.message.HiMessageContext;
/*    */ 
/*    */ public class HiSqnMng1
/*    */ {
/* 12 */   private static HiSqnMng2 sqnMng2 = null;
/* 13 */   private static Logger log = HiLog.getErrorLogger("SYS.log");
/*    */ 
/*    */   public static void updActDat(String oldDat, String newDat)
/*    */     throws HiException
/*    */   {
/* 25 */     sqnMng2.updActDat(oldDat, newDat);
/*    */   }
/*    */ 
/*    */   public static String getDumTlr(String brNo, String aplCod, String aplSub) throws HiException
/*    */   {
/* 30 */     return sqnMng2.getDumTlr(brNo, aplCod, aplSub);
/*    */   }
/*    */ 
/*    */   public static String getLogNo(int num) throws HiException {
/* 34 */     return sqnMng2.getLogNo(num);
/*    */   }
/*    */ 
/*    */   public static String getLogNo(HiMessageContext ctx, int num) throws HiException {
/* 38 */     String actDat = ctx.getCurrentMsg().getETFBody().getChildValue("ACC_DT");
/* 39 */     return sqnMng2.getLogNo(actDat, num);
/*    */   }
/*    */ 
/*    */   static
/*    */   {
/* 15 */     sqnMng2 = new HiSqnMng2();
/*    */     try {
/* 17 */       sqnMng2.init();
/*    */     } catch (HiException e) {
/* 19 */       log.error(e, e);
/*    */     }
/*    */   }
/*    */ }