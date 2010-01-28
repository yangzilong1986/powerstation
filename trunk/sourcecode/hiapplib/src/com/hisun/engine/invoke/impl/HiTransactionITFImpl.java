 package com.hisun.engine.invoke.impl;
 
 import com.hisun.database.HiDataBaseUtil;
 import com.hisun.engine.invoke.HiIEngineModel;
 import com.hisun.exception.HiException;
 import com.hisun.hiexpression.HiExpFactory;
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiContext;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import com.hisun.util.HiStringManager;
 import java.sql.Timestamp;
 import java.util.HashMap;
 import org.apache.commons.lang.StringUtils;
 
 public class HiTransactionITFImpl extends HiAbstractTransaction
 {
   private HashMap childMap = new HashMap();
   private boolean isByPass;
   private String strServerName = null;
 
   private String strServiceCode = null;
 
   private String strErrorName = "Default";
 
   public HiTransactionITFImpl()
   {
     this.context = HiContext.createAndPushContext();
 
     this.strServerName = ((String)this.context.getProperty("app_server"));
 
     this.context.setProperty("log_level", this.strLogSwitch);
     this.context.setProperty("mon_switch", this.strMonSwitch);
     this.context.setProperty("trans_switch", this.strTranSwitch);
     this.context.setProperty("ECO", this.strErrorName);
 
     this.log_id = this.context.getStrProp("__LOG_ID");
     if (this.log_id != null)
       this.log_idExpr = HiExpFactory.createExp(this.log_id);
   }
 
   public void addChilds(HiIEngineModel child)
     throws HiException
   {
     super.addChilds(child);
     if (child instanceof HiRequest)
       this.childMap.put("rq", child);
     else
       this.childMap.put("rp", child);
   }
 
   public boolean getByPass() {
     return this.isByPass;
   }
 
   public String getServerName() {
     return this.strServerName;
   }
 
   public String getService() {
     return this.strServiceCode;
   }
 
   public void process(HiMessageContext ctx)
     throws HiException
   {
     HiMessage msg = ctx.getCurrentMsg();
     Logger log = HiLog.getLogger(msg);
     try {
       msg.setHeadItem("ECO", getError());
 
       if (log.isInfo3Enabled()) {
         if (StringUtils.equalsIgnoreCase(msg.getHeadItem("SCH"), "rq"))
         {
           log.info3(this.sm.getString("HiTransactionITFImpl.process01", HiMessageContext.getCurrentMessageContext().getStrProp("SVR.name")));
         }
         else
         {
           log.info3(this.sm.getString("HiTransactionITFImpl.process02", HiMessageContext.getCurrentMessageContext().getStrProp("SVR.name")));
         }
 
         if (msg.hasHeadItem("PlainText")) {
           log.info2(this.sm.getString("HiTransactionITFImpl.process03", msg.getHead(), msg.getObjectHeadItem("PlainText")));
         }
         else
         {
           log.info2(this.sm.getString("HiTransactionITFImpl.process03", msg.getHead(), msg.getBody()));
         }
 
         log.info3(this.sm.getString("HiTransactionITFImpl.process05", this.strCode));
       }
 
       Long startTime = (Long)msg.getObjectHeadItem("STM");
       if (log.isDebugEnabled()) {
         log.debug(this.sm.getString("HiTransactionITFImpl.process1", new Timestamp(startTime.longValue())));
       }
 
       if (this.timeout != -1) {
         Integer time = new Integer(this.timeout * 1000);
         long endTime = time.longValue() + startTime.longValue();
         msg.setHeadItem("ETM", new Long(endTime));
         if (log.isDebugEnabled()) {
           log.debug(this.sm.getString("HiTransactionITFImpl.process2", new Timestamp(endTime)));
         }
       }
 
       String strType = msg.getHeadItem("SCH");
       if (log.isDebugEnabled()) {
         log.debug(this.sm.getString("HiTransactionITFImpl.process3", strType));
       }
 
       HiAbstractRqAndRp element = (HiAbstractRqAndRp)this.childMap.get(strType);
 
       if ((!(StringUtils.isEmpty(this.strServerName))) || (!(StringUtils.isEmpty(this.strServiceCode))))
       {
         String strSTCCode = this.strServiceCode;
         if (StringUtils.isEmpty(this.strServiceCode)) {
           strSTCCode = getCode();
         }
 
         msg.addHeadItem("STC", strSTCCode);
 
         if (StringUtils.isNotEmpty(this.strServerName))
           msg.setHeadItem("SDT", this.strServerName);
       }
       HiProcess.process(element, ctx);
     }
     catch (HiException e)
     {
     }
     finally
     {
       if (ctx.getDataBaseUtil() != null) {
         ctx.getDataBaseUtil().closeAll();
       }
     }
     if (log.isInfo3Enabled()) {
       log.info2(this.sm.getString("HiTransactionITFImpl.process03", msg.getHead(), msg.getBody().toString()));
 
       log.info3(this.sm.getString("HiTransactionITFImpl.process07"));
     }
   }
 
   public void setPass(boolean isByPass) {
     this.isByPass = isByPass;
     this.context.setProperty("trans_pass", Boolean.valueOf(isByPass));
   }
 
   public void setServer(String strServerName)
   {
     this.strServerName = strServerName;
   }
 
   public void setService(String strServiceCode)
   {
     this.strServiceCode = strServiceCode;
   }
 
   public String getError() {
     return this.strErrorName;
   }
 
   public void setError(String strErrorName)
   {
     this.strErrorName = strErrorName;
     this.context.setProperty("ECO", strErrorName);
   }
 
   public void loadAfter() throws HiException {
     super.popOwnerContext();
   }
 }