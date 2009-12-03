/*     */ package com.hisun.engine.invoke.impl;
/*     */ 
/*     */ import com.hisun.engine.HiEngineModel;
/*     */ import com.hisun.engine.HiEngineUtilities;
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.hilog4j.HiLog;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiContext;
/*     */ import com.hisun.message.HiETF;
/*     */ import com.hisun.message.HiMessage;
/*     */ import com.hisun.message.HiMessageContext;
/*     */ import com.hisun.util.HiStringManager;
/*     */ 
/*     */ public class HiCopyItem extends HiEngineModel
/*     */ {
/*     */   private final Logger logger;
/*     */   private String strSour_name;
/*     */   private String strDest_name;
/*     */ 
/*     */   public HiCopyItem()
/*     */   {
/*  27 */     this.logger = ((Logger)HiContext.getCurrentContext().getProperty("SVR.log"));
/*     */   }
/*     */ 
/*     */   public void setSour_name(String strSour_name)
/*     */   {
/*  34 */     if (this.logger.isDebugEnabled())
/*     */     {
/*  36 */       this.logger.debug("setSour_name(String) - start");
/*     */     }
/*     */ 
/*  39 */     this.strSour_name = strSour_name;
/*     */ 
/*  41 */     if (!(this.logger.isDebugEnabled()))
/*     */       return;
/*  43 */     this.logger.debug("setSour_name(String) - end");
/*     */   }
/*     */ 
/*     */   public void setDest_name(String strDest_name)
/*     */   {
/*  51 */     if (this.logger.isDebugEnabled())
/*     */     {
/*  53 */       this.logger.debug("setDest_name(String) - start");
/*     */     }
/*     */ 
/*  56 */     this.strDest_name = strDest_name;
/*     */ 
/*  58 */     if (!(this.logger.isDebugEnabled()))
/*     */       return;
/*  60 */     this.logger.debug("setDest_name(String) - end");
/*     */   }
/*     */ 
/*     */   public void process(HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/*  71 */     HiMessage msg = ctx.getCurrentMsg();
/*  72 */     Logger log = HiLog.getLogger(msg);
/*  73 */     if (log.isDebugEnabled())
/*     */     {
/*  75 */       log.debug("HiCopyItem:process(HiMessageContext) - start");
/*     */     }
/*     */ 
/*  79 */     HiETF etf = (HiETF)msg.getBody();
/*     */ 
/*  81 */     String value = etf.getChildValue(HiItemHelper.getCurEtfLevel(msg) + this.strSour_name);
/*     */ 
/*  83 */     if (log.isInfoEnabled()) {
/*  84 */       log.info(sm.getString("HiCopyItem.process00", HiEngineUtilities.getCurFlowStep(), this.strSour_name, value));
/*     */     }
/*     */ 
/*  91 */     HiItemHelper.addEtfItem(msg, this.strDest_name, value);
/*     */ 
/*  93 */     if (!(log.isDebugEnabled()))
/*     */       return;
/*  95 */     log.debug("HiCopyItem:process(HiMessageContext) - end");
/*     */   }
/*     */ 
/*     */   public String getNodeName()
/*     */   {
/* 101 */     return "CopyItem";
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 106 */     return getNodeName() + "[" + this.strSour_name + "],[" + this.strDest_name + "]";
/*     */   }
/*     */ }