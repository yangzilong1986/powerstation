/*   */ package com.hisun.util;
/*   */ 
/*   */ public class HiThreadSemaphore extends HiSemaphore
/*   */ {
/*   */   public HiThreadSemaphore(int maxNum, int tmOut)
/*   */   {
/* 7 */     super(maxNum, tmOut);
/* 8 */     this.msg = "Thread";
/*   */   }
/*   */ }