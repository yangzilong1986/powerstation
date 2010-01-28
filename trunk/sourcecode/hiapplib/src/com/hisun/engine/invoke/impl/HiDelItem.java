 package com.hisun.engine.invoke.impl;
 
 import com.hisun.engine.HiEngineModel;
 import com.hisun.engine.HiEngineUtilities;
 import com.hisun.exception.HiException;
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiETF;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import com.hisun.util.HiStringManager;
 
 public class HiDelItem extends HiEngineModel
 {
   private String strName;
 
   public void setName(String strName)
   {
     this.strName = strName;
   }
 
   public void process(HiMessageContext messContext)
     throws HiException
   {
     HiMessage mess = messContext.getCurrentMsg();
     Logger log = HiLog.getLogger(mess);
 
     if (log.isInfoEnabled()) {
       log.info(sm.getString("HiDelItem.process", HiEngineUtilities.getCurFlowStep(), this.strName));
     }
 
     HiETF etf = (HiETF)mess.getBody();
     etf.removeGrandChild(HiItemHelper.getCurEtfLevel(mess) + this.strName);
   }
 
   public String getNodeName()
   {
     return "DelItem";
   }
 
   public String toString()
   {
     return super.toString() + ": name[" + this.strName + "]";
   }
 }