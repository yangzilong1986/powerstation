 package com.hisun.jms;
 
 import com.hisun.framework.HiDefaultServer;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiMessageContext;
 
 public class HiPoolJMS
   implements Runnable
 {
   private HiMessageContext ctx;
   private HiJMSReceiverListener listener;
 
   public HiPoolJMS(HiMessageContext ctx, HiJMSReceiverListener listener)
   {
     this.ctx = ctx;
     this.listener = listener;
   }
 
   public void run()
   {
     try
     {
       if (HiJMSReceiverListener.log.isDebugEnabled())
       {
         HiJMSReceiverListener.log.debug(this.ctx);
       }
       this.listener.getServer().process(this.ctx);
     }
     catch (Exception e)
     {
     }
   }
 }