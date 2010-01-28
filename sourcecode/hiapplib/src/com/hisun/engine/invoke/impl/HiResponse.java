 package com.hisun.engine.invoke.impl;
 
 import com.hisun.exception.HiException;
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 
 public class HiResponse extends HiAbstractRqAndRp
 {
   public String getNodeName()
   {
     return "Response";
   }
 
   public void afterProcess(HiMessageContext messContext)
     throws HiException
   {
     HiMessage mess = messContext.getCurrentMsg();
     Logger log = HiLog.getLogger(mess);
     super.afterProcess(messContext);
     mess.delHeadItemVal("STC");
     if (!(log.isDebugEnabled()))
       return;
     log.debug("HiResponse(HiMessageContext) - end");
   }
 }