/*    */ package com.hisun.component.web;
/*    */ 
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.hilib.HiATLParam;
/*    */ import com.hisun.message.HiMessage;
/*    */ import com.hisun.message.HiMessageContext;
/*    */ 
/*    */ public class SetRetPage
/*    */ {
/*    */   public int execute(HiATLParam args, HiMessageContext ctx)
/*    */     throws HiException
/*    */   {
/* 20 */     HiMessage msg = ctx.getCurrentMsg();
/*    */ 
/* 22 */     msg.setHeadItem("SEND_OUTPUT", args.get("PAGE"));
/* 23 */     return 0;
/*    */   }
/*    */ }