 package com.hisun.jms;
 
 import com.hisun.exception.HiException;
 import com.hisun.message.HiMessage;
 import com.hisun.util.HiServiceLocator;
 import javax.jms.BytesMessage;
 import javax.jms.Connection;
 import javax.jms.ConnectionFactory;
 import javax.jms.Destination;
 import javax.jms.JMSException;
 import javax.jms.MessageProducer;
 import javax.jms.Session;
 
 class HiSender
 {
   public void send(String connectionFactoryName, String destinationName, HiMessage mess)
     throws HiException
   {
     Connection connection = null;
     try
     {
       ConnectionFactory factory = HiServiceLocator.getInstance().getConnectionFactory(connectionFactoryName);
 
       Destination destination = (Destination)HiServiceLocator.getInstance().lookup(destinationName);
 
       connection = factory.createConnection();
       Session session = connection.createSession(false, 1);
 
       MessageProducer sender = session.createProducer(destination);
 
       BytesMessage message = session.createBytesMessage();
       sender.send(message);
     }
     catch (Exception e)
     {
       e.printStackTrace();
     }
     finally
     {
       if (connection != null)
       {
         try
         {
           connection.close();
         }
         catch (JMSException e)
         {
         }
       }
     }
   }
 }