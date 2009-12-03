/*     */ package com.hisun.engine.invoke.impl;
/*     */ 
/*     */ import com.hisun.database.HiDataBaseUtil;
/*     */ import com.hisun.engine.invoke.HiIEngineModel;
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.hiexpression.HiExpFactory;
/*     */ import com.hisun.hilog4j.HiLog;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiContext;
/*     */ import com.hisun.message.HiMessage;
/*     */ import com.hisun.message.HiMessageContext;
/*     */ import com.hisun.util.HiStringManager;
/*     */ import java.sql.Timestamp;
/*     */ import java.util.HashMap;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class HiTransactionITFImpl extends HiAbstractTransaction
/*     */ {
/*  36 */   private HashMap childMap = new HashMap();
/*     */   private boolean isByPass;
/*  46 */   private String strServerName = null;
/*     */ 
/*  51 */   private String strServiceCode = null;
/*     */ 
/* 192 */   private String strErrorName = "Default";
/*     */ 
/*     */   public HiTransactionITFImpl()
/*     */   {
/*  20 */     this.context = HiContext.createAndPushContext();
/*     */ 
/*  22 */     this.strServerName = ((String)this.context.getProperty("app_server"));
/*     */ 
/*  25 */     this.context.setProperty("log_level", this.strLogSwitch);
/*  26 */     this.context.setProperty("mon_switch", this.strMonSwitch);
/*  27 */     this.context.setProperty("trans_switch", this.strTranSwitch);
/*  28 */     this.context.setProperty("ECO", this.strErrorName);
/*     */ 
/*  30 */     this.log_id = this.context.getStrProp("__LOG_ID");
/*  31 */     if (this.log_id != null)
/*  32 */       this.log_idExpr = HiExpFactory.createExp(this.log_id);
/*     */   }
/*     */ 
/*     */   public void addChilds(HiIEngineModel child)
/*     */     throws HiException
/*     */   {
/*  59 */     super.addChilds(child);
/*  60 */     if (child instanceof HiRequest)
/*  61 */       this.childMap.put("rq", child);
/*     */     else
/*  63 */       this.childMap.put("rp", child);
/*     */   }
/*     */ 
/*     */   public boolean getByPass() {
/*  67 */     return this.isByPass;
/*     */   }
/*     */ 
/*     */   public String getServerName() {
/*  71 */     return this.strServerName;
/*     */   }
/*     */ 
/*     */   public String getService() {
/*  75 */     return this.strServiceCode;
/*     */   }
/*     */ 
/*     */   public void process(HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/*  83 */     HiMessage msg = ctx.getCurrentMsg();
/*  84 */     Logger log = HiLog.getLogger(msg);
/*     */     try {
/*  86 */       msg.setHeadItem("ECO", getError());
/*     */ 
/*  88 */       if (log.isInfo3Enabled()) {
/*  89 */         if (StringUtils.equalsIgnoreCase(msg.getHeadItem("SCH"), "rq"))
/*     */         {
/*  92 */           log.info3(this.sm.getString("HiTransactionITFImpl.process01", HiMessageContext.getCurrentMessageContext().getStrProp("SVR.name")));
/*     */         }
/*     */         else
/*     */         {
/*  96 */           log.info3(this.sm.getString("HiTransactionITFImpl.process02", HiMessageContext.getCurrentMessageContext().getStrProp("SVR.name")));
/*     */         }
/*     */ 
/* 101 */         if (msg.hasHeadItem("PlainText")) {
/* 102 */           log.info2(this.sm.getString("HiTransactionITFImpl.process03", msg.getHead(), msg.getObjectHeadItem("PlainText")));
/*     */         }
/*     */         else
/*     */         {
/* 106 */           log.info2(this.sm.getString("HiTransactionITFImpl.process03", msg.getHead(), msg.getBody()));
/*     */         }
/*     */ 
/* 109 */         log.info3(this.sm.getString("HiTransactionITFImpl.process05", this.strCode));
/*     */       }
/*     */ 
/* 113 */       Long startTime = (Long)msg.getObjectHeadItem("STM");
/* 114 */       if (log.isDebugEnabled()) {
/* 115 */         log.debug(this.sm.getString("HiTransactionITFImpl.process1", new Timestamp(startTime.longValue())));
/*     */       }
/*     */ 
/* 118 */       if (this.timeout != -1) {
/* 119 */         Integer time = new Integer(this.timeout * 1000);
/* 120 */         long endTime = time.longValue() + startTime.longValue();
/* 121 */         msg.setHeadItem("ETM", new Long(endTime));
/* 122 */         if (log.isDebugEnabled()) {
/* 123 */           log.debug(this.sm.getString("HiTransactionITFImpl.process2", new Timestamp(endTime)));
/*     */         }
/*     */       }
/*     */ 
/* 127 */       String strType = msg.getHeadItem("SCH");
/* 128 */       if (log.isDebugEnabled()) {
/* 129 */         log.debug(this.sm.getString("HiTransactionITFImpl.process3", strType));
/*     */       }
/*     */ 
/* 132 */       HiAbstractRqAndRp element = (HiAbstractRqAndRp)this.childMap.get(strType);
/*     */ 
/* 139 */       if ((!(StringUtils.isEmpty(this.strServerName))) || (!(StringUtils.isEmpty(this.strServiceCode))))
/*     */       {
/* 142 */         String strSTCCode = this.strServiceCode;
/* 143 */         if (StringUtils.isEmpty(this.strServiceCode)) {
/* 144 */           strSTCCode = getCode();
/*     */         }
/*     */ 
/* 148 */         msg.addHeadItem("STC", strSTCCode);
/*     */ 
/* 150 */         if (StringUtils.isNotEmpty(this.strServerName))
/* 151 */           msg.setHeadItem("SDT", this.strServerName);
/*     */       }
/* 153 */       HiProcess.process(element, ctx);
/*     */     }
/*     */     catch (HiException e)
/*     */     {
/*     */     }
/*     */     finally
/*     */     {
/* 162 */       if (ctx.getDataBaseUtil() != null) {
/* 163 */         ctx.getDataBaseUtil().closeAll();
/*     */       }
/*     */     }
/* 166 */     if (log.isInfo3Enabled()) {
/* 167 */       log.info2(this.sm.getString("HiTransactionITFImpl.process03", msg.getHead(), msg.getBody().toString()));
/*     */ 
/* 169 */       log.info3(this.sm.getString("HiTransactionITFImpl.process07"));
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setPass(boolean isByPass) {
/* 174 */     this.isByPass = isByPass;
/* 175 */     this.context.setProperty("trans_pass", Boolean.valueOf(isByPass));
/*     */   }
/*     */ 
/*     */   public void setServer(String strServerName)
/*     */   {
/* 180 */     this.strServerName = strServerName;
/*     */   }
/*     */ 
/*     */   public void setService(String strServiceCode)
/*     */   {
/* 185 */     this.strServiceCode = strServiceCode;
/*     */   }
/*     */ 
/*     */   public String getError() {
/* 189 */     return this.strErrorName;
/*     */   }
/*     */ 
/*     */   public void setError(String strErrorName)
/*     */   {
/* 195 */     this.strErrorName = strErrorName;
/* 196 */     this.context.setProperty("ECO", strErrorName);
/*     */   }
/*     */ 
/*     */   public void loadAfter() throws HiException {
/* 200 */     super.popOwnerContext();
/*     */   }
/*     */ }