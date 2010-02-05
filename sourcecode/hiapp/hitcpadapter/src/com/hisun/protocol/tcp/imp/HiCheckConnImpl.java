 package com.hisun.protocol.tcp.imp;
 
 import com.hisun.protocol.tcp.ICheckConn;
 import com.hisun.util.HiByteBuffer;
 
 public class HiCheckConnImpl
   implements ICheckConn
 {
   private String _data;
 
   public void setCheckData(String data)
   {
     this._data = data;
   }
 
   public boolean isCheckData(HiByteBuffer buf) {
     return this._data.equals(buf.toString()); }
 
   public boolean isCheckData(byte[] buf) {
     return this._data.equals(new String(buf, 0, buf.length)); }
 
   public String getCheckData() {
     return this._data;
   }
 
   public HiByteBuffer getRspCheckData(HiByteBuffer buf) {
     return new HiByteBuffer(this._data.getBytes());
   }
 }