/*     */ package com.hisun.atc;
/*     */ 
/*     */ import com.hisun.atc.common.HiArgUtils;
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.hilib.HiATLParam;
/*     */ import com.hisun.message.HiETF;
/*     */ import com.hisun.message.HiMessage;
/*     */ import com.hisun.message.HiMessageContext;
/*     */ import com.hisun.task.HiBatchTask;
/*     */ import com.hisun.task.HiBatchTaskPool;
/*     */ import com.hisun.task.HiTask;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class HiBatchTaskATC
/*     */ {
/*     */   public int ApplyBatchTask(HiATLParam args, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/*     */     HiBatchTask batchTask;
/*  36 */     String batchId = HiArgUtils.getStringNotNull(args, "batchId");
/*  37 */     String workLoadId = args.get("workLoadId");
/*  38 */     boolean isClean = args.getBoolean("IsClean");
/*  39 */     int tmOut = args.getInt("TmOut");
/*     */ 
/*  41 */     HiBatchTaskPool batchTaskPool = HiBatchTaskPool.getInstance();
/*  42 */     if (batchTaskPool.contains(batchId)) {
/*  43 */       if (isClean)
/*  44 */         batchTaskPool.removeBatchTask(batchId);
/*     */       else {
/*  46 */         return 1;
/*     */       }
/*     */     }
/*     */ 
/*  50 */     if (StringUtils.isBlank(workLoadId))
/*  51 */       batchTask = batchTaskPool.addBatchTask(batchId);
/*     */     else {
/*  53 */       batchTask = batchTaskPool.addBatchTask(batchId, workLoadId);
/*     */     }
/*     */ 
/*  56 */     if (tmOut > 0) {
/*  57 */       batchTask.setTmOut(tmOut);
/*     */     }
/*  59 */     return 0;
/*     */   }
/*     */ 
/*     */   public int SubmitTask(HiATLParam args, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/*  88 */     String batchId = HiArgUtils.getStringNotNull(args, "batchId");
/*  89 */     String taskId = HiArgUtils.getStringNotNull(args, "TaskId");
/*  90 */     String txnCod = HiArgUtils.getStringNotNull(args, "txnCod");
/*  91 */     String objSvr = args.get("ObjSvr");
/*  92 */     String inParam = args.get("In");
/*  93 */     String outParam = args.get("Out");
/*  94 */     String srn = args.get("Srn");
/*  95 */     int tmOut = args.getInt("TmOut");
/*     */ 
/*  97 */     boolean sync = true;
/*  98 */     if (args.contains("sync")) {
/*  99 */       sync = args.getBoolean("sync");
/*     */     }
/*     */ 
/* 102 */     HiBatchTask batchTask = HiBatchTaskPool.getInstance().getBatchTask(batchId);
/*     */ 
/* 104 */     if (batchTask == null) {
/* 105 */       return 2;
/*     */     }
/* 107 */     HiTask task = null;
/* 108 */     if (batchTask.contains(taskId)) {
/* 109 */       return 1;
/*     */     }
/* 111 */     task = batchTask.addTask(taskId);
/* 112 */     if (tmOut > 0) {
/* 113 */       task.setTmOut(tmOut);
/*     */     }
/* 115 */     task.setSync(sync);
/* 116 */     task.setTxnCod(txnCod);
/* 117 */     task.setObjSvr(objSvr);
/* 118 */     task.setInParam(inParam);
/* 119 */     task.setOutParam(outParam);
/* 120 */     task.setSrn(srn);
/* 121 */     task.setReqMsg(ctx.getCurrentMsg());
/* 122 */     batchTask.sumbit(task);
/* 123 */     return 0;
/*     */   }
/*     */ 
/*     */   public int CollectTaskResult(HiATLParam args, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/*     */     HiETF recvRoot;
/* 139 */     String batchId = HiArgUtils.getStringNotNull(args, "batchId");
/* 140 */     HiBatchTask batchTask = HiBatchTaskPool.getInstance().getBatchTask(batchId);
/*     */ 
/* 142 */     String waitTyp = "ALL";
/* 143 */     if (args.contains("waitTyp")) {
/* 144 */       waitTyp = args.get("waitTyp");
/*     */     }
/*     */ 
/* 147 */     if (batchTask == null) {
/* 148 */       return 2;
/*     */     }
/*     */ 
/* 151 */     String grpNam = args.get("GrpNam");
/*     */ 
/* 153 */     if (StringUtils.isNotBlank(grpNam)) {
/* 154 */       recvRoot = batchTask.collectResult(HiBatchTask.getWaitType(waitTyp), grpNam);
/*     */     }
/*     */     else {
/* 157 */       recvRoot = batchTask.collectResult(HiBatchTask.getWaitType(waitTyp));
/*     */     }
/* 159 */     HiETF root = ctx.getCurrentMsg().getETFBody();
/* 160 */     root.combine(recvRoot, true);
/* 161 */     return 0;
/*     */   }
/*     */ 
/*     */   public int SubmitTaskResult(HiATLParam args, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 175 */     String batchId = HiArgUtils.getStringNotNull(args, "batchId");
/* 176 */     String taskId = HiArgUtils.getStringNotNull(args, "taskId");
/* 177 */     HiBatchTask batchTask = HiBatchTaskPool.getInstance().getBatchTask(batchId);
/*     */ 
/* 179 */     if (batchTask == null) {
/* 180 */       return 2;
/*     */     }
/* 182 */     HiTask task = batchTask.getTask(taskId);
/* 183 */     if (task == null) {
/* 184 */       return 3;
/*     */     }
/* 186 */     task.setRspMsg(ctx.getCurrentMsg());
/* 187 */     task.setTaskSucc();
/* 188 */     return 0;
/*     */   }
/*     */ }