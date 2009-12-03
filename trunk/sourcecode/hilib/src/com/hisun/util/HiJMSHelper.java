/*     */ package com.hisun.util;
/*     */ 
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.message.HiMessage;
/*     */ import com.hisun.message.HiMessageContext;
/*     */ import javax.jms.JMSException;
/*     */ import javax.jms.ObjectMessage;
/*     */ import javax.jms.Queue;
/*     */ import javax.jms.QueueConnection;
/*     */ import javax.jms.QueueConnectionFactory;
/*     */ import javax.jms.QueueSender;
/*     */ import javax.jms.QueueSession;
/*     */ 
/*     */ public class HiJMSHelper
/*     */ {
/*  23 */   private QueueConnection qc = null;
/*  24 */   private QueueSession qs = null;
/*  25 */   private QueueSender qsend = null;
/*     */ 
/*     */   public void initialize(String factoryName, String queueName)
/*     */     throws HiException
/*     */   {
/*     */     try
/*     */     {
/*  38 */       HiServiceLocator locator = HiServiceLocator.getInstance();
/*  39 */       QueueConnectionFactory qcf = locator.getQueueConnectionFactory(factoryName);
/*  40 */       this.qc = qcf.createQueueConnection();
/*  41 */       this.qs = this.qc.createQueueSession(false, 1);
/*  42 */       Queue queue = locator.getQueue(queueName);
/*  43 */       this.qsend = this.qs.createSender(queue);
/*  44 */       this.qc.start(); } catch (JMSException e) {
/*     */       try {
/*  46 */         this.qsend.close(); } catch (Exception ex) { }
/*     */       try { this.qs.close(); } catch (Exception ex) { }
/*     */       try { this.qc.close(); } catch (Exception ex) { }
/*  49 */       throw new HiException("212009", "JMS", e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void destory()
/*     */   {
/*     */     try
/*     */     {
/*  58 */       this.qsend.close(); } catch (Exception ex) { }
/*     */     try { this.qs.close(); } catch (Exception ex) { }
/*     */     try { this.qc.close();
/*     */     }
/*     */     catch (Exception ex)
/*     */     {
/*     */     }
/*     */   }
/*     */ 
/*     */   public void sendMessage(HiMessageContext ctx) throws HiException
/*     */   {
/*     */     try {
/*  70 */       ObjectMessage msg = this.qs.createObjectMessage(ctx.getCurrentMsg());
/*  71 */       this.qsend.send(msg);
/*     */     } catch (Exception e) {
/*  73 */       throw new HiException("212006", "JMS", e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void sendMessage(HiMessageContext ctx, int priority)
/*     */     throws HiException
/*     */   {
/*     */     try
/*     */     {
/*  84 */       ObjectMessage msg = this.qs.createObjectMessage(ctx.getCurrentMsg());
/*  85 */       msg.setJMSPriority(priority);
/*  86 */       this.qsend.send(msg);
/*     */     } catch (Exception e) {
/*  88 */       throw new HiException("212006", "JMS", e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void sendMessage(HiMessage sendMsg)
/*     */     throws HiException
/*     */   {
/*     */     try
/*     */     {
/*  98 */       ObjectMessage msg = this.qs.createObjectMessage(sendMsg);
/*  99 */       this.qsend.send(msg);
/*     */     } catch (Exception e) {
/* 101 */       throw new HiException("212006", "JMS", e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void sendMessage(HiMessage sendMsg, int priority)
/*     */     throws HiException
/*     */   {
/*     */     try
/*     */     {
/* 112 */       ObjectMessage msg = this.qs.createObjectMessage(sendMsg);
/* 113 */       msg.setJMSPriority(priority);
/* 114 */       this.qsend.send(msg);
/*     */     } catch (Exception e) {
/* 116 */       throw new HiException("212006", "JMS", e);
/*     */     }
/*     */   }
/*     */ }