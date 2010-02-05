 package com.hisun.task;
 
 import com.hisun.exception.HiException;
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import com.hisun.util.HiStringManager;
 import com.hisun.workload.HiWorkLoad;
 import com.hisun.workload.HiWorkLoadPool;
 import java.util.Iterator;
 import java.util.Set;
 import java.util.concurrent.ConcurrentHashMap;
 
 public class HiBatchTaskPool
 {
   private Logger log;
   protected static HiStringManager sm = HiStringManager.getManager();
   private ConcurrentHashMap batchTaskMap;
   private static HiBatchTaskPool instance = null;
 
   public HiBatchTaskPool()
   {
     this.log = HiLog.getLogger("BATCH_TASK.trc");
 
     this.batchTaskMap = new ConcurrentHashMap(); }
 
   public static synchronized HiBatchTaskPool getInstance() {
     if (instance == null) {
       instance = new HiBatchTaskPool();
     }
     return instance;
   }
 
   public HiBatchTask addBatchTask(String batchTaskName) throws HiException {
     return addBatchTask(batchTaskName, "Default"); }
 
   public void removeBatchTask(String batchTaskName) {
     if (this.batchTaskMap.containsKey(batchTaskName)) {
       HiBatchTask batchTask = (HiBatchTask)this.batchTaskMap.get(batchTaskName);
       if (this.log.isInfoEnabled()) {
         this.log.info("BATCH TASK[" + batchTask.getId() + "] removed");
       }
       this.batchTaskMap.remove(batchTaskName);
     }
   }
 
   public HiBatchTask addBatchTask(String batchTaskName, String workLoadName) throws HiException {
     check();
 
     if (this.batchTaskMap.containsKey(batchTaskName)) {
       throw new HiException("241147", batchTaskName);
     }
     HiBatchTask batchTask = new HiBatchTask(batchTaskName);
     HiWorkLoad workLoad = HiWorkLoadPool.getInstance().getWorkLoad(workLoadName);
 
     if (workLoad == null) {
       throw new HiException("241148", workLoadName);
     }
     if (this.log.isInfoEnabled()) {
       this.log.info("workLoad:{" + workLoad + "}");
     }
     batchTask.setWorkLoad(workLoad);
 
     this.batchTaskMap.put(batchTaskName, batchTask);
     return batchTask;
   }
 
   public HiBatchTask getBatchTask(String batchTaskName) {
     return ((HiBatchTask)this.batchTaskMap.get(batchTaskName));
   }
 
   public boolean contains(String batchTaskName) {
     return this.batchTaskMap.containsKey(batchTaskName);
   }
 
   public void check()
   {
     Iterator iter = this.batchTaskMap.keySet().iterator();
     while (iter.hasNext()) {
       String key = (String)iter.next();
       HiBatchTask batchTask = (HiBatchTask)this.batchTaskMap.get(key);
       batchTask.check();
       if (batchTask.isTimeOut()) {
         this.batchTaskMap.remove(key);
         if (this.log.isInfoEnabled()) {
           this.log.info("BATCH TASK:{" + batchTask.getId() + "}{" + batchTask.getTmOut() + "} time out");
         }
         batchTask.destroy();
       }
     }
   }
 }