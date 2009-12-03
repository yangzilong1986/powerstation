/*     */ package com.hisun.task;
/*     */ 
/*     */ import com.hisun.hilog4j.HiLog;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.util.HiStringManager;
/*     */ 
/*     */ public abstract class HiAbstractTask
/*     */ {
/*  13 */   protected static HiStringManager sm = HiStringManager.getManager();
/*     */   public static final String TASK_INIT = "I";
/*     */   public static final String TASK_WAIT = "W";
/*     */   public static final String TASK_RUN = "R";
/*     */   public static final String TASK_FAIL = "E";
/*     */   public static final String TASK_SUCC = "S";
/*     */   public static final String TASK_TMOUT = "T";
/*     */   public static final String TASK_FIN = "F";
/*     */   private Logger log;
/*     */   private long startTm;
/*     */   private long endTm;
/*     */   private int tmOut;
/*     */   private String taskSts;
/*     */   protected String id;
/*     */   protected String msg;
/*     */ 
/*     */   public HiAbstractTask()
/*     */   {
/*  52 */     this.log = HiLog.getLogger("BATCH_TASK.trc");
/*     */ 
/*  65 */     this.tmOut = 120;
/*     */ 
/*  73 */     this.id = "DEFAULT_TASK";
/*     */   }
/*     */ 
/*     */   public String getId()
/*     */   {
/*  80 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(String id) {
/*  84 */     this.id = id;
/*     */   }
/*     */ 
/*     */   public boolean isEnd() {
/*  88 */     return (("S".equals(this.taskSts)) || ("E".equals(this.taskSts)) || (isTimeOut()));
/*     */   }
/*     */ 
/*     */   public boolean isSucc() {
/*  92 */     return "S".equals(this.taskSts);
/*     */   }
/*     */ 
/*     */   public boolean isInit() {
/*  96 */     return "I".equals(this.taskSts);
/*     */   }
/*     */ 
/*     */   public String getTaskSts() {
/* 100 */     return this.taskSts;
/*     */   }
/*     */ 
/*     */   public long getStartTm() {
/* 104 */     return this.startTm;
/*     */   }
/*     */ 
/*     */   public void setStartTm(long startTm) {
/* 108 */     this.startTm = startTm;
/*     */   }
/*     */ 
/*     */   public long getTmOut() {
/* 112 */     return this.tmOut;
/*     */   }
/*     */ 
/*     */   public void setTmOut(int tmOut) {
/* 116 */     this.tmOut = tmOut;
/*     */   }
/*     */ 
/*     */   public boolean isTimeOut() {
/* 120 */     if (isInit()) {
/* 121 */       return false;
/*     */     }
/* 123 */     boolean flag = System.currentTimeMillis() > this.startTm + this.tmOut * 1000;
/* 124 */     return flag;
/*     */   }
/*     */ 
/*     */   public void setTaskRun() {
/* 128 */     if ("R".equals(this.taskSts)) {
/* 129 */       return;
/*     */     }
/* 131 */     if (this.log.isInfoEnabled()) {
/* 132 */       this.log.info(sm.getString("HiAbstractTask.task.run", this.id));
/*     */     }
/* 134 */     this.taskSts = "R";
/*     */   }
/*     */ 
/*     */   public void setTaskInit() {
/* 138 */     if ("I".equals(this.taskSts)) {
/* 139 */       return;
/*     */     }
/*     */ 
/* 144 */     setStartTm(System.currentTimeMillis());
/* 145 */     this.taskSts = "I";
/*     */   }
/*     */ 
/*     */   public void setTaskFail() {
/* 149 */     if ("E".equals(this.taskSts)) {
/* 150 */       return;
/*     */     }
/*     */ 
/* 155 */     setEndTm(System.currentTimeMillis());
/* 156 */     this.taskSts = "E";
/*     */   }
/*     */ 
/*     */   public void setTaskSucc() {
/* 160 */     if ("S".equals(this.taskSts)) {
/* 161 */       return;
/*     */     }
/*     */ 
/* 166 */     setEndTm(System.currentTimeMillis());
/* 167 */     this.taskSts = "S";
/*     */   }
/*     */ 
/*     */   public void setTaskWait() {
/* 171 */     if ("W".equals(this.taskSts)) {
/* 172 */       return;
/*     */     }
/*     */ 
/* 177 */     this.taskSts = "W";
/*     */   }
/*     */ 
/*     */   public void setTaskTmOut() {
/* 181 */     if ("T".equals(this.taskSts)) {
/* 182 */       return;
/*     */     }
/* 184 */     setEndTm(System.currentTimeMillis());
/* 185 */     this.taskSts = "T";
/*     */   }
/*     */ 
/*     */   public void setTaskFin() {
/* 189 */     if ("F".equals(this.taskSts)) {
/* 190 */       return;
/*     */     }
/*     */ 
/* 195 */     setEndTm(System.currentTimeMillis());
/* 196 */     this.taskSts = "F";
/*     */   }
/*     */ 
/*     */   public long getEndTm() {
/* 200 */     return this.endTm;
/*     */   }
/*     */ 
/*     */   public void setEndTm(long endTm) {
/* 204 */     this.endTm = endTm;
/*     */   }
/*     */ 
/*     */   public String getMsg() {
/* 208 */     return this.msg;
/*     */   }
/*     */ 
/*     */   public void setMsg(String msg) {
/* 212 */     this.msg = msg;
/*     */   }
/*     */ }