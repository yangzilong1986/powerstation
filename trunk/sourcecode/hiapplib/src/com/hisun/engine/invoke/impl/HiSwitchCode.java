/*     */ package com.hisun.engine.invoke.impl;
/*     */ 
/*     */ import com.hisun.engine.HiEngineModel;
/*     */ import com.hisun.engine.HiEngineUtilities;
/*     */ import com.hisun.engine.invoke.load.HiTableInfo;
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.hiexpression.HiExpFactory;
/*     */ import com.hisun.hiexpression.HiExpression;
/*     */ import com.hisun.hilog4j.HiLog;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiETF;
/*     */ import com.hisun.message.HiMessage;
/*     */ import com.hisun.message.HiMessageContext;
/*     */ import com.hisun.util.HiStringManager;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class HiSwitchCode extends HiEngineModel
/*     */ {
/*     */   private String strName;
/*     */   private String strNew_Name;
/*     */   private String strTab_Name;
/*     */   private HiExpression tab_NameExpr;
/*     */   private String strCol_Name;
/*     */   private String strCCol_Name;
/*     */ 
/*     */   public String getNodeName()
/*     */   {
/*  33 */     return "SwitchCode ";
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  38 */     return super.toString() + ":name[" + this.strName + "]";
/*     */   }
/*     */ 
/*     */   public void setName(String strName)
/*     */   {
/*  69 */     this.strName = strName;
/*     */   }
/*     */ 
/*     */   public void setNew_name(String strNewName) {
/*  73 */     this.strNew_Name = strNewName;
/*     */   }
/*     */ 
/*     */   public void setTab_name(String strTableName) {
/*  77 */     this.strTab_Name = strTableName;
/*  78 */     this.tab_NameExpr = HiExpFactory.createExp(this.strTab_Name);
/*     */   }
/*     */ 
/*     */   public void setCol_name(String strColName) {
/*  82 */     this.strCol_Name = strColName;
/*     */   }
/*     */ 
/*     */   public void setCcol_name(String strCColName) {
/*  86 */     this.strCCol_Name = strCColName;
/*     */   }
/*     */ 
/*     */   public void process(HiMessageContext messContext)
/*     */     throws HiException
/*     */   {
/*  97 */     HiMessage mess = messContext.getCurrentMsg();
/*  98 */     Logger log = HiLog.getLogger(mess);
/*     */ 
/* 100 */     HiETF etf = (HiETF)mess.getBody();
/*     */ 
/* 102 */     String strOldValue = HiItemHelper.getEtfItem(mess, this.strName);
/* 103 */     String tabName = this.tab_NameExpr.getValue(messContext);
/* 104 */     if (log.isDebugEnabled()) {
/* 105 */       log.debug("SwitchCode TabName:[" + tabName + "]");
/*     */     }
/*     */ 
/* 108 */     HiTableInfo tableInfo = getTableInfo(messContext, tabName);
/* 109 */     if (tableInfo == null) {
/* 110 */       throw new HiException("241046", tabName);
/*     */     }
/*     */ 
/* 113 */     String strNewValue = null;
/* 114 */     if (!(StringUtils.isEmpty(this.strCCol_Name))) {
/* 115 */       strNewValue = tableInfo.getColValue(this.strCol_Name, strOldValue, this.strCCol_Name);
/*     */     }
/*     */     else {
/* 118 */       strNewValue = tableInfo.getColValue(this.strCol_Name, strOldValue);
/*     */     }
/*     */ 
/* 121 */     if (StringUtils.isEmpty(this.strNew_Name))
/* 122 */       this.strNew_Name = this.strName;
/* 123 */     if (log.isInfoEnabled()) {
/* 124 */       log.info(sm.getString("HiSwitchCode.process00", HiEngineUtilities.getCurFlowStep(), this.strName, strOldValue, this.strNew_Name, strNewValue));
/*     */     }
/*     */ 
/* 128 */     if (strNewValue == null) {
/* 129 */       throw new HiException("241044", tabName, this.strName, this.strCol_Name);
/*     */     }
/* 131 */     HiItemHelper.addEtfItem(mess, this.strNew_Name, strNewValue);
/*     */   }
/*     */ 
/*     */   public HiTableInfo getTableInfo(HiMessageContext messContext, String tabName)
/*     */     throws HiException
/*     */   {
/* 138 */     HiTableInfo tableInfo = (HiTableInfo)messContext.getProperty("CODESWITCHING", tabName);
/*     */ 
/* 140 */     return tableInfo;
/*     */   }
/*     */ 
/*     */   public void loadAfter()
/*     */     throws HiException
/*     */   {
/*     */   }
/*     */ }