/*     */ package com.hisun.server.manage.servlet;
/*     */ 
/*     */ import com.hisun.deploy.HiDeploymentHelper;
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.message.HiETF;
/*     */ import com.hisun.message.HiMessage;
/*     */ import com.hisun.message.HiXmlETF;
/*     */ import com.hisun.startup.HiStartup;
/*     */ import com.hisun.util.HiJMSHelper;
/*     */ import com.hisun.util.HiServiceLocator;
/*     */ import java.io.PrintStream;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import org.apache.commons.beanutils.MethodUtils;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class HiClientUtilPOJO
/*     */ {
/*     */   public static void start(String serverName)
/*     */     throws HiException
/*     */   {
/*  25 */     HiMessage msg = getMessage();
/*  26 */     msg.setHeadItem("CMD", "start");
/*  27 */     msg.getETFBody().setChildValue("SERVER", serverName);
/*  28 */     String jndi = "ejb/" + serverName;
/*  29 */     invoke(msg, jndi);
/*     */   }
/*     */ 
/*     */   public static void stop(String serverName) throws HiException {
/*  33 */     HiMessage msg = getMessage();
/*  34 */     msg.setHeadItem("CMD", "stop");
/*  35 */     msg.getETFBody().setChildValue("SERVER", serverName);
/*  36 */     String jndi = "ejb/" + serverName;
/*  37 */     invoke(msg, jndi);
/*     */   }
/*     */ 
/*     */   public static void deploy(String serverName) throws HiException {
/*  41 */     HiMessage msg = getMessage();
/*  42 */     msg.setHeadItem("CMD", "deploy");
/*  43 */     msg.getETFBody().setChildValue("SERVER", serverName);
/*  44 */     String jndi = "ejb/" + serverName;
/*  45 */     invoke(msg, jndi);
/*     */   }
/*     */ 
/*     */   public static void undeploy(String serverName) throws HiException {
/*  49 */     HiMessage msg = getMessage();
/*  50 */     msg.setHeadItem("CMD", "undeploy");
/*  51 */     msg.getETFBody().setChildValue("SERVER", serverName);
/*  52 */     String jndi = "ejb/" + serverName;
/*  53 */     invoke(msg, jndi);
/*     */   }
/*     */ 
/*     */   public static void pause(String serverName) throws HiException {
/*  57 */     HiMessage msg = getMessage();
/*  58 */     msg.setHeadItem("CMD", "pause");
/*  59 */     msg.getETFBody().setChildValue("SERVER", serverName);
/*  60 */     String jndi = "ejb/" + serverName;
/*  61 */     invoke(msg, jndi);
/*     */   }
/*     */ 
/*     */   public static void redeploy(String serverName) throws HiException {
/*  65 */     HiMessage msg = getMessage();
/*  66 */     msg.setHeadItem("CMD", "redeploy");
/*  67 */     msg.getETFBody().setChildValue("SERVER", serverName);
/*  68 */     String jndi = "ejb/" + serverName;
/*  69 */     invoke(msg, jndi);
/*     */   }
/*     */ 
/*     */   public static void resume(String serverName) throws HiException {
/*  73 */     HiMessage msg = getMessage();
/*  74 */     msg.setHeadItem("CMD", "resume");
/*  75 */     msg.getETFBody().setChildValue("SERVER", serverName);
/*  76 */     String jndi = "ejb/" + serverName;
/*  77 */     invoke(msg, jndi);
/*     */   }
/*     */ 
/*     */   private static void invoke(HiMessage msg, String jndi)
/*     */     throws HiException
/*     */   {
/*  87 */     HiServiceLocator locator = HiServiceLocator.getInstance();
/*     */     try
/*     */     {
/*  93 */       Object o = locator.lookup(jndi);
/*  94 */       if (o == null) {
/*  95 */         HiStartup startup = new HiStartup("");
/*  96 */         locator.bind(jndi, startup);
/*  97 */         o = startup;
/*     */       }
/*  99 */       msg = (HiMessage)MethodUtils.invokeExactMethod(o, "manage", msg);
/*     */     } catch (NoSuchMethodException e) {
/* 101 */       throw HiException.makeException(e);
/*     */     } catch (IllegalAccessException e) {
/* 103 */       throw HiException.makeException(e);
/*     */     } catch (InvocationTargetException e) {
/* 105 */       throw HiException.makeException(e);
/*     */     }
/*     */ 
/* 108 */     if (StringUtils.equalsIgnoreCase(msg.getStatus(), "E")) {
/* 109 */       String code = msg.getHeadItem("SSC");
/* 110 */       throw new HiException("215027", code + ":" + msg.getHeadItem("SEM"));
/*     */     }
/*     */   }
/*     */ 
/*     */   private static HiMessage getMessage() {
/* 115 */     HiMessage msg = new HiMessage("Client", "CMsgType");
/* 116 */     HiXmlETF etf = new HiXmlETF();
/* 117 */     msg.setBody(etf);
/* 118 */     return msg;
/*     */   }
/*     */ 
/*     */   private static void invoke(String method, String serverName) throws HiException
/*     */   {
/* 123 */     HiMessage msg = new HiMessage("Client", "CMsgType");
/* 124 */     HiXmlETF etf = new HiXmlETF();
/* 125 */     msg.setBody(etf);
/* 126 */     msg.setHeadItem("CMD", method);
/* 127 */     etf.setChildValue("SERVER", serverName);
/* 128 */     String jndi = "ejb/" + serverName;
/* 129 */     invoke(msg, jndi);
/* 130 */     msg.clearHead();
/* 131 */     msg = null;
/* 132 */     etf = null;
/*     */   }
/*     */ 
/*     */   private static void invoke(HiMessage msg, String factoryName, String queueName)
/*     */     throws HiException
/*     */   {
/* 143 */     HiJMSHelper jmsHelper = new HiJMSHelper();
/*     */ 
/* 145 */     jmsHelper.initialize(factoryName, queueName);
/*     */     try {
/* 147 */       jmsHelper.sendMessage(msg, 9);
/*     */     } finally {
/* 149 */       jmsHelper.destory();
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void invoke(String method, String serverName, String groupName, String configFile, String type)
/*     */     throws HiException
/*     */   {
/* 156 */     if ((StringUtils.equalsIgnoreCase(method, "install")) || (StringUtils.equalsIgnoreCase(method, "uninstall")))
/*     */     {
/* 158 */       System.out.println("HiClientUtil: run deployment start...");
/* 159 */       HiDeploymentHelper.deployManage(method, serverName);
/* 160 */       return;
/*     */     }
/*     */ 
/* 164 */     HiMessage msg = new HiMessage("Client", "SYSMNG");
/* 165 */     HiXmlETF etf = new HiXmlETF();
/* 166 */     msg.setBody(etf);
/* 167 */     msg.setHeadItem("CMD", method);
/* 168 */     etf.setChildValue("APP_NM", groupName);
/* 169 */     etf.setChildValue("SERVER", serverName);
/* 170 */     etf.setChildValue("CONFIG_FILE", configFile);
/*     */ 
/* 172 */     if (StringUtils.equalsIgnoreCase(type, "jms"))
/*     */     {
/* 180 */       String factoryName = HiSYSCFG.getInstance().getProperty("_QUEUE_FACTORY");
/*     */ 
/* 182 */       String queueName = HiSYSCFG.getInstance().getProperty("_QUEUE");
/*     */ 
/* 184 */       if (!(StringUtils.equalsIgnoreCase(serverName, "S.MDBSVR")))
/*     */       {
/* 188 */         queueName = HiSYSCFG.getInstance().getProperty("_QUEUE");
/* 189 */         int idx = queueName.lastIndexOf("Queue");
/* 190 */         if (idx != -1) {
/* 191 */           queueName = queueName.substring(0, idx) + serverName;
/*     */         }
/*     */       }
/*     */ 
/* 195 */       invoke(msg, factoryName, queueName);
/*     */     }
/*     */     else
/*     */     {
/* 199 */       String jndi = "ibs/ejb/" + serverName;
/* 200 */       invoke(msg, jndi);
/*     */     }
/* 202 */     msg.clearHead();
/* 203 */     msg = null;
/* 204 */     etf = null;
/*     */   }
/*     */ }