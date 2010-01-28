 package com.hisun.mon.handler;
 
 import com.hisun.exception.HiException;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiContext;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import com.hisun.pubinterface.IHandler;
 import com.hisun.util.HiByteBuffer;
 
 public class HiMessageMatch
   implements IHandler
 {
   private String reqProcess = "reqProc";
   private String rspProcess = "rspProc";
 
   final Logger log = (Logger)HiContext.getCurrentContext()
     .getProperty("SVR.log");
 
   public void process(HiMessageContext ctx) throws HiException
   {
     HiMessage msg = ctx.getCurrentMsg();
 
     Object body = msg.getBody();
     if (body instanceof HiByteBuffer) {
       HiByteBuffer bb = (HiByteBuffer)body;
       byte type = bb.charAt(0);
       if (this.log.isDebugEnabled()) {
         this.log.debug("Msg Type:" + type);
       }
       if (type % 2 == 0) {
         msg.setHeadItem("SCH", "rq");
         ctx.setProperty("_SUBPROCESS", this.reqProcess);
       } else {
         msg.setHeadItem("SCH", "rp");
         ctx.setProperty("_SUBPROCESS", this.rspProcess);
       }
 
       msg.setBody(new HiByteBuffer(bb.subbyte(1, bb.length() - 1)));
     }
   }
 
   public void setReqProcess(String val)
   {
     this.reqProcess = val;
   }
 
   public void setRspProcess(String val) {
     this.rspProcess = val;
   }
 }