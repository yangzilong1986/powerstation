 package com.hisun.atc;
 
 import com.hisun.atc.common.HiArgUtils;
 import com.hisun.exception.HiException;
 import com.hisun.hilib.HiATLParam;
 import com.hisun.message.HiETF;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import com.hisun.task.HiBatchTask;
 import com.hisun.task.HiBatchTaskPool;
 import com.hisun.task.HiTask;
 import org.apache.commons.lang.StringUtils;
 
 public class HiBatchTaskATC
 {
   public int ApplyBatchTask(HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     HiBatchTask batchTask;
     String batchId = HiArgUtils.getStringNotNull(args, "batchId");
     String workLoadId = args.get("workLoadId");
     boolean isClean = args.getBoolean("IsClean");
     int tmOut = args.getInt("TmOut");
 
     HiBatchTaskPool batchTaskPool = HiBatchTaskPool.getInstance();
     if (batchTaskPool.contains(batchId)) {
       if (isClean)
         batchTaskPool.removeBatchTask(batchId);
       else {
         return 1;
       }
     }
 
     if (StringUtils.isBlank(workLoadId))
       batchTask = batchTaskPool.addBatchTask(batchId);
     else {
       batchTask = batchTaskPool.addBatchTask(batchId, workLoadId);
     }
 
     if (tmOut > 0) {
       batchTask.setTmOut(tmOut);
     }
     return 0;
   }
 
   public int SubmitTask(HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     String batchId = HiArgUtils.getStringNotNull(args, "batchId");
     String taskId = HiArgUtils.getStringNotNull(args, "TaskId");
     String txnCod = HiArgUtils.getStringNotNull(args, "txnCod");
     String objSvr = args.get("ObjSvr");
     String inParam = args.get("In");
     String outParam = args.get("Out");
     String srn = args.get("Srn");
     int tmOut = args.getInt("TmOut");
 
     boolean sync = true;
     if (args.contains("sync")) {
       sync = args.getBoolean("sync");
     }
 
     HiBatchTask batchTask = HiBatchTaskPool.getInstance().getBatchTask(batchId);
 
     if (batchTask == null) {
       return 2;
     }
     HiTask task = null;
     if (batchTask.contains(taskId)) {
       return 1;
     }
     task = batchTask.addTask(taskId);
     if (tmOut > 0) {
       task.setTmOut(tmOut);
     }
     task.setSync(sync);
     task.setTxnCod(txnCod);
     task.setObjSvr(objSvr);
     task.setInParam(inParam);
     task.setOutParam(outParam);
     task.setSrn(srn);
     task.setReqMsg(ctx.getCurrentMsg());
     batchTask.sumbit(task);
     return 0;
   }
 
   public int CollectTaskResult(HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     HiETF recvRoot;
     String batchId = HiArgUtils.getStringNotNull(args, "batchId");
     HiBatchTask batchTask = HiBatchTaskPool.getInstance().getBatchTask(batchId);
 
     String waitTyp = "ALL";
     if (args.contains("waitTyp")) {
       waitTyp = args.get("waitTyp");
     }
 
     if (batchTask == null) {
       return 2;
     }
 
     String grpNam = args.get("GrpNam");
 
     if (StringUtils.isNotBlank(grpNam)) {
       recvRoot = batchTask.collectResult(HiBatchTask.getWaitType(waitTyp), grpNam);
     }
     else {
       recvRoot = batchTask.collectResult(HiBatchTask.getWaitType(waitTyp));
     }
     HiETF root = ctx.getCurrentMsg().getETFBody();
     root.combine(recvRoot, true);
     return 0;
   }
 
   public int SubmitTaskResult(HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     String batchId = HiArgUtils.getStringNotNull(args, "batchId");
     String taskId = HiArgUtils.getStringNotNull(args, "taskId");
     HiBatchTask batchTask = HiBatchTaskPool.getInstance().getBatchTask(batchId);
 
     if (batchTask == null) {
       return 2;
     }
     HiTask task = batchTask.getTask(taskId);
     if (task == null) {
       return 3;
     }
     task.setRspMsg(ctx.getCurrentMsg());
     task.setTaskSucc();
     return 0;
   }
 }