/*    */ package com.hisun.component.web;
/*    */ 
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.hilib.HiATLParam;
/*    */ import com.hisun.message.HiMessage;
/*    */ import com.hisun.message.HiMessageContext;
/*    */ 
/*    */ public class SetRedirectPage
/*    */ {
/*    */   public int execute(HiATLParam args, HiMessageContext ctx)
/*    */     throws HiException
/*    */   {
/* 20 */     HiMessage msg = ctx.getCurrentMsg();
/* 21 */     msg.setHeadItem("SEND_REDIRECT", args.get("PAGE"));
/* 22 */     return 0;
/*    */   }
/*    */ }