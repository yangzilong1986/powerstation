 package com.hisun.mon.handler;
 
 import com.hisun.protocol.tcp.ICheckConn;
 import com.hisun.util.HiByteBuffer;
 
 public class HiALMCliCheckConn
   implements ICheckConn
 {
   private String reqCheckData = "2";
   private String rspCheckData = "3";
 
   public String getCheckData()
   {
     return this.reqCheckData;
   }
 
   public boolean isCheckData(HiByteBuffer bb) {
     String type = bb.substr(0, 1);
 
     return ((!(this.reqCheckData.equals(type))) && (!(this.rspCheckData.equals(type))));
   }
 
   public boolean isCheckData(byte[] bb)
   {
     return (!(this.rspCheckData.equals(new String(bb))));
   }
 
   public void setReqCheckData(String reqCheckData)
   {
     this.reqCheckData = reqCheckData;
   }
 
   public void setRspCheckData(String rspCheckData) {
     this.rspCheckData = rspCheckData;
   }
 
   public HiByteBuffer getRspCheckData(HiByteBuffer buf) {
     HiByteBuffer bb = new HiByteBuffer(buf.length() + 1);
     bb.append(this.rspCheckData);
     bb.append(buf.subbyte(1, buf.length() - 1));
     return bb;
   }
 }