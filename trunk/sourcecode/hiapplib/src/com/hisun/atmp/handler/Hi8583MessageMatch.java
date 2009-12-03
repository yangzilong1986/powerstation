/*    */ package com.hisun.atmp.handler;
/*    */ 
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.message.HiETF;
/*    */ import com.hisun.message.HiMessage;
/*    */ import com.hisun.message.HiMessageContext;
/*    */ import com.hisun.pubinterface.IHandler;
/*    */ 
/*    */ public class Hi8583MessageMatch
/*    */   implements IHandler
/*    */ {
/*    */   private String _respProcess;
/*    */   private String _reqProcess;
/*    */ 
/*    */   public Hi8583MessageMatch()
/*    */   {
/* 18 */     this._respProcess = "respProc";
/* 19 */     this._reqProcess = "reqProc"; }
/*    */ 
/*    */   public void process(HiMessageContext arg0) throws HiException {
/* 22 */     HiMessage msg = arg0.getCurrentMsg();
/* 23 */     HiETF root = msg.getETFBody();
/* 24 */     String msgID = root.getChildValue("MsgID");
/*    */ 
/* 26 */     if (msgID.charAt(2) % '\2' == 1) {
/* 27 */       msg.setHeadItem("SCH", "rp");
/* 28 */       arg0.setProperty("_SUBPROCESS", this._respProcess);
/*    */     } else {
/* 30 */       msg.setHeadItem("SCH", "rq");
/* 31 */       arg0.setProperty("_SUBPROCESS", this._reqProcess);
/*    */     }
/*    */   }
/*    */ 
/*    */   public String getRespProcess() {
/* 36 */     return this._respProcess;
/*    */   }
/*    */ 
/*    */   public void setRespProcess(String respProcess) {
/* 40 */     this._respProcess = respProcess;
/*    */   }
/*    */ 
/*    */   public String getReqProcess() {
/* 44 */     return this._reqProcess;
/*    */   }
/*    */ 
/*    */   public void setReqProcess(String reqProcess) {
/* 48 */     this._reqProcess = reqProcess;
/*    */   }
/*    */ }