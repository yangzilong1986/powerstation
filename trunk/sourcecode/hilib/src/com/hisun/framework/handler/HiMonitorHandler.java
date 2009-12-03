/*    */ package com.hisun.framework.handler;
/*    */ 
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.hilog4j.HiLog;
/*    */ import com.hisun.hilog4j.Logger;
/*    */ import com.hisun.message.HiMessage;
/*    */ import com.hisun.message.HiMessageContext;
/*    */ import com.hisun.pubinterface.IHandler;
/*    */ import com.hisun.util.HiJMSHelper;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ 
/*    */ public class HiMonitorHandler
/*    */   implements IHandler
/*    */ {
/*    */   private String factory;
/*    */   private String queue;
/*    */   private HiJMSHelper jsm;
/*    */ 
/*    */   public void setFactory(String factory)
/*    */   {
/* 25 */     this.factory = factory;
/*    */   }
/*    */ 
/*    */   public void setQueue(String queue)
/*    */   {
/* 30 */     this.queue = queue;
/*    */   }
/*    */ 
/*    */   public void init() throws HiException {
/* 34 */     this.jsm = new HiJMSHelper();
/* 35 */     this.jsm.initialize(this.factory, this.queue);
/*    */   }
/*    */ 
/*    */   public void destory()
/*    */   {
/* 40 */     if (this.jsm != null)
/* 41 */       this.jsm.destory();
/*    */   }
/*    */ 
/*    */   public void process(HiMessageContext ctx) throws HiException {
/* 45 */     HiMessage msg = ctx.getCurrentMsg();
/*    */ 
/* 47 */     if (StringUtils.equals("1", msg.getHeadItem("MON"))) {
/* 48 */       Logger log = HiLog.getLogger(msg);
/* 49 */       if (log.isDebugEnabled()) {
/* 50 */         log.debug("send message to JMS queue!");
/*    */       }
/* 52 */       this.jsm.sendMessage(ctx);
/*    */     }
/*    */   }
/*    */ }