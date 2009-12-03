/*    */ package com.hisun.mon.handler;
/*    */ 
/*    */ import com.hisun.protocol.tcp.HiAbstractALMClient;
/*    */ import com.hisun.protocol.tcp.HiMessageInOut;
/*    */ 
/*    */ public class HiALMClient extends HiAbstractALMClient
/*    */ {
/*    */   private HiALMCliCheckConn checkConn;
/*    */ 
/*    */   public HiALMClient()
/*    */   {
/* 11 */     setMsginout(new HiMessageInOut());
/* 12 */     this.checkConn = new HiALMCliCheckConn();
/* 13 */     setCheckConn(this.checkConn);
/*    */   }
/*    */ 
/*    */   public void setReqCheckData(String reqChkData) {
/* 17 */     this.checkConn.setReqCheckData(reqChkData);
/*    */   }
/*    */ 
/*    */   public void setRspCheckData(String rsqChkData) {
/* 21 */     this.checkConn.setRspCheckData(rsqChkData);
/*    */   }
/*    */ }