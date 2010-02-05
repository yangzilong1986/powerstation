 package com.hisun.jms;
 
 import com.hisun.exception.HiException;
 import com.hisun.framework.event.ServerEvent;
 import com.hisun.framework.imp.HiAbstractListener;
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiMessage;
 
 public class HiJMSReceiverListener extends HiAbstractListener
 {
   private String factoryName;
   private String queueName;
   private HiReceiver receiver = null;
 
   public static final Logger log = HiLog.getLogger("jms.trc");
 
   public void setQueueName(String queueName)
   {
     this.queueName = queueName;
   }
 
   public void setFactoryName(String factoryName)
   {
     this.factoryName = factoryName;
   }
 
   public HiMessage getHiMessage()
   {
     HiMessage mess = super.getHiMessage();
     return mess;
   }
 
   public void serverInit(ServerEvent arg0)
     throws HiException
   {
     this.receiver = new HiReceiver();
     this.receiver.receive(this.factoryName, this.queueName);
   }
 
   public void serverStart(ServerEvent arg0) throws HiException {
     this.receiver.start(this);
   }
 
   public void serverStop(ServerEvent arg0)
     throws HiException
   {
   }
 
   public void serverDestroy(ServerEvent arg0) throws HiException
   {
     this.receiver.destory();
   }
 
   public void serverPause(ServerEvent arg0)
   {
   }
 
   public void serverResume(ServerEvent arg0)
   {
   }
 }