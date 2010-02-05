 package com.hisun.engine.invoke.impl;
 
 import com.hisun.exception.HiException;
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiContext;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import com.hisun.util.HiStringManager;
 import java.util.HashMap;
 import org.apache.commons.lang.StringUtils;
 
 public class HiApplicationITFImpl extends HiAbstractApplication
 {
   private final Logger logger = (Logger)HiContext.getCurrentContext().getProperty("SVR.log");
 
   private final HiStringManager sm = HiStringManager.getManager();
 
   private String rootName = "ROOT";
   private String strAppName;
   private String strObjServerName;
   private String strTrace;
   private HiGetTxnCode txnCode = null;
 
   public HiApplicationITFImpl() throws HiException
   {
     this.context = HiContext.createAndPushContext();
   }
 
   public String getAppName()
   {
     return this.strAppName;
   }
 
   public String getObjServerName()
   {
     return this.strObjServerName;
   }
 
   public HiTransactionITFImpl getTransaction(String strCode)
   {
     HashMap map = getTranMap();
 
     HiTransactionITFImpl tran = (HiTransactionITFImpl)getTranMap().get(strCode);
 
     return tran;
   }
 
   private HiGetTxnCode getTxnCode()
   {
     return this.txnCode;
   }
 
   public void beforeProcess(HiMessageContext messContext)
     throws HiException
   {
     Logger log = HiLog.getLogger(messContext.getCurrentMsg());
 
     HiMessage mess = messContext.getCurrentMsg();
 
     super.beforeProcess(messContext);
 
     String strCode = mess.getHeadItem("STC");
 
     if (!(StringUtils.isEmpty(strCode))) {
       return;
     }
     HiGetTxnCode txdCode = getTxnCode();
     if (txdCode == null)
     {
       throw new HiException("213339");
     }
     HiProcess.process(txdCode, messContext);
   }
 
   public void afterProcess(HiMessageContext messContext)
     throws HiException
   {
     HiMessage mess = messContext.getCurrentMsg();
     String strType = mess.getHeadItem("SCH");
     Logger log = HiLog.getLogger(mess);
     if (log.isDebugEnabled())
     {
       log.debug("HiApplicationITFImpl:afterProcess(HiMessageContext) - start");
     }
 
     super.afterProcess(messContext);
 
     if (!(log.isDebugEnabled()))
       return;
     log.debug("HiApplicationITFImpl:afterProcess(HiMessageContext) - end");
   }
 
   public void process(HiMessageContext ctx)
     throws HiException
   {
     HiMessage msg = ctx.getCurrentMsg();
 
     Logger log = HiLog.getLogger(msg);
     if (log.isDebugEnabled())
     {
       log.debug("HiApplicationITFImpl:process(HiMessageContext) - start");
     }
 
     String strCode = msg.getHeadItem("STC");
     msg.setHeadItem("APP", this.strAppName);
     ctx.setProperty("TXN_CD", strCode);
 
     HiTransactionITFImpl tranAction = getTransaction(strCode);
     if (tranAction == null)
     {
       throw new HiException("213322", strCode);
     }
 
     HiProcess.process(tranAction, ctx);
 
     if (!(log.isDebugEnabled()))
       return;
     log.debug("HiApplicationITFImpl:process(HiMessageContext) - end");
   }
 
   public void setName(String strAppName)
   {
     if (this.logger.isDebugEnabled())
     {
       this.logger.debug("setName(String) - start");
     }
 
     this.strAppName = strAppName;
     this.context.setId(HiContext.APP_PRE + this.strAppName);
     this.context.setProperty("app_name", strAppName);
     if (!(this.logger.isDebugEnabled()))
       return;
     this.logger.debug("setName(String) - end");
   }
 
   public void setRoot_name(String strRootName)
   {
     if (this.logger.isDebugEnabled())
     {
       this.logger.debug("setRoot_name(String) - start");
     }
 
     if (StringUtils.isNotEmpty(strRootName)) {
       this.rootName = strRootName;
     }
     if (!(this.logger.isDebugEnabled()))
       return;
     this.logger.debug("setRoot_name(String) - end");
   }
 
   public String getRootName()
   {
     return this.rootName;
   }
 
   public void setServer(String strAppServerName)
   {
     if (this.logger.isDebugEnabled())
     {
       this.logger.debug("setServer(String) - start");
     }
 
     this.strObjServerName = strAppServerName;
 
     this.context.setProperty("app_server", this.strObjServerName);
 
     if (!(this.logger.isDebugEnabled()))
       return;
     this.logger.debug("setServer(String) - end");
   }
 
   public void setTxnCode(HiGetTxnCode txnCode)
   {
     if (this.logger.isDebugEnabled())
     {
       this.logger.debug("setTxnCode(HiGetTxnCode) - start");
     }
 
     this.txnCode = txnCode;
 
     if (!(this.logger.isDebugEnabled()))
       return;
     this.logger.debug("setTxnCode(HiGetTxnCode) - end");
   }
 
   public void loadAfter()
     throws HiException
   {
     if (this.logger.isDebugEnabled())
     {
       this.logger.debug("HiAbstractApplication:loadAfter() - start");
     }
 
     super.popOwnerContext();
 
     if (!(this.logger.isDebugEnabled()))
       return;
     this.logger.debug("HiAbstractApplication:loadAfter() - end");
   }
 }