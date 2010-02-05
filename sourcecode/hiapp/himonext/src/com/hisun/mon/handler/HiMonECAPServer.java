 package com.hisun.mon.handler;
 
 import com.hisun.protocol.tcp.HiAbstractALMServer;
 
 public class HiMonECAPServer extends HiAbstractALMServer
 {
   private HiALMCliCheckConn checkConn;
 
   public HiMonECAPServer()
   {
     setMsginout(new HiMonECAPMessageInOut());
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