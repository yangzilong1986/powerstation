 package com.hisun.jms.bean;
 
 import com.hisun.exception.HiException;
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiMessage;
 import com.hisun.startup.HiStartup;
 import com.hisun.util.HiServiceLocator;
 import com.hisun.util.HiStringManager;
 import java.io.PrintStream;
 import javax.jms.JMSException;
 import javax.jms.Message;
 import javax.jms.MessageConsumer;
 import javax.jms.MessageListener;
 import javax.jms.ObjectMessage;
 import javax.jms.QueueConnection;
 import javax.jms.QueueConnectionFactory;
 import javax.jms.Session;
 import org.apache.commons.lang.StringUtils;
 
 public class HiASyncProcess
   implements MessageListener
 {
   private QueueConnection connection;
   private Session session;
   MessageConsumer consumer;
   private HiStartup startup;
   private String serverName;
   private static Logger log = HiLog.getErrorLogger("SYS.log");
 
   private static HiStringManager sm = HiStringManager.getManager();
 
   public HiASyncProcess()
   {
     this.startup = null;
 
     this.serverName = null;
   }
 
   public static HiASyncProcess getInstance(String serverName)
     throws HiException
   {
     HiASyncProcess asyncObj = new HiASyncProcess();
     asyncObj.initialize(serverName);
     asyncObj.run();
 
     return asyncObj;
   }
 
   public HiMessage process(HiMessage message) throws HiException
   {
     if (this.startup == null) {
       message.setStatus("E");
       message.setHeadItem("SSC", "212010");
 
       return message;
     }
     return this.startup.process(message);
   }
 
   public HiMessage manage(HiMessage message) {
     if (this.startup == null) {
       message.setStatus("E");
       message.setHeadItem("SSC", "211007");
       message.setHeadItem("SEM", sm.getString("211007"));
       return message;
     }
     try
     {
       return this.startup.manage(message);
     } catch (Throwable e) {
       message.setStatus("E");
       if (e instanceof HiException) {
         message.setHeadItem("SSC", ((HiException)e).getCode());
         message.setHeadItem("SEM", ((HiException)e).getAppMessage());
       } else {
         message.setHeadItem("SSC", "211007");
         message.setHeadItem("SEM", sm.getString("211007"));
       }
 
       HiLog.logSysError("initializ00", e); }
     return message;
   }
 
   public void onMessage(Message arg0)
   {
     Logger msgLog = null;
     try {
       HiMessage message = (HiMessage)((ObjectMessage)arg0).getObject();
       if (message == null) {
         System.out.println("HiAsyncProcess:onMessage:throw out invalid message");
         return;
       }
 
       msgLog = HiLog.getLogger(message);
       if (msgLog.isDebugEnabled()) {
         msgLog.debug("HiASyncProcess.onMessage start");
       }
       if (msgLog.isDebugEnabled()) {
         msgLog.debug(sm.getString("HiASyncProcess.onMessage", message));
       }
 
       String cmd = message.getHeadItem("CMD");
       if (!(StringUtils.isEmpty(cmd)))
         manage(message);
       else
         process(message);
     }
     catch (Throwable e) {
       if (e instanceof HiException) {
         String msgCode = ((HiException)e).getCode();
         if ((StringUtils.equals(msgCode, "212010")) || (StringUtils.equals(msgCode, "212101")))
         {
           log.error(e, e);
 
           Thread.yield();
           try {
             Thread.currentThread(); Thread.sleep(1000L);
           } catch (InterruptedException e1) {
             log.error(e1, e1);
           }
           throw new RuntimeException("Server not start", e);
         }
       }
       log.error(e, e);
     }
   }
 
   public void run()
     throws HiException
   {
     try
     {
       if (this.connection != null)
       {
         this.connection.close();
       }
 
       String jndi_pre = "java:comp/env/";
       String factoryName = jndi_pre + "ibs/jms/QueueFactory";
 
       HiServiceLocator locator = HiServiceLocator.getInstance();
       QueueConnectionFactory qcf = locator.getQueueConnectionFactory(factoryName);
       this.connection = qcf.createQueueConnection();
 
       this.session = this.connection.createQueueSession(false, 1);
 
       String destJndi = "ibs/jms/Queue";
       if (!(StringUtils.equalsIgnoreCase(this.serverName, "S.MDBSVR")))
       {
         destJndi = "ibs/jms/" + this.serverName.toUpperCase();
       }
       destJndi = jndi_pre + destJndi;
 
       this.consumer = this.session.createConsumer(locator.getQueue(destJndi));
       this.consumer.setMessageListener(this);
 
       this.connection.start();
     }
     catch (JMSException e)
     {
       close();
       throw new HiException(e);
     }
   }
 
   public void destory()
   {
     try {
       close();
     } catch (HiException e1) {
       log.error(e1.getMessage(), e1);
     }
     if (this.startup == null)
       return;
     try {
       this.startup.setFailured(false);
       this.startup.destory();
     } catch (HiException e) {
       System.out.println(sm.getString("HiRouterInBean.remove00", this.startup.getServerName()));
     }
 
     this.startup = null;
   }
 
   public void initialize(String serverName)
   {
     this.serverName = serverName;
 
     this.startup = new HiStartup(serverName);
   }
 
   private void close()
     throws HiException
   {
     try
     {
       if (this.consumer != null)
       {
         this.consumer.close();
       }
       if (this.session != null)
       {
         this.session.close();
       }
       if (this.connection != null)
       {
         this.connection.close();
       }
     } catch (JMSException e1) {
       throw new HiException(e1);
     }
   }
 
   public static void main(String[] argv) throws Exception {
     HiASyncProcess process = getInstance("S.MDBSVR");
   }
 }