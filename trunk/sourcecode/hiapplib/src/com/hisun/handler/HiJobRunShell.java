/*    */ package com.hisun.handler;
/*    */ 
/*    */ import com.hisun.framework.HiDefaultServer;
/*    */ import com.hisun.listener.HiTimeListener;
/*    */ import com.hisun.message.HiMessage;
/*    */ import com.hisun.message.HiMessageContext;
/*    */ import java.util.Date;
/*    */ import java.util.Timer;
/*    */ import java.util.TimerTask;
/*    */ import org.apache.commons.lang.time.DateUtils;
/*    */ 
/*    */ public class HiJobRunShell extends TimerTask
/*    */ {
/* 17 */   private HiTimeListener listener = null;
/*    */ 
/*    */   public HiJobRunShell(HiTimeListener listener)
/*    */   {
/* 21 */     this.listener = listener;
/*    */   }
/*    */ 
/*    */   public void run()
/*    */   {
/*    */     try
/*    */     {
/* 28 */       Date currentDate = new Date(System.currentTimeMillis() + 60000L);
/* 29 */       Date startDate = DateUtils.truncate(currentDate, 12);
/*    */ 
/* 33 */       HiMessage mess = this.listener.getHiMessage();
/* 34 */       HiMessageContext ctx = new HiMessageContext();
/* 35 */       ctx.setCurrentMsg(mess);
/* 36 */       HiMessageContext.setCurrentMessageContext(ctx);
/*    */ 
/* 38 */       this.listener.getServer().process(ctx);
/*    */     }
/*    */     catch (Exception e)
/*    */     {
/* 43 */       e.printStackTrace();
/*    */     }
/*    */   }
/*    */ 
/*    */   public static Timer getJobRunShell(int second, HiTimeListener listener)
/*    */   {
/*    */     try
/*    */     {
/* 51 */       Date currentDate = new Date(System.currentTimeMillis() + 60000L);
/* 52 */       Date startDate = DateUtils.truncate(currentDate, 12);
/*    */ 
/* 54 */       Timer timer = new Timer();
/* 55 */       HiJobRunShell run = new HiJobRunShell(listener);
/* 56 */       timer.schedule(run, startDate, second * 1000);
/* 57 */       return timer;
/*    */     }
/*    */     catch (Exception e)
/*    */     {
/* 61 */       e.printStackTrace(); }
/* 62 */     return null;
/*    */   }
/*    */ }