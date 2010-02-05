 package com.hisun.jms;
 
 import com.hisun.exception.HiException;
 import com.hisun.framework.event.IServerDestroyListener;
 import com.hisun.framework.event.IServerInitListener;
 import com.hisun.framework.event.ServerEvent;
 import com.hisun.message.HiMessageContext;
 import com.hisun.pubinterface.IHandler;
 import com.hisun.util.HiJMSHelper;
 
 public class HiJMSSendHandler
   implements IHandler, IServerInitListener, IServerDestroyListener
 {
   private String factoryName;
   private String queueName;
   private HiJMSHelper jms;
 
   public void process(HiMessageContext ctx)
     throws HiException
   {
     this.jms.sendMessage(ctx);
   }
 
   public String getFactoryName()
   {
     return this.factoryName;
   }
 
   public void setFactoryName(String factoryName)
   {
     this.factoryName = factoryName;
   }
 
   public String getQueueName()
   {
     return this.queueName;
   }
 
   public void setQueueName(String queueName)
   {
     this.queueName = queueName;
   }
 
   public void serverInit(ServerEvent arg0) throws HiException {
     this.jms = new HiJMSHelper();
     this.jms.initialize(getFactoryName(), getQueueName());
   }
 
   public void serverDestroy(ServerEvent arg0) throws HiException {
     this.jms.destory();
   }
 }