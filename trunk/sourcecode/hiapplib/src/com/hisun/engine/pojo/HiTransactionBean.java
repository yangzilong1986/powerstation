/*     */ package com.hisun.engine.pojo;
/*     */ 
/*     */ import com.hisun.database.HiDataBaseUtil;
/*     */ import com.hisun.engine.exception.HiErrorException;
/*     */ import com.hisun.engine.exception.HiExitException;
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.hilog4j.HiLog;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiContext;
/*     */ import com.hisun.message.HiETF;
/*     */ import com.hisun.message.HiMessage;
/*     */ import com.hisun.message.HiMessageContext;
/*     */ import com.hisun.register.HiRegisterService;
/*     */ import com.hisun.register.HiServiceObject;
/*     */ import com.hisun.util.HiStringManager;
/*     */ 
/*     */ public abstract class HiTransactionBean extends HiPojoEngineModel
/*     */   implements HiITransaction
/*     */ {
/*     */   protected HiContext context;
/*     */   protected int timeOut;
/*     */   protected String code;
/*     */   protected String strMonSwitch;
/*     */   protected String strTranSwitch;
/*     */   protected String strLogSwitch;
/*     */   protected String desc;
/*     */   protected String serverName;
/*     */ 
/*     */   public HiTransactionBean()
/*     */   {
/*  20 */     this.context = HiContext.createContext(HiContext.getCurrentContext());
/*     */ 
/*  24 */     this.timeOut = -1;
/*     */ 
/*  29 */     this.code = null;
/*     */ 
/*  34 */     this.strMonSwitch = "0";
/*     */ 
/*  39 */     this.strTranSwitch = "1";
/*     */ 
/*  44 */     this.strLogSwitch = "0";
/*     */   }
/*     */ 
/*     */   public String getCode()
/*     */   {
/*  55 */     return this.code;
/*     */   }
/*     */ 
/*     */   public void setCode(String code)
/*     */   {
/*  60 */     this.code = code;
/*     */ 
/*  62 */     this.context.setId(HiContext.TRN_PRE + this.code);
/*  63 */     this.context.setProperty("trans_code", this.code);
/*     */   }
/*     */ 
/*     */   public void setTimeOut(int timeOut) {
/*  67 */     this.timeOut = timeOut;
/*     */   }
/*     */ 
/*     */   public int getTimeOut() {
/*  71 */     return this.timeOut;
/*     */   }
/*     */ 
/*     */   public void setTran_switch(String strTranSwitch) {
/*  75 */     this.strTranSwitch = strTranSwitch;
/*  76 */     this.context.setProperty("trans_switch", strTranSwitch);
/*     */   }
/*     */ 
/*     */   public void setLog_switch(String strLogSwitch) {
/*  80 */     this.strLogSwitch = strLogSwitch;
/*  81 */     this.context.setProperty("log_level", strLogSwitch);
/*     */   }
/*     */ 
/*     */   public void setMon_switch(String strMonSwitch) {
/*  85 */     this.strMonSwitch = strMonSwitch;
/*  86 */     this.context.setProperty("mon_switch", strMonSwitch);
/*     */   }
/*     */ 
/*     */   public String getDesc() {
/*  90 */     return this.desc;
/*     */   }
/*     */ 
/*     */   public void setDesc(String strDesc)
/*     */   {
/*  95 */     this.desc = strDesc;
/*  96 */     this.context.setProperty("trans_desc", strDesc);
/*     */   }
/*     */ 
/*     */   public String getServerName() {
/* 100 */     return this.serverName;
/*     */   }
/*     */ 
/*     */   public String getNodeName()
/*     */   {
/* 105 */     return super.getClass().getSimpleName();
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 110 */     return super.toString() + ":code[" + this.code + "]";
/*     */   }
/*     */ 
/*     */   public void init() throws HiException
/*     */   {
/* 115 */     HiContext.pushCurrentContext(this.context);
/* 116 */     if (this.context.getId() == null)
/*     */     {
/* 118 */       this.context.setId(HiContext.TRN_PRE);
/*     */     }
/* 120 */     this.context = this.context;
/*     */ 
/* 122 */     this.serverName = ((String)this.context.getProperty("app_server"));
/* 123 */     this.context.setProperty("log_level", this.strLogSwitch);
/* 124 */     this.context.setProperty("mon_switch", this.strMonSwitch);
/* 125 */     this.context.setProperty("trans_switch", this.strTranSwitch);
/*     */ 
/* 128 */     loadComponent();
/*     */ 
/* 131 */     initProcess();
/*     */ 
/* 134 */     loadAfter();
/*     */   }
/*     */ 
/*     */   public void initProcess()
/*     */     throws HiException
/*     */   {
/*     */   }
/*     */ 
/*     */   public void loadComponent()
/*     */     throws HiException
/*     */   {
/*     */   }
/*     */ 
/*     */   public void loadAfter()
/*     */     throws HiException
/*     */   {
/* 153 */     popOwnerContext();
/*     */   }
/*     */ 
/*     */   public void afterProcess(HiMessageContext ctx) throws HiException {
/* 157 */     HiMessage msg = ctx.getCurrentMsg();
/* 158 */     msg.setHeadItem("SCH", "rp");
/* 159 */     if (msg.getHeadItemValSize("STC") > 1) {
/* 160 */       msg.delHeadItemVal("STC");
/*     */     }
/*     */ 
/* 163 */     Logger log = HiLog.getLogger(msg);
/* 164 */     if (log.isInfo3Enabled()) {
/* 165 */       log.info3(sm.getString("HiTransactionCTLImpl.EndOfProcess"));
/*     */     }
/* 167 */     super.afterProcess(ctx);
/*     */   }
/*     */ 
/*     */   public void beforeProcess(HiMessageContext ctx) throws HiException {
/* 171 */     HiMessage msg = ctx.getCurrentMsg();
/*     */ 
/* 173 */     HiServiceObject service = HiRegisterService.getService(getCode());
/*     */ 
/* 175 */     if (!(service.isRunning())) {
/* 176 */       throw new HiException("213301", getCode());
/*     */     }
/* 178 */     if (msg.getHeadItem("STF") == null) {
/* 179 */       msg.setHeadItem("STF", service.getLogLevel());
/*     */     }
/*     */ 
/* 182 */     if (!(msg.hasHeadItem("MON"))) {
/* 183 */       msg.setHeadItem("MON", service.getMonSwitch());
/*     */     }
/*     */ 
/* 186 */     if (this.timeOut != -1) {
/* 187 */       Long startTime = (Long)msg.getObjectHeadItem("STM");
/*     */ 
/* 189 */       if (startTime == null) {
/* 190 */         throw new HiException("213331");
/*     */       }
/* 192 */       long endTime = this.timeOut * 1000 + startTime.longValue();
/* 193 */       msg.setHeadItem("ETM", new Long(endTime));
/*     */     }
/*     */ 
/* 197 */     Logger log = HiLog.getLogger(msg);
/* 198 */     if (log.isInfo3Enabled()) {
/* 199 */       log.info3(sm.getString("HiTransactionCTLImpl.process00", ctx.getStrProp("SVR.name")));
/*     */ 
/* 201 */       log.info3(sm.getString("HiTransactionCTLImpl.process01", getCode()));
/*     */     }
/*     */ 
/* 204 */     super.beforeProcess(ctx);
/*     */   }
/*     */ 
/*     */   public void process(HiMessageContext ctx) throws HiException {
/* 208 */     HiMessage msg = ctx.getCurrentMsg();
/*     */     try
/*     */     {
/* 211 */       doProcess(ctx);
/*     */     }
/*     */     catch (Throwable e)
/*     */     {
/* 215 */       if (!(e instanceof HiExitException))
/*     */       {
/* 217 */         HiLog.logServiceError(msg, HiException.makeException(e));
/*     */       }
/*     */     }
/*     */     finally
/*     */     {
/* 222 */       if (ctx.getDataBaseUtil() != null)
/*     */       {
/* 224 */         ctx.getDataBaseUtil().closeAll();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void doProcess(HiMessageContext ctx) throws HiException {
/* 230 */     HiMessage msg = ctx.getCurrentMsg();
/*     */     try
/*     */     {
/* 235 */       service(ctx);
/*     */     }
/*     */     catch (HiErrorException e)
/*     */     {
/* 239 */       errorProcess(ctx, e);
/*     */     }
/*     */     catch (HiException e)
/*     */     {
/* 243 */       Object body = msg.getBody();
/* 244 */       if (body instanceof HiETF)
/*     */       {
/* 246 */         HiETF etfRoot = (HiETF)body;
/* 247 */         etfRoot.setChildValue("MSG_TYP", "E");
/* 248 */         etfRoot.setChildValue("RSP_CD", e.getCode());
/* 249 */         etfRoot.setChildValue("RSP_MSG", e.getAppMessage());
/*     */       }
/* 251 */       HiLog.logServiceError(msg, e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void errorProcess(HiMessageContext ctx, HiException e)
/*     */     throws HiException
/*     */   {
/* 262 */     HiMessage msg = ctx.getCurrentMsg();
/* 263 */     Logger log = HiLog.getLogger(msg);
/* 264 */     if (log.isInfoEnabled())
/* 265 */       log.info(sm.getString("HiTransactionCTLImpl.IgnoreErrorProcess"));
/*     */   }
/*     */ }