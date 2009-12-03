/*    */ package com.hisun.atmp.handler;
/*    */ 
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.message.HiMessage;
/*    */ import com.hisun.message.HiMessageContext;
/*    */ import com.hisun.pubinterface.IHandler;
/*    */ import java.util.ArrayList;
/*    */ 
/*    */ public class HiDiaboMessageMatch
/*    */   implements IHandler
/*    */ {
/*    */   private ArrayList _codes;
/*    */   private String _respProcess;
/*    */   private String _reqProcess;
/*    */ 
/*    */   public HiDiaboMessageMatch()
/*    */   {
/* 17 */     this._codes = new ArrayList();
/* 18 */     this._respProcess = "respProc";
/* 19 */     this._reqProcess = "reqProc"; }
/*    */ 
/*    */   public void process(HiMessageContext arg0) throws HiException { HiMessage msg = arg0.getCurrentMsg();
/* 22 */     String code = msg.getHeadItem("STC");
/* 23 */     if (this._codes.contains(code)) {
/* 24 */       msg.setHeadItem("SCH", "rp");
/* 25 */       arg0.setProperty("_SUBPROCESS", this._respProcess);
/*    */     } else {
/* 27 */       arg0.setProperty("_SUBPROCESS", this._reqProcess);
/*    */     }
/*    */   }
/*    */ 
/*    */   public void setCode(String code) {
/* 32 */     this._codes.add(code);
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