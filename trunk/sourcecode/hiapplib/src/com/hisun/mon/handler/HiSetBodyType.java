 package com.hisun.mon.handler;
 
 import com.hisun.exception.HiException;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import com.hisun.pubinterface.IHandler;
 import com.hisun.util.HiByteBuffer;
 
 public class HiSetBodyType
   implements IHandler
 {
   private String type = "1";
 
   public void setType(String type) {
     this.type = type;
   }
 
   public void process(HiMessageContext ctx) throws HiException {
     HiMessage msg = ctx.getCurrentMsg();
 
     HiByteBuffer oldBody = (HiByteBuffer)msg.getBody();
     HiByteBuffer newBody = new HiByteBuffer(oldBody.length() + 1);
     newBody.append(this.type);
     newBody.append(oldBody.getBytes());
 
     msg.setBody(newBody);
   }
 }