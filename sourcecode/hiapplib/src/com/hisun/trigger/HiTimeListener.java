/*    */ package com.hisun.trigger;
/*    */ 
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.framework.event.ServerEvent;
/*    */ import com.hisun.framework.imp.HiAbstractListener;
/*    */ import com.hisun.hilog4j.Logger;
/*    */ import com.hisun.message.HiMessage;
/*    */ import java.util.Timer;
/*    */ 
/*    */ public class HiTimeListener extends HiAbstractListener
/*    */ {
/* 16 */   private int second = -1;
/*    */ 
/* 29 */   private Timer timer = null;
/*    */ 
/*    */   public void setSecond(int second)
/*    */   {
/* 20 */     this.second = second;
/*    */   }
/*    */ 
/*    */   public HiMessage getHiMessage()
/*    */   {
/* 25 */     HiMessage mess = super.getHiMessage();
/* 26 */     return mess;
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
/*    */   public void serverStop(ServerEvent event) throws HiException
/*    */   {
/* 44 */     HiTimeTrigger.log.debug("stop start....");
/* 45 */     if (this.timer != null)
/* 46 */       this.timer.cancel();
/*    */   }
/*    */ 
/*    */   public void serverDestroy(ServerEvent event) throws HiException
/*    */   {
/* 51 */     HiTimeTrigger.log.debug("destroy start....");
/* 52 */     if (this.timer != null)
/* 53 */       this.timer.cancel();
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