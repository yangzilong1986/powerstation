/*    */ package com.hisun.mon.handler;
/*    */ 
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.hilog4j.Logger;
/*    */ import com.hisun.message.HiContext;
/*    */ import com.hisun.message.HiETF;
/*    */ import com.hisun.message.HiMessage;
/*    */ import com.hisun.message.HiMessageContext;
/*    */ import com.hisun.pubinterface.IHandler;
/*    */ 
/*    */ public class HiSetMsgHeaderItem
/*    */   implements IHandler
/*    */ {
/* 15 */   final Logger log = (Logger)HiContext.getCurrentContext()
/* 15 */     .getProperty("SVR.log");
/*    */ 
/* 17 */   private String stc = "STC";
/* 18 */   private String node = "RTxnCd";
/*    */ 
/*    */   public void setStc(String code) {
/* 21 */     this.stc = code;
/*    */   }
/*    */ 
/*    */   public void setNode(String node) {
/* 25 */     this.node = node;
/*    */   }
/*    */ 
/*    */   public void process(HiMessageContext ctx)
/*    */     throws HiException
/*    */   {
/* 31 */     HiMessage msg = ctx.getCurrentMsg();
/*    */ 
/* 33 */     HiETF etf = msg.getETFBody();
/* 34 */     msg.setHeadItem(this.stc, etf.getGrandChildValue(this.node));
/*    */   }
/*    */ }