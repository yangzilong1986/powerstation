/*    */ package com.hisun.jms;
/*    */ 
/*    */ import com.hisun.framework.HiDefaultServer;
/*    */ import com.hisun.hilog4j.Logger;
/*    */ import com.hisun.message.HiMessageContext;
/*    */ 
/*    */ public class HiPoolJMS
/*    */   implements Runnable
/*    */ {
/*    */   private HiMessageContext ctx;
/*    */   private HiJMSReceiverListener listener;
/*    */ 
/*    */   public HiPoolJMS(HiMessageContext ctx, HiJMSReceiverListener listener)
/*    */   {
/* 14 */     this.ctx = ctx;
/* 15 */     this.listener = listener;
/*    */   }
/*    */ 
/*    */   public void run()
/*    */   {
/*    */     try
/*    */     {
/* 22 */       if (HiJMSReceiverListener.log.isDebugEnabled())
/*    */       {
/* 24 */         HiJMSReceiverListener.log.debug(this.ctx);
/*    */       }
/* 26 */       this.listener.getServer().process(this.ctx);
/*    */     }
/*    */     catch (Exception e)
/*    */     {
/*    */     }
/*    */   }
/*    */ }