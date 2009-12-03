/*    */ package com.hisun.listener;
/*    */ 
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.framework.event.ServerEvent;
/*    */ import com.hisun.framework.imp.HiAbstractListener;
/*    */ import com.hisun.handler.HiJobRunShell;
/*    */ import com.hisun.message.HiMessage;
/*    */ import java.util.Timer;
/*    */ 
/*    */ public class HiTimeListener extends HiAbstractListener
/*    */ {
/* 18 */   private int second = -1;
/*    */ 
/* 31 */   private Timer timer = null;
/*    */ 
/*    */   public void setSecond(int second)
/*    */   {
/* 22 */     this.second = second;
/*    */   }
/*    */ 
/*    */   public HiMessage getHiMessage()
/*    */   {
/* 27 */     HiMessage mess = super.getHiMessage();
/* 28 */     return mess;
/*    */   }
/*    */ 
/*    */   public void serverInit(ServerEvent event)
/*    */     throws HiException
/*    */   {
/*    */   }
/*    */ 
/*    */   public void serverStart(ServerEvent event)
/*    */     throws HiException
/*    */   {
/* 39 */     this.timer = HiJobRunShell.getJobRunShell(this.second, this);
/*    */   }
/*    */ 
/*    */   public void serverStop(ServerEvent event) throws HiException {
/* 43 */     if (this.timer != null)
/* 44 */       this.timer.cancel();
/*    */   }
/*    */ 
/*    */   public void serverDestroy(ServerEvent event) throws HiException {
/* 48 */     if (this.timer != null)
/* 49 */       this.timer.cancel();
/*    */   }
/*    */ 
/*    */   public void serverPause(ServerEvent event)
/*    */   {
/*    */   }
/*    */ 
/*    */   public void serverResume(ServerEvent event)
/*    */   {
/*    */   }
/*    */ }