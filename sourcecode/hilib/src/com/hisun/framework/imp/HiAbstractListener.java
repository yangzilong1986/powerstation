/*    */ package com.hisun.framework.imp;
/*    */ 
/*    */ import com.hisun.framework.HiDefaultServer;
/*    */ import com.hisun.framework.event.IServerDestroyListener;
/*    */ import com.hisun.framework.event.IServerInitListener;
/*    */ import com.hisun.framework.event.IServerPauseListener;
/*    */ import com.hisun.framework.event.IServerResumeListener;
/*    */ import com.hisun.framework.event.IServerStartListener;
/*    */ import com.hisun.framework.event.IServerStopListener;
/*    */ import com.hisun.hilog4j.Logger;
/*    */ import com.hisun.message.HiMessage;
/*    */ import com.hisun.message.HiMessageContext;
/*    */ import com.hisun.util.HiStringManager;
/*    */ 
/*    */ public abstract class HiAbstractListener
/*    */   implements IServerInitListener, IServerStartListener, IServerStopListener, IServerDestroyListener, IServerPauseListener, IServerResumeListener
/*    */ {
/* 19 */   public static final HiStringManager sm = HiStringManager.getManager();
/*    */   private String name;
/*    */   private String msgType;
/*    */   private HiDefaultServer server;
/*    */   public Logger log;
/*    */ 
/*    */   public HiDefaultServer getServer()
/*    */   {
/* 30 */     return this.server;
/*    */   }
/*    */ 
/*    */   public void setServer(HiDefaultServer service) {
/* 34 */     this.server = service;
/* 35 */     this.log = this.server.getLog();
/*    */   }
/*    */ 
/*    */   public String getName() {
/* 39 */     return this.name;
/*    */   }
/*    */ 
/*    */   public String getMsgType() {
/* 43 */     return this.msgType;
/*    */   }
/*    */ 
/*    */   public void setName(String name) {
/* 47 */     this.name = name;
/*    */   }
/*    */ 
/*    */   public void setMsgType(String process) {
/* 51 */     this.msgType = process;
/*    */   }
/*    */ 
/*    */   public HiMessage getHiMessage() {
/* 55 */     HiMessage msg = new HiMessage(getServer().getName(), getMsgType());
/*    */ 
/* 58 */     msg.setHeadItem("ECT", "text/plain");
/* 59 */     msg.setHeadItem("SCH", "rq");
/* 60 */     return msg;
/*    */   }
/*    */ 
/*    */   public HiMessageContext getMessageContext(HiMessage msg) {
/* 64 */     HiMessageContext ctx = new HiMessageContext();
/* 65 */     ctx.setCurrentMsg(msg);
/* 66 */     return ctx;
/*    */   }
/*    */ }