/*    */ package com.hisun.mon.handler;
/*    */ 
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.hilog4j.Logger;
/*    */ import com.hisun.message.HiContext;
/*    */ import com.hisun.message.HiMessage;
/*    */ import com.hisun.message.HiMessageContext;
/*    */ import com.hisun.pubinterface.IHandler;
/*    */ 
/*    */ public class HiSetOutETFBody
/*    */   implements IHandler
/*    */ {
/* 14 */   final Logger log = (Logger)HiContext.getCurrentContext()
/* 14 */     .getProperty("SVR.log");
/*    */ 
/* 16 */   private String name = "ETFOUTLIST";
/*    */ 
/*    */   public void setName(String name) {
/* 19 */     this.name = name;
/*    */   }
/*    */ 
/*    */   public void process(HiMessageContext ctx) throws HiException {
/* 23 */     HiMessage msg = ctx.getCurrentMsg();
/*    */ 
/* 25 */     Object body = msg.getObjectHeadItem(this.name);
/* 26 */     if (body == null) {
/* 27 */       return;
/*    */     }
/*    */ 
/* 30 */     msg.setBody(body);
/*    */   }
/*    */ }