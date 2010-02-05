 package com.hisun.engine.invoke.impl;
 
 import com.hisun.engine.HiEngineModel;
 import com.hisun.engine.invoke.HiIAction;
 import com.hisun.engine.invoke.HiIEngineModel;
 import com.hisun.exception.HiException;
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiContext;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import java.util.HashMap;
 import java.util.List;
 
 public abstract class HiAbstractApplication extends HiEngineModel
 {
   private final Logger logger = (Logger)HiContext.getCurrentContext().getProperty("SVR.log");
 
   private HashMap tranMap = new HashMap();
   private String version;
   private int timeout = -1;
   private String fileName;
   private String type;
   private String log_id;
 
   public void addChilds(HiIEngineModel child)
     throws HiException
   {
     if (this.logger.isDebugEnabled()) {
       this.logger.debug("HiAbstractApplication:addChilds(HiIEngineModel) - start");
     }
 
     if (child instanceof HiAbstractTransaction) {
       HiAbstractTransaction tranObj = (HiAbstractTransaction)child;
 
       this.tranMap.put(tranObj.getCode(), tranObj);
     }
     super.addChilds(child);
 
     if (this.logger.isDebugEnabled())
       this.logger.debug("HiAbstractApplication:addChilds(HiIEngineModel) - end");
   }
 
   public HashMap getTranMap()
   {
     return this.tranMap;
   }
 
   public void addTransaction(HiAbstractTransaction action) {
     if (this.logger.isDebugEnabled()) {
       this.logger.debug("HiAbstractApplication:addTransaction(HiAbstractTransaction) - start");
     }
 
     this.tranMap.put(action.getCode(), action);
 
     if (this.logger.isDebugEnabled())
       this.logger.debug("HiAbstractApplication:addTransaction(HiAbstractTransaction) - end");
   }
 
   public void addTransaction(HiAbstractTransaction action, String strCode)
   {
     if (this.logger.isDebugEnabled()) {
       this.logger.debug("HiAbstractApplication:addTransaction(HiAbstractTransaction, String) - start");
     }
 
     this.tranMap.put(strCode, action);
 
     if (this.logger.isDebugEnabled())
       this.logger.debug("HiAbstractApplication:addTransaction(HiAbstractTransaction, String) - end");
   }
 
   public void beforeProcess(HiMessageContext messContext)
     throws HiException
   {
     Logger log = HiLog.getLogger(messContext.getCurrentMsg());
     if (log.isDebugEnabled()) {
       log.debug("HiAbstractApplication:beforeProcess(HiMessageContext) - start");
     }
     HiMessage mess = messContext.getCurrentMsg();
     Long startTime = (Long)mess.getObjectHeadItem("STM");
 
     if (startTime == null) {
       throw new HiException("213331");
     }
     if ((this.timeout != -1) && (!(mess.hasHeadItem("ETM"))))
     {
       Integer time = new Integer(this.timeout * 1000);
       long endTime = time.longValue() + startTime.longValue();
       mess.setHeadItem("ETM", new Long(endTime));
     }
     super.beforeProcess(messContext);
 
     List initList = super.getInitList();
     if (initList.size() > 0) {
       for (int i = 0; i < initList.size(); ++i) {
         HiIAction action = (HiIAction)initList.get(i);
         HiProcess.process(action, messContext);
       }
     }
 
     if (log.isDebugEnabled())
       log.debug("HiAbstractApplication:beforeProcess(HiMessageContext) - end");
   }
 
   public HiAbstractTransaction getTransactions(String strCode)
   {
     if (this.logger.isDebugEnabled()) {
       this.logger.debug("HiAbstractApplication:getTransactions(String) - start");
     }
 
     HiAbstractTransaction returnHiAbstractTransaction = (HiAbstractTransaction)this.tranMap.get(strCode);
 
     if (this.logger.isDebugEnabled()) {
       this.logger.debug("HiAbstractApplication:getTransactions(String) - end");
     }
     return returnHiAbstractTransaction;
   }
 
   public String getNodeName() {
     return "Application";
   }
 
   public String getVersion() {
     return this.context.getStrProp("@SYS", "_VERSION");
   }
 
   public void setVersion(String version) {
     this.version = version;
     this.context.setProperty("@SYS", "_VERSION", this.version);
   }
 
   public int getTimeout() {
     return this.timeout;
   }
 
   public void setTimeout(int timeout) {
     this.timeout = timeout;
   }
 
   public String getFileName()
   {
     return this.fileName;
   }
 
   public void setFileName(String fileName)
   {
     this.fileName = fileName;
   }
 
   public String getType()
   {
     return this.type;
   }
 
   public void setType(String type)
   {
     this.type = type;
   }
 
   public String getLog_id()
   {
     return this.log_id;
   }
 
   public void setLog_id(String log_id)
   {
     this.log_id = log_id;
     this.context.setProperty("__LOG_ID", log_id);
   }
 }