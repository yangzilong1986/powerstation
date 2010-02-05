 package com.hisun.engine.invoke.impl;
 
 import com.hisun.engine.HiEngineModel;
 import com.hisun.engine.HiEngineUtilities;
 import com.hisun.exception.HiException;
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import com.hisun.util.HiStringManager;
 
 public class HiHeadDel extends HiEngineModel
 {
   private String strHead_name;
 
   public void setHead_name(String strHead_name)
   {
     this.strHead_name = strHead_name;
   }
 
   public String getNodeName()
   {
     return "DelHead";
   }
 
   public String toString()
   {
     return super.toString() + ":Head_Name[" + this.strHead_name + "]";
   }
 
   public void process(HiMessageContext messContext) throws HiException
   {
     HiMessage mess = messContext.getCurrentMsg();
     Logger log = HiLog.getLogger(mess);
     if (log.isInfoEnabled()) {
       log.info(sm.getString("HiHeadDel.process00", HiEngineUtilities.getCurFlowStep(), this.strHead_name));
     }
     mess.delHeadItem(this.strHead_name);
   }
 }