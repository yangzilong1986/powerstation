 package com.hisun.workload;
 
 import com.hisun.util.HiThreadPool;
 import java.util.concurrent.TimeUnit;
 
 public class HiWorkLoad
 {
   private String name;
   private int maxThreads;
   private int minThreads;
   private int queueSize;
   private HiThreadPool tp;
 
   public HiWorkLoad()
   {
     this.maxThreads = 10;
     this.minThreads = 5;
     this.queueSize = 100; }
 
   public void init() {
     this.tp = HiThreadPool.createThreadPool(this.minThreads, this.maxThreads, this.queueSize);
   }
 
   public void destroy() {
     this.tp.shutdownNow();
     try {
       this.tp.awaitTermination(10L, TimeUnit.SECONDS);
     } catch (InterruptedException e) {
       e.printStackTrace();
     }
   }
 
   public HiThreadPool getThreadPool() {
     return this.tp;
   }
 
   public String getName() {
     return this.name;
   }
 
   public void setName(String name) {
     this.name = name;
   }
 
   public int getMaxThreads() {
     return this.maxThreads;
   }
 
   public void setMaxThreads(int maxThreads) {
     this.maxThreads = maxThreads;
   }
 
   public int getMinThreads() {
     return this.minThreads;
   }
 
   public void setMinThreads(int minThreads) {
     this.minThreads = minThreads;
   }
 
   public int getQueueSize() {
     return this.queueSize;
   }
 
   public void setQueueSize(int queueSize) {
     this.queueSize = queueSize;
   }
 
   public String toString() {
     StringBuffer buf = new StringBuffer();
     buf.append(this.name);
     buf.append("|");
     buf.append(this.maxThreads);
     buf.append("|");
     buf.append(this.minThreads);
     buf.append("|");
     buf.append(this.queueSize);
     return buf.toString();
   }
 }