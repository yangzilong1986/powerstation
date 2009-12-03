/*     */ package com.hisun.engine.invoke.impl;
/*     */ 
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.hilog4j.HiLog;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiContext;
/*     */ import com.hisun.message.HiETF;
/*     */ import com.hisun.message.HiMessage;
/*     */ import com.hisun.message.HiMessageContext;
/*     */ import com.hisun.util.HiParaParser;
/*     */ import java.util.HashMap;
/*     */ import org.dom4j.Element;
/*     */ 
/*     */ public class HiApplicationCTLImpl extends HiAbstractApplication
/*     */ {
/*  26 */   private final Logger logger = (Logger)HiContext.getCurrentContext().getProperty("SVR.log");
/*     */   private String strAppName;
/*     */   private String strCode;
/*     */   private String strTrace;
/*     */ 
/*     */   public HiApplicationCTLImpl()
/*     */     throws HiException
/*     */   {
/*  19 */     this.context = HiContext.createAndPushContext();
/*     */   }
/*     */ 
/*     */   public String getAppCode()
/*     */   {
/*  51 */     return this.strCode;
/*     */   }
/*     */ 
/*     */   public String getAppName()
/*     */   {
/*  61 */     return this.strAppName;
/*     */   }
/*     */ 
/*     */   public HiTransactionCTLImpl getTransaction(String strCode) {
/*  65 */     if (this.logger.isDebugEnabled()) {
/*  66 */       this.logger.debug("getTransaction(String) - start");
/*     */     }
/*     */ 
/*  69 */     HiTransactionCTLImpl returnHiTransactionCTLImpl = (HiTransactionCTLImpl)getTranMap().get(strCode);
/*     */ 
/*  72 */     if (this.logger.isDebugEnabled()) {
/*  73 */       this.logger.debug("getTransaction(String) - end");
/*     */     }
/*  75 */     return returnHiTransactionCTLImpl;
/*     */   }
/*     */ 
/*     */   public boolean isTrace()
/*     */   {
/*  85 */     return (!("yes".equalsIgnoreCase(this.strTrace)));
/*     */   }
/*     */ 
/*     */   public void setCode(String strCode)
/*     */   {
/*  91 */     if (this.logger.isDebugEnabled()) {
/*  92 */       this.logger.debug("setCode(String) - start");
/*     */     }
/*     */ 
/*  95 */     this.strCode = strCode;
/*  96 */     this.context.setProperty("app_code", strCode);
/*     */ 
/*  98 */     if (this.logger.isDebugEnabled())
/*  99 */       this.logger.debug("setCode(String) - end");
/*     */   }
/*     */ 
/*     */   public void setName(String strAppName)
/*     */   {
/* 104 */     if (this.logger.isDebugEnabled()) {
/* 105 */       this.logger.debug("setName(String) - start");
/*     */     }
/*     */ 
/* 108 */     this.strAppName = strAppName;
/* 109 */     this.context.setId(HiContext.APP_PRE + this.strAppName);
/* 110 */     this.context.setProperty("app_name", strAppName);
/*     */ 
/* 112 */     if (this.logger.isDebugEnabled())
/* 113 */       this.logger.debug("setName(String) - end");
/*     */   }
/*     */ 
/*     */   public void setTrace(String strTrace)
/*     */   {
/* 118 */     if (this.logger.isDebugEnabled()) {
/* 119 */       this.logger.debug("setTrace(String) - start");
/*     */     }
/*     */ 
/* 122 */     this.strTrace = strTrace;
/* 123 */     this.context.setProperty("app_log_level", strTrace);
/*     */ 
/* 125 */     if (this.logger.isDebugEnabled())
/* 126 */       this.logger.debug("setTrace(String) - end");
/*     */   }
/*     */ 
/*     */   public void process(HiMessageContext messContext) throws HiException
/*     */   {
/* 131 */     HiMessage mess = messContext.getCurrentMsg();
/* 132 */     String strCode = mess.getHeadItem("STC");
/* 133 */     Logger log = HiLog.getLogger(mess);
/* 134 */     if (log.isDebugEnabled()) {
/* 135 */       log.debug("strCode====" + strCode);
/*     */     }
/* 137 */     mess.setHeadItem("APP", this.strAppName);
/*     */ 
/* 139 */     mess.getETFBody().setChildValue("TXN_CD", strCode);
/* 140 */     mess.getETFBody().setChildValue("APP_CLS", getAppCode());
/*     */ 
/* 143 */     HiTransactionCTLImpl tranAction = getTransaction(strCode);
/* 144 */     log.debug("HiTransactionCTLImpl=" + tranAction.getClass());
/* 145 */     HiProcess.process(tranAction, messContext);
/*     */ 
/* 147 */     if (this.logger.isDebugEnabled())
/* 148 */       this.logger.debug("process(HiMessageContext) - end ");
/*     */   }
/*     */ 
/*     */   public void loadAfter() throws HiException
/*     */   {
/* 153 */     if (this.logger.isDebugEnabled()) {
/* 154 */       this.logger.debug("HiAbstractApplication:loadAfter() - start");
/*     */     }
/*     */ 
/* 157 */     Element element = (Element)this.context.getProperty("CONFIGDECLARE", "BUSCFG");
/*     */ 
/* 159 */     if (element != null) {
/* 160 */       HiParaParser.setAppParam(this.context, this.strAppName, element);
/* 161 */       HiContext ctx = this.context.getFirstChild();
/* 162 */       for (; ctx != null; ctx = ctx.getNextBrother()) {
/* 163 */         String code = ctx.getStrProp("trans_code");
/* 164 */         HiParaParser.setTrnParam(ctx, this.strAppName, code, element);
/*     */       }
/*     */     }
/* 167 */     element = null;
/*     */ 
/* 183 */     super.popOwnerContext();
/*     */ 
/* 185 */     if (this.logger.isDebugEnabled())
/* 186 */       this.logger.debug("HiAbstractApplication:loadAfter() - end");
/*     */   }
/*     */ }