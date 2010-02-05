 package com.hisun.engine.invoke.impl;
 
 import com.hisun.engine.HiEngineModel;
 import com.hisun.engine.HiEngineUtilities;
 import com.hisun.exception.HiException;
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiMessageContext;
 import com.hisun.util.HiStringManager;
 
 public class HiDelete extends HiEngineModel
 {
   private String strName;
 
   public void setName(String strName)
   {
     this.strName = strName;
   }
 
   public void process(HiMessageContext ctx)
     throws HiException
   {
     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
     if (log.isInfoEnabled()) {
       log.info(sm.getString("HiDelete.process00", HiEngineUtilities.getCurFlowStep(), this.strName));
     }
     HiEngineUtilities.processFlow(this.strName, null, false, ctx);
   }
 
   public String getNodeName()
   {
     return "Delete";
   }
 
   public String toString()
   {
     return super.toString() + ": name[" + this.strName + "]";
   }
 }