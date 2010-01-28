 package com.hisun.atc.common;
 
 import com.hisun.message.HiContext;
 import java.util.HashMap;
 
 public class HiTriggerMsg
 {
   private static final String NOTIFY_MSG_KEY = "__NOTIFY_MSG_KEY";
   private static final Object lock1 = new Object();
   private static final Object lock2 = new Object();
 
   public static void triggerMsg(HiContext ctx, String msgId) {
     synchronized (lock1) {
       if (!(ctx.containsProperty("__NOTIFY_MSG_KEY"))) {
         ctx.setProperty("__NOTIFY_MSG_KEY", new HashMap());
       }
       HashMap map = (HashMap)ctx.getProperty("__NOTIFY_MSG_KEY");
       if (!(map.containsKey(msgId))) {
         map.put(msgId, new StringBuffer());
       }
       StringBuffer buf = (StringBuffer)map.get(msgId);
       buf.append("1");
       synchronized (buf) {
         buf.notify();
       }
     }
   }
 
   public static void waitMsg(HiContext ctx, String msgId, int tmOut) throws InterruptedException
   {
     synchronized (lock2) {
       if (!(ctx.containsProperty("__NOTIFY_MSG_KEY"))) {
         ctx.setProperty("__NOTIFY_MSG_KEY", new HashMap());
       }
       HashMap map = (HashMap)ctx.getProperty("__NOTIFY_MSG_KEY");
       if (!(map.containsKey(msgId))) {
         map.put(msgId, new StringBuffer());
       }
       StringBuffer buf = (StringBuffer)map.get(msgId);
       if (buf.length() > 0) {
         buf.setLength(0);
         return;
       }
       synchronized (buf) {
         if (tmOut == -1)
           buf.wait();
         else {
           buf.wait(tmOut * 1000);
         }
       }
       buf.setLength(0);
       return;
     }
   }
 }