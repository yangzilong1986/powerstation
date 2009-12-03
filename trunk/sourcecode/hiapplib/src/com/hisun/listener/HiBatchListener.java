/*    */ package com.hisun.listener;
/*    */ 
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.framework.HiDefaultServer;
/*    */ import com.hisun.framework.event.ServerEvent;
/*    */ import com.hisun.framework.imp.HiAbstractListener;
/*    */ import com.hisun.hilog4j.HiLog;
/*    */ import com.hisun.hilog4j.Logger;
/*    */ import com.hisun.message.HiMessage;
/*    */ import com.hisun.message.HiMessageContext;
/*    */ 
/*    */ public class HiBatchListener extends HiAbstractListener
/*    */ {
/*    */   public void serverInit(ServerEvent event)
/*    */     throws HiException
/*    */   {
/*    */   }
/*    */ 
/*    */   public void serverStart(ServerEvent event)
/*    */     throws HiException
/*    */   {
/* 59 */     HiProcess pro = new HiProcess(this);
/* 60 */     pro.start();
/*    */   }
/*    */ 
/*    */   public void serverStop(ServerEvent event)
/*    */     throws HiException
/*    */   {
/*    */   }
/*    */ 
/*    */   public void serverDestroy(ServerEvent event)
/*    */     throws HiException
/*    */   {
/*    */   }
/*    */ 
/*    */   public void serverPause(ServerEvent event)
/*    */   {
/*    */   }
/*    */ 
/*    */   public void serverResume(ServerEvent event)
/*    */   {
/*    */   }
/*    */ 
/*    */   class HiProcess extends Thread
/*    */   {
/*    */     HiBatchListener list;
/*    */ 
/*    */     HiProcess(HiBatchListener paramHiBatchListener)
/*    */     {
/* 23 */       this.list = paramHiBatchListener;
/*    */     }
/*    */ 
/*    */     public void run()
/*    */     {
/* 28 */       HiMessage msg = new HiMessage(HiBatchListener.this.getServer().getName(), HiBatchListener.this.getMsgType());
/*    */ 
/* 30 */       msg.setHeadItem("STF", HiBatchListener.this.getServer().getTrace());
/* 31 */       HiMessageContext ctx = new HiMessageContext();
/* 32 */       ctx.setProperty("app_server", HiBatchListener.this.getServer().getName());
/* 33 */       ctx.setCurrentMsg(msg);
/* 34 */       HiMessageContext.setCurrentMessageContext(ctx);
/* 35 */       Logger log = HiLog.getLogger(msg);
/* 36 */       if (log.isInfoEnabled())
/*    */       {
/* 38 */         log.info("Begin batchOnlineServer");
/*    */       }
/*    */       try
/*    */       {
/* 42 */         sleep(2000L);
/* 43 */         this.list.getServer().process(ctx);
/*    */       }
/*    */       catch (Exception e)
/*    */       {
/*    */       }
/*    */     }
/*    */   }
/*    */ }