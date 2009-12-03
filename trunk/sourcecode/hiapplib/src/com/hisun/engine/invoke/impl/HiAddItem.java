/*     */ package com.hisun.engine.invoke.impl;
/*     */ 
/*     */ import com.hisun.engine.HiEngineModel;
/*     */ import com.hisun.engine.HiEngineUtilities;
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.hiexpression.HiExpFactory;
/*     */ import com.hisun.hiexpression.HiExpression;
/*     */ import com.hisun.hilog4j.HiLog;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiMessage;
/*     */ import com.hisun.message.HiMessageContext;
/*     */ import com.hisun.util.HiStringManager;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.HashMap;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class HiAddItem extends HiEngineModel
/*     */ {
/*  25 */   private static final HiStringManager sm = HiStringManager.getManager();
/*     */ 
/*  33 */   private HiExpression exp = null;
/*     */   private String strName;
/*     */   private String strPro_dll;
/*     */   private String strPro_func;
/*     */   private String strValue;
/*     */ 
/*     */   public void process(HiMessageContext messContext)
/*     */     throws HiException
/*     */   {
/*  56 */     HiMessage mess = messContext.getCurrentMsg();
/*  57 */     Logger log = HiLog.getLogger(mess);
/*  58 */     if (log.isDebugEnabled()) {
/*  59 */       log.debug("HiAddItem:process(HiMessageContext) - start");
/*     */     }
/*     */ 
/*  63 */     if (StringUtils.isNotEmpty(this.strValue))
/*     */     {
/*  66 */       if (log.isInfoEnabled()) {
/*  67 */         log.info(sm.getString("HiAddItem.process00", HiEngineUtilities.getCurFlowStep(), this.strName, this.strValue));
/*     */       }
/*     */ 
/*  70 */       HiItemHelper.addEtfItem(mess, this.strName, this.strValue);
/*  71 */     } else if (this.exp != null)
/*     */     {
/*  73 */       Object value = this.exp.getValue(messContext);
/*  74 */       if (log.isInfoEnabled()) {
/*  75 */         log.info(sm.getString("HiAddItem.process00", HiEngineUtilities.getCurFlowStep(), this.strName, value));
/*     */       }
/*  77 */       HiItemHelper.addEtfItem(mess, this.strName, value.toString());
/*     */     }
/*     */     else
/*     */     {
/*     */       try {
/*  82 */         Class cl = Class.forName(this.strPro_dll);
/*  83 */         Method method = cl.getDeclaredMethod(this.strPro_func, new Class[] { HiMessageContext.class, HashMap.class });
/*     */ 
/*  85 */         HashMap map = new HashMap();
/*  86 */         map.put("name", this.strName);
/*  87 */         Object value = method.invoke(cl.newInstance(), new Object[] { messContext, map });
/*  88 */         if (log.isInfoEnabled()) {
/*  89 */           log.info(sm.getString("HiAddItem.process01", HiEngineUtilities.getCurFlowStep(), this.strPro_dll, this.strPro_func, this.strName, value));
/*     */         }
/*  91 */         HiItemHelper.addEtfItem(mess, this.strName, value.toString());
/*     */       } catch (Throwable t) {
/*  93 */         throw HiException.makeException(t);
/*     */       }
/*     */     }
/*     */ 
/*  97 */     if (log.isDebugEnabled())
/*  98 */       log.debug("HiAddItem:process(HiMessageContext) - end");
/*     */   }
/*     */ 
/*     */   public void setExpression(String strExpression)
/*     */   {
/* 106 */     this.exp = HiExpFactory.createExp(strExpression);
/*     */   }
/*     */ 
/*     */   public void setName(String strName) {
/* 110 */     this.strName = strName;
/*     */   }
/*     */ 
/*     */   public void setPro_dll(String strPro_dll) {
/* 114 */     this.strPro_dll = strPro_dll;
/*     */   }
/*     */ 
/*     */   public void setPro_func(String strPro_func) {
/* 118 */     this.strPro_func = strPro_func;
/*     */   }
/*     */ 
/*     */   public void setValue(String strValue) {
/* 122 */     this.strValue = strValue;
/*     */   }
/*     */ 
/*     */   public String getNodeName() {
/* 126 */     return "AddItem";
/*     */   }
/*     */ }