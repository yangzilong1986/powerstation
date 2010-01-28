 package com.hisun.engine.invoke.impl;
 
 import com.hisun.engine.HiEngineModel;
 import com.hisun.engine.HiEngineUtilities;
 import com.hisun.engine.exception.HiBreakException;
 import com.hisun.exception.HiException;
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiMessageContext;
 import com.hisun.util.HiStringManager;
 
 public class HiBreakProcess extends HiEngineModel
 {
   public void process(HiMessageContext messContext)
     throws HiException
   {
     Logger log = HiLog.getLogger(messContext.getCurrentMsg());
     if (log.isInfoEnabled()) {
       log.info(sm.getString("HiBreakProcess.process00", HiEngineUtilities.getCurFlowStep()));
     }
 
     throw new HiBreakException();
   }
 
   public String getNodeName() {
     return "Break";
   }
 }