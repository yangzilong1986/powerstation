/*     */ package com.hisun.engine.invoke.impl;
/*     */ 
/*     */ import com.hisun.engine.HiEngineModel;
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.hiexpression.HiExpFactory;
/*     */ import com.hisun.hiexpression.HiExpression;
/*     */ import com.hisun.hilog4j.HiLog;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiContext;
/*     */ import com.hisun.message.HiMessage;
/*     */ import com.hisun.message.HiMessageContext;
/*     */ import com.hisun.register.HiRegisterService;
/*     */ import com.hisun.register.HiServiceObject;
/*     */ import com.hisun.util.HiICSProperty;
/*     */ import com.hisun.util.HiStringManager;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public abstract class HiAbstractTransaction extends HiEngineModel
/*     */ {
/*     */   private static final String LOG_ID_MAP = "__LOG_ID_MAP";
/*  30 */   protected final Logger logger = (Logger)HiContext.getCurrentContext().getProperty("SVR.log");
/*     */ 
/*  32 */   protected final HiStringManager sm = HiStringManager.getManager();
/*     */   protected String strCode;
/*  37 */   protected int timeout = -1;
/*     */ 
/*  41 */   protected String strMonSwitch = "0";
/*     */ 
/*  46 */   protected String strTranSwitch = "1";
/*     */ 
/*  51 */   protected String strLogSwitch = "0";
/*     */ 
/*  56 */   protected HiExpression log_idExpr = null;
/*  57 */   protected String log_id = null;
/*     */   private String strDesc;
/*     */ 
/*     */   public String getNodeName()
/*     */   {
/*  64 */     return "Transaction";
/*     */   }
/*     */ 
/*     */   public void setTimeout(int timeout) {
/*  68 */     this.timeout = timeout;
/*     */   }
/*     */ 
/*     */   public int getTimeout() {
/*  72 */     return this.timeout;
/*     */   }
/*     */ 
/*     */   public void setTran_switch(String strTranSwitch) {
/*  76 */     this.context.setProperty("trans_switch", strTranSwitch);
/*     */   }
/*     */ 
/*     */   public void setLog_switch(String strLogSwitch) {
/*  80 */     this.context.setProperty("log_level", strLogSwitch);
/*     */   }
/*     */ 
/*     */   public void setMon_switch(String strMonSwitch) {
/*  84 */     this.context.setProperty("mon_switch", strMonSwitch);
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  89 */     return super.toString() + ":code[" + this.strCode + "]";
/*     */   }
/*     */ 
/*     */   public HiAbstractApplication getApplication() throws HiException {
/*  93 */     HiAbstractApplication returnHiAbstractApplication = (HiAbstractApplication)getParent();
/*  94 */     return returnHiAbstractApplication;
/*     */   }
/*     */ 
/*     */   public String getCode() {
/*  98 */     return this.strCode;
/*     */   }
/*     */ 
/*     */   public String getDesc() {
/* 102 */     return this.strDesc;
/*     */   }
/*     */ 
/*     */   public void setDesc(String strDesc)
/*     */   {
/* 108 */     if (this.logger.isDebugEnabled()) {
/* 109 */       this.logger.debug("setDesc(String) - start");
/*     */     }
/*     */ 
/* 112 */     this.strDesc = strDesc;
/* 113 */     this.context.setProperty("trans_desc", strDesc);
/*     */ 
/* 115 */     if (this.logger.isDebugEnabled())
/* 116 */       this.logger.debug("setDesc(String) - end");
/*     */   }
/*     */ 
/*     */   public void setCode(String strCode)
/*     */   {
/* 121 */     if (this.logger.isDebugEnabled()) {
/* 122 */       this.logger.debug("setCode(String) - start");
/*     */     }
/*     */ 
/* 125 */     this.strCode = strCode;
/* 126 */     this.context.setId(HiContext.TRN_PRE + this.strCode);
/* 127 */     this.context.setProperty("trans_code", strCode);
/*     */ 
/* 129 */     if (this.logger.isDebugEnabled())
/* 130 */       this.logger.debug("setCode(String) - end");
/*     */   }
/*     */ 
/*     */   public void beforeProcess(HiMessageContext ctx) throws HiException {
/* 134 */     HiMessage msg = ctx.getCurrentMsg();
/* 135 */     HiServiceObject service = HiRegisterService.getService(this.context.getStrProp("SVR.name"), getCode());
/*     */ 
/* 138 */     if (!(service.isRunning())) {
/* 139 */       throw new HiException("213301", getCode());
/*     */     }
/* 141 */     if (msg.getHeadItem("STF") == null) {
/* 142 */       msg.setHeadItem("STF", service.getLogLevel());
/*     */     }
/*     */ 
/* 145 */     if (!(msg.hasHeadItem("MON"))) {
/* 146 */       msg.setHeadItem("MON", service.getMonSwitch());
/*     */     }
/*     */ 
/* 149 */     if (this.timeout != -1) {
/* 150 */       Long startTime = (Long)msg.getObjectHeadItem("STM");
/*     */ 
/* 152 */       if (startTime == null) {
/* 153 */         throw new HiException("213331");
/*     */       }
/* 155 */       long endTime = this.timeout * 1000 + startTime.longValue();
/* 156 */       msg.setHeadItem("ETM", new Long(endTime));
/*     */     }
/* 158 */     if (this.log_idExpr != null)
/*     */     {
/* 160 */       String fid = this.log_idExpr.getValue(ctx);
/* 161 */       if (StringUtils.isNotBlank(fid)) {
/* 162 */         String logLevel = getLogLevel(fid);
/* 163 */         if (logLevel != null) {
/* 164 */           msg.setHeadItem("STF", logLevel);
/*     */         }
/* 166 */         msg.setHeadItem("FID", fid);
/* 167 */         Logger log = HiLog.getLogger(msg);
/* 168 */         log.setMsgId(msg.getRequestId());
/*     */       }
/*     */     }
/* 171 */     super.beforeProcess(ctx);
/*     */   }
/*     */ 
/*     */   public String getLog_id() {
/* 175 */     return this.log_id;
/*     */   }
/*     */ 
/*     */   public void setLog_id(String log_id)
/*     */   {
/* 183 */     this.log_idExpr = HiExpFactory.createExp(log_id);
/*     */   }
/*     */ 
/*     */   private static synchronized ConcurrentHashMap getLogIDMap() {
/* 187 */     HiContext ctx = HiContext.getRootContext();
/* 188 */     if (!(ctx.containsProperty("__LOG_ID_MAP"))) {
/* 189 */       ctx.setProperty("__LOG_ID_MAP", new ConcurrentHashMap());
/*     */     }
/* 191 */     return ((ConcurrentHashMap)ctx.getProperty("__LOG_ID_MAP"));
/*     */   }
/*     */ 
/*     */   private String getLogLevel(String id) {
/* 195 */     ConcurrentHashMap logIDMap = getLogIDMap();
/* 196 */     if (logIDMap.containsKey(id)) {
/* 197 */       String value = (String)logIDMap.get(id);
/* 198 */       if (StringUtils.isNotBlank(value)) {
/* 199 */         return value;
/*     */       }
/*     */     }
/* 202 */     if (HiICSProperty.isPrdEnv()) {
/* 203 */       return "0";
/*     */     }
/* 205 */     return "1";
/*     */   }
/*     */ }