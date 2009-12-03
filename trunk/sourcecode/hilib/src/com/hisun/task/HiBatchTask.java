/*     */ package com.hisun.task;
/*     */ 
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.hilog4j.HiLog;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiETF;
/*     */ import com.hisun.message.HiETFFactory;
/*     */ import com.hisun.util.HiThreadPool;
/*     */ import com.hisun.workload.HiWorkLoad;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.RejectedExecutionException;
/*     */ 
/*     */ public class HiBatchTask extends HiAbstractTask
/*     */ {
/*     */   private static final int WAIT_NONE = 0;
/*     */   private static final int WAIT_ALL = 1;
/*     */   private static final int WAIT_ANY = 2;
/*  26 */   private Logger log = HiLog.getLogger("BATCH_TASK.trc");
/*  27 */   private HashMap taskMap = new HashMap();
/*  28 */   private HiWorkLoad workLoad = null;
/*     */ 
/*     */   public HiBatchTask(String batchId) { setId(batchId);
/*  31 */     setTaskInit();
/*     */   }
/*     */ 
/*     */   public HiBatchTask() {
/*  35 */     setTaskInit();
/*     */   }
/*     */ 
/*     */   public void sumbit(HiTask task) {
/*  39 */     task.setStartTm(System.currentTimeMillis());
/*  40 */     task.setTaskWait();
/*     */     while (true)
/*     */       try {
/*  43 */         if ((this.workLoad.getThreadPool().isShutdown()) || (Thread.currentThread().isInterrupted())) {
/*     */           return;
/*     */         }
/*     */ 
/*  47 */         this.workLoad.getThreadPool().execute(task);
/*     */       } catch (RejectedExecutionException e) {
/*     */         while (true) {
/*  50 */           this.log.warn("Batch Task:[ " + this.id + "] Please increase maxThreads!");
/*     */ 
/*  53 */           Thread.yield();
/*     */           try {
/*  55 */             Thread.sleep(100L);
/*     */           } catch (InterruptedException e1) {
/*  57 */             return;
/*     */           }
/*     */         }
/*     */       }
/*     */   }
/*     */ 
/*     */   public HiTask addTask(String taskId) throws HiException {
/*  64 */     if (this.taskMap.containsKey(taskId)) {
/*  65 */       throw new HiException("241147", taskId);
/*     */     }
/*  67 */     HiTask task = new HiTask(taskId);
/*  68 */     this.taskMap.put(taskId, task);
/*  69 */     return task;
/*     */   }
/*     */ 
/*     */   public HiTask getTask(String taskId) {
/*  73 */     return ((HiTask)this.taskMap.get(taskId));
/*     */   }
/*     */ 
/*     */   public HiTask addTask(String taskId, boolean sync) throws HiException {
/*  77 */     if (this.taskMap.containsKey(taskId)) {
/*  78 */       throw new HiException("241147", taskId);
/*     */     }
/*  80 */     HiTask task = new HiTask(taskId, sync);
/*  81 */     this.taskMap.put(taskId, task);
/*  82 */     return task;
/*     */   }
/*     */ 
/*     */   public HiWorkLoad getWorkLoad() {
/*  86 */     return this.workLoad;
/*     */   }
/*     */ 
/*     */   public void setWorkLoad(HiWorkLoad workLoad) {
/*  90 */     this.workLoad = workLoad;
/*     */   }
/*     */ 
/*     */   public boolean check() {
/*  94 */     if (isTimeOut()) {
/*  95 */       setTaskTmOut();
/*  96 */       return true;
/*     */     }
/*     */ 
/*  99 */     Iterator iter = this.taskMap.keySet().iterator();
/* 100 */     boolean isFin = false;
/* 101 */     while (iter.hasNext()) {
/* 102 */       String name = (String)iter.next();
/* 103 */       HiTask task = (HiTask)this.taskMap.get(name);
/* 104 */       if (task.isTimeOut()) {
/* 105 */         if (this.log.isInfoEnabled()) {
/* 106 */           this.log.info("TASK:{" + this.id + "}{" + task.getTmOut() + "} time out");
/*     */         }
/* 108 */         setTaskTmOut();
/*     */       }
/* 110 */       if (!(task.isEnd()))
/* 111 */         isFin = false;
/*     */       else {
/* 113 */         isFin = true;
/*     */       }
/*     */     }
/* 116 */     if (isFin) {
/* 117 */       setTaskFin();
/*     */     }
/* 119 */     return true;
/*     */   }
/*     */ 
/*     */   public HiETF collectResultWaitAny() {
/* 123 */     return collectResult(2);
/*     */   }
/*     */ 
/*     */   public HiETF collectResultWaitAny(String grpNam) {
/* 127 */     return collectResult(2, grpNam);
/*     */   }
/*     */ 
/*     */   public HiETF collectResultWaitAll() {
/* 131 */     return collectResult(1);
/*     */   }
/*     */ 
/*     */   public HiETF collectResultWaitAll(String grpNam) {
/* 135 */     return collectResult(1, grpNam);
/*     */   }
/*     */ 
/*     */   public HiETF collectResult() {
/* 139 */     return collectResult(0);
/*     */   }
/*     */ 
/*     */   public HiETF collectResult(String grpNam) {
/* 143 */     return collectResult(0, grpNam);
/*     */   }
/*     */ 
/*     */   public HiETF collectResult(int waitType) {
/* 147 */     waitTask(waitType);
/* 148 */     Iterator iter = this.taskMap.keySet().iterator();
/* 149 */     HiETF root = HiETFFactory.createETF();
/* 150 */     while (iter.hasNext()) {
/* 151 */       String name = (String)iter.next();
/* 152 */       HiTask task = (HiTask)this.taskMap.get(name);
/* 153 */       HiETF grpNode = root.addNode(task.getId());
/* 154 */       grpNode.setChildValue("ID", task.getId());
/* 155 */       grpNode.setChildValue("STS", task.getTaskSts());
/* 156 */       grpNode.setChildValue("MSG", task.getMsg());
/* 157 */       if (!(task.isSucc())) {
/*     */         continue;
/*     */       }
/* 160 */       root.combine(task.getRspData(), true);
/*     */     }
/* 162 */     return root;
/*     */   }
/*     */ 
/*     */   public HiETF collectResult(int waitType, String grpNam) {
/* 166 */     waitTask(waitType);
/* 167 */     int k = 1;
/* 168 */     Iterator iter = this.taskMap.keySet().iterator();
/* 169 */     HiETF root = HiETFFactory.createETF();
/* 170 */     while (iter.hasNext()) {
/* 171 */       String name = (String)iter.next();
/* 172 */       HiTask task = (HiTask)this.taskMap.get(name);
/* 173 */       HiETF grpNode = root.addNode(grpNam + "_" + k);
/* 174 */       grpNode.setChildValue("TASK_ID", task.getId());
/* 175 */       grpNode.setChildValue("TASK_STS", task.getTaskSts());
/* 176 */       grpNode.setChildValue("TASK_MSG", task.getMsg());
/* 177 */       ++k;
/* 178 */       if (!(task.isSucc())) {
/*     */         continue;
/*     */       }
/* 181 */       grpNode.combine(task.getRspData(), true);
/*     */     }
/* 183 */     return root;
/*     */   }
/*     */ 
/*     */   public void waitTask(int waitType) {
/* 187 */     if (waitType == 0) {
/* 188 */       return;
/*     */     }
/*     */     while (true)
/*     */     {
/* 192 */       if (((waitType == 1) && (isAllEnd())) || ((waitType == 2) && (isAnyEnd())))
/*     */       {
/* 194 */         return;
/*     */       }
/*     */       try
/*     */       {
/* 198 */         Thread.sleep(1000L);
/*     */       } catch (InterruptedException e) {
/* 200 */         e.printStackTrace();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean isAnyEnd()
/*     */   {
/* 208 */     Iterator iter = this.taskMap.keySet().iterator();
/* 209 */     while (iter.hasNext()) {
/* 210 */       HiTask task = (HiTask)this.taskMap.get(iter.next());
/* 211 */       if (task.isEnd()) {
/* 212 */         return true;
/*     */       }
/*     */     }
/* 215 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean isAllEnd() {
/* 219 */     Iterator iter = this.taskMap.keySet().iterator();
/* 220 */     while (iter.hasNext()) {
/* 221 */       HiTask task = (HiTask)this.taskMap.get(iter.next());
/* 222 */       if (!(task.isEnd())) {
/* 223 */         return false;
/*     */       }
/*     */     }
/* 226 */     setTaskFin();
/* 227 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean contains(String taskId)
/*     */   {
/* 232 */     return this.taskMap.containsKey(taskId);
/*     */   }
/*     */ 
/*     */   public void destroy() {
/* 236 */     Iterator iter = this.taskMap.keySet().iterator();
/* 237 */     while (iter.hasNext()) {
/* 238 */       ((HiTask)this.taskMap.get(iter.next())).destroy();
/*     */     }
/* 240 */     this.taskMap.clear();
/*     */   }
/*     */ 
/*     */   public static int getWaitType(String waitTyp) {
/* 244 */     if ("ALL".equals(waitTyp))
/* 245 */       return 1;
/* 246 */     if ("ANY".equals(waitTyp))
/* 247 */       return 2;
/* 248 */     if ("NONE".equals(waitTyp)) {
/* 249 */       return 0;
/*     */     }
/* 251 */     return 0;
/*     */   }
/*     */ }