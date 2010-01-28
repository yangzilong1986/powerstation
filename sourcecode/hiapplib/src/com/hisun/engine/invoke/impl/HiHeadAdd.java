 package com.hisun.engine.invoke.impl;
 
 import com.hisun.engine.HiEngineModel;
 import com.hisun.engine.HiEngineUtilities;
 import com.hisun.exception.HiException;
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import com.hisun.util.HiStringManager;
 import org.apache.commons.lang.StringUtils;
 
 public class HiHeadAdd extends HiEngineModel
 {
   private String strHead_name;
   private String strName;
   private String strValue;
 
   public void setHead_name(String strHead_name)
   {
     this.strHead_name = strHead_name;
   }
 
   public void setName(String strName)
   {
     this.strName = strName;
   }
 
   public void setValue(String strValue)
   {
     this.strValue = strValue;
   }
 
   public String getNodeName()
   {
     return "AddHead";
   }
 
   public String toString()
   {
     return super.toString() + ":name[" + this.strName + "]value[" + this.strValue + "]";
   }
 
   public void process(HiMessageContext messContext)
     throws HiException
   {
     HiMessage mess = messContext.getCurrentMsg();
     Logger log = HiLog.getLogger(mess);
     if (StringUtils.isNotBlank(this.strName))
     {
       String val = HiItemHelper.getEtfItem(mess, this.strName);
       if (StringUtils.isNotEmpty(val))
       {
         this.strValue = val;
       }
     }
 
     mess.setHeadItem(this.strHead_name, this.strValue);
     if (log.isInfoEnabled())
       log.info(sm.getString("HiHeadAdd.process00", HiEngineUtilities.getCurFlowStep(), this.strHead_name, this.strValue));
   }
 }