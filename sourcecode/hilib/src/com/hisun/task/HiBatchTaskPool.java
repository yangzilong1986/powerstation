/*    */ package com.hisun.task;
/*    */ 
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.hilog4j.HiLog;
/*    */ import com.hisun.hilog4j.Logger;
/*    */ import com.hisun.util.HiStringManager;
/*    */ import com.hisun.workload.HiWorkLoad;
/*    */ import com.hisun.workload.HiWorkLoadPool;
/*    */ import java.util.Iterator;
/*    */ import java.util.Set;
/*    */ import java.util.concurrent.ConcurrentHashMap;
/*    */ 
/*    */ public class HiBatchTaskPool
/*    */ {
/*    */   private Logger log;
/* 24 */   protected static HiStringManager sm = HiStringManager.getManager();
/*    */   private ConcurrentHashMap batchTaskMap;
/* 27 */   private static HiBatchTaskPool instance = null;
/*    */ 
/*    */   public HiBatchTaskPool()
/*    */   {
/* 23 */     this.log = HiLog.getLogger("BATCH_TASK.trc");
/*    */ 
/* 26 */     this.batchTaskMap = new ConcurrentHashMap(); }
/*    */ 
/*    */   public static synchronized HiBatchTaskPool getInstance() {
/* 29 */     if (instance == null) {
/* 30 */       instance = new HiBatchTaskPool();
/*    */     }
/* 32 */     return instance;
/*    */   }
/*    */ 
/*    */   public HiBatchTask addBatchTask(String batchTaskName) throws HiException {
/* 36 */     return addBatchTask(batchTaskName, "Default"); }
/*    */ 
/*    */   public void removeBatchTask(String batchTaskName) {
/* 39 */     if (this.batchTaskMap.containsKey(batchTaskName)) {
/* 40 */       HiBatchTask batchTask = (HiBatchTask)this.batchTaskMap.get(batchTaskName);
/* 41 */       if (this.log.isInfoEnabled()) {
/* 42 */         this.log.info("BATCH TASK[" + batchTask.getId() + "] removed");
/*    */       }
/* 44 */       this.batchTaskMap.remove(batchTaskName);
/*    */     }
/*    */   }
/*    */ 
/*    */   public HiBatchTask addBatchTask(String batchTaskName, String workLoadName) throws HiException {
/* 49 */     check();
/*    */ 
/* 51 */     if (this.batchTaskMap.containsKey(batchTaskName)) {
/* 52 */       throw new HiException("241147", batchTaskName);
/*    */     }
/* 54 */     HiBatchTask batchTask = new HiBatchTask(batchTaskName);
/* 55 */     HiWorkLoad workLoad = HiWorkLoadPool.getInstance().getWorkLoad(workLoadName);
/*    */ 
/* 57 */     if (workLoad == null) {
/* 58 */       throw new HiException("241148", workLoadName);
/*    */     }
/* 60 */     if (this.log.isInfoEnabled()) {
/* 61 */       this.log.info("workLoad:{" + workLoad + "}");
/*    */     }
/* 63 */     batchTask.setWorkLoad(workLoad);
/*    */ 
/* 65 */     this.batchTaskMap.put(batchTaskName, batchTask);
/* 66 */     return batchTask;
/*    */   }
/*    */ 
/*    */   public HiBatchTask getBatchTask(String batchTaskName) {
/* 70 */     return ((HiBatchTask)this.batchTaskMap.get(batchTaskName));
/*    */   }
/*    */ 
/*    */   public boolean contains(String batchTaskName) {
/* 74 */     return this.batchTaskMap.containsKey(batchTaskName);
/*    */   }
/*    */ 
/*    */   public void check()
/*    */   {
/* 81 */     Iterator iter = this.batchTaskMap.keySet().iterator();
/* 82 */     while (iter.hasNext()) {
/* 83 */       String key = (String)iter.next();
/* 84 */       HiBatchTask batchTask = (HiBatchTask)this.batchTaskMap.get(key);
/* 85 */       batchTask.check();
/* 86 */       if (batchTask.isTimeOut()) {
/* 87 */         this.batchTaskMap.remove(key);
/* 88 */         if (this.log.isInfoEnabled()) {
/* 89 */           this.log.info("BATCH TASK:{" + batchTask.getId() + "}{" + batchTask.getTmOut() + "} time out");
/*    */         }
/* 91 */         batchTask.destroy();
/*    */       }
/*    */     }
/*    */   }
/*    */ }