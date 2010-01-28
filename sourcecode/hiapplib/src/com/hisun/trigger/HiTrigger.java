 package com.hisun.trigger;
 
 import com.hisun.exception.HiException;
 import com.hisun.framework.event.IServerDestroyListener;
 import com.hisun.framework.event.IServerInitListener;
 import com.hisun.framework.event.ServerEvent;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import com.hisun.pubinterface.IHandler;
 import java.util.ArrayList;
 import java.util.Date;
 import org.apache.commons.lang.time.DateUtils;
 
 public class HiTrigger
   implements IHandler, IServerInitListener, IServerDestroyListener
 {
   private String strFileName = null;
 
   private HiTimeTrigger triggerObj = null;
 
   public void setFilename(String strFileName)
   {
     this.strFileName = strFileName;
   }
 
   public void process(HiMessageContext ctx)
     throws HiException
   {
     ArrayList schItems = this.triggerObj.getSchItems();
     Date currentDate = new Date(System.currentTimeMillis());
     for (int i = 0; i < schItems.size(); ++i) {
       HiSchItem item = (HiSchItem)schItems.get(i);
       Logger log = HiTimeTrigger.log;
       currentDate = DateUtils.truncate(currentDate, 12);
       try {
         if (isTrigger(item, currentDate)) {
           currentDate = DateUtils.truncate(currentDate, 12);
 
           HiMessage msg = ctx.getCurrentMsg().cloneNoBodyAndRqID();
           ctx.setCurrentMsg(msg);
           item.process(ctx, currentDate);
         }
       } catch (Exception e) {
         log.error(e, e);
       }
     }
   }
 
   public boolean isTrigger(HiSchItem item, Date currentDate)
     throws HiException
   {
     Logger log = HiTimeTrigger.log;
 
     Date lastDate = item.getLastDate();
 
     if (DateUtils.truncate(lastDate, 12).equals(currentDate))
     {
       return (!(item.isRepeatSend()));
     }
 
     Date afterDate = item.getCronExpression().getTimeAfter(currentDate);
 
     long triggerTime = DateUtils.truncate(afterDate, 12).getTime();
 
     return (triggerTime > currentDate.getTime());
   }
 
   public void serverInit(ServerEvent arg0)
     throws HiException
   {
     if (this.strFileName.equalsIgnoreCase(""))
       this.triggerObj = HiSchUtilites.loadTimeTriggerFormDB();
     else
       this.triggerObj = HiSchUtilites.loadTimeTrigger(this.strFileName);
     this.triggerObj.init();
   }
 
   public void serverDestroy(ServerEvent arg0)
     throws HiException
   {
   }
 }