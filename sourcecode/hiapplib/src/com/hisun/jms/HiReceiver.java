/*     */ package com.hisun.jms;
/*     */ 
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.message.HiMessage;
/*     */ import com.hisun.message.HiMessageContext;
/*     */ import com.hisun.util.HiServiceLocator;
/*     */ import com.hisun.util.HiThreadPool;
/*     */ import javax.jms.BytesMessage;
/*     */ import javax.jms.Message;
/*     */ import javax.jms.MessageListener;
/*     */ import javax.jms.Queue;
/*     */ import javax.jms.QueueConnection;
/*     */ import javax.jms.QueueConnectionFactory;
/*     */ import javax.jms.QueueReceiver;
/*     */ import javax.jms.QueueSession;
/*     */ import javax.jms.TextMessage;
/*     */ 
/*     */ public class HiReceiver
/*     */   implements MessageListener
/*     */ {
/*  28 */   QueueConnection connection = null;
/*     */ 
/*  30 */   QueueSession session = null;
/*     */ 
/*  32 */   QueueReceiver receiver = null;
/*     */   private HiJMSReceiverListener listener;
/* 122 */   protected HiThreadPool tp = HiThreadPool.createThreadPool();
/*     */ 
/*     */   public void receive(String connectionFactoryName, String destinationName)
/*     */     throws HiException
/*     */   {
/*     */     try
/*     */     {
/*  39 */       QueueConnectionFactory factory = HiServiceLocator.getInstance().getQueueConnectionFactory(connectionFactoryName);
/*     */ 
/*  44 */       Queue queue = HiServiceLocator.getInstance().getQueue(destinationName);
/*     */ 
/*  48 */       this.connection = factory.createQueueConnection();
/*  49 */       this.session = this.connection.createQueueSession(false, 1);
/*  50 */       this.receiver = this.session.createReceiver(queue);
/*     */ 
/*  52 */       this.receiver.setMessageListener(this);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*  56 */       e.printStackTrace();
/*     */       try
/*     */       {
/*  59 */         this.session.close();
/*     */       }
/*     */       catch (Exception ex)
/*     */       {
/*     */       }
/*     */       try
/*     */       {
/*  66 */         this.receiver.close();
/*     */       }
/*     */       catch (Exception ex)
/*     */       {
/*     */       }
/*     */       try
/*     */       {
/*  73 */         this.connection.close();
/*     */       }
/*     */       catch (Exception ex)
/*     */       {
/*     */       }
/*  78 */       throw new HiException("212009", "JMS", e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void start(HiJMSReceiverListener listener)
/*     */     throws HiException
/*     */   {
/*     */     try
/*     */     {
/*  88 */       this.listener = listener;
/*  89 */       this.connection.start();
/*     */     }
/*     */     catch (Exception ex)
/*     */     {
/*  93 */       throw new HiException("212009", "JMS", ex);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void destory()
/*     */   {
/*     */     try
/*     */     {
/* 101 */       this.session.close();
/*     */     }
/*     */     catch (Exception ex)
/*     */     {
/*     */     }
/*     */     try
/*     */     {
/* 108 */       this.receiver.close();
/*     */     }
/*     */     catch (Exception ex)
/*     */     {
/*     */     }
/*     */     try
/*     */     {
/* 115 */       this.connection.close();
/*     */     }
/*     */     catch (Exception ex)
/*     */     {
/*     */     }
/*     */   }
/*     */ 
/*     */   public void onMessage(Message message)
/*     */   {
/*     */     try
/*     */     {
/* 128 */       byte[] data = null;
/* 129 */       if (message instanceof BytesMessage)
/*     */       {
/* 131 */         BytesMessage bm = (BytesMessage)message;
/* 132 */         data = new byte[(int)bm.getBodyLength()];
/* 133 */         bm.readBytes(data);
/*     */       }
/* 135 */       else if (message instanceof TextMessage)
/*     */       {
/* 137 */         String text = ((TextMessage)message).getText();
/* 138 */         data = text.getBytes();
/*     */       }
/* 140 */       HiMessage msg = this.listener.getHiMessage();
/* 141 */       HiMessageContext ctx = new HiMessageContext();
/* 142 */       ctx.setCurrentMsg(msg);
/* 143 */       HiMessageContext.setCurrentMessageContext(ctx);
/* 144 */       msg.setBody(data);
/* 145 */       msg.setHeadItem("STM", new Long(System.currentTimeMillis()));
/* 146 */       this.tp.execute(new HiPoolJMS(ctx, this.listener));
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 150 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ }