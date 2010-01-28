 package com.hisun.engine.parse;
 
 import com.hisun.exception.HiException;
 import com.hisun.message.HiMessage;
 import org.apache.commons.lang.StringUtils;
 
 public class MngByte2XMLHandler
 {
   public static final String MNGTYPE = "MNG_TYPE";
 
   public HiMessage process(HiMessage msg)
     throws HiException
   {
     byte[] data = (byte[])(byte[])msg.getBody();
     String sdata = new String(data);
     String mngtype = StringUtils.substringBefore(sdata, "<");
 
     msg.setHeadItem("MNG_TYPE", mngtype);
 
     msg.setHeadItem("ECT", "text/xml");
 
     sdata = StringUtils.substringAfter(sdata, mngtype);
 
     msg.setBody(sdata.getBytes());
 
     return msg;
   }
 }