/*   */ package com.hisun.util;
/*   */ 
/*   */ public class HiDBSemaphore extends HiSemaphore
/*   */ {
/*   */   public HiDBSemaphore(int maxNum, int tmOut)
/*   */   {
/* 7 */     super(maxNum, tmOut);
/* 8 */     this.msg = "DB Connection";
/*   */   }
/*   */ }