 package com.hisun.handler;
 
 import com.hisun.engine.invoke.impl.HiValidateMsg;
 import com.hisun.exception.HiException;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import com.hisun.pubinterface.IHandler;
 
 public class HiValidator
   implements IHandler
 {
   public void process(HiMessageContext arg0)
     throws HiException
   {
     HiValidateMsg validateMsg = HiValidateMsg.get(arg0);
     if (!(validateMsg.isEmpty())) {
       validateMsg.dump(arg0.getCurrentMsg().getETFBody());
       throw new HiException("241048", arg0.getCurrentMsg().getHeadItemRoot("STC"));
     }
   }
 }