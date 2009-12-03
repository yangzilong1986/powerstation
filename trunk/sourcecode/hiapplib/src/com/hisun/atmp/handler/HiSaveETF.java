/*    */ package com.hisun.atmp.handler;
/*    */ 
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.message.HiETF;
/*    */ import com.hisun.message.HiMessage;
/*    */ import com.hisun.message.HiMessageContext;
/*    */ import com.hisun.pubinterface.IHandler;
/*    */ 
/*    */ public class HiSaveETF
/*    */   implements IHandler
/*    */ {
/*    */   private String key;
/*    */ 
/*    */   public HiSaveETF()
/*    */   {
/* 14 */     this.key = "POS_EXTRA"; }
/*    */ 
/*    */   public void process(HiMessageContext arg0) throws HiException {
/* 17 */     HiMessage msg = arg0.getCurrentMsg();
/* 18 */     HiETF root = msg.getETFBody();
/*    */ 
/* 20 */     arg0.setProperty(this.key, root);
/*    */   }
/*    */ 
/*    */   public String getKey() {
/* 24 */     return this.key;
/*    */   }
/*    */ 
/*    */   public void setKey(String key) {
/* 28 */     this.key = key;
/*    */   }
/*    */ }