/*    */ package com.hisun.jms;
/*    */ 
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.message.HiMessage;
/*    */ import com.hisun.util.HiServiceLocator;
/*    */ import javax.jms.BytesMessage;
/*    */ import javax.jms.Connection;
/*    */ import javax.jms.ConnectionFactory;
/*    */ import javax.jms.Destination;
/*    */ import javax.jms.JMSException;
/*    */ import javax.jms.MessageProducer;
/*    */ import javax.jms.Session;
/*    */ 
/*    */ class HiSender
/*    */ {
/*    */   public void send(String connectionFactoryName, String destinationName, HiMessage mess)
/*    */     throws HiException
/*    */   {
/* 19 */     Connection connection = null;
/*    */     try
/*    */     {
/* 22 */       ConnectionFactory factory = HiServiceLocator.getInstance().getConnectionFactory(connectionFactoryName);
/*    */ 
/* 25 */       Destination destination = (Destination)HiServiceLocator.getInstance().lookup(destinationName);
/*    */ 
/* 29 */       connection = factory.createConnection();
/* 30 */       Session session = connection.createSession(false, 1);
/*    */ 
/* 32 */       MessageProducer sender = session.createProducer(destination);
/*    */ 
/* 34 */       BytesMessage message = session.createBytesMessage();
/* 35 */       sender.send(message);
/*    */     }
/*    */     catch (Exception e)
/*    */     {
/* 39 */       e.printStackTrace();
/*    */     }
/*    */     finally
/*    */     {
/* 43 */       if (connection != null)
/*    */       {
/*    */         try
/*    */         {
/* 47 */           connection.close();
/*    */         }
/*    */         catch (JMSException e)
/*    */         {
/*    */         }
/*    */       }
/*    */     }
/*    */   }
/*    */ }