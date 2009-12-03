/*    */ package com.hisun.dispatcher;
/*    */ 
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.hilog4j.HiLog;
/*    */ import com.hisun.hilog4j.Logger;
/*    */ import com.hisun.message.HiContext;
/*    */ import com.hisun.message.HiMessage;
/*    */ import com.hisun.util.HiJMSHelper;
/*    */ import com.hisun.util.HiStringManager;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ 
/*    */ public class HiJMSProcess
/*    */ {
/* 22 */   private static boolean initialized = false;
/* 23 */   private static Logger log1 = HiLog.getLogger("SYS.trc");
/* 24 */   private static HiStringManager sm = HiStringManager.getManager();
/* 25 */   private static HiJMSHelper jmsHelper = new HiJMSHelper();
/*    */ 
/*    */   private static void init()
/*    */     throws HiException
/*    */   {
/* 36 */     if (log1.isDebugEnabled()) {
/* 37 */       log1.debug("init() - start");
/*    */     }
/*    */ 
/* 40 */     HiContext ctx = HiContext.getCurrentContext();
/* 41 */     String factoryName = ctx.getStrProp("@PARA", "_QUEUE_FACTORY");
/*    */ 
/* 43 */     if (StringUtils.isEmpty(factoryName)) {
/* 44 */       throw new HiException("212008", "_QUEUE_FACTORY");
/*    */     }
/*    */ 
/* 48 */     if (log1.isDebugEnabled()) {
/* 49 */       log1.debug(sm.getString("HiJmsHelper.initialize.QueueFactory", factoryName));
/*    */     }
/*    */ 
/* 52 */     String queueName = ctx.getStrProp("@PARA", "_QUEUE");
/*    */ 
/* 55 */     if (StringUtils.isEmpty(queueName)) {
/* 56 */       throw new HiException("212008", "_QUEUE");
/*    */     }
/*    */ 
/* 60 */     if (log1.isDebugEnabled()) {
/* 61 */       log1.debug(sm.getString("HiJmsHelper.initialize.Queue", queueName));
/*    */     }
/* 63 */     jmsHelper.initialize(factoryName, queueName);
/* 64 */     initialized = true;
/*    */ 
/* 66 */     if (log1.isDebugEnabled())
/* 67 */       log1.debug("HiJMSProcess.init() - end");
/*    */   }
/*    */ 
/*    */   public static void sendMessage(HiMessage message)
/*    */     throws HiException
/*    */   {
/* 79 */     Logger log2 = HiLog.getLogger(message);
/*    */ 
/* 81 */     if (log2.isDebugEnabled()) {
/* 82 */       log2.debug("sendMessage(HiMessage) - start");
/*    */     }
/*    */ 
/* 85 */     if (!(initialized)) {
/* 86 */       init();
/*    */     }
/*    */ 
/* 89 */     jmsHelper.sendMessage(message);
/*    */ 
/* 91 */     if (log2.isDebugEnabled())
/* 92 */       log2.debug("sendMessage(HiMessage) - end");
/*    */   }
/*    */ 
/*    */   public static void destroy()
/*    */   {
/* 97 */     jmsHelper.destory();
/*    */   }
/*    */ }