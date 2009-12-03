/*    */ package com.hisun.trigger;
/*    */ 
/*    */ import com.hisun.framework.HiDefaultServer;
/*    */ import com.hisun.message.HiMessage;
/*    */ import com.hisun.message.HiMessageContext;
/*    */ import java.util.Date;
/*    */ import java.util.Timer;
/*    */ import java.util.TimerTask;
/*    */ import org.apache.commons.lang.time.DateUtils;
/*    */ 
/*    */ public class HiJobRunShell extends TimerTask
/*    */ {
/* 16 */   private HiTimeListener listener = null;
/*    */ 
/*    */   public HiJobRunShell(HiTimeListener listener)
/*    */   {
/* 20 */     this.listener = listener;
/*    */   }
/*    */ 
/*    */   public void run()
/*    */   {
/*    */     try
/*    */     {
/* 27 */       Date currentDate = new Date(System.currentTimeMillis() + 60000L);
/* 28 */       Date startDate = DateUtils.truncate(currentDate, 12);
/*    */ 
/* 32 */       HiMessage mess = this.listener.getHiMessage();
/* 33 */       HiMessageContext ctx = new HiMessageContext();
/* 34 */       ctx.setCurrentMsg(mess);
/* 35 */       HiMessageContext.setCurrentMessageContext(ctx);
/*    */ 
/* 37 */       this.listener.getServer().process(ctx);
/*    */     }
/*    */     catch (Exception e)
/*    */     {
/* 42 */       e.printStackTrace();
/*    */     }
/*    */   }
/*    */ 
/*    */   public static Timer getJobRunShell(int second, HiTimeListener listener)
/*    */   {
/*    */     try
/*    */     {
/* 50 */       Date currentDate = new Date(System.currentTimeMillis() + 60000L);
/* 51 */       Date startDate = DateUtils.truncate(currentDate, 12);
/*    */ 
/* 53 */       Timer timer = new Timer();
/* 54 */       HiJobRunShell run = new HiJobRunShell(listener);
/* 55 */       timer.schedule(run, startDate, second * 1000);
/* 56 */       return timer;
/*    */     }
/*    */     catch (Exception e)
/*    */     {
/* 60 */       e.printStackTrace(); }
/* 61 */     return null;
/*    */   }
/*    */ }