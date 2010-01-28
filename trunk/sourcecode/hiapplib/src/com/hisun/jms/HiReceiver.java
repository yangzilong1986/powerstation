 package com.hisun.jms;
 
 import com.hisun.exception.HiException;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import com.hisun.util.HiServiceLocator;
 import com.hisun.util.HiThreadPool;
 import javax.jms.BytesMessage;
 import javax.jms.Message;
 import javax.jms.MessageListener;
 import javax.jms.Queue;
 import javax.jms.QueueConnection;
 import javax.jms.QueueConnectionFactory;
 import javax.jms.QueueReceiver;
 import javax.jms.QueueSession;
 import javax.jms.TextMessage;
 
 public class HiReceiver
   implements MessageListener
 {
   QueueConnection connection = null;
 
   QueueSession session = null;
 
   QueueReceiver receiver = null;
   private HiJMSReceiverListener listener;
   protected HiThreadPool tp = HiThreadPool.createThreadPool();
 
   public void receive(String connectionFactoryName, String destinationName)
     throws HiException
   {
     try
     {
       QueueConnectionFactory factory = HiServiceLocator.getInstance().getQueueConnectionFactory(connectionFactoryName);
 
       Queue queue = HiServiceLocator.getInstance().getQueue(destinationName);
 
       this.connection = factory.createQueueConnection();
       this.session = this.connection.createQueueSession(false, 1);
       this.receiver = this.session.createReceiver(queue);
 
       this.receiver.setMessageListener(this);
     }
     catch (Exception e)
     {
       e.printStackTrace();
       try
       {
         this.session.close();
       }
       catch (Exception ex)
       {
       }
       try
       {
         this.receiver.close();
       }
       catch (Exception ex)
       {
       }
       try
       {
         this.connection.close();
       }
       catch (Exception ex)
       {
       }
       throw new HiException("212009", "JMS", e);
     }
   }
 
   public void start(HiJMSReceiverListener listener)
     throws HiException
   {
     try
     {
       this.listener = listener;
       this.connection.start();
     }
     catch (Exception ex)
     {
       throw new HiException("212009", "JMS", ex);
     }
   }
 
   public void destory()
   {
     try
     {
       this.session.close();
     }
     catch (Exception ex)
     {
     }
     try
     {
       this.receiver.close();
     }
     catch (Exception ex)
     {
     }
     try
     {
       this.connection.close();
     }
     catch (Exception ex)
     {
     }
   }
 
   public void onMessage(Message message)
   {
     try
     {
       byte[] data = null;
       if (message instanceof BytesMessage)
       {
         BytesMessage bm = (BytesMessage)message;
         data = new byte[(int)bm.getBodyLength()];
         bm.readBytes(data);
       }
       else if (message instanceof TextMessage)
       {
         String text = ((TextMessage)message).getText();
         data = text.getBytes();
       }
       HiMessage msg = this.listener.getHiMessage();
       HiMessageContext ctx = new HiMessageContext();
       ctx.setCurrentMsg(msg);
       HiMessageContext.setCurrentMessageContext(ctx);
       msg.setBody(data);
       msg.setHeadItem("STM", new Long(System.currentTimeMillis()));
       this.tp.execute(new HiPoolJMS(ctx, this.listener));
     }
     catch (Exception e)
     {
       e.printStackTrace();
     }
   }
 }