/*     */ package com.hisun.server.manage.servlet;
/*     */ 
/*     */ import com.hisun.deploy.HiDeploymentHelper;
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.message.HiETF;
/*     */ import com.hisun.message.HiMessage;
/*     */ import com.hisun.message.HiXmlETF;
/*     */ import com.hisun.util.HiJMSHelper;
/*     */ import com.hisun.util.HiServiceLocator;
/*     */ import java.io.PrintStream;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import javax.ejb.EJBHome;
/*     */ import org.apache.commons.beanutils.MethodUtils;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class HiClientUtil
/*     */ {
/*     */   public static void start(String serverName)
/*     */     throws HiException
/*     */   {
/*  23 */     HiMessage msg = getMessage();
/*  24 */     msg.setHeadItem("CMD", "start");
/*  25 */     msg.getETFBody().setChildValue("SERVER", serverName);
/*  26 */     String jndi = "ejb/" + serverName;
/*  27 */     invoke(msg, jndi);
/*     */   }
/*     */ 
/*     */   public static void stop(String serverName) throws HiException {
/*  31 */     HiMessage msg = getMessage();
/*  32 */     msg.setHeadItem("CMD", "stop");
/*  33 */     msg.getETFBody().setChildValue("SERVER", serverName);
/*  34 */     String jndi = "ejb/" + serverName;
/*  35 */     invoke(msg, jndi);
/*     */   }
/*     */ 
/*     */   public static void deploy(String serverName) throws HiException {
/*  39 */     HiMessage msg = getMessage();
/*  40 */     msg.setHeadItem("CMD", "deploy");
/*  41 */     msg.getETFBody().setChildValue("SERVER", serverName);
/*  42 */     String jndi = "ejb/" + serverName;
/*  43 */     invoke(msg, jndi);
/*     */   }
/*     */ 
/*     */   public static void undeploy(String serverName) throws HiException {
/*  47 */     HiMessage msg = getMessage();
/*  48 */     msg.setHeadItem("CMD", "undeploy");
/*  49 */     msg.getETFBody().setChildValue("SERVER", serverName);
/*  50 */     String jndi = "ejb/" + serverName;
/*  51 */     invoke(msg, jndi);
/*     */   }
/*     */ 
/*     */   public static void pause(String serverName) throws HiException {
/*  55 */     HiMessage msg = getMessage();
/*  56 */     msg.setHeadItem("CMD", "pause");
/*  57 */     msg.getETFBody().setChildValue("SERVER", serverName);
/*  58 */     String jndi = "ejb/" + serverName;
/*  59 */     invoke(msg, jndi);
/*     */   }
/*     */ 
/*     */   public static void redeploy(String serverName) throws HiException {
/*  63 */     HiMessage msg = getMessage();
/*  64 */     msg.setHeadItem("CMD", "redeploy");
/*  65 */     msg.getETFBody().setChildValue("SERVER", serverName);
/*  66 */     String jndi = "ejb/" + serverName;
/*  67 */     invoke(msg, jndi);
/*     */   }
/*     */ 
/*     */   public static void resume(String serverName) throws HiException {
/*  71 */     HiMessage msg = getMessage();
/*  72 */     msg.setHeadItem("CMD", "resume");
/*  73 */     msg.getETFBody().setChildValue("SERVER", serverName);
/*  74 */     String jndi = "ejb/" + serverName;
/*  75 */     invoke(msg, jndi);
/*     */   }
/*     */ 
/*     */   private static void invoke(HiMessage msg, String jndi)
/*     */     throws HiException
/*     */   {
/*  85 */     HiServiceLocator locator = HiServiceLocator.getInstance();
/*  86 */     EJBHome home = locator.getRemoteHome(jndi, EJBHome.class);
/*     */     try
/*     */     {
/*  90 */       Object o = MethodUtils.invokeExactMethod(home, "create", null);
/*  91 */       msg = (HiMessage)MethodUtils.invokeExactMethod(o, "manage", msg);
/*     */     } catch (NoSuchMethodException e) {
/*  93 */       e.printStackTrace();
/*     */     } catch (IllegalAccessException e) {
/*  95 */       e.printStackTrace();
/*     */     }
/*     */     catch (InvocationTargetException e) {
/*  98 */       e.getTargetException().printStackTrace();
/*     */     }
/*     */ 
/* 101 */     if (StringUtils.equalsIgnoreCase(msg.getStatus(), "E")) {
/* 102 */       String code = msg.getHeadItem("SSC");
/* 103 */       throw new HiException("215027", code + ":" + msg.getHeadItem("SEM"));
/*     */     }
/*     */   }
/*     */ 
/*     */   private static HiMessage getMessage() {
/* 108 */     HiMessage msg = new HiMessage("Client", "CMsgType");
/* 109 */     HiXmlETF etf = new HiXmlETF();
/* 110 */     msg.setBody(etf);
/* 111 */     return msg;
/*     */   }
/*     */ 
/*     */   private static void invoke(String method, String serverName) throws HiException
/*     */   {
/* 116 */     HiMessage msg = new HiMessage("Client", "CMsgType");
/* 117 */     HiXmlETF etf = new HiXmlETF();
/* 118 */     msg.setBody(etf);
/* 119 */     msg.setHeadItem("CMD", method);
/* 120 */     etf.setChildValue("SERVER", serverName);
/* 121 */     String jndi = "ejb/" + serverName;
/* 122 */     invoke(msg, jndi);
/* 123 */     msg.clearHead();
/* 124 */     msg = null;
/* 125 */     etf = null;
/*     */   }
/*     */ 
/*     */   private static void invoke(HiMessage msg, String factoryName, String queueName)
/*     */     throws HiException
/*     */   {
/* 136 */     HiJMSHelper jmsHelper = new HiJMSHelper();
/*     */ 
/* 138 */     jmsHelper.initialize(factoryName, queueName);
/*     */     try {
/* 140 */       jmsHelper.sendMessage(msg, 9);
/*     */     } finally {
/* 142 */       jmsHelper.destory();
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void invoke(String method, String serverName, String groupName, String configFile, String type)
/*     */     throws HiException
/*     */   {
/* 149 */     if ((StringUtils.equalsIgnoreCase(method, "install")) || (StringUtils.equalsIgnoreCase(method, "uninstall")))
/*     */     {
/* 151 */       System.out.println("HiClientUtil: run deployment start...");
/* 152 */       HiDeploymentHelper.deployManage(method, serverName);
/* 153 */       return;
/*     */     }
/*     */ 
/* 157 */     HiMessage msg = new HiMessage("Client", "SYSMNG");
/* 158 */     HiXmlETF etf = new HiXmlETF();
/* 159 */     msg.setBody(etf);
/* 160 */     msg.setHeadItem("CMD", method);
/* 161 */     etf.setChildValue("APP_NM", groupName);
/* 162 */     etf.setChildValue("SERVER", serverName);
/* 163 */     etf.setChildValue("CONFIG_FILE", configFile);
/*     */ 
/* 165 */     if (StringUtils.equalsIgnoreCase(type, "jms"))
/*     */     {
/* 167 */       String factoryName = HiSYSCFG.getInstance().getProperty("_QUEUE_FACTORY");
/* 168 */       String queueName = HiSYSCFG.getInstance().getProperty("_QUEUE");
/*     */ 
/* 172 */       if (!(StringUtils.equalsIgnoreCase(serverName, "S.MDBSVR")))
/*     */       {
/* 175 */         queueName = "ibs/jms/" + serverName;
/*     */       }
/*     */ 
/* 178 */       invoke(msg, factoryName, queueName);
/*     */     }
/*     */     else
/*     */     {
/* 182 */       String jndi = "ibs/ejb/" + serverName;
/* 183 */       invoke(msg, jndi);
/*     */     }
/* 185 */     msg.clearHead();
/* 186 */     msg = null;
/* 187 */     etf = null;
/*     */   }
/*     */ }