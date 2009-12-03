/*     */ package com.hisun.engine.invoke.impl;
/*     */ 
/*     */ import com.hisun.engine.HiEngineModel;
/*     */ import com.hisun.engine.HiEngineStack;
/*     */ import com.hisun.engine.HiEngineUtilities;
/*     */ import com.hisun.engine.exception.HiBreakException;
/*     */ import com.hisun.engine.exception.HiContinueException;
/*     */ import com.hisun.engine.invoke.HiIAction;
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.exception.HiResponseException;
/*     */ import com.hisun.exception.HiSysException;
/*     */ import com.hisun.hiexpression.HiExpFactory;
/*     */ import com.hisun.hiexpression.HiExpression;
/*     */ import com.hisun.hilog4j.HiLog;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiMessage;
/*     */ import com.hisun.message.HiMessageContext;
/*     */ import com.hisun.util.HiICSProperty;
/*     */ import com.hisun.util.HiStringManager;
/*     */ import java.util.List;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.commons.lang.math.NumberUtils;
/*     */ 
/*     */ public class HiWhileProcess extends HiEngineModel
/*     */ {
/*  34 */   private HiExpression exp = null;
/*     */ 
/*  36 */   private int MAX_TIMES = 10000;
/*     */ 
/*  47 */   private String strCondition = "";
/*     */ 
/*     */   public HiWhileProcess()
/*     */   {
/*  40 */     String tmp = HiICSProperty.getProperty("sys.max_loop_times");
/*  41 */     int num = NumberUtils.toInt(tmp);
/*  42 */     if (num > 0)
/*  43 */       this.MAX_TIMES = num;
/*     */   }
/*     */ 
/*     */   public void setCondition(String strCondition)
/*     */   {
/*  51 */     this.strCondition = strCondition;
/*  52 */     this.exp = HiExpFactory.createExp(strCondition);
/*     */   }
/*     */ 
/*     */   protected boolean isSuccess(HiMessage mess, HiMessageContext tranData, HiEngineModel rsEn, int degree)
/*     */     throws HiException
/*     */   {
/*  58 */     if (this.exp == null)
/*     */     {
/*  60 */       return true;
/*     */     }
/*  62 */     if ((rsEn != null) && (degree == 0)) {
/*  63 */       return true;
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/*  68 */       boolean b = this.exp.isReturnTrue(tranData);
/*     */ 
/*  70 */       return b;
/*     */     }
/*     */     catch (HiException e)
/*     */     {
/*  74 */       e.addMsgStack("213326", this.strCondition);
/*  75 */       throw e;
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*  79 */       throw new HiSysException("213327", this.strCondition, e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void process(HiMessageContext messContext)
/*     */     throws HiException
/*     */   {
/*  89 */     HiMessage mess = messContext.getCurrentMsg();
/*  90 */     Logger log = HiLog.getLogger(mess);
/*  91 */     int degree = 0;
/*     */ 
/*  94 */     HiEngineModel rsEn = null;
/*  95 */     HiEngineStack stack = HiEngineStack.getCurrentStack(messContext);
/*  96 */     if (stack != null)
/*     */     {
/*  98 */       rsEn = (HiEngineModel)stack.pop();
/*     */     }
/*     */ 
/* 101 */     while (isSuccess(mess, messContext, rsEn, degree))
/*     */     {
/* 103 */       if (Thread.currentThread().isInterrupted()) {
/* 104 */         label37: throw new HiException("241149", Thread.currentThread().getName());
/*     */       }
/* 106 */       HiEngineUtilities.timeoutCheck(mess);
/*     */ 
/* 108 */       ++degree;
/* 109 */       if (degree > this.MAX_TIMES)
/*     */       {
/* 111 */         throw new HiException("213341", String.valueOf(this.MAX_TIMES));
/*     */       }
/*     */ 
/* 114 */       if (log.isInfoEnabled())
/*     */       {
/* 116 */         log.info(HiStringManager.getManager().getString("HiWhileProcess.process2", String.valueOf(degree)));
/*     */       }
/*     */ 
/* 119 */       List childs = getChilds();
/*     */       try
/*     */       {
/* 122 */         for (int i = 0; i < childs.size(); ++i)
/*     */         {
/* 124 */           HiIAction child = (HiIAction)childs.get(i);
/*     */ 
/* 126 */           if (rsEn != null)
/*     */           {
/* 128 */             if (child != rsEn)
/*     */               continue;
/* 130 */             rsEn = null;
/* 131 */             if (stack.size() == 0)
/*     */               continue;
/*     */           }
/* 134 */           HiEngineUtilities.setCurFlowStep(i);
/*     */           try
/*     */           {
/* 137 */             HiProcess.process(child, messContext);
/*     */           }
/*     */           catch (HiResponseException e)
/*     */           {
/* 141 */             HiEngineStack.getEngineStack(messContext).push(this);
/* 142 */             throw e;
/*     */           }
/*     */           catch (HiException e)
/*     */           {
/* 149 */             throw e;
/*     */           }
/*     */           catch (Exception e)
/*     */           {
/* 153 */             throw new HiSysException("213325", new String[] { HiEngineUtilities.getCurFlowStep(), String.valueOf(degree) }, e);
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/*     */       }
/*     */       catch (HiBreakException e)
/*     */       {
/* 162 */         return;
/*     */       }
/*     */       catch (HiContinueException e)
/*     */       {
/* 166 */         break label37:
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public String getNodeName()
/*     */   {
/* 174 */     return "While";
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 180 */     if (StringUtils.isNotEmpty(this.strCondition))
/* 181 */       return getNodeName() + ":condition[" + this.strCondition + "]";
/* 182 */     return super.toString();
/*     */   }
/*     */ }