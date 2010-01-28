 package com.hisun.mng.handler;
 
 import com.hisun.message.HiETF;
 import com.hisun.message.HiMessage;
 
 public class ETF2ByteHandler
 {
   public HiMessage process(HiMessage msg)
   {
     HiETF root = msg.getETFBody();
     byte[] data = root.toString().getBytes();
 
     msg.setBody(data);
     return msg;
   }
 }