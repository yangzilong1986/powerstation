 package com.hisun.engine.parse;
 
 import com.hisun.message.HiETF;
 import com.hisun.message.HiMessage;
 
 public class XML2ByteHandler
 {
   public HiMessage process(HiMessage msg)
   {
     HiETF root = (HiETF)msg.getBody();
     byte[] data = root.getXmlString().getBytes();
 
     msg.setBody(data);
     return msg;
   }
 }