 package com.hisun.engine.invoke.impl;
 
 import com.hisun.engine.HiEngineModel;
 import com.hisun.engine.HiEngineUtilities;
 import com.hisun.exception.HiException;
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiContext;
 import com.hisun.message.HiETF;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import com.hisun.util.HiStringManager;
 
 public class HiCopyItem extends HiEngineModel
 {
   private final Logger logger;
   private String strSour_name;
   private String strDest_name;
 
   public HiCopyItem()
   {
     this.logger = ((Logger)HiContext.getCurrentContext().getProperty("SVR.log"));
   }
 
   public void setSour_name(String strSour_name)
   {
     if (this.logger.isDebugEnabled())
     {
       this.logger.debug("setSour_name(String) - start");
     }
 
     this.strSour_name = strSour_name;
 
     if (!(this.logger.isDebugEnabled()))
       return;
     this.logger.debug("setSour_name(String) - end");
   }
 
   public void setDest_name(String strDest_name)
   {
     if (this.logger.isDebugEnabled())
     {
       this.logger.debug("setDest_name(String) - start");
     }
 
     this.strDest_name = strDest_name;
 
     if (!(this.logger.isDebugEnabled()))
       return;
     this.logger.debug("setDest_name(String) - end");
   }
 
   public void process(HiMessageContext ctx)
     throws HiException
   {
     HiMessage msg = ctx.getCurrentMsg();
     Logger log = HiLog.getLogger(msg);
     if (log.isDebugEnabled())
     {
       log.debug("HiCopyItem:process(HiMessageContext) - start");
     }
 
     HiETF etf = (HiETF)msg.getBody();
 
     String value = etf.getChildValue(HiItemHelper.getCurEtfLevel(msg) + this.strSour_name);
 
     if (log.isInfoEnabled()) {
       log.info(sm.getString("HiCopyItem.process00", HiEngineUtilities.getCurFlowStep(), this.strSour_name, value));
     }
 
     HiItemHelper.addEtfItem(msg, this.strDest_name, value);
 
     if (!(log.isDebugEnabled()))
       return;
     log.debug("HiCopyItem:process(HiMessageContext) - end");
   }
 
   public String getNodeName()
   {
     return "CopyItem";
   }
 
   public String toString()
   {
     return getNodeName() + "[" + this.strSour_name + "],[" + this.strDest_name + "]";
   }
 }