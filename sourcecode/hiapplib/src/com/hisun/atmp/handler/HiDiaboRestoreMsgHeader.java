 package com.hisun.atmp.handler;
 
 import com.hisun.engine.HiMessagePool;
 import com.hisun.exception.HiException;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import com.hisun.pubinterface.IHandler;
 
 public class HiDiaboRestoreMsgHeader
   implements IHandler
 {
   public void process(HiMessageContext messContext)
     throws HiException
   {
     HiMessage mess = messContext.getCurrentMsg();
 
     String sip = mess.getHeadItem("SIP");
     String stc = mess.getHeadItem("STC");
 
     HiMessagePool mp = HiMessagePool.getMessagePool(messContext);
     HiMessage msg1 = (HiMessage)mp.getHeader(sip + stc);
 
     if (msg1 == null) {
       throw new HiException("310009");
     }
     mess.setRequestId(msg1.getRequestId());
     mess.setHeadItem("STC", msg1.getHeadItem("STC"));
     mess.setHeadItem("STF", msg1.getHeadItem("STF"));
   }
 }