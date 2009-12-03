/*    */ package com.hisun.mon.handler;
/*    */ 
/*    */ import com.hisun.protocol.tcp.HiAbstractALMServer;
/*    */ 
/*    */ public class HiMonECAPServer extends HiAbstractALMServer
/*    */ {
/*    */   private HiALMCliCheckConn checkConn;
/*    */ 
/*    */   public HiMonECAPServer()
/*    */   {
/* 10 */     setMsginout(new HiMonECAPMessageInOut());
/* 11 */     this.checkConn = new HiALMCliCheckConn();
/* 12 */     setCheckConn(this.checkConn);
/*    */   }
/*    */ 
/*    */   public void setReqCheckData(String reqChkData) {
/* 16 */     this.checkConn.setReqCheckData(reqChkData);
/*    */   }
/*    */ 
/*    */   public void setRspCheckData(String rsqChkData) {
/* 20 */     this.checkConn.setRspCheckData(rsqChkData);
/*    */   }
/*    */ }