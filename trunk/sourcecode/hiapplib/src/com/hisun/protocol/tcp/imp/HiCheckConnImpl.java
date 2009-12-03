/*    */ package com.hisun.protocol.tcp.imp;
/*    */ 
/*    */ import com.hisun.protocol.tcp.ICheckConn;
/*    */ import com.hisun.util.HiByteBuffer;
/*    */ 
/*    */ public class HiCheckConnImpl
/*    */   implements ICheckConn
/*    */ {
/*    */   private String _data;
/*    */ 
/*    */   public void setCheckData(String data)
/*    */   {
/* 10 */     this._data = data;
/*    */   }
/*    */ 
/*    */   public boolean isCheckData(HiByteBuffer buf) {
/* 14 */     return this._data.equals(buf.toString()); }
/*    */ 
/*    */   public boolean isCheckData(byte[] buf) {
/* 17 */     return this._data.equals(new String(buf, 0, buf.length)); }
/*    */ 
/*    */   public String getCheckData() {
/* 20 */     return this._data;
/*    */   }
/*    */ 
/*    */   public HiByteBuffer getRspCheckData(HiByteBuffer buf) {
/* 24 */     return new HiByteBuffer(this._data.getBytes());
/*    */   }
/*    */ }