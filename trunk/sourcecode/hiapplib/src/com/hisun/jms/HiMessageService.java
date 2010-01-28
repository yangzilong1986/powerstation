 package com.hisun.jms;
 
 import com.hisun.exception.HiException;
 import com.hisun.util.HiServiceLocator;
 import javax.jms.Connection;
 import javax.jms.ConnectionFactory;
 import javax.jms.Destination;
 import javax.jms.JMSException;
 import javax.jms.MessageConsumer;
 import javax.jms.MessageProducer;
 import javax.jms.Session;
 
 public class HiMessageService
 {
   public MessageProducer getProducer(String connectionFactoryName, String destinationName)
     throws HiException
   {
     try
     {
       ConnectionFactory factory = HiServiceLocator.getInstance().getConnectionFactory(connectionFactoryName);
 
       Destination destination = (Destination)HiServiceLocator.getInstance().lookup(destinationName);
 
       Connection connection = factory.createConnection();
       Session session = connection.createSession(false, 1);
 
       MessageProducer producer = session.createProducer(destination);
       return producer;
     }
     catch (JMSException e)
     {
       throw new HiException(e);
     }
   }
 
   public MessageConsumer getConsumer(String connectionFactoryName, String destinationName)
     throws HiException
   {
     try
     {
       ConnectionFactory factory = HiServiceLocator.getInstance().getConnectionFactory(connectionFactoryName);
 
       Destination destination = (Destination)HiServiceLocator.getInstance().lookup(destinationName);
 
       Connection connection = factory.createConnection();
       Session session = connection.createSession(false, 1);
 
       MessageConsumer consumer = session.createConsumer(destination);
       return consumer;
     }
     catch (JMSException e)
     {
       throw new HiException(e);
     }
   }
 }