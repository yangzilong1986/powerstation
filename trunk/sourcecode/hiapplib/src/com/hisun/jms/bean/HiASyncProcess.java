/*     */ package com.hisun.jms.bean;
/*     */ 
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.hilog4j.HiLog;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiMessage;
/*     */ import com.hisun.startup.HiStartup;
/*     */ import com.hisun.util.HiServiceLocator;
/*     */ import com.hisun.util.HiStringManager;
/*     */ import java.io.PrintStream;
/*     */ import javax.jms.JMSException;
/*     */ import javax.jms.Message;
/*     */ import javax.jms.MessageConsumer;
/*     */ import javax.jms.MessageListener;
/*     */ import javax.jms.ObjectMessage;
/*     */ import javax.jms.QueueConnection;
/*     */ import javax.jms.QueueConnectionFactory;
/*     */ import javax.jms.Session;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class HiASyncProcess
/*     */   implements MessageListener
/*     */ {
/*     */   private QueueConnection connection;
/*     */   private Session session;
/*     */   MessageConsumer consumer;
/*     */   private HiStartup startup;
/*     */   private String serverName;
/*  43 */   private static Logger log = HiLog.getErrorLogger("SYS.log");
/*     */ 
/*  45 */   private static HiStringManager sm = HiStringManager.getManager();
/*     */ 
/*     */   public HiASyncProcess()
/*     */   {
/*  39 */     this.startup = null;
/*     */ 
/*  41 */     this.serverName = null;
/*     */   }
/*     */ 
/*     */   public static HiASyncProcess getInstance(String serverName)
/*     */     throws HiException
/*     */   {
/*  56 */     HiASyncProcess asyncObj = new HiASyncProcess();
/*  57 */     asyncObj.initialize(serverName);
/*  58 */     asyncObj.run();
/*     */ 
/*  60 */     return asyncObj;
/*     */   }
/*     */ 
/*     */   public HiMessage process(HiMessage message) throws HiException
/*     */   {
/*  65 */     if (this.startup == null) {
/*  66 */       message.setStatus("E");
/*  67 */       message.setHeadItem("SSC", "212010");
/*     */ 
/*  69 */       return message;
/*     */     }
/*  71 */     return this.startup.process(message);
/*     */   }
/*     */ 
/*     */   public HiMessage manage(HiMessage message) {
/*  75 */     if (this.startup == null) {
/*  76 */       message.setStatus("E");
/*  77 */       message.setHeadItem("SSC", "211007");
/*  78 */       message.setHeadItem("SEM", sm.getString("211007"));
/*  79 */       return message;
/*     */     }
/*     */     try
/*     */     {
/*  83 */       return this.startup.manage(message);
/*     */     } catch (Throwable e) {
/*  85 */       message.setStatus("E");
/*  86 */       if (e instanceof HiException) {
/*  87 */         message.setHeadItem("SSC", ((HiException)e).getCode());
/*  88 */         message.setHeadItem("SEM", ((HiException)e).getAppMessage());
/*     */       } else {
/*  90 */         message.setHeadItem("SSC", "211007");
/*  91 */         message.setHeadItem("SEM", sm.getString("211007"));
/*     */       }
/*     */ 
/*  94 */       HiLog.logSysError("initializ00", e); }
/*  95 */     return message;
/*     */   }
/*     */ 
/*     */   public void onMessage(Message arg0)
/*     */   {
/* 103 */     Logger msgLog = null;
/*     */     try {
/* 105 */       HiMessage message = (HiMessage)((ObjectMessage)arg0).getObject();
/* 106 */       if (message == null) {
/* 107 */         System.out.println("HiAsyncProcess:onMessage:throw out invalid message");
/* 108 */         return;
/*     */       }
/*     */ 
/* 111 */       msgLog = HiLog.getLogger(message);
/* 112 */       if (msgLog.isDebugEnabled()) {
/* 113 */         msgLog.debug("HiASyncProcess.onMessage start");
/*     */       }
/* 115 */       if (msgLog.isDebugEnabled()) {
/* 116 */         msgLog.debug(sm.getString("HiASyncProcess.onMessage", message));
/*     */       }
/*     */ 
/* 119 */       String cmd = message.getHeadItem("CMD");
/* 120 */       if (!(StringUtils.isEmpty(cmd)))
/* 121 */         manage(message);
/*     */       else
/* 123 */         process(message);
/*     */     }
/*     */     catch (Throwable e) {
/* 126 */       if (e instanceof HiException) {
/* 127 */         String msgCode = ((HiException)e).getCode();
/* 128 */         if ((StringUtils.equals(msgCode, "212010")) || (StringUtils.equals(msgCode, "212101")))
/*     */         {
/* 133 */           log.error(e, e);
/*     */ 
/* 135 */           Thread.yield();
/*     */           try {
/* 137 */             Thread.currentThread(); Thread.sleep(1000L);
/*     */           } catch (InterruptedException e1) {
/* 139 */             log.error(e1, e1);
/*     */           }
/* 141 */           throw new RuntimeException("Server not start", e);
/*     */         }
/*     */       }
/* 144 */       log.error(e, e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void run()
/*     */     throws HiException
/*     */   {
/*     */     try
/*     */     {
/* 154 */       if (this.connection != null)
/*     */       {
/* 156 */         this.connection.close();
/*     */       }
/*     */ 
/* 159 */       String jndi_pre = "java:comp/env/";
/* 160 */       String factoryName = jndi_pre + "ibs/jms/QueueFactory";
/*     */ 
/* 162 */       HiServiceLocator locator = HiServiceLocator.getInstance();
/* 163 */       QueueConnectionFactory qcf = locator.getQueueConnectionFactory(factoryName);
/* 164 */       this.connection = qcf.createQueueConnection();
/*     */ 
/* 166 */       this.session = this.connection.createQueueSession(false, 1);
/*     */ 
/* 168 */       String destJndi = "ibs/jms/Queue";
/* 169 */       if (!(StringUtils.equalsIgnoreCase(this.serverName, "S.MDBSVR")))
/*     */       {
/* 171 */         destJndi = "ibs/jms/" + this.serverName.toUpperCase();
/*     */       }
/* 173 */       destJndi = jndi_pre + destJndi;
/*     */ 
/* 175 */       this.consumer = this.session.createConsumer(locator.getQueue(destJndi));
/* 176 */       this.consumer.setMessageListener(this);
/*     */ 
/* 178 */       this.connection.start();
/*     */     }
/*     */     catch (JMSException e)
/*     */     {
/* 183 */       close();
/* 184 */       throw new HiException(e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void destory()
/*     */   {
/*     */     try {
/* 191 */       close();
/*     */     } catch (HiException e1) {
/* 193 */       log.error(e1.getMessage(), e1);
/*     */     }
/* 195 */     if (this.startup == null)
/* 196 */       return;
/*     */     try {
/* 198 */       this.startup.setFailured(false);
/* 199 */       this.startup.destory();
/*     */     } catch (HiException e) {
/* 201 */       System.out.println(sm.getString("HiRouterInBean.remove00", this.startup.getServerName()));
/*     */     }
/*     */ 
/* 204 */     this.startup = null;
/*     */   }
/*     */ 
/*     */   public void initialize(String serverName)
/*     */   {
/* 209 */     this.serverName = serverName;
/*     */ 
/* 211 */     this.startup = new HiStartup(serverName);
/*     */   }
/*     */ 
/*     */   private void close()
/*     */     throws HiException
/*     */   {
/*     */     try
/*     */     {
/* 219 */       if (this.consumer != null)
/*     */       {
/* 221 */         this.consumer.close();
/*     */       }
/* 223 */       if (this.session != null)
/*     */       {
/* 225 */         this.session.close();
/*     */       }
/* 227 */       if (this.connection != null)
/*     */       {
/* 229 */         this.connection.close();
/*     */       }
/*     */     } catch (JMSException e1) {
/* 232 */       throw new HiException(e1);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void main(String[] argv) throws Exception {
/* 237 */     HiASyncProcess process = getInstance("S.MDBSVR");
/*     */   }
/*     */ }