/*    */ package com.hisun.atc.rpt;
/*    */ 
/*    */ public class HiReportRuntimeException extends RuntimeException
/*    */ {
/*    */   private final int errCode;
/*    */ 
/*    */   public HiReportRuntimeException(Throwable cause)
/*    */   {
/*  7 */     super(cause);
/*  8 */     this.errCode = -1;
/*    */   }
/*    */ 
/*    */   public HiReportRuntimeException(int errcode, String msg) {
/* 12 */     super(msg);
/* 13 */     this.errCode = errcode;
/*    */   }
/*    */ 
/*    */   public int getErrCode() {
/* 17 */     return this.errCode;
/*    */   }
/*    */ }