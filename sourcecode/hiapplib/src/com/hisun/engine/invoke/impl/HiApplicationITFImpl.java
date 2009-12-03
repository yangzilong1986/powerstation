/*     */ package com.hisun.engine.invoke.impl;
/*     */ 
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.hilog4j.HiLog;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiContext;
/*     */ import com.hisun.message.HiMessage;
/*     */ import com.hisun.message.HiMessageContext;
/*     */ import com.hisun.util.HiStringManager;
/*     */ import java.util.HashMap;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class HiApplicationITFImpl extends HiAbstractApplication
/*     */ {
/*  26 */   private final Logger logger = (Logger)HiContext.getCurrentContext().getProperty("SVR.log");
/*     */ 
/*  29 */   private final HiStringManager sm = HiStringManager.getManager();
/*     */ 
/*  31 */   private String rootName = "ROOT";
/*     */   private String strAppName;
/*     */   private String strObjServerName;
/*     */   private String strTrace;
/*  59 */   private HiGetTxnCode txnCode = null;
/*     */ 
/*     */   public HiApplicationITFImpl() throws HiException
/*     */   {
/*  63 */     this.context = HiContext.createAndPushContext();
/*     */   }
/*     */ 
/*     */   public String getAppName()
/*     */   {
/*  74 */     return this.strAppName;
/*     */   }
/*     */ 
/*     */   public String getObjServerName()
/*     */   {
/*  85 */     return this.strObjServerName;
/*     */   }
/*     */ 
/*     */   public HiTransactionITFImpl getTransaction(String strCode)
/*     */   {
/*  90 */     HashMap map = getTranMap();
/*     */ 
/*  92 */     HiTransactionITFImpl tran = (HiTransactionITFImpl)getTranMap().get(strCode);
/*     */ 
/*  95 */     return tran;
/*     */   }
/*     */ 
/*     */   private HiGetTxnCode getTxnCode()
/*     */   {
/* 100 */     return this.txnCode;
/*     */   }
/*     */ 
/*     */   public void beforeProcess(HiMessageContext messContext)
/*     */     throws HiException
/*     */   {
/* 106 */     Logger log = HiLog.getLogger(messContext.getCurrentMsg());
/*     */ 
/* 112 */     HiMessage mess = messContext.getCurrentMsg();
/*     */ 
/* 130 */     super.beforeProcess(messContext);
/*     */ 
/* 134 */     String strCode = mess.getHeadItem("STC");
/*     */ 
/* 137 */     if (!(StringUtils.isEmpty(strCode))) {
/*     */       return;
/*     */     }
/* 140 */     HiGetTxnCode txdCode = getTxnCode();
/* 141 */     if (txdCode == null)
/*     */     {
/* 143 */       throw new HiException("213339");
/*     */     }
/* 145 */     HiProcess.process(txdCode, messContext);
/*     */   }
/*     */ 
/*     */   public void afterProcess(HiMessageContext messContext)
/*     */     throws HiException
/*     */   {
/* 153 */     HiMessage mess = messContext.getCurrentMsg();
/* 154 */     String strType = mess.getHeadItem("SCH");
/* 155 */     Logger log = HiLog.getLogger(mess);
/* 156 */     if (log.isDebugEnabled())
/*     */     {
/* 158 */       log.debug("HiApplicationITFImpl:afterProcess(HiMessageContext) - start");
/*     */     }
/*     */ 
/* 177 */     super.afterProcess(messContext);
/*     */ 
/* 180 */     if (!(log.isDebugEnabled()))
/*     */       return;
/* 182 */     log.debug("HiApplicationITFImpl:afterProcess(HiMessageContext) - end");
/*     */   }
/*     */ 
/*     */   public void process(HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 189 */     HiMessage msg = ctx.getCurrentMsg();
/*     */ 
/* 191 */     Logger log = HiLog.getLogger(msg);
/* 192 */     if (log.isDebugEnabled())
/*     */     {
/* 194 */       log.debug("HiApplicationITFImpl:process(HiMessageContext) - start");
/*     */     }
/*     */ 
/* 197 */     String strCode = msg.getHeadItem("STC");
/* 198 */     msg.setHeadItem("APP", this.strAppName);
/* 199 */     ctx.setProperty("TXN_CD", strCode);
/*     */ 
/* 204 */     HiTransactionITFImpl tranAction = getTransaction(strCode);
/* 205 */     if (tranAction == null)
/*     */     {
/* 207 */       throw new HiException("213322", strCode);
/*     */     }
/*     */ 
/* 210 */     HiProcess.process(tranAction, ctx);
/*     */ 
/* 212 */     if (!(log.isDebugEnabled()))
/*     */       return;
/* 214 */     log.debug("HiApplicationITFImpl:process(HiMessageContext) - end");
/*     */   }
/*     */ 
/*     */   public void setName(String strAppName)
/*     */   {
/* 220 */     if (this.logger.isDebugEnabled())
/*     */     {
/* 222 */       this.logger.debug("setName(String) - start");
/*     */     }
/*     */ 
/* 225 */     this.strAppName = strAppName;
/* 226 */     this.context.setId(HiContext.APP_PRE + this.strAppName);
/* 227 */     this.context.setProperty("app_name", strAppName);
/* 228 */     if (!(this.logger.isDebugEnabled()))
/*     */       return;
/* 230 */     this.logger.debug("setName(String) - end");
/*     */   }
/*     */ 
/*     */   public void setRoot_name(String strRootName)
/*     */   {
/* 236 */     if (this.logger.isDebugEnabled())
/*     */     {
/* 238 */       this.logger.debug("setRoot_name(String) - start");
/*     */     }
/*     */ 
/* 241 */     if (StringUtils.isNotEmpty(strRootName)) {
/* 242 */       this.rootName = strRootName;
/*     */     }
/* 244 */     if (!(this.logger.isDebugEnabled()))
/*     */       return;
/* 246 */     this.logger.debug("setRoot_name(String) - end");
/*     */   }
/*     */ 
/*     */   public String getRootName()
/*     */   {
/* 252 */     return this.rootName;
/*     */   }
/*     */ 
/*     */   public void setServer(String strAppServerName)
/*     */   {
/* 257 */     if (this.logger.isDebugEnabled())
/*     */     {
/* 259 */       this.logger.debug("setServer(String) - start");
/*     */     }
/*     */ 
/* 262 */     this.strObjServerName = strAppServerName;
/*     */ 
/* 264 */     this.context.setProperty("app_server", this.strObjServerName);
/*     */ 
/* 267 */     if (!(this.logger.isDebugEnabled()))
/*     */       return;
/* 269 */     this.logger.debug("setServer(String) - end");
/*     */   }
/*     */ 
/*     */   public void setTxnCode(HiGetTxnCode txnCode)
/*     */   {
/* 287 */     if (this.logger.isDebugEnabled())
/*     */     {
/* 289 */       this.logger.debug("setTxnCode(HiGetTxnCode) - start");
/*     */     }
/*     */ 
/* 292 */     this.txnCode = txnCode;
/*     */ 
/* 294 */     if (!(this.logger.isDebugEnabled()))
/*     */       return;
/* 296 */     this.logger.debug("setTxnCode(HiGetTxnCode) - end");
/*     */   }
/*     */ 
/*     */   public void loadAfter()
/*     */     throws HiException
/*     */   {
/* 302 */     if (this.logger.isDebugEnabled())
/*     */     {
/* 304 */       this.logger.debug("HiAbstractApplication:loadAfter() - start");
/*     */     }
/*     */ 
/* 307 */     super.popOwnerContext();
/*     */ 
/* 309 */     if (!(this.logger.isDebugEnabled()))
/*     */       return;
/* 311 */     this.logger.debug("HiAbstractApplication:loadAfter() - end");
/*     */   }
/*     */ }