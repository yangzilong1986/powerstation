/*    */ package com.hisun.mon.atc;
/*    */ 
/*    */ import com.hisun.atc.common.HiArgUtils;
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.hilib.HiATLParam;
/*    */ import com.hisun.message.HiETF;
/*    */ import com.hisun.message.HiMessage;
/*    */ import com.hisun.message.HiMessageContext;
/*    */ 
/*    */ public class GetStatTimeCond
/*    */ {
/*    */   public int execute(HiATLParam args, HiMessageContext ctx)
/*    */     throws HiException
/*    */   {
/* 15 */     String[] arr = { "000000", "010000", "020000", "030000", "040000", "050000", "060000", "070000", "080000", 
/* 16 */       "090000", "100000", "110000", "120000", "130000", "140000", "150000", "160000", "170000", "180000", 
/* 17 */       "190000", "200000", "210000", "220000", "230000", "240000" };
/*    */ 
/* 19 */     int num = Integer.parseInt(HiArgUtils.getStringNotNull(args, "CND_NUM"));
/* 20 */     if ((num <= 0) || (num > 24)) {
/* 21 */       return 2;
/*    */     }
/* 23 */     HiETF etf = ctx.getCurrentMsg().getETFBody();
/* 24 */     etf.setChildValue("BEG_STS", arr[(num - 1)]);
/* 25 */     etf.setChildValue("END_STS", arr[num]);
/* 26 */     return 0;
/*    */   }
/*    */ }