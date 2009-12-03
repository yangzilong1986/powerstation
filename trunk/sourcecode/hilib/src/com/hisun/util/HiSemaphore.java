/*    */ package com.hisun.util;
/*    */ 
/*    */ import com.hisun.exception.HiException;
/*    */ import edu.emory.mathcs.backport.java.util.concurrent.Semaphore;
/*    */ import edu.emory.mathcs.backport.java.util.concurrent.TimeUnit;
/*    */ 
/*    */ public class HiSemaphore
/*    */ {
/* 10 */   private Semaphore available = null;
/*    */ 
/* 12 */   private int tmOut = -1;
/* 13 */   protected String msg = null;
/*    */ 
/*    */   public HiSemaphore(int maxNum, int tmOut) {
/* 16 */     if (maxNum != -1) {
/* 17 */       this.available = new Semaphore(maxNum, true);
/*    */     }
/* 19 */     this.tmOut = tmOut;
/*    */   }
/*    */ 
/*    */   public Semaphore getSemaphore() {
/* 23 */     return this.available; }
/*    */ 
/*    */   public void acquire() throws HiException {
/* 26 */     if (this.available == null)
/* 27 */       return;
/*    */     try
/*    */     {
/* 30 */       if (this.tmOut == -1) {
/* 31 */         this.available.acquire();
/*    */       }
/* 33 */       else if (!(this.available.tryAcquire(this.tmOut, TimeUnit.SECONDS)))
/* 34 */         throw new HiException("215029", this.msg);
/*    */     }
/*    */     catch (InterruptedException e)
/*    */     {
/* 38 */       throw new HiException(e);
/*    */     }
/*    */   }
/*    */ 
/*    */   public void release() throws HiException {
/* 43 */     this.available.release();
/*    */   }
/*    */ }