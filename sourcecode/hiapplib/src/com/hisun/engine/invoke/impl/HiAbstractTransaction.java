 package com.hisun.engine.invoke.impl;
 
 import com.hisun.engine.HiEngineModel;
 import com.hisun.exception.HiException;
 import com.hisun.hiexpression.HiExpFactory;
 import com.hisun.hiexpression.HiExpression;
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiContext;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import com.hisun.register.HiRegisterService;
 import com.hisun.register.HiServiceObject;
 import com.hisun.util.HiICSProperty;
 import com.hisun.util.HiStringManager;
 import java.util.concurrent.ConcurrentHashMap;
 import org.apache.commons.lang.StringUtils;
 
 public abstract class HiAbstractTransaction extends HiEngineModel
 {
   private static final String LOG_ID_MAP = "__LOG_ID_MAP";
   protected final Logger logger = (Logger)HiContext.getCurrentContext().getProperty("SVR.log");
 
   protected final HiStringManager sm = HiStringManager.getManager();
   protected String strCode;
   protected int timeout = -1;
 
   protected String strMonSwitch = "0";
 
   protected String strTranSwitch = "1";
 
   protected String strLogSwitch = "0";
 
   protected HiExpression log_idExpr = null;
   protected String log_id = null;
   private String strDesc;
 
   public String getNodeName()
   {
     return "Transaction";
   }
 
   public void setTimeout(int timeout) {
     this.timeout = timeout;
   }
 
   public int getTimeout() {
     return this.timeout;
   }
 
   public void setTran_switch(String strTranSwitch) {
     this.context.setProperty("trans_switch", strTranSwitch);
   }
 
   public void setLog_switch(String strLogSwitch) {
     this.context.setProperty("log_level", strLogSwitch);
   }
 
   public void setMon_switch(String strMonSwitch) {
     this.context.setProperty("mon_switch", strMonSwitch);
   }
 
   public String toString()
   {
     return super.toString() + ":code[" + this.strCode + "]";
   }
 
   public HiAbstractApplication getApplication() throws HiException {
     HiAbstractApplication returnHiAbstractApplication = (HiAbstractApplication)getParent();
     return returnHiAbstractApplication;
   }
 
   public String getCode() {
     return this.strCode;
   }
 
   public String getDesc() {
     return this.strDesc;
   }
 
   public void setDesc(String strDesc)
   {
     if (this.logger.isDebugEnabled()) {
       this.logger.debug("setDesc(String) - start");
     }
 
     this.strDesc = strDesc;
     this.context.setProperty("trans_desc", strDesc);
 
     if (this.logger.isDebugEnabled())
       this.logger.debug("setDesc(String) - end");
   }
 
   public void setCode(String strCode)
   {
     if (this.logger.isDebugEnabled()) {
       this.logger.debug("setCode(String) - start");
     }
 
     this.strCode = strCode;
     this.context.setId(HiContext.TRN_PRE + this.strCode);
     this.context.setProperty("trans_code", strCode);
 
     if (this.logger.isDebugEnabled())
       this.logger.debug("setCode(String) - end");
   }
 
   public void beforeProcess(HiMessageContext ctx) throws HiException {
     HiMessage msg = ctx.getCurrentMsg();
     HiServiceObject service = HiRegisterService.getService(this.context.getStrProp("SVR.name"), getCode());
 
     if (!(service.isRunning())) {
       throw new HiException("213301", getCode());
     }
     if (msg.getHeadItem("STF") == null) {
       msg.setHeadItem("STF", service.getLogLevel());
     }
 
     if (!(msg.hasHeadItem("MON"))) {
       msg.setHeadItem("MON", service.getMonSwitch());
     }
 
     if (this.timeout != -1) {
       Long startTime = (Long)msg.getObjectHeadItem("STM");
 
       if (startTime == null) {
         throw new HiException("213331");
       }
       long endTime = this.timeout * 1000 + startTime.longValue();
       msg.setHeadItem("ETM", new Long(endTime));
     }
     if (this.log_idExpr != null)
     {
       String fid = this.log_idExpr.getValue(ctx);
       if (StringUtils.isNotBlank(fid)) {
         String logLevel = getLogLevel(fid);
         if (logLevel != null) {
           msg.setHeadItem("STF", logLevel);
         }
         msg.setHeadItem("FID", fid);
         Logger log = HiLog.getLogger(msg);
         log.setMsgId(msg.getRequestId());
       }
     }
     super.beforeProcess(ctx);
   }
 
   public String getLog_id() {
     return this.log_id;
   }
 
   public void setLog_id(String log_id)
   {
     this.log_idExpr = HiExpFactory.createExp(log_id);
   }
 
   private static synchronized ConcurrentHashMap getLogIDMap() {
     HiContext ctx = HiContext.getRootContext();
     if (!(ctx.containsProperty("__LOG_ID_MAP"))) {
       ctx.setProperty("__LOG_ID_MAP", new ConcurrentHashMap());
     }
     return ((ConcurrentHashMap)ctx.getProperty("__LOG_ID_MAP"));
   }
 
   private String getLogLevel(String id) {
     ConcurrentHashMap logIDMap = getLogIDMap();
     if (logIDMap.containsKey(id)) {
       String value = (String)logIDMap.get(id);
       if (StringUtils.isNotBlank(value)) {
         return value;
       }
     }
     if (HiICSProperty.isPrdEnv()) {
       return "0";
     }
     return "1";
   }
 }