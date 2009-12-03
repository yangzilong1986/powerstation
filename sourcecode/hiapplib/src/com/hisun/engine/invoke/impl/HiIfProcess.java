/*     */ package com.hisun.engine.invoke.impl;
/*     */ 
/*     */ import com.hisun.engine.HiEngineModel;
/*     */ import com.hisun.engine.HiEngineStack;
/*     */ import com.hisun.engine.HiEngineUtilities;
/*     */ import com.hisun.engine.invoke.HiIAction;
/*     */ import com.hisun.engine.invoke.HiIEngineModel;
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.exception.HiResponseException;
/*     */ import com.hisun.hiexpression.HiExpFactory;
/*     */ import com.hisun.hiexpression.HiExpression;
/*     */ import com.hisun.hilog4j.HiLog;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiMessage;
/*     */ import com.hisun.message.HiMessageContext;
/*     */ import com.hisun.util.HiStringManager;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class HiIfProcess extends HiEngineModel
/*     */ {
/*  32 */   private static final HiStringManager sm = HiStringManager.getManager();
/*     */ 
/*  34 */   private ArrayList controlNodes = new ArrayList();
/*     */   private String strCondition;
/*  38 */   private HiExpression exp = null;
/*     */ 
/*     */   public void addControlNodes(HiIEngineModel control)
/*     */   {
/*  51 */     this.controlNodes.add(control);
/*     */   }
/*     */ 
/*     */   public String getNodeName()
/*     */   {
/*  57 */     return "If";
/*     */   }
/*     */ 
/*     */   public void process(HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/*  68 */     HiMessage mess = ctx.getCurrentMsg();
/*  69 */     Logger log = HiLog.getLogger(mess);
/*  70 */     if (log.isInfoEnabled())
/*     */     {
/*  72 */       log.info(sm.getString("HiIfProcess.process00", HiEngineUtilities.getCurFlowStep(), this.strCondition));
/*     */     }
/*     */ 
/*  75 */     boolean isSuccess = true;
/*     */ 
/*  79 */     HiEngineModel rsEn = null;
/*  80 */     HiEngineStack stack = HiEngineStack.getCurrentStack(ctx);
/*  81 */     if (stack != null)
/*     */     {
/*  83 */       rsEn = (HiEngineModel)stack.pop();
/*     */     }
/*     */ 
/*  86 */     if (rsEn == null)
/*     */     {
/*  88 */       isSuccess = isSuccess(mess, ctx);
/*     */     }
/*  92 */     else if (getChilds().indexOf(rsEn) != -1) {
/*  93 */       isSuccess = false;
/*     */     }
/*     */ 
/*  96 */     if (isSuccess)
/*     */     {
/*  99 */       List childs = getChilds();
/* 100 */       if ((childs == null) || (childs.size() == 0)) {
/* 101 */         return;
/*     */       }
/* 103 */       for (int i = 0; i < childs.size(); ++i)
/*     */       {
/* 105 */         HiIAction child = (HiIAction)childs.get(i);
/*     */ 
/* 107 */         if (rsEn != null)
/*     */         {
/* 109 */           if (child != rsEn)
/*     */             continue;
/* 111 */           rsEn = null;
/* 112 */           if (stack.size() == 0) {
/*     */             continue;
/*     */           }
/*     */         }
/* 116 */         HiEngineUtilities.setCurFlowStep(i);
/*     */         try
/*     */         {
/* 119 */           HiProcess.process(child, ctx);
/*     */         }
/*     */         catch (HiResponseException e)
/*     */         {
/* 123 */           HiEngineStack.getEngineStack(ctx).push(this);
/* 124 */           throw e;
/*     */         }
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 130 */       for (int i = 0; i < this.controlNodes.size(); ++i)
/*     */       {
/* 132 */         HiIAction child = (HiIAction)this.controlNodes.get(i);
/*     */ 
/* 134 */         if ((child instanceof HiIfProcess) || (child instanceof HiElseIfProcess))
/*     */         {
/* 137 */           HiIfProcess pro = (HiIfProcess)child;
/* 138 */           if (pro.isSuccess(mess, ctx))
/*     */           {
/*     */             try
/*     */             {
/* 142 */               HiProcess.process(child, ctx);
/*     */             }
/*     */             catch (HiResponseException e)
/*     */             {
/* 146 */               HiEngineStack.getEngineStack(ctx).push(this);
/* 147 */               throw e;
/*     */             }
/*     */           }
/*     */ 
/*     */         }
/*     */         else
/*     */         {
/*     */           try
/*     */           {
/* 156 */             HiProcess.process(child, ctx);
/*     */           }
/*     */           catch (HiResponseException e)
/*     */           {
/* 160 */             HiEngineStack.getEngineStack(ctx).push(this);
/* 161 */             throw e;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setCondition(String strCondition)
/*     */   {
/* 170 */     this.strCondition = strCondition;
/* 171 */     this.exp = HiExpFactory.createIfExp(strCondition);
/*     */   }
/*     */ 
/*     */   protected boolean isSuccess(HiMessage mess, HiMessageContext tranData)
/*     */     throws HiException
/*     */   {
/*     */     try
/*     */     {
/* 181 */       Logger log = HiLog.getLogger(mess);
/*     */ 
/* 183 */       boolean b = this.exp.isReturnTrue(tranData);
/*     */ 
/* 185 */       if (log.isInfoEnabled())
/*     */       {
/* 187 */         log.info(HiStringManager.getManager().getString("HiIfProcess.isSuccess", this.strCondition, String.valueOf(b)));
/*     */       }
/*     */ 
/* 192 */       return b;
/*     */     }
/*     */     catch (Throwable e)
/*     */     {
/* 196 */       throw HiException.makeException("213330", HiStringManager.getManager().getString("HiIfProcess.isSuccess1", this.strCondition), e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void loadAfter()
/*     */     throws HiException
/*     */   {
/* 203 */     if (StringUtils.isBlank(this.strCondition))
/* 204 */       throw new HiException("if condition empty");
/*     */   }
/*     */ }