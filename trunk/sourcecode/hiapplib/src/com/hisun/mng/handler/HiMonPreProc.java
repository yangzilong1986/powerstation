/*    */ package com.hisun.mng.handler;
/*    */ 
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.framework.event.IServerDestroyListener;
/*    */ import com.hisun.framework.event.ServerEvent;
/*    */ import com.hisun.message.HiETF;
/*    */ import com.hisun.message.HiMessage;
/*    */ import com.hisun.message.HiMessageContext;
/*    */ import com.hisun.mng.HiMonitor;
/*    */ import com.hisun.pubinterface.IHandler;
/*    */ 
/*    */ public class HiMonPreProc
/*    */   implements IHandler, IServerDestroyListener
/*    */ {
/*    */   private String _code;
/*    */ 
/*    */   public HiMonPreProc()
/*    */   {
/* 15 */     this._code = null; }
/*    */ 
/*    */   public void setCode(String code) {
/* 18 */     this._code = code;
/*    */   }
/*    */ 
/*    */   public void process(HiMessageContext arg0) throws HiException {
/* 22 */     HiMessage msg = arg0.getCurrentMsg();
/* 23 */     HiETF root = msg.getETFBody();
/* 24 */     String txnCod = root.getChildValue("TXN_CD");
/* 25 */     root.setChildValue("TxnCod", txnCod);
/* 26 */     msg.setHeadItem("STC", this._code);
/*    */   }
/*    */ 
/*    */   public void serverDestroy(ServerEvent arg0) throws HiException {
/* 30 */     HiMonitor.closeAllMonitor(HiMonitor.getMonMap());
/*    */   }
/*    */ }