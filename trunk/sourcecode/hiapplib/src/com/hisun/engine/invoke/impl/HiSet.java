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
/*     */ 
/*     */ public class HiSet extends HiEngineModel
/*     */ {
/*     */   private String strName;
/*     */   private HiExpression exp;
/*     */   private HiExpression strNameExpr;
/*     */   private String statement;
/*     */   private HiStringManager sm;
/*     */ 
/*     */   public HiSet()
/*     */   {
/*  25 */     this.exp = null;
/*     */ 
/*  29 */     this.strNameExpr = null;
/*     */ 
/*  31 */     this.statement = null;
/*     */ 
/*  33 */     this.sm = HiStringManager.getManager();
/*     */   }
/*     */ 
/*     */   public void setName(String strName)
/*     */     throws HiException
/*     */   {
/*     */     int idx;
/*  38 */     this.statement = strName;
/*  39 */     if ((idx = strName.indexOf(61)) == -1) {
/*  40 */       throw new HiException("213329", strName);
/*     */     }
/*     */ 
/*  43 */     this.strName = strName.substring(0, idx);
/*  44 */     if (isArrayExpr(this.strName))
/*  45 */       this.strNameExpr = HiExpFactory.createExp(getArrayExpr(this.strName.trim()));
/*     */     else {
/*  47 */       switch (this.strName.charAt(0))
/*     */       {
/*     */       case '#':
/*     */       case '%':
/*     */       case '@':
/*     */       case '~':
/*  53 */         break;
/*     */       default:
/*  55 */         this.strNameExpr = HiExpFactory.createExp(this.strName.trim());
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/*  60 */     if (idx != strName.length() - 1) {
/*  61 */       String tmp = strName.substring(idx + 1);
/*  62 */       if (isArrayExpr(tmp))
/*  63 */         this.exp = HiExpFactory.createExp(getArrayExpr(tmp.trim()));
/*     */       else
/*  65 */         this.exp = HiExpFactory.createExp(tmp);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected String getArrayExpr(String expr)
/*     */     throws HiException
/*     */   {
/*  76 */     int i = 0;
/*  77 */     StringBuffer result = new StringBuffer();
/*  78 */     if (expr.charAt(0) == '$') {
/*  79 */       ++i;
/*  80 */       result.append("ETF(STRCAT(");
/*     */     } else {
/*  82 */       result.append("STRCAT(");
/*     */     }
/*     */ 
/*  85 */     for (; i < expr.length(); ++i)
/*  86 */       if (expr.charAt(i) != '[') {
/*  87 */         result.append(expr.charAt(i));
/*     */       }
/*     */       else {
/*  90 */         ++i;
/*  91 */         result.append("_,");
/*  92 */         for (; i < expr.length(); ++i) {
/*  93 */           if (expr.charAt(i) == ']') {
/*  94 */             result.append(',');
/*  95 */             break;
/*     */           }
/*  97 */           result.append(expr.charAt(i));
/*     */         }
/*     */       }
/* 100 */     if (expr.charAt(0) == '$')
/* 101 */       result.append("))");
/*     */     else {
/* 103 */       result.append(")");
/*     */     }
/* 105 */     return result.toString();
/*     */   }
/*     */ 
/*     */   protected boolean isArrayExpr(String expr)
/*     */   {
/* 110 */     int idx1 = expr.indexOf(91);
/* 111 */     int idx2 = expr.indexOf(93);
/*     */ 
/* 114 */     return ((idx1 == -1) || (idx1 == 0) || (idx2 == -1) || (Character.isWhitespace(expr.charAt(idx1 - 1))) || (expr.charAt(idx2 + 1) != '.'));
/*     */   }
/*     */ 
/*     */   public String getNodeName()
/*     */   {
/* 122 */     return "Set";
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 127 */     return super.toString() + ":name[" + this.statement + "]";
/*     */   }
/*     */ 
/*     */   public void process(HiMessageContext messContext)
/*     */     throws HiException
/*     */   {
/*     */     try
/*     */     {
/* 141 */       HiMessage mess = messContext.getCurrentMsg();
/* 142 */       Logger log = HiLog.getLogger(mess);
/*     */ 
/* 144 */       String strNewValue = "";
/* 145 */       if (this.exp != null)
/* 146 */         strNewValue = this.exp.getValue(messContext);
/* 147 */       String name = this.strName;
/* 148 */       if (this.strNameExpr != null) {
/* 149 */         name = this.strNameExpr.getValue(messContext);
/*     */       }
/* 151 */       HiEngineUtilities.processFlow(name, strNewValue, true, messContext);
/*     */ 
/* 153 */       if (log.isInfoEnabled())
/* 154 */         log.info(this.sm.getString("HiSet.process00", HiEngineUtilities.getCurFlowStep(), this.statement, name, strNewValue));
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 158 */       if (e instanceof HiException) {
/* 159 */         throw ((HiException)e);
/*     */       }
/* 161 */       throw new HiException(e);
/*     */     }
/*     */   }
/*     */ }