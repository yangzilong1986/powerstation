/*     */ package com.hisun.handler;
/*     */ 
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.framework.event.IServerDestroyListener;
/*     */ import com.hisun.framework.event.IServerInitListener;
/*     */ import com.hisun.framework.event.ServerEvent;
/*     */ import com.hisun.hilog4j.HiLog;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiETF;
/*     */ import com.hisun.message.HiMessage;
/*     */ import com.hisun.message.HiMessageContext;
/*     */ import com.hisun.pubinterface.IHandler;
/*     */ import com.hisun.register.HiRegisterService;
/*     */ import com.hisun.register.HiServiceObject;
/*     */ import com.hisun.util.HiJMSHelper;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class HiMonHandler
/*     */   implements IHandler, IServerInitListener, IServerDestroyListener
/*     */ {
/*     */   private HiJMSHelper jmsHelper;
/*     */   private String factoryName;
/*     */   private String queueName;
/*     */ 
/*     */   public HiMonHandler()
/*     */   {
/*  28 */     this.jmsHelper = new HiJMSHelper();
/*     */   }
/*     */ 
/*     */   public void setServiceInfo(HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/*  40 */     HiMessage msg = ctx.getCurrentMsg();
/*  41 */     if (!(msg.hasHeadItem("STC")))
/*  42 */       return;
/*  43 */     HiServiceObject service = HiRegisterService.getService(msg.getHeadItem("STC"));
/*  44 */     if (service.isMonSwitch()) {
/*  45 */       msg.setHeadItem("MON", "1");
/*     */     }
/*     */ 
/*  48 */     if ((!(msg.hasHeadItem("STF"))) && (service.getLogLevel() != null))
/*  49 */       msg.setHeadItem("STF", service.getLogLevel());
/*     */   }
/*     */ 
/*     */   public void dump(HiMessageContext ctx) throws HiException
/*     */   {
/*  54 */     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
/*  55 */     if (log.isInfoEnabled()) {
/*  56 */       log.info("=========DUMP MSG begin =====");
/*  57 */       log.info(ctx.getCurrentMsg());
/*  58 */       log.info("=========DUMP MSG end =====");
/*     */     }
/*     */   }
/*     */ 
/*     */   public void process(HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/*  69 */     HiMessage msg = ctx.getCurrentMsg();
/*     */ 
/*  71 */     if (!(StringUtils.equals(msg.getHeadItem("MON"), "1"))) {
/*  72 */       return;
/*     */     }
/*     */ 
/*  75 */     if (msg.hasHeadItem("STC"))
/*     */     {
/*  77 */       msg.getETFBody().setChildValue("MonCod", msg.getHeadItem("STC"));
/*     */     }
/*     */ 
/*  80 */     this.jmsHelper.sendMessage(msg);
/*  81 */     Logger log = HiLog.getLogger(msg);
/*  82 */     if (log.isInfoEnabled())
/*  83 */       log.info("[" + msg.getHeadItem("STC") + "]发送异步消息成功");
/*     */   }
/*     */ 
/*     */   public void setQueueName(String queueName)
/*     */   {
/*  88 */     this.queueName = queueName;
/*     */   }
/*     */ 
/*     */   public void setFactoryName(String factoryName) {
/*  92 */     this.factoryName = factoryName;
/*     */   }
/*     */ 
/*     */   public void serverInit(ServerEvent arg0) throws HiException
/*     */   {
/*     */   }
/*     */ 
/*     */   public void serverDestroy(ServerEvent arg0) throws HiException {
/* 100 */     this.jmsHelper.destory();
/*     */   }
/*     */ }