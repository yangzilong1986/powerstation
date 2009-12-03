/*    */ package com.hisun.cnaps.messages;
/*    */ 
/*    */ public class HiCnapsMessageAreaFactory
/*    */ {
/*    */   public static HiCnapsMessageArea createCnapsMessageArea(String mark)
/*    */   {
/* 13 */     if (mark.equals("{2:")) {
/* 14 */       return new HiCnapsMessageBusArea();
/*    */     }
/* 16 */     if (mark.equals("{3:")) {
/* 17 */       return new HiCnapsTagMessageArea();
/*    */     }
/* 19 */     return null;
/*    */   }
/*    */ 
/*    */   public static HiCnapsMessageArea createCnapsMessageArea(int body)
/*    */   {
/* 26 */     switch (body)
/*    */     {
/*    */     case 2:
/* 28 */       return new HiCnapsMessageBusArea();
/*    */     case 3:
/* 30 */       return new HiCnapsTagMessageArea();
/*    */     }
/* 32 */     return null;
/*    */   }
/*    */ }