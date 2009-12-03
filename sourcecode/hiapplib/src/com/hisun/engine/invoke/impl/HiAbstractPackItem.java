/*     */ package com.hisun.engine.invoke.impl;
/*     */ 
/*     */ import com.hisun.engine.HiITFEngineModel;
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.hilog4j.HiLog;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiContext;
/*     */ import com.hisun.message.HiMessage;
/*     */ import com.hisun.util.HiByteBuffer;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public abstract class HiAbstractPackItem extends HiITFEngineModel
/*     */ {
/*     */   private final Logger logger;
/*     */   protected String name;
/*     */ 
/*     */   public HiAbstractPackItem()
/*     */   {
/*  22 */     this.logger = ((Logger)HiContext.getCurrentContext().getProperty("SVR.log"));
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/*  40 */     return this.name;
/*     */   }
/*     */ 
/*     */   public void setName(String name)
/*     */   {
/*  70 */     if (this.logger.isDebugEnabled())
/*     */     {
/*  72 */       this.logger.debug("setName(String) - start");
/*     */     }
/*     */ 
/*  75 */     this.name = name;
/*     */ 
/*  77 */     if (!(this.logger.isDebugEnabled()))
/*     */       return;
/*  79 */     this.logger.debug("setName(String) - end");
/*     */   }
/*     */ 
/*     */   public void loadAfter()
/*     */     throws HiException
/*     */   {
/* 175 */     if (this.logger.isDebugEnabled())
/*     */     {
/* 177 */       this.logger.debug("init() - start");
/*     */     }
/*     */ 
/* 180 */     if (StringUtils.isBlank(this.name))
/*     */     {
/* 182 */       throw new HiException("213144");
/*     */     }
/*     */ 
/* 185 */     if (!(this.logger.isDebugEnabled()))
/*     */       return;
/* 187 */     this.logger.debug("init() - end");
/*     */   }
/*     */ 
/*     */   protected void initMsgState(HiMessage msg, String ect, HiByteBuffer packValue)
/*     */   {
/* 203 */     Logger log = HiLog.getLogger(msg);
/* 204 */     if (log.isDebugEnabled())
/*     */     {
/* 206 */       log.debug("initMsgState(HiMessage, String, String) - start");
/*     */     }
/*     */ 
/* 209 */     msg.setHeadItem("PlainText", packValue);
/* 210 */     msg.setHeadItem("PlainOffset", "0");
/* 211 */     msg.setHeadItem("ECT", ect);
/*     */ 
/* 213 */     if (!(log.isDebugEnabled()))
/*     */       return;
/* 215 */     log.debug("initMsgState(HiMessage, String, String) - end");
/*     */   }
/*     */ 
/*     */   protected void restoreMsgState(HiMessage msg, HiByteBuffer plainBuf, int plainOffset, String msgType)
/*     */   {
/* 228 */     Logger log = HiLog.getLogger(msg);
/* 229 */     if (log.isDebugEnabled())
/*     */     {
/* 231 */       log.debug("restoreMsgState(HiMessage, StringBuffer, int, String) - start");
/*     */     }
/*     */ 
/* 235 */     msg.setHeadItem("PlainText", plainBuf);
/* 236 */     msg.setHeadItem("PlainOffset", String.valueOf(plainOffset));
/* 237 */     msg.setHeadItem("ECT", msgType);
/*     */ 
/* 239 */     if (!(log.isDebugEnabled()))
/*     */       return;
/* 241 */     log.debug("restoreMsgState(HiMessage, StringBuffer, int, String) - end");
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 248 */     return super.toString() + ":name[" + this.name + "]";
/*     */   }
/*     */ }