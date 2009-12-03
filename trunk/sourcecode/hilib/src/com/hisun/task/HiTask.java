/*     */ package com.hisun.task;
/*     */ 
/*     */ import com.hisun.dispatcher.HiRouterOut;
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.hilog4j.HiLog;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiETF;
/*     */ import com.hisun.message.HiETFFactory;
/*     */ import com.hisun.message.HiMessage;
/*     */ import com.hisun.message.HiMessageContext;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.commons.lang.math.NumberUtils;
/*     */ 
/*     */ public class HiTask extends HiAbstractTask
/*     */   implements Runnable
/*     */ {
/*  22 */   private Logger log = HiLog.getLogger("BATCH_TASK.trc");
/*     */   private HiMessage reqMsg;
/*     */   private HiMessage rspMsg;
/*     */   private String outParam;
/*     */   private String inParam;
/*     */   private String srn;
/*     */   private String txnCod;
/*     */   private String objSvr;
/*  54 */   private boolean sync = true;
/*     */ 
/*  56 */   private long total = 0L;
/*     */ 
/*     */   public HiTask(String taskId) { setId(taskId);
/*  59 */     setTaskInit();
/*     */   }
/*     */ 
/*     */   public HiTask(String taskId, boolean sync) {
/*  63 */     setId(taskId);
/*  64 */     this.sync = sync;
/*  65 */     setTaskInit();
/*     */   }
/*     */ 
/*     */   public HiTask() {
/*  69 */     setTaskInit();
/*     */   }
/*     */ 
/*     */   public HiMessage getReqMsg() {
/*  73 */     return this.reqMsg;
/*     */   }
/*     */ 
/*     */   public void setReqMsg(HiMessage reqMsg)
/*     */   {
/*  82 */     this.reqMsg = createReqMsg();
/*  83 */     this.reqMsg.setBody(buildBody(reqMsg.getETFBody(), this.inParam));
/*     */   }
/*     */ 
/*     */   public HiETF getRspData() {
/*  87 */     return this.rspMsg.getETFBody();
/*     */   }
/*     */ 
/*     */   public HiMessage getRspMsg() {
/*  91 */     return this.rspMsg;
/*     */   }
/*     */ 
/*     */   public void setRspMsg(HiMessage rspMsg)
/*     */     throws HiException
/*     */   {
/* 100 */     this.rspMsg = rspMsg.cloneNoBody();
/* 101 */     HiETF rootETF = HiETFFactory.createETF();
/* 102 */     this.rspMsg.setBody(buildBody(rspMsg.getETFBody(), this.outParam));
/*     */   }
/*     */ 
/*     */   public void run() {
/*     */     try {
/* 107 */       long l1 = System.currentTimeMillis();
/* 108 */       setTaskRun();
/* 109 */       HiMessageContext ctx = new HiMessageContext();
/* 110 */       HiMessageContext.setCurrentMessageContext(ctx);
/* 111 */       ctx.setCurrentMsg(this.reqMsg);
/* 112 */       HiRouterOut.process(ctx);
/* 113 */       this.total += System.currentTimeMillis() - l1;
/* 114 */       if (!(isSync())) {
/* 115 */         return;
/*     */       }
/* 117 */       setEndTm(System.currentTimeMillis());
/* 118 */       if (ctx.getCurrentMsg().getBody() instanceof HiETF) {
/* 119 */         this.rspMsg = ctx.getCurrentMsg();
/* 120 */         this.rspMsg.setBody(buildBody(ctx.getCurrentMsg().getETFBody(), this.outParam));
/*     */       }
/* 122 */       setTaskSucc();
/* 123 */       this.reqMsg = null;
/*     */     } catch (Throwable e) {
/* 125 */       this.msg = e.toString();
/* 126 */       this.log.error("task:[" + this.id + "] failure", e);
/* 127 */       setTaskFail();
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean isSync() {
/* 132 */     return this.sync;
/*     */   }
/*     */ 
/*     */   public void setSync(boolean sync) {
/* 136 */     this.sync = sync;
/*     */   }
/*     */ 
/*     */   public String getOutParam() {
/* 140 */     return this.outParam;
/*     */   }
/*     */ 
/*     */   public void setOutParam(String outParam) {
/* 144 */     this.outParam = outParam;
/*     */   }
/*     */ 
/*     */   public HiMessage createReqMsg()
/*     */   {
/* 152 */     HiMessage msg = new HiMessage(this.id, "PLTOUT");
/* 153 */     msg.setHeadItem("STM", new Long(System.currentTimeMillis()));
/* 154 */     if (msg.hasHeadItem("plain_type")) {
/* 155 */       msg.setHeadItem("plain_type", "byte");
/*     */     }
/*     */ 
/* 158 */     msg.setHeadItem("STC", this.txnCod);
/* 159 */     msg.setHeadItem("SCH", "rq");
/* 160 */     msg.setHeadItem("ECT", "text/etf");
/* 161 */     if (StringUtils.isNotBlank(this.objSvr)) {
/* 162 */       msg.setHeadItem("SDT", this.objSvr);
/*     */     }
/*     */ 
/* 165 */     if (StringUtils.isNotBlank(this.srn)) {
/* 166 */       msg.setHeadItem("SRN", this.srn);
/*     */     }
/* 168 */     return msg;
/*     */   }
/*     */ 
/*     */   public static HiETF buildBody(HiETF inRoot, String param)
/*     */   {
/* 178 */     HiETF outRoot = null;
/* 179 */     if (param == null) {
/* 180 */       outRoot = inRoot.cloneRootNode();
/* 181 */       return outRoot;
/*     */     }
/* 183 */     outRoot = HiETFFactory.createETF();
/* 184 */     if (StringUtils.isBlank(param)) {
/* 185 */       return outRoot;
/*     */     }
/*     */ 
/* 188 */     String[] tmps = param.split("\\|");
/* 189 */     for (int i = 0; i < tmps.length; ++i) {
/* 190 */       if (StringUtils.isBlank(tmps[i])) {
/*     */         continue;
/*     */       }
/* 193 */       int idx = StringUtils.indexOf(tmps[i], "[]");
/* 194 */       if (idx == -1)
/*     */       {
/* 196 */         HiETF node1 = inRoot.getChildNode(tmps[i]);
/* 197 */         if (node1 == null) {
/*     */           continue;
/*     */         }
/* 200 */         outRoot.appendNode(node1.cloneNode());
/*     */       }
/*     */       else {
/* 203 */         String groupName = StringUtils.substring(tmps[i], 0, idx - 1);
/* 204 */         String tmp = inRoot.getChildValue(groupName + "_NUM");
/* 205 */         int groupNum = -1;
/* 206 */         if (tmp != null) {
/* 207 */           groupNum = NumberUtils.toInt(tmp);
/* 208 */           outRoot.setChildValue(groupName + "_NUM", tmp);
/*     */         }
/* 210 */         for (int j = 1; ; ++j) {
/* 211 */           HiETF node1 = inRoot.getChildNode(groupName + "_" + j);
/* 212 */           if (((groupNum != -1) && (j > groupNum)) || (node1 == null)) break; if (node1.isEndNode()) {
/*     */             break;
/*     */           }
/* 215 */           HiETF node2 = outRoot.getChildNode(groupName + "_" + j);
/* 216 */           outRoot.appendNode(node1.cloneNode());
/*     */         }
/*     */       }
/*     */     }
/* 220 */     return outRoot;
/*     */   }
/*     */ 
/*     */   public String getInParam()
/*     */   {
/* 225 */     return this.inParam;
/*     */   }
/*     */ 
/*     */   public void setInParam(String inParam) {
/* 229 */     this.inParam = inParam;
/*     */   }
/*     */ 
/*     */   public String getSrn() {
/* 233 */     return this.srn;
/*     */   }
/*     */ 
/*     */   public void setSrn(String srn) {
/* 237 */     this.srn = srn;
/*     */   }
/*     */ 
/*     */   public String getTxnCod() {
/* 241 */     return this.txnCod;
/*     */   }
/*     */ 
/*     */   public void setTxnCod(String txnCod) {
/* 245 */     this.txnCod = txnCod;
/*     */   }
/*     */ 
/*     */   public String getObjSvr() {
/* 249 */     return this.objSvr;
/*     */   }
/*     */ 
/*     */   public void setObjSvr(String objSvr) {
/* 253 */     this.objSvr = objSvr;
/*     */   }
/*     */ 
/*     */   public void destroy()
/*     */   {
/*     */   }
/*     */ }