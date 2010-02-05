 package com.hisun.server.manage.servlet;
 
 import com.hisun.deploy.HiDeploymentHelper;
 import com.hisun.exception.HiException;
 import com.hisun.message.HiETF;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiXmlETF;
 import com.hisun.util.HiJMSHelper;
 import com.hisun.util.HiServiceLocator;
 import java.io.PrintStream;
 import java.lang.reflect.InvocationTargetException;
 import javax.ejb.EJBHome;
 import org.apache.commons.beanutils.MethodUtils;
 import org.apache.commons.lang.StringUtils;
 
 public class HiClientUtil
 {
   public static void start(String serverName)
     throws HiException
   {
     HiMessage msg = getMessage();
     msg.setHeadItem("CMD", "start");
     msg.getETFBody().setChildValue("SERVER", serverName);
     String jndi = "ejb/" + serverName;
     invoke(msg, jndi);
   }
 
   public static void stop(String serverName) throws HiException {
     HiMessage msg = getMessage();
     msg.setHeadItem("CMD", "stop");
     msg.getETFBody().setChildValue("SERVER", serverName);
     String jndi = "ejb/" + serverName;
     invoke(msg, jndi);
   }
 
   public static void deploy(String serverName) throws HiException {
     HiMessage msg = getMessage();
     msg.setHeadItem("CMD", "deploy");
     msg.getETFBody().setChildValue("SERVER", serverName);
     String jndi = "ejb/" + serverName;
     invoke(msg, jndi);
   }
 
   public static void undeploy(String serverName) throws HiException {
     HiMessage msg = getMessage();
     msg.setHeadItem("CMD", "undeploy");
     msg.getETFBody().setChildValue("SERVER", serverName);
     String jndi = "ejb/" + serverName;
     invoke(msg, jndi);
   }
 
   public static void pause(String serverName) throws HiException {
     HiMessage msg = getMessage();
     msg.setHeadItem("CMD", "pause");
     msg.getETFBody().setChildValue("SERVER", serverName);
     String jndi = "ejb/" + serverName;
     invoke(msg, jndi);
   }
 
   public static void redeploy(String serverName) throws HiException {
     HiMessage msg = getMessage();
     msg.setHeadItem("CMD", "redeploy");
     msg.getETFBody().setChildValue("SERVER", serverName);
     String jndi = "ejb/" + serverName;
     invoke(msg, jndi);
   }
 
   public static void resume(String serverName) throws HiException {
     HiMessage msg = getMessage();
     msg.setHeadItem("CMD", "resume");
     msg.getETFBody().setChildValue("SERVER", serverName);
     String jndi = "ejb/" + serverName;
     invoke(msg, jndi);
   }
 
   private static void invoke(HiMessage msg, String jndi)
     throws HiException
   {
     HiServiceLocator locator = HiServiceLocator.getInstance();
     EJBHome home = locator.getRemoteHome(jndi, EJBHome.class);
     try
     {
       Object o = MethodUtils.invokeExactMethod(home, "create", null);
       msg = (HiMessage)MethodUtils.invokeExactMethod(o, "manage", msg);
     } catch (NoSuchMethodException e) {
       e.printStackTrace();
     } catch (IllegalAccessException e) {
       e.printStackTrace();
     }
     catch (InvocationTargetException e) {
       e.getTargetException().printStackTrace();
     }
 
     if (StringUtils.equalsIgnoreCase(msg.getStatus(), "E")) {
       String code = msg.getHeadItem("SSC");
       throw new HiException("215027", code + ":" + msg.getHeadItem("SEM"));
     }
   }
 
   private static HiMessage getMessage() {
     HiMessage msg = new HiMessage("Client", "CMsgType");
     HiXmlETF etf = new HiXmlETF();
     msg.setBody(etf);
     return msg;
   }
 
   private static void invoke(String method, String serverName) throws HiException
   {
     HiMessage msg = new HiMessage("Client", "CMsgType");
     HiXmlETF etf = new HiXmlETF();
     msg.setBody(etf);
     msg.setHeadItem("CMD", method);
     etf.setChildValue("SERVER", serverName);
     String jndi = "ejb/" + serverName;
     invoke(msg, jndi);
     msg.clearHead();
     msg = null;
     etf = null;
   }
 
   private static void invoke(HiMessage msg, String factoryName, String queueName)
     throws HiException
   {
     HiJMSHelper jmsHelper = new HiJMSHelper();
 
     jmsHelper.initialize(factoryName, queueName);
     try {
       jmsHelper.sendMessage(msg, 9);
     } finally {
       jmsHelper.destory();
     }
   }
 
   public static void invoke(String method, String serverName, String groupName, String configFile, String type)
     throws HiException
   {
     if ((StringUtils.equalsIgnoreCase(method, "install")) || (StringUtils.equalsIgnoreCase(method, "uninstall")))
     {
       System.out.println("HiClientUtil: run deployment start...");
       HiDeploymentHelper.deployManage(method, serverName);
       return;
     }
 
     HiMessage msg = new HiMessage("Client", "SYSMNG");
     HiXmlETF etf = new HiXmlETF();
     msg.setBody(etf);
     msg.setHeadItem("CMD", method);
     etf.setChildValue("APP_NM", groupName);
     etf.setChildValue("SERVER", serverName);
     etf.setChildValue("CONFIG_FILE", configFile);
 
     if (StringUtils.equalsIgnoreCase(type, "jms"))
     {
       String factoryName = HiSYSCFG.getInstance().getProperty("_QUEUE_FACTORY");
       String queueName = HiSYSCFG.getInstance().getProperty("_QUEUE");
 
       if (!(StringUtils.equalsIgnoreCase(serverName, "S.MDBSVR")))
       {
         queueName = "ibs/jms/" + serverName;
       }
 
       invoke(msg, factoryName, queueName);
     }
     else
     {
       String jndi = "ibs/ejb/" + serverName;
       invoke(msg, jndi);
     }
     msg.clearHead();
     msg = null;
     etf = null;
   }
 }