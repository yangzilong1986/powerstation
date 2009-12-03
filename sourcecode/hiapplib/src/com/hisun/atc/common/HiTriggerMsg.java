/*    */ package com.hisun.atc.common;
/*    */ 
/*    */ import com.hisun.message.HiContext;
/*    */ import java.util.HashMap;
/*    */ 
/*    */ public class HiTriggerMsg
/*    */ {
/*    */   private static final String NOTIFY_MSG_KEY = "__NOTIFY_MSG_KEY";
/* 14 */   private static final Object lock1 = new Object();
/* 15 */   private static final Object lock2 = new Object();
/*    */ 
/*    */   public static void triggerMsg(HiContext ctx, String msgId) {
/* 18 */     synchronized (lock1) {
/* 19 */       if (!(ctx.containsProperty("__NOTIFY_MSG_KEY"))) {
/* 20 */         ctx.setProperty("__NOTIFY_MSG_KEY", new HashMap());
/*    */       }
/* 22 */       HashMap map = (HashMap)ctx.getProperty("__NOTIFY_MSG_KEY");
/* 23 */       if (!(map.containsKey(msgId))) {
/* 24 */         map.put(msgId, new StringBuffer());
/*    */       }
/* 26 */       StringBuffer buf = (StringBuffer)map.get(msgId);
/* 27 */       buf.append("1");
/* 28 */       synchronized (buf) {
/* 29 */         buf.notify();
/*    */       }
/*    */     }
/*    */   }
/*    */ 
/*    */   public static void waitMsg(HiContext ctx, String msgId, int tmOut) throws InterruptedException
/*    */   {
/* 36 */     synchronized (lock2) {
/* 37 */       if (!(ctx.containsProperty("__NOTIFY_MSG_KEY"))) {
/* 38 */         ctx.setProperty("__NOTIFY_MSG_KEY", new HashMap());
/*    */       }
/* 40 */       HashMap map = (HashMap)ctx.getProperty("__NOTIFY_MSG_KEY");
/* 41 */       if (!(map.containsKey(msgId))) {
/* 42 */         map.put(msgId, new StringBuffer());
/*    */       }
/* 44 */       StringBuffer buf = (StringBuffer)map.get(msgId);
/* 45 */       if (buf.length() > 0) {
/* 46 */         buf.setLength(0);
/* 47 */         return;
/*    */       }
/* 49 */       synchronized (buf) {
/* 50 */         if (tmOut == -1)
/* 51 */           buf.wait();
/*    */         else {
/* 53 */           buf.wait(tmOut * 1000);
/*    */         }
/*    */       }
/* 56 */       buf.setLength(0);
/* 57 */       return;
/*    */     }
/*    */   }
/*    */ }