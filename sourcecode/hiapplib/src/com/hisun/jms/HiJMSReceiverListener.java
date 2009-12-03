/*    */ package com.hisun.jms;
/*    */ 
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.framework.event.ServerEvent;
/*    */ import com.hisun.framework.imp.HiAbstractListener;
/*    */ import com.hisun.hilog4j.HiLog;
/*    */ import com.hisun.hilog4j.Logger;
/*    */ import com.hisun.message.HiMessage;
/*    */ 
/*    */ public class HiJMSReceiverListener extends HiAbstractListener
/*    */ {
/*    */   private String factoryName;
/*    */   private String queueName;
/* 39 */   private HiReceiver receiver = null;
/*    */ 
/* 41 */   public static final Logger log = HiLog.getLogger("jms.trc");
/*    */ 
/*    */   public void setQueueName(String queueName)
/*    */   {
/* 25 */     this.queueName = queueName;
/*    */   }
/*    */ 
/*    */   public void setFactoryName(String factoryName)
/*    */   {
/* 30 */     this.factoryName = factoryName;
/*    */   }
/*    */ 
/*    */   public HiMessage getHiMessage()
/*    */   {
/* 35 */     HiMessage mess = super.getHiMessage();
/* 36 */     return mess;
/*    */   }
/*    */ 
/*    */   public void serverInit(ServerEvent arg0)
/*    */     throws HiException
/*    */   {
/* 45 */     this.receiver = new HiReceiver();
/* 46 */     this.receiver.receive(this.factoryName, this.queueName);
/*    */   }
/*    */ 
/*    */   public void serverStart(ServerEvent arg0) throws HiException {
/* 50 */     this.receiver.start(this);
/*    */   }
/*    */ 
/*    */   public void serverStop(ServerEvent arg0)
/*    */     throws HiException
/*    */   {
/*    */   }
/*    */ 
/*    */   public void serverDestroy(ServerEvent arg0) throws HiException
/*    */   {
/* 60 */     this.receiver.destory();
/*    */   }
/*    */ 
/*    */   public void serverPause(ServerEvent arg0)
/*    */   {
/*    */   }
/*    */ 
/*    */   public void serverResume(ServerEvent arg0)
/*    */   {
/*    */   }
/*    */ }