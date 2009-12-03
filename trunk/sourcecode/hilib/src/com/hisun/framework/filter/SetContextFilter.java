/*    */ package com.hisun.framework.filter;
/*    */ 
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.message.HiContext;
/*    */ import com.hisun.message.HiMessageContext;
/*    */ import com.hisun.pubinterface.IHandler;
/*    */ import com.hisun.pubinterface.IHandlerFilter;
/*    */ 
/*    */ public class SetContextFilter
/*    */   implements IHandlerFilter
/*    */ {
/*    */   private final HiContext serverContext;
/*    */ 
/*    */   public SetContextFilter(HiContext context)
/*    */   {
/* 13 */     this.serverContext = context;
/*    */   }
/*    */ 
/*    */   public void process(HiMessageContext ctx, IHandler handler) throws HiException
/*    */   {
/* 18 */     ctx.pushParent(this.serverContext);
/* 19 */     HiMessageContext.setCurrentMessageContext(ctx);
/*    */     try
/*    */     {
/* 22 */       handler.process(ctx);
/*    */     }
/*    */     finally {
/* 25 */       ctx.popParent();
/* 26 */       HiMessageContext.setCurrentMessageContext(null);
/*    */     }
/*    */   }
/*    */ }