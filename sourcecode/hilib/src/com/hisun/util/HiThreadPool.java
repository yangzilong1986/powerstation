/*    */ package com.hisun.util;
/*    */ 
/*    */ import java.util.concurrent.ArrayBlockingQueue;
/*    */ import java.util.concurrent.SynchronousQueue;
/*    */ import java.util.concurrent.ThreadFactory;
/*    */ import java.util.concurrent.ThreadPoolExecutor;
/*    */ import java.util.concurrent.TimeUnit;
/*    */ import java.util.concurrent.atomic.AtomicInteger;
/*    */ 
/*    */ public class HiThreadPool extends ThreadPoolExecutor
/*    */ {
/*    */   public HiThreadPool()
/*    */   {
/* 42 */     super(5, 50, 60L, TimeUnit.SECONDS, new SynchronousQueue());
/*    */   }
/*    */ 
/*    */   private HiThreadPool(int min, int max, int queue)
/*    */   {
/* 52 */     super(min, max, 60L, TimeUnit.SECONDS, new ArrayBlockingQueue(queue));
/*    */   }
/*    */ 
/*    */   private HiThreadPool(int min, int max) {
/* 56 */     super(min, max, 60L, TimeUnit.SECONDS, new SynchronousQueue());
/*    */   }
/*    */ 
/*    */   public static HiThreadPool createThreadPool() {
/* 60 */     return new HiThreadPool();
/*    */   }
/*    */ 
/*    */   public static HiThreadPool createThreadPool(int minThreads, int maxThreads) {
/* 64 */     return new HiThreadPool(minThreads, maxThreads);
/*    */   }
/*    */ 
/*    */   public static HiThreadPool createThreadPool(int minThreads, int maxThreads, int queueSize)
/*    */   {
/* 69 */     return new HiThreadPool(minThreads, maxThreads, queueSize);
/*    */   }
/*    */ 
/*    */   public static HiThreadPool createThreadPool(String name) {
/* 73 */     HiThreadPool pool = new HiThreadPool();
/* 74 */     if (name != null)
/* 75 */       pool.setThreadFactory(hisunThreadFactory(name));
/* 76 */     return pool;
/*    */   }
/*    */ 
/*    */   public static HiThreadPool createTimeOutCtlThreadPool(int timeout) {
/* 80 */     return new TimeOutCtlThreadPool(timeout);
/*    */   }
/*    */ 
/*    */   public static ThreadFactory hisunThreadFactory(String name)
/*    */   {
/* 85 */     SecurityManager s = System.getSecurityManager();
/* 86 */     ThreadGroup group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
/*    */ 
/* 88 */     AtomicInteger threadNumber = new AtomicInteger(1);
/* 89 */     String namePrefix = "hisun-pool-" + name + "-thread-";
/*    */ 
/* 91 */     return new ThreadFactory(group, namePrefix, threadNumber) { private final ThreadGroup val$group;
/*    */       private final String val$namePrefix;
/*    */       private final AtomicInteger val$threadNumber;
/*    */ 
/*    */       public Thread newThread(Runnable r) { Thread t = new Thread(this.val$group, r, this.val$namePrefix + this.val$threadNumber.getAndIncrement(), 0L);
/*    */ 
/* 95 */         if (t.isDaemon())
/* 96 */           t.setDaemon(false);
/* 97 */         if (t.getPriority() != 5)
/* 98 */           t.setPriority(5);
/* 99 */         return t;
/*    */       }
/*    */     };
/*    */   }
/*    */ }