/*    */ package com.hisun.mon.handler;
/*    */ 
/*    */ import com.hisun.protocol.tcp.ICheckConn;
/*    */ import com.hisun.util.HiByteBuffer;
/*    */ 
/*    */ public class HiALMCliCheckConn
/*    */   implements ICheckConn
/*    */ {
/*  8 */   private String reqCheckData = "2";
/*  9 */   private String rspCheckData = "3";
/*    */ 
/*    */   public String getCheckData()
/*    */   {
/* 13 */     return this.reqCheckData;
/*    */   }
/*    */ 
/*    */   public boolean isCheckData(HiByteBuffer bb) {
/* 17 */     String type = bb.substr(0, 1);
/*    */ 
/* 19 */     return ((!(this.reqCheckData.equals(type))) && (!(this.rspCheckData.equals(type))));
/*    */   }
/*    */ 
/*    */   public boolean isCheckData(byte[] bb)
/*    */   {
/* 27 */     return (!(this.rspCheckData.equals(new String(bb))));
/*    */   }
/*    */ 
/*    */   public void setReqCheckData(String reqCheckData)
/*    */   {
/* 33 */     this.reqCheckData = reqCheckData;
/*    */   }
/*    */ 
/*    */   public void setRspCheckData(String rspCheckData) {
/* 37 */     this.rspCheckData = rspCheckData;
/*    */   }
/*    */ 
/*    */   public HiByteBuffer getRspCheckData(HiByteBuffer buf) {
/* 41 */     HiByteBuffer bb = new HiByteBuffer(buf.length() + 1);
/* 42 */     bb.append(this.rspCheckData);
/* 43 */     bb.append(buf.subbyte(1, buf.length() - 1));
/* 44 */     return bb;
/*    */   }
/*    */ }