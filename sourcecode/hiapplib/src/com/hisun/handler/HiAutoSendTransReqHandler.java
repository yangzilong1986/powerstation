/*     */ package com.hisun.handler;
/*     */ 
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.framework.HiDefaultServer;
/*     */ import com.hisun.framework.event.IServerInitListener;
/*     */ import com.hisun.framework.event.IServerStartListener;
/*     */ import com.hisun.framework.event.IServerStopListener;
/*     */ import com.hisun.framework.event.ServerEvent;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiETF;
/*     */ import com.hisun.message.HiETFFactory;
/*     */ import com.hisun.message.HiMessage;
/*     */ import com.hisun.message.HiMessageContext;
/*     */ import com.hisun.util.HiStringManager;
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ public class HiAutoSendTransReqHandler
/*     */   implements IServerInitListener, IServerStartListener, IServerStopListener
/*     */ {
/*     */   private HiStringManager sm;
/*     */   private ArrayList codeParams;
/*     */   private String msgType;
/*     */   private Logger log;
/*     */   private HiDefaultServer server;
/*     */   private Thread thd;
/*     */ 
/*     */   public HiAutoSendTransReqHandler()
/*     */   {
/*  33 */     this.sm = HiStringManager.getManager();
/*     */ 
/*  37 */     this.codeParams = new ArrayList();
/*     */ 
/*  41 */     this.msgType = "PLTIN0";
/*     */   }
/*     */ 
/*     */   public void serverInit(ServerEvent arg0)
/*     */     throws HiException
/*     */   {
/*  50 */     this.log = arg0.getLog();
/*  51 */     this.server = ((HiDefaultServer)arg0.getServer());
/*     */   }
/*     */ 
/*     */   public void setCodeList(String codeList) {
/*  55 */     parseCodeParam(codeList);
/*     */   }
/*     */ 
/*     */   public String getCodeList() {
/*  59 */     return "";
/*     */   }
/*     */ 
/*     */   public void serverStart(ServerEvent arg0) throws HiException {
/*  63 */     this.thd = new Thread(new HiAutoTriggerTrans());
/*  64 */     this.thd.start();
/*     */   }
/*     */ 
/*     */   public String getMsgType() {
/*  68 */     return this.msgType;
/*     */   }
/*     */ 
/*     */   public void setMsgType(String msgType) {
/*  72 */     this.msgType = msgType;
/*     */   }
/*     */ 
/*     */   public void parseCodeParam(String codeParam) {
/*  76 */     String[] tmps1 = codeParam.split("\\|");
/*  77 */     for (int i = 0; i < tmps1.length; ++i) {
/*  78 */       String tmp = tmps1[i];
/*  79 */       int idx1 = tmp.indexOf(63);
/*  80 */       String code = tmp;
/*  81 */       HiETF root = HiETFFactory.createETF();
/*  82 */       if (idx1 != -1) {
/*  83 */         code = tmp.substring(0, idx1);
/*  84 */         String[] tmps2 = tmp.substring(idx1 + 1).split("&");
/*  85 */         for (int j = 0; j < tmps2.length; ++j) {
/*  86 */           idx1 = tmps2[j].indexOf(61);
/*  87 */           root.setChildValue(tmps2[j].substring(0, idx1), tmps2[j].substring(idx1 + 1));
/*     */         }
/*     */       }
/*     */ 
/*  91 */       this.codeParams.add(new HiCodeParam(code, root));
/*     */     }
/*     */   }
/*     */ 
/*     */   public void serverStop(ServerEvent arg0)
/*     */     throws HiException
/*     */   {
/* 163 */     if ((this.thd != null) && (this.thd.isAlive()))
/* 164 */       this.thd.interrupt();
/*     */   }
/*     */ 
/*     */   class HiCodeParam
/*     */   {
/*     */     String code;
/*     */     HiETF param;
/*     */ 
/*     */     public HiCodeParam(String paramString, HiETF paramHiETF)
/*     */     {
/* 157 */       this.code = paramString;
/* 158 */       this.param = param;
/*     */     }
/*     */   }
/*     */ 
/*     */   class HiAutoTriggerTrans
/*     */     implements Runnable
/*     */   {
/*     */     public void run()
/*     */     {
/* 103 */       while (!(HiAutoSendTransReqHandler.this.server.isRunning())) {
/*     */         try {
/* 105 */           Thread.sleep(1000L);
/*     */         } catch (InterruptedException e) {
/* 107 */           e.printStackTrace();
/* 108 */           return;
/*     */         }
/*     */       }
/*     */ 
/* 112 */       HiMessageContext ctx = new HiMessageContext();
/* 113 */       HiMessageContext.setCurrentMessageContext(ctx);
/* 114 */       for (int i = 0; i < HiAutoSendTransReqHandler.this.codeParams.size(); ++i) {
/* 115 */         if (Thread.currentThread().isInterrupted()) {
/*     */           return;
/*     */         }
/* 118 */         HiAutoSendTransReqHandler.HiCodeParam codeParam = (HiAutoSendTransReqHandler.HiCodeParam)HiAutoSendTransReqHandler.this.codeParams.get(i);
/* 119 */         HiMessage msg = createReqMsg(codeParam.code);
/* 120 */         ctx.setCurrentMsg(msg);
/*     */ 
/* 123 */         msg.setBody(codeParam.param);
/* 124 */         if (HiAutoSendTransReqHandler.this.log.isInfoEnabled())
/* 125 */           HiAutoSendTransReqHandler.this.log.info(HiAutoSendTransReqHandler.this.sm.getString("HiAutoSendTransReqHandler.autotrigger", codeParam.code));
/*     */         try
/*     */         {
/* 128 */           HiAutoSendTransReqHandler.this.server.process(ctx);
/*     */         } catch (Throwable t) {
/* 130 */           HiAutoSendTransReqHandler.this.log.error(HiAutoSendTransReqHandler.this.sm.getString("HiAutoSendTransReqHandler.autotrigger.failure", codeParam.code), t);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/*     */     public HiMessage createReqMsg(String txnCod)
/*     */     {
/* 141 */       HiMessage msg = new HiMessage(HiAutoSendTransReqHandler.this.server.getName(), HiAutoSendTransReqHandler.this.msgType);
/* 142 */       msg.setHeadItem("STM", new Long(System.currentTimeMillis()));
/*     */ 
/* 145 */       msg.setHeadItem("STC", txnCod);
/* 146 */       msg.setHeadItem("SCH", "rq");
/* 147 */       msg.setHeadItem("ECT", "text/etf");
/* 148 */       return msg;
/*     */     }
/*     */   }
/*     */ }