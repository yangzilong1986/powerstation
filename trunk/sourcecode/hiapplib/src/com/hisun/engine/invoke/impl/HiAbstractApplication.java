/*     */ package com.hisun.engine.invoke.impl;
/*     */ 
/*     */ import com.hisun.engine.HiEngineModel;
/*     */ import com.hisun.engine.invoke.HiIAction;
/*     */ import com.hisun.engine.invoke.HiIEngineModel;
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.hilog4j.HiLog;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiContext;
/*     */ import com.hisun.message.HiMessage;
/*     */ import com.hisun.message.HiMessageContext;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ 
/*     */ public abstract class HiAbstractApplication extends HiEngineModel
/*     */ {
/*  26 */   private final Logger logger = (Logger)HiContext.getCurrentContext().getProperty("SVR.log");
/*     */ 
/*  35 */   private HashMap tranMap = new HashMap();
/*     */   private String version;
/*  44 */   private int timeout = -1;
/*     */   private String fileName;
/*     */   private String type;
/*     */   private String log_id;
/*     */ 
/*     */   public void addChilds(HiIEngineModel child)
/*     */     throws HiException
/*     */   {
/*  63 */     if (this.logger.isDebugEnabled()) {
/*  64 */       this.logger.debug("HiAbstractApplication:addChilds(HiIEngineModel) - start");
/*     */     }
/*     */ 
/*  68 */     if (child instanceof HiAbstractTransaction) {
/*  69 */       HiAbstractTransaction tranObj = (HiAbstractTransaction)child;
/*     */ 
/*  71 */       this.tranMap.put(tranObj.getCode(), tranObj);
/*     */     }
/*  73 */     super.addChilds(child);
/*     */ 
/*  75 */     if (this.logger.isDebugEnabled())
/*  76 */       this.logger.debug("HiAbstractApplication:addChilds(HiIEngineModel) - end");
/*     */   }
/*     */ 
/*     */   public HashMap getTranMap()
/*     */   {
/*  82 */     return this.tranMap;
/*     */   }
/*     */ 
/*     */   public void addTransaction(HiAbstractTransaction action) {
/*  86 */     if (this.logger.isDebugEnabled()) {
/*  87 */       this.logger.debug("HiAbstractApplication:addTransaction(HiAbstractTransaction) - start");
/*     */     }
/*     */ 
/*  91 */     this.tranMap.put(action.getCode(), action);
/*     */ 
/*  93 */     if (this.logger.isDebugEnabled())
/*  94 */       this.logger.debug("HiAbstractApplication:addTransaction(HiAbstractTransaction) - end");
/*     */   }
/*     */ 
/*     */   public void addTransaction(HiAbstractTransaction action, String strCode)
/*     */   {
/* 100 */     if (this.logger.isDebugEnabled()) {
/* 101 */       this.logger.debug("HiAbstractApplication:addTransaction(HiAbstractTransaction, String) - start");
/*     */     }
/*     */ 
/* 105 */     this.tranMap.put(strCode, action);
/*     */ 
/* 107 */     if (this.logger.isDebugEnabled())
/* 108 */       this.logger.debug("HiAbstractApplication:addTransaction(HiAbstractTransaction, String) - end");
/*     */   }
/*     */ 
/*     */   public void beforeProcess(HiMessageContext messContext)
/*     */     throws HiException
/*     */   {
/* 119 */     Logger log = HiLog.getLogger(messContext.getCurrentMsg());
/* 120 */     if (log.isDebugEnabled()) {
/* 121 */       log.debug("HiAbstractApplication:beforeProcess(HiMessageContext) - start");
/*     */     }
/* 123 */     HiMessage mess = messContext.getCurrentMsg();
/* 124 */     Long startTime = (Long)mess.getObjectHeadItem("STM");
/*     */ 
/* 126 */     if (startTime == null) {
/* 127 */       throw new HiException("213331");
/*     */     }
/* 129 */     if ((this.timeout != -1) && (!(mess.hasHeadItem("ETM"))))
/*     */     {
/* 131 */       Integer time = new Integer(this.timeout * 1000);
/* 132 */       long endTime = time.longValue() + startTime.longValue();
/* 133 */       mess.setHeadItem("ETM", new Long(endTime));
/*     */     }
/* 135 */     super.beforeProcess(messContext);
/*     */ 
/* 137 */     List initList = super.getInitList();
/* 138 */     if (initList.size() > 0) {
/* 139 */       for (int i = 0; i < initList.size(); ++i) {
/* 140 */         HiIAction action = (HiIAction)initList.get(i);
/* 141 */         HiProcess.process(action, messContext);
/*     */       }
/*     */     }
/*     */ 
/* 145 */     if (log.isDebugEnabled())
/* 146 */       log.debug("HiAbstractApplication:beforeProcess(HiMessageContext) - end");
/*     */   }
/*     */ 
/*     */   public HiAbstractTransaction getTransactions(String strCode)
/*     */   {
/* 151 */     if (this.logger.isDebugEnabled()) {
/* 152 */       this.logger.debug("HiAbstractApplication:getTransactions(String) - start");
/*     */     }
/*     */ 
/* 156 */     HiAbstractTransaction returnHiAbstractTransaction = (HiAbstractTransaction)this.tranMap.get(strCode);
/*     */ 
/* 158 */     if (this.logger.isDebugEnabled()) {
/* 159 */       this.logger.debug("HiAbstractApplication:getTransactions(String) - end");
/*     */     }
/* 161 */     return returnHiAbstractTransaction;
/*     */   }
/*     */ 
/*     */   public String getNodeName() {
/* 165 */     return "Application";
/*     */   }
/*     */ 
/*     */   public String getVersion() {
/* 169 */     return this.context.getStrProp("@SYS", "_VERSION");
/*     */   }
/*     */ 
/*     */   public void setVersion(String version) {
/* 173 */     this.version = version;
/* 174 */     this.context.setProperty("@SYS", "_VERSION", this.version);
/*     */   }
/*     */ 
/*     */   public int getTimeout() {
/* 178 */     return this.timeout;
/*     */   }
/*     */ 
/*     */   public void setTimeout(int timeout) {
/* 182 */     this.timeout = timeout;
/*     */   }
/*     */ 
/*     */   public String getFileName()
/*     */   {
/* 189 */     return this.fileName;
/*     */   }
/*     */ 
/*     */   public void setFileName(String fileName)
/*     */   {
/* 196 */     this.fileName = fileName;
/*     */   }
/*     */ 
/*     */   public String getType()
/*     */   {
/* 203 */     return this.type;
/*     */   }
/*     */ 
/*     */   public void setType(String type)
/*     */   {
/* 210 */     this.type = type;
/*     */   }
/*     */ 
/*     */   public String getLog_id()
/*     */   {
/* 217 */     return this.log_id;
/*     */   }
/*     */ 
/*     */   public void setLog_id(String log_id)
/*     */   {
/* 224 */     this.log_id = log_id;
/* 225 */     this.context.setProperty("__LOG_ID", log_id);
/*     */   }
/*     */ }