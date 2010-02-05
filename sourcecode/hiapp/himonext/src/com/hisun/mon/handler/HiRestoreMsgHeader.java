 package com.hisun.mon.handler;
 
 import com.hisun.engine.HiMessagePool;
 import com.hisun.exception.HiException;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiContext;
 import com.hisun.message.HiETF;
 import com.hisun.message.HiETFFactory;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import com.hisun.pubinterface.IHandler;
 import com.hisun.util.HiByteBuffer;
 
 public class HiRestoreMsgHeader
   implements IHandler
 {
   final Logger log = (Logger)HiContext.getCurrentContext()
     .getProperty("SVR.log");
 
   private String keyName = "Rsv_No";
 
   public void setKeyName(String val) { this.keyName = val;
   }
 
   public void process(HiMessageContext ctx)
     throws HiException
   {
     HiETF etf;
     HiMessage msg = ctx.getCurrentMsg();
 
     Object body = msg.getBody();
 
     if (body instanceof HiByteBuffer) {
       HiByteBuffer bb = (HiByteBuffer)body;
 
       etf = HiETFFactory.createETF(bb.toString());
     }
     else {
       etf = (HiETF)body;
     }
 
     String keyVal = etf.getGrandChildValue(this.keyName);
 
     if (this.log.isDebugEnabled())
     {
       this.log.debug("Get Message match ID:" + keyVal);
     }
 
     HiMessagePool mp = HiMessagePool.getMessagePool(ctx);
     HiMessage oldMsg = (HiMessage)mp.getHeader(keyVal);
     if (oldMsg == null)
     {
       throw new HiException("The Message is no exist,may be timeout,msg:" + oldMsg.toString());
     }
 
     msg.setHead(oldMsg.getHead());
     msg.setHeadItem("SCH", "rp");
 
     msg.setHeadItem("PlainText", etf);
     msg.setBody(HiETFFactory.createETF());
   }
 }