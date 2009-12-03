/*    */ package com.hisun.exception;
/*    */ 
/*    */ public class HiSysException extends HiException
/*    */ {
/*    */   public HiSysException()
/*    */   {
/*    */   }
/*    */ 
/*    */   public HiSysException(String code)
/*    */   {
/* 14 */     super(code);
/*    */   }
/*    */ 
/*    */   public HiSysException(String code, String msg)
/*    */   {
/* 27 */     super(code, msg);
/*    */   }
/*    */ 
/*    */   public HiSysException(String code, String arg0, String arg1)
/*    */   {
/* 32 */     super(code, arg0, arg1);
/*    */   }
/*    */ 
/*    */   public HiSysException(String code, String arg0, String arg1, String arg2)
/*    */   {
/* 37 */     super(code, arg0, arg1, arg2);
/*    */   }
/*    */ 
/*    */   public HiSysException(String code, String arg0, String arg1, String arg2, String arg3)
/*    */   {
/* 43 */     super(code, arg0, arg1, arg2, arg3);
/*    */   }
/*    */ 
/*    */   public HiSysException(String code, String[] args)
/*    */   {
/* 48 */     super(code, args);
/*    */   }
/*    */ 
/*    */   public HiSysException(Throwable nestedException)
/*    */   {
/* 60 */     super(nestedException);
/*    */   }
/*    */ 
/*    */   public HiSysException(String code, String msg, Throwable nestedException)
/*    */   {
/* 75 */     super(code, msg, nestedException);
/*    */   }
/*    */ 
/*    */   public HiSysException(String code, String[] msg, Throwable nestedException)
/*    */   {
/* 80 */     super(code, msg, nestedException);
/*    */   }
/*    */ }