/*    */ package com.hisun.jms;
/*    */ 
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.framework.event.IServerDestroyListener;
/*    */ import com.hisun.framework.event.IServerInitListener;
/*    */ import com.hisun.framework.event.ServerEvent;
/*    */ import com.hisun.message.HiMessageContext;
/*    */ import com.hisun.pubinterface.IHandler;
/*    */ import com.hisun.util.HiJMSHelper;
/*    */ 
/*    */ public class HiJMSSendHandler
/*    */   implements IHandler, IServerInitListener, IServerDestroyListener
/*    */ {
/*    */   private String factoryName;
/*    */   private String queueName;
/*    */   private HiJMSHelper jms;
/*    */ 
/*    */   public void process(HiMessageContext ctx)
/*    */     throws HiException
/*    */   {
/* 32 */     this.jms.sendMessage(ctx);
/*    */   }
/*    */ 
/*    */   public String getFactoryName()
/*    */   {
/* 41 */     return this.factoryName;
/*    */   }
/*    */ 
/*    */   public void setFactoryName(String factoryName)
/*    */   {
/* 50 */     this.factoryName = factoryName;
/*    */   }
/*    */ 
/*    */   public String getQueueName()
/*    */   {
/* 58 */     return this.queueName;
/*    */   }
/*    */ 
/*    */   public void setQueueName(String queueName)
/*    */   {
/* 66 */     this.queueName = queueName;
/*    */   }
/*    */ 
/*    */   public void serverInit(ServerEvent arg0) throws HiException {
/* 70 */     this.jms = new HiJMSHelper();
/* 71 */     this.jms.initialize(getFactoryName(), getQueueName());
/*    */   }
/*    */ 
/*    */   public void serverDestroy(ServerEvent arg0) throws HiException {
/* 75 */     this.jms.destory();
/*    */   }
/*    */ }