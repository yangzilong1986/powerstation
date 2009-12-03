/*    */ package com.hisun.exception;
/*    */ 
/*    */ public class HiAppException extends HiException
/*    */ {
/*  6 */   private int retCode = -1;
/*    */ 
/*    */   public HiAppException(int retCode, String code)
/*    */   {
/* 10 */     super(code, "");
/* 11 */     this.retCode = retCode;
/*    */   }
/*    */ 
/*    */   public HiAppException(int retCode, String code, String msg)
/*    */   {
/* 26 */     super(code, msg);
/* 27 */     this.retCode = retCode;
/*    */   }
/*    */ 
/*    */   public HiAppException(int retCode, String code, String arg0, String arg1)
/*    */   {
/* 32 */     super(code, arg0, arg1);
/* 33 */     this.retCode = retCode;
/*    */   }
/*    */ 
/*    */   public HiAppException(int retCode, String code, String[] args)
/*    */   {
/* 38 */     super(code, args);
/*    */ 
/* 40 */     this.retCode = retCode;
/*    */   }
/*    */ 
/*    */   public HiAppException(int retCode, String code, String msg, Throwable nestedException)
/*    */   {
/* 58 */     super(code, msg, nestedException);
/* 59 */     this.retCode = retCode;
/*    */   }
/*    */ 
/*    */   public HiAppException(int retCode, String code, String[] msg, Throwable nestedException)
/*    */   {
/* 65 */     super(code, msg, nestedException);
/* 66 */     this.retCode = retCode;
/*    */   }
/*    */ 
/*    */   public int getRetCode()
/*    */   {
/* 76 */     return this.retCode;
/*    */   }
/*    */ 
/*    */   public void setRetCode(int retCode)
/*    */   {
/* 81 */     this.retCode = retCode;
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 86 */     return "retCode[" + this.retCode + "] \n" + super.toString();
/*    */   }
/*    */ }