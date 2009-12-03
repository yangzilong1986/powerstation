/*     */ package com.hisun.util;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.Executors;
/*     */ import java.util.concurrent.Future;
/*     */ import java.util.concurrent.ScheduledExecutorService;
/*     */ import java.util.concurrent.ScheduledFuture;
/*     */ 
/*     */ class TimeOutCtlThreadPool extends HiThreadPool
/*     */ {
/*     */   private int timeout;
/* 147 */   private Map futures = new HashMap();
/*     */ 
/* 196 */   private ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
/*     */ 
/*     */   protected void afterExecute(Runnable r, Throwable t)
/*     */   {
/* 133 */     System.out.println("after execute task:" + r);
/* 134 */     if (this.futures.containsKey(r)) {
/* 135 */       ScheduledFuture future = (ScheduledFuture)this.futures.remove(r);
/* 136 */       future.cancel(true);
/* 137 */       System.out.println("cancle check timeout task");
/*     */     }
/*     */   }
/*     */ 
/*     */   public void shutdown() {
/* 142 */     super.shutdown();
/* 143 */     this.executor.shutdown();
/*     */   }
/*     */ 
/*     */   public TimeOutCtlThreadPool(int timeout)
/*     */   {
/* 151 */     this.timeout = timeout;
/*     */   }
/*     */ 
/*     */   final class TimeOutChecker
/*     */     implements Runnable
/*     */   {
/*     */     final Runnable task;
/*     */     final Future future;
/*     */ 
/*     */     TimeOutChecker(Runnable paramRunnable, Future paramFuture)
/*     */     {
/* 177 */       this.task = paramRunnable;
/* 178 */       this.future = future;
/*     */     }
/*     */ 
/*     */     public void run() {
/* 182 */       if (this.future.isDone())
/* 183 */         return;
/* 184 */       if (this.future.cancel(true))
/* 185 */         System.out.println("成功cancle任务执行!");
/*     */       else {
/* 187 */         System.out.println("终止线程失败!");
/*     */       }
/*     */ 
/* 191 */       TimeOutCtlThreadPool.this.futures.remove(this.task);
/* 192 */       System.out.println("remove check timeout task:" + this.task);
/*     */     }
/*     */   }
/*     */ }