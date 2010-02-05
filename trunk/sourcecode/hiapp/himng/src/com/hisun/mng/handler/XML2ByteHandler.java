 package com.hisun.mng.handler;
 
 import com.hisun.message.HiMessage;
 import org.dom4j.Element;
 
 public class XML2ByteHandler
 {
   public HiMessage process(HiMessage msg)
   {
     Element root = (Element)msg.getObjectHeadItem("MNGXML");
     byte[] data = root.asXML().getBytes();
 
     msg.setBody(data);
     return msg;
   }
 }