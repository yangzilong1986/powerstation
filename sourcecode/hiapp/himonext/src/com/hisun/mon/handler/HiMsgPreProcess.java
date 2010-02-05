 package com.hisun.mon.handler;
 
 import com.hisun.exception.HiException;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiContext;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import com.hisun.pubinterface.IHandler;
 import com.hisun.util.HiByteBuffer;
 
 public class HiMsgPreProcess
   implements IHandler
 {
   private String reqType = "0";
 
   final Logger log = (Logger)HiContext.getCurrentContext()
     .getProperty("SVR.log");
 
   public void setReqType(String val)
   {
     this.reqType = val;
   }
 
   public void process(HiMessageContext ctx)
     throws HiException
   {
     HiMessage msg = ctx.getCurrentMsg();
 
     Object body = msg.getBody();
     if (body instanceof HiByteBuffer) {
       HiByteBuffer bb = (HiByteBuffer)body;
 
       byte type = bb.charAt(0);
       if (type != 48) {
         this.log.error("Request Message Type is Error,should be" + this.reqType);
         throw new HiException("215027", "Message Package Type is Error,should be:" + this.reqType);
       }
       msg.setBody(new HiByteBuffer(bb.subbyte(1, bb.length() - 1)));
     }
   }
 }