/*    */ package com.hisun.mng.handler;
/*    */ 
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.framework.event.IServerDestroyListener;
/*    */ import com.hisun.framework.event.IServerInitListener;
/*    */ import com.hisun.framework.event.ServerEvent;
/*    */ import com.hisun.hilog4j.Logger;
/*    */ import com.hisun.message.HiETF;
/*    */ import com.hisun.message.HiMessage;
/*    */ import com.hisun.message.HiMessageContext;
/*    */ import com.hisun.pubinterface.IHandler;
/*    */ import com.hisun.register.HiRegisterService;
/*    */ import com.hisun.util.HiJMSHelper;
/*    */ 
/*    */ public class HiMonHandler
/*    */   implements IHandler, IServerInitListener, IServerDestroyListener
/*    */ {
/*    */   private HiJMSHelper jms;
/*    */   private String factoryName;
/*    */   private String queueName;
/*    */   private Logger log;
/*    */ 
/*    */   public void process(HiMessageContext ctx)
/*    */     throws HiException
/*    */   {
/* 26 */     HiMessage msg = ctx.getCurrentMsg();
/* 27 */     String service = msg.getHeadItem("STC");
/*    */ 
/* 29 */     if (HiRegisterService.getMonSwitch(service)) {
/* 30 */       msg.getETFBody().setChildValue("MonCod", service);
/* 31 */       this.jms.sendMessage(ctx);
/* 32 */       if (this.log.isInfoEnabled())
/* 33 */         this.log.info("send msg to monitor:" + service);
/*    */     }
/*    */   }
/*    */ 
/*    */   public void serverInit(ServerEvent event) throws HiException {
/* 38 */     this.jms = new HiJMSHelper();
/* 39 */     this.jms.initialize(this.factoryName, this.queueName);
/*    */   }
/*    */ 
/*    */   public void setFactoryName(String factoryName) {
/* 43 */     this.factoryName = factoryName;
/*    */   }
/*    */ 
/*    */   public void setQueueName(String queueName) {
/* 47 */     this.queueName = queueName;
/*    */   }
/*    */ 
/*    */   public void setLog(Logger log) {
/* 51 */     this.log = log;
/*    */   }
/*    */ 
/*    */   public void serverDestroy(ServerEvent event) throws HiException {
/* 55 */     this.jms.destory();
/*    */   }
/*    */ }