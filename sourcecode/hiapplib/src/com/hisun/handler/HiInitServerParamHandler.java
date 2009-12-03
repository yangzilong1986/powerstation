/*    */ package com.hisun.handler;
/*    */ 
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.framework.event.IServerInitListener;
/*    */ import com.hisun.framework.event.ServerEvent;
/*    */ import com.hisun.message.HiContext;
/*    */ import com.hisun.util.HiSysParamParser;
/*    */ 
/*    */ public class HiInitServerParamHandler
/*    */   implements IServerInitListener
/*    */ {
/*    */   public void serverInit(ServerEvent arg0)
/*    */     throws HiException
/*    */   {
/* 22 */     HiContext ctx = arg0.getServerContext();
/* 23 */     HiSysParamParser.load(ctx, ctx.getStrProp("SVR.name"));
/*    */   }
/*    */ }