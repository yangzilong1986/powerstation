/*    */ package com.hisun.workload;
/*    */ 
/*    */ import com.hisun.util.HiThreadPool;
/*    */ import java.util.concurrent.TimeUnit;
/*    */ 
/*    */ public class HiWorkLoad
/*    */ {
/*    */   private String name;
/*    */   private int maxThreads;
/*    */   private int minThreads;
/*    */   private int queueSize;
/*    */   private HiThreadPool tp;
/*    */ 
/*    */   public HiWorkLoad()
/*    */   {
/* 15 */     this.maxThreads = 10;
/* 16 */     this.minThreads = 5;
/* 17 */     this.queueSize = 100; }
/*    */ 
/*    */   public void init() {
/* 20 */     this.tp = HiThreadPool.createThreadPool(this.minThreads, this.maxThreads, this.queueSize);
/*    */   }
/*    */ 
/*    */   public void destroy() {
/* 24 */     this.tp.shutdownNow();
/*    */     try {
/* 26 */       this.tp.awaitTermination(10L, TimeUnit.SECONDS);
/*    */     } catch (InterruptedException e) {
/* 28 */       e.printStackTrace();
/*    */     }
/*    */   }
/*    */ 
/*    */   public HiThreadPool getThreadPool() {
/* 33 */     return this.tp;
/*    */   }
/*    */ 
/*    */   public String getName() {
/* 37 */     return this.name;
/*    */   }
/*    */ 
/*    */   public void setName(String name) {
/* 41 */     this.name = name;
/*    */   }
/*    */ 
/*    */   public int getMaxThreads() {
/* 45 */     return this.maxThreads;
/*    */   }
/*    */ 
/*    */   public void setMaxThreads(int maxThreads) {
/* 49 */     this.maxThreads = maxThreads;
/*    */   }
/*    */ 
/*    */   public int getMinThreads() {
/* 53 */     return this.minThreads;
/*    */   }
/*    */ 
/*    */   public void setMinThreads(int minThreads) {
/* 57 */     this.minThreads = minThreads;
/*    */   }
/*    */ 
/*    */   public int getQueueSize() {
/* 61 */     return this.queueSize;
/*    */   }
/*    */ 
/*    */   public void setQueueSize(int queueSize) {
/* 65 */     this.queueSize = queueSize;
/*    */   }
/*    */ 
/*    */   public String toString() {
/* 69 */     StringBuffer buf = new StringBuffer();
/* 70 */     buf.append(this.name);
/* 71 */     buf.append("|");
/* 72 */     buf.append(this.maxThreads);
/* 73 */     buf.append("|");
/* 74 */     buf.append(this.minThreads);
/* 75 */     buf.append("|");
/* 76 */     buf.append(this.queueSize);
/* 77 */     return buf.toString();
/*    */   }
/*    */ }