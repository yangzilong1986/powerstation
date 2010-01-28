 package com.hisun.listener;
 
 import com.hisun.exception.HiException;
 import com.hisun.framework.HiDefaultServer;
 import com.hisun.framework.event.ServerEvent;
 import com.hisun.framework.imp.HiAbstractListener;
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 
 public class HiBatchListener extends HiAbstractListener
 {
   public void serverInit(ServerEvent event)
     throws HiException
   {
   }
 
   public void serverStart(ServerEvent event)
     throws HiException
   {
     HiProcess pro = new HiProcess(this);
     pro.start();
   }
 
   public void serverStop(ServerEvent event)
     throws HiException
   {
   }
 
   public void serverDestroy(ServerEvent event)
     throws HiException
   {
   }
 
   public void serverPause(ServerEvent event)
   {
   }
 
   public void serverResume(ServerEvent event)
   {
   }
 
   class HiProcess extends Thread
   {
     HiBatchListener list;
 
     HiProcess(HiBatchListener paramHiBatchListener)
     {
       this.list = paramHiBatchListener;
     }
 
     public void run()
     {
       HiMessage msg = new HiMessage(HiBatchListener.this.getServer().getName(), HiBatchListener.this.getMsgType());
 
       msg.setHeadItem("STF", HiBatchListener.this.getServer().getTrace());
       HiMessageContext ctx = new HiMessageContext();
       ctx.setProperty("app_server", HiBatchListener.this.getServer().getName());
       ctx.setCurrentMsg(msg);
       HiMessageContext.setCurrentMessageContext(ctx);
       Logger log = HiLog.getLogger(msg);
       if (log.isInfoEnabled())
       {
         log.info("Begin batchOnlineServer");
       }
       try
       {
         sleep(2000L);
         this.list.getServer().process(ctx);
       }
       catch (Exception e)
       {
       }
     }
   }
 }