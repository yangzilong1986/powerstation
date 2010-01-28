 package com.hisun.engine.pojo;
 
 import com.hisun.database.HiDataBaseUtil;
 import com.hisun.engine.exception.HiErrorException;
 import com.hisun.engine.exception.HiExitException;
 import com.hisun.exception.HiException;
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiContext;
 import com.hisun.message.HiETF;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import com.hisun.register.HiRegisterService;
 import com.hisun.register.HiServiceObject;
 import com.hisun.util.HiStringManager;
 
 public abstract class HiTransactionBean extends HiPojoEngineModel
   implements HiITransaction
 {
   protected HiContext context;
   protected int timeOut;
   protected String code;
   protected String strMonSwitch;
   protected String strTranSwitch;
   protected String strLogSwitch;
   protected String desc;
   protected String serverName;
 
   public HiTransactionBean()
   {
     this.context = HiContext.createContext(HiContext.getCurrentContext());
 
     this.timeOut = -1;
 
     this.code = null;
 
     this.strMonSwitch = "0";
 
     this.strTranSwitch = "1";
 
     this.strLogSwitch = "0";
   }
 
   public String getCode()
   {
     return this.code;
   }
 
   public void setCode(String code)
   {
     this.code = code;
 
     this.context.setId(HiContext.TRN_PRE + this.code);
     this.context.setProperty("trans_code", this.code);
   }
 
   public void setTimeOut(int timeOut) {
     this.timeOut = timeOut;
   }
 
   public int getTimeOut() {
     return this.timeOut;
   }
 
   public void setTran_switch(String strTranSwitch) {
     this.strTranSwitch = strTranSwitch;
     this.context.setProperty("trans_switch", strTranSwitch);
   }
 
   public void setLog_switch(String strLogSwitch) {
     this.strLogSwitch = strLogSwitch;
     this.context.setProperty("log_level", strLogSwitch);
   }
 
   public void setMon_switch(String strMonSwitch) {
     this.strMonSwitch = strMonSwitch;
     this.context.setProperty("mon_switch", strMonSwitch);
   }
 
   public String getDesc() {
     return this.desc;
   }
 
   public void setDesc(String strDesc)
   {
     this.desc = strDesc;
     this.context.setProperty("trans_desc", strDesc);
   }
 
   public String getServerName() {
     return this.serverName;
   }
 
   public String getNodeName()
   {
     return super.getClass().getSimpleName();
   }
 
   public String toString()
   {
     return super.toString() + ":code[" + this.code + "]";
   }
 
   public void init() throws HiException
   {
     HiContext.pushCurrentContext(this.context);
     if (this.context.getId() == null)
     {
       this.context.setId(HiContext.TRN_PRE);
     }
     this.context = this.context;
 
     this.serverName = ((String)this.context.getProperty("app_server"));
     this.context.setProperty("log_level", this.strLogSwitch);
     this.context.setProperty("mon_switch", this.strMonSwitch);
     this.context.setProperty("trans_switch", this.strTranSwitch);
 
     loadComponent();
 
     initProcess();
 
     loadAfter();
   }
 
   public void initProcess()
     throws HiException
   {
   }
 
   public void loadComponent()
     throws HiException
   {
   }
 
   public void loadAfter()
     throws HiException
   {
     popOwnerContext();
   }
 
   public void afterProcess(HiMessageContext ctx) throws HiException {
     HiMessage msg = ctx.getCurrentMsg();
     msg.setHeadItem("SCH", "rp");
     if (msg.getHeadItemValSize("STC") > 1) {
       msg.delHeadItemVal("STC");
     }
 
     Logger log = HiLog.getLogger(msg);
     if (log.isInfo3Enabled()) {
       log.info3(sm.getString("HiTransactionCTLImpl.EndOfProcess"));
     }
     super.afterProcess(ctx);
   }
 
   public void beforeProcess(HiMessageContext ctx) throws HiException {
     HiMessage msg = ctx.getCurrentMsg();
 
     HiServiceObject service = HiRegisterService.getService(getCode());
 
     if (!(service.isRunning())) {
       throw new HiException("213301", getCode());
     }
     if (msg.getHeadItem("STF") == null) {
       msg.setHeadItem("STF", service.getLogLevel());
     }
 
     if (!(msg.hasHeadItem("MON"))) {
       msg.setHeadItem("MON", service.getMonSwitch());
     }
 
     if (this.timeOut != -1) {
       Long startTime = (Long)msg.getObjectHeadItem("STM");
 
       if (startTime == null) {
         throw new HiException("213331");
       }
       long endTime = this.timeOut * 1000 + startTime.longValue();
       msg.setHeadItem("ETM", new Long(endTime));
     }
 
     Logger log = HiLog.getLogger(msg);
     if (log.isInfo3Enabled()) {
       log.info3(sm.getString("HiTransactionCTLImpl.process00", ctx.getStrProp("SVR.name")));
 
       log.info3(sm.getString("HiTransactionCTLImpl.process01", getCode()));
     }
 
     super.beforeProcess(ctx);
   }
 
   public void process(HiMessageContext ctx) throws HiException {
     HiMessage msg = ctx.getCurrentMsg();
     try
     {
       doProcess(ctx);
     }
     catch (Throwable e)
     {
       if (!(e instanceof HiExitException))
       {
         HiLog.logServiceError(msg, HiException.makeException(e));
       }
     }
     finally
     {
       if (ctx.getDataBaseUtil() != null)
       {
         ctx.getDataBaseUtil().closeAll();
       }
     }
   }
 
   public void doProcess(HiMessageContext ctx) throws HiException {
     HiMessage msg = ctx.getCurrentMsg();
     try
     {
       service(ctx);
     }
     catch (HiErrorException e)
     {
       errorProcess(ctx, e);
     }
     catch (HiException e)
     {
       Object body = msg.getBody();
       if (body instanceof HiETF)
       {
         HiETF etfRoot = (HiETF)body;
         etfRoot.setChildValue("MSG_TYP", "E");
         etfRoot.setChildValue("RSP_CD", e.getCode());
         etfRoot.setChildValue("RSP_MSG", e.getAppMessage());
       }
       HiLog.logServiceError(msg, e);
     }
   }
 
   public void errorProcess(HiMessageContext ctx, HiException e)
     throws HiException
   {
     HiMessage msg = ctx.getCurrentMsg();
     Logger log = HiLog.getLogger(msg);
     if (log.isInfoEnabled())
       log.info(sm.getString("HiTransactionCTLImpl.IgnoreErrorProcess"));
   }
 }