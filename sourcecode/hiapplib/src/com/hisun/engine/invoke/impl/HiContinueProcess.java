 package com.hisun.engine.invoke.impl;
 
 import com.hisun.engine.HiEngineModel;
 import com.hisun.engine.HiEngineUtilities;
 import com.hisun.engine.exception.HiContinueException;
 import com.hisun.exception.HiException;
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiMessageContext;
 import com.hisun.util.HiStringManager;
 
 public class HiContinueProcess extends HiEngineModel
 {
   public void process(HiMessageContext messContext)
     throws HiException
   {
     Logger log = HiLog.getLogger(messContext.getCurrentMsg());
     if (log.isInfoEnabled()) {
       log.info(sm.getString("HiContinueProcess.process00", HiEngineUtilities.getCurFlowStep()));
     }
 
     throw new HiContinueException();
   }
 
   public String getNodeName() {
     return "Continue";
   }
 }