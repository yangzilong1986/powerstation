 package com.hisun.mng.handler;
 
 import com.hisun.exception.HiException;
 import com.hisun.framework.event.IServerDestroyListener;
 import com.hisun.framework.event.IServerInitListener;
 import com.hisun.framework.event.ServerEvent;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiETF;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import com.hisun.pubinterface.IHandler;
 import com.hisun.register.HiRegisterService;
 import com.hisun.util.HiJMSHelper;
 
 public class HiMonHandler
   implements IHandler, IServerInitListener, IServerDestroyListener
 {
   private HiJMSHelper jms;
   private String factoryName;
   private String queueName;
   private Logger log;
 
   public void process(HiMessageContext ctx)
     throws HiException
   {
     HiMessage msg = ctx.getCurrentMsg();
     String service = msg.getHeadItem("STC");
 
     if (HiRegisterService.getMonSwitch(service)) {
       msg.getETFBody().setChildValue("MonCod", service);
       this.jms.sendMessage(ctx);
       if (this.log.isInfoEnabled())
         this.log.info("send msg to monitor:" + service);
     }
   }
 
   public void serverInit(ServerEvent event) throws HiException {
     this.jms = new HiJMSHelper();
     this.jms.initialize(this.factoryName, this.queueName);
   }
 
   public void setFactoryName(String factoryName) {
     this.factoryName = factoryName;
   }
 
   public void setQueueName(String queueName) {
     this.queueName = queueName;
   }
 
   public void setLog(Logger log) {
     this.log = log;
   }
 
   public void serverDestroy(ServerEvent event) throws HiException {
     this.jms.destory();
   }
 }