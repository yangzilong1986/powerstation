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
 import org.apache.commons.lang.StringUtils;
 
 public class HiHeadGet extends HiEngineModel
 {
   private String strHead_name;
   private String strName;
 
   public void setHead_name(String strHead_name)
   {
     this.strHead_name = strHead_name;
   }
 
   public void setName(String strName)
   {
     this.strName = strName;
   }
 
   public String getNodeName()
   {
     return "GetHead";
   }
 
   public String toString()
   {
     return super.toString() + ":name[" + this.strName + "] head_name[" + this.strHead_name + "]";
   }
 
   public void process(HiMessageContext messContext)
     throws HiException
   {
     HiMessage mess = messContext.getCurrentMsg();
     String strValue = mess.getHeadItem(this.strHead_name);
     if (StringUtils.isEmpty(strValue)) {
       throw new HiException("213328", this.strHead_name);
     }
     HiETF etf = (HiETF)mess.getBody();
     etf.setGrandChildNode(this.strName, strValue);
     Logger log = HiLog.getLogger(mess);
     if (log.isInfoEnabled())
       log.info(sm.getString("HiHeadGet.process00", HiEngineUtilities.getCurFlowStep(), this.strHead_name, strValue));
   }
 }