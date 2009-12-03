/*    */ package com.hisun.framework.event;
/*    */ 
/*    */ import com.hisun.framework.IServer;
/*    */ import com.hisun.hilog4j.Logger;
/*    */ import com.hisun.message.HiContext;
/*    */ 
/*    */ public class ServerEvent
/*    */ {
/*    */   private static final long serialVersionUID = -2768663865535425218L;
/*    */   private IServer server;
/*    */   private Logger log;
/*    */   private HiContext serverContext;
/*    */ 
/*    */   public ServerEvent(IServer server, Logger log, HiContext ctx)
/*    */   {
/* 24 */     this.server = server;
/* 25 */     this.log = log;
/* 26 */     this.serverContext = ctx;
/*    */   }
/*    */ 
/*    */   public IServer getServer() {
/* 30 */     return this.server;
/*    */   }
/*    */ 
/*    */   public Logger getLog() {
/* 34 */     return this.log;
/*    */   }
/*    */ 
/*    */   public HiContext getServerContext() {
/* 38 */     return this.serverContext;
/*    */   }
/*    */ }