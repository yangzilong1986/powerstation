 package com.hisun.handler;
 
 import com.hisun.exception.HiException;
 import com.hisun.message.HiETF;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import com.hisun.pubinterface.IHandler;
 import com.hisun.util.HiByteBuffer;
 import org.apache.commons.lang.math.NumberUtils;
 
 public class HiLSNSVRHandler
   implements IHandler
 {
   public void doRequest(HiMessageContext ctx)
     throws HiException
   {
     HiMessage msg1 = ctx.getCurrentMsg();
     HiByteBuffer byteBuffer = (HiByteBuffer)msg1.getBody();
     HiMessage msg2 = new HiMessage(byteBuffer.toString());
     String stm = msg2.getHeadItem("STM");
     if (stm != null) {
       msg2.setHeadItem("STM", new Long(NumberUtils.toLong(stm)));
     }
     String etm = msg2.getHeadItem("ETM");
     if (etm != null) {
       msg2.setHeadItem("ETM", new Long(NumberUtils.toLong(etm)));
     }
 
     ctx.setCurrentMsg(msg2);
   }
 
   public void doResponse(HiMessageContext ctx)
     throws HiException
   {
     HiMessage msg1 = ctx.getCurrentMsg();
     HiByteBuffer byteBuffer = new HiByteBuffer(200);
     byteBuffer.append(msg1.toString());
     msg1.setBody(byteBuffer);
     ctx.setCurrentMsg(msg1);
   }
 
   public void process(HiMessageContext arg0) throws HiException
   {
   }
 
   public void specProc(HiMessageContext ctx) throws HiException {
     HiMessage msg1 = ctx.getCurrentMsg();
     HiETF root = msg1.getETFBody();
     root.removeChildNode("1");
   }
 }