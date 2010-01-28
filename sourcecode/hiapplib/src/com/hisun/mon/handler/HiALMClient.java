 package com.hisun.mon.handler;
 
 import com.hisun.protocol.tcp.HiAbstractALMClient;
 import com.hisun.protocol.tcp.HiMessageInOut;
 
 public class HiALMClient extends HiAbstractALMClient
 {
   private HiALMCliCheckConn checkConn;
 
   public HiALMClient()
   {
     setMsginout(new HiMessageInOut());
     this.checkConn = new HiALMCliCheckConn();
     setCheckConn(this.checkConn);
   }
 
   public void setReqCheckData(String reqChkData) {
     this.checkConn.setReqCheckData(reqChkData);
   }
 
   public void setRspCheckData(String rsqChkData) {
     this.checkConn.setRspCheckData(rsqChkData);
   }
 }