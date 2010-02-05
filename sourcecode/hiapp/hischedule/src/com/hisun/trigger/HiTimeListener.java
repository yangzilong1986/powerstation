 package com.hisun.trigger;
 
 import com.hisun.exception.HiException;
 import com.hisun.framework.event.ServerEvent;
 import com.hisun.framework.imp.HiAbstractListener;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiMessage;
 import java.util.Timer;
 
 public class HiTimeListener extends HiAbstractListener
 {
   private int second = -1;
 
   private Timer timer = null;
 
   public void setSecond(int second)
   {
     this.second = second;
   }
 
   public HiMessage getHiMessage()
   {
     HiMessage mess = super.getHiMessage();
     return mess;
   }
 
   public void serverInit(ServerEvent event)
     throws HiException
   {
   }
 
   public void serverStart(ServerEvent event)
     throws HiException
   {
     this.timer = HiJobRunShell.getJobRunShell(this.second, this);
   }
 
   public void serverStop(ServerEvent event) throws HiException
   {
     HiTimeTrigger.log.debug("stop start....");
     if (this.timer != null)
       this.timer.cancel();
   }
 
   public void serverDestroy(ServerEvent event) throws HiException
   {
     HiTimeTrigger.log.debug("destroy start....");
     if (this.timer != null)
       this.timer.cancel();
   }
 
   public void serverPause(ServerEvent event)
   {
   }
 
   public void serverResume(ServerEvent event)
   {
   }
 }