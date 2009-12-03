/*    */ package com.hisun.engine.invoke.impl;
/*    */ 
/*    */ import com.hisun.message.HiContext;
/*    */ import com.hisun.message.HiETF;
/*    */ import java.util.ArrayList;
/*    */ 
/*    */ public class HiValidateMsg
/*    */ {
/*    */   private ArrayList _errMsg;
/*    */   private static final String VALIDATE_MSG_KEY = "_VALIDATE_MSG_KEY";
/*    */ 
/*    */   public HiValidateMsg()
/*    */   {
/* 14 */     this._errMsg = new ArrayList();
/*    */   }
/*    */ 
/*    */   public static HiValidateMsg get(HiContext ctx)
/*    */   {
/* 23 */     if (ctx.containsProperty("_VALIDATE_MSG_KEY")) {
/* 24 */       return ((HiValidateMsg)ctx.getProperty("_VALIDATE_MSG_KEY"));
/*    */     }
/* 26 */     HiValidateMsg msg = new HiValidateMsg();
/* 27 */     ctx.setProperty("_VALIDATE_MSG_KEY", msg);
/* 28 */     return msg;
/*    */   }
/*    */ 
/*    */   public boolean isEmpty()
/*    */   {
/* 33 */     return (this._errMsg.size() == 0);
/*    */   }
/*    */ 
/*    */   public void add(String code, String msg) {
/* 37 */     this._errMsg.add(new HiValidateMsgItem(code, msg));
/*    */   }
/*    */ 
/*    */   public void dump(HiETF root) {
/* 41 */     int i = 0;
/*    */ 
/* 43 */     for (i = 0; i < this._errMsg.size(); ++i) {
/* 44 */       HiValidateMsgItem item = (HiValidateMsgItem)this._errMsg.get(i);
/* 45 */       HiETF errRoot = root.addNode("ERR_MSG_" + (i + 1));
/* 46 */       errRoot.setChildValue("CODE", item.code);
/* 47 */       errRoot.setChildValue("MSG", item.msg);
/*    */     }
/*    */ 
/* 50 */     if (i != 0)
/* 51 */       root.setChildValue("ERR_MSG_NUM", String.valueOf(i));
/*    */   }
/*    */ }