/*    */ package com.hisun.framework.filter;
/*    */ 
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.framework.HiDefaultServer;
/*    */ import com.hisun.hilog4j.Logger;
/*    */ import com.hisun.message.HiMessage;
/*    */ import com.hisun.message.HiMessageContext;
/*    */ import com.hisun.pubinterface.IHandler;
/*    */ import com.hisun.pubinterface.IHandlerFilter;
/*    */ import com.hisun.register.HiRegisterService;
/*    */ import com.hisun.register.HiServiceObject;
/*    */ import com.hisun.util.HiStringManager;
/*    */ import org.apache.log4j.Level;
/*    */ 
/*    */ public class ServerStateCheckFilter
/*    */   implements IHandlerFilter
/*    */ {
/* 18 */   public static final HiStringManager sm = HiStringManager.getManager();
/*    */   private final HiDefaultServer _server;
/*    */ 
/*    */   public ServerStateCheckFilter(HiDefaultServer server)
/*    */   {
/* 23 */     this._server = server;
/*    */   }
/*    */ 
/*    */   public void process(HiMessageContext ctx, IHandler handler) throws HiException
/*    */   {
/* 28 */     HiMessage msg = ctx.getCurrentMsg();
/* 29 */     HiServiceObject serviceObject = HiRegisterService.getService(this._server.getName());
/*    */ 
/* 31 */     if (!(serviceObject.isRunning()))
/*    */     {
/* 33 */       String errmsg = sm.getString("HiDefaultServer.process01", msg.getRequestId(), this._server.getName(), this._server.getType());
/*    */ 
/* 35 */       this._server.getLog().error(errmsg);
/* 36 */       ctx.popParent();
/* 37 */       throw new HiException("211003", errmsg);
/*    */     }
/*    */ 
/* 40 */     if (serviceObject.getLogLevel() != null) {
/* 41 */       this._server.getLog().setLevel(Level.toLevel(serviceObject.getLogLevel()));
/*    */     }
/*    */ 
/* 44 */     handler.process(ctx);
/*    */   }
/*    */ }