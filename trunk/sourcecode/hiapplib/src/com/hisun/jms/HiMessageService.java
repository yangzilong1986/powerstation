/*    */ package com.hisun.jms;
/*    */ 
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.util.HiServiceLocator;
/*    */ import javax.jms.Connection;
/*    */ import javax.jms.ConnectionFactory;
/*    */ import javax.jms.Destination;
/*    */ import javax.jms.JMSException;
/*    */ import javax.jms.MessageConsumer;
/*    */ import javax.jms.MessageProducer;
/*    */ import javax.jms.Session;
/*    */ 
/*    */ public class HiMessageService
/*    */ {
/*    */   public MessageProducer getProducer(String connectionFactoryName, String destinationName)
/*    */     throws HiException
/*    */   {
/*    */     try
/*    */     {
/* 24 */       ConnectionFactory factory = HiServiceLocator.getInstance().getConnectionFactory(connectionFactoryName);
/*    */ 
/* 27 */       Destination destination = (Destination)HiServiceLocator.getInstance().lookup(destinationName);
/*    */ 
/* 31 */       Connection connection = factory.createConnection();
/* 32 */       Session session = connection.createSession(false, 1);
/*    */ 
/* 36 */       MessageProducer producer = session.createProducer(destination);
/* 37 */       return producer;
/*    */     }
/*    */     catch (JMSException e)
/*    */     {
/* 41 */       throw new HiException(e);
/*    */     }
/*    */   }
/*    */ 
/*    */   public MessageConsumer getConsumer(String connectionFactoryName, String destinationName)
/*    */     throws HiException
/*    */   {
/*    */     try
/*    */     {
/* 50 */       ConnectionFactory factory = HiServiceLocator.getInstance().getConnectionFactory(connectionFactoryName);
/*    */ 
/* 52 */       Destination destination = (Destination)HiServiceLocator.getInstance().lookup(destinationName);
/*    */ 
/* 56 */       Connection connection = factory.createConnection();
/* 57 */       Session session = connection.createSession(false, 1);
/*    */ 
/* 61 */       MessageConsumer consumer = session.createConsumer(destination);
/* 62 */       return consumer;
/*    */     }
/*    */     catch (JMSException e)
/*    */     {
/* 66 */       throw new HiException(e);
/*    */     }
/*    */   }
/*    */ }