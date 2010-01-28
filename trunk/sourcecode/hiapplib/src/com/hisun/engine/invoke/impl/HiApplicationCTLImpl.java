 package com.hisun.engine.invoke.impl;
 
 import com.hisun.exception.HiException;
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiContext;
 import com.hisun.message.HiETF;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import com.hisun.util.HiParaParser;
 import java.util.HashMap;
 import org.dom4j.Element;
 
 public class HiApplicationCTLImpl extends HiAbstractApplication
 {
   private final Logger logger = (Logger)HiContext.getCurrentContext().getProperty("SVR.log");
   private String strAppName;
   private String strCode;
   private String strTrace;
 
   public HiApplicationCTLImpl()
     throws HiException
   {
     this.context = HiContext.createAndPushContext();
   }
 
   public String getAppCode()
   {
     return this.strCode;
   }
 
   public String getAppName()
   {
     return this.strAppName;
   }
 
   public HiTransactionCTLImpl getTransaction(String strCode) {
     if (this.logger.isDebugEnabled()) {
       this.logger.debug("getTransaction(String) - start");
     }
 
     HiTransactionCTLImpl returnHiTransactionCTLImpl = (HiTransactionCTLImpl)getTranMap().get(strCode);
 
     if (this.logger.isDebugEnabled()) {
       this.logger.debug("getTransaction(String) - end");
     }
     return returnHiTransactionCTLImpl;
   }
 
   public boolean isTrace()
   {
     return (!("yes".equalsIgnoreCase(this.strTrace)));
   }
 
   public void setCode(String strCode)
   {
     if (this.logger.isDebugEnabled()) {
       this.logger.debug("setCode(String) - start");
     }
 
     this.strCode = strCode;
     this.context.setProperty("app_code", strCode);
 
     if (this.logger.isDebugEnabled())
       this.logger.debug("setCode(String) - end");
   }
 
   public void setName(String strAppName)
   {
     if (this.logger.isDebugEnabled()) {
       this.logger.debug("setName(String) - start");
     }
 
     this.strAppName = strAppName;
     this.context.setId(HiContext.APP_PRE + this.strAppName);
     this.context.setProperty("app_name", strAppName);
 
     if (this.logger.isDebugEnabled())
       this.logger.debug("setName(String) - end");
   }
 
   public void setTrace(String strTrace)
   {
     if (this.logger.isDebugEnabled()) {
       this.logger.debug("setTrace(String) - start");
     }
 
     this.strTrace = strTrace;
     this.context.setProperty("app_log_level", strTrace);
 
     if (this.logger.isDebugEnabled())
       this.logger.debug("setTrace(String) - end");
   }
 
   public void process(HiMessageContext messContext) throws HiException
   {
     HiMessage mess = messContext.getCurrentMsg();
     String strCode = mess.getHeadItem("STC");
     Logger log = HiLog.getLogger(mess);
     if (log.isDebugEnabled()) {
       log.debug("strCode====" + strCode);
     }
     mess.setHeadItem("APP", this.strAppName);
 
     mess.getETFBody().setChildValue("TXN_CD", strCode);
     mess.getETFBody().setChildValue("APP_CLS", getAppCode());
 
     HiTransactionCTLImpl tranAction = getTransaction(strCode);
     log.debug("HiTransactionCTLImpl=" + tranAction.getClass());
     HiProcess.process(tranAction, messContext);
 
     if (this.logger.isDebugEnabled())
       this.logger.debug("process(HiMessageContext) - end ");
   }
 
   public void loadAfter() throws HiException
   {
     if (this.logger.isDebugEnabled()) {
       this.logger.debug("HiAbstractApplication:loadAfter() - start");
     }
 
     Element element = (Element)this.context.getProperty("CONFIGDECLARE", "BUSCFG");
 
     if (element != null) {
       HiParaParser.setAppParam(this.context, this.strAppName, element);
       HiContext ctx = this.context.getFirstChild();
       for (; ctx != null; ctx = ctx.getNextBrother()) {
         String code = ctx.getStrProp("trans_code");
         HiParaParser.setTrnParam(ctx, this.strAppName, code, element);
       }
     }
     element = null;
 
     super.popOwnerContext();
 
     if (this.logger.isDebugEnabled())
       this.logger.debug("HiAbstractApplication:loadAfter() - end");
   }
 }