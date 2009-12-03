/*    */ package com.hisun.engine.invoke.impl;
/*    */ 
/*    */ import com.hisun.engine.HiEngineModel;
/*    */ import com.hisun.engine.HiEngineStack;
/*    */ import com.hisun.engine.HiEngineUtilities;
/*    */ import com.hisun.engine.exception.HiBreakException;
/*    */ import com.hisun.engine.exception.HiReturnException;
/*    */ import com.hisun.engine.invoke.HiIAction;
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.exception.HiResponseException;
/*    */ import com.hisun.hiexpression.HiExpFactory;
/*    */ import com.hisun.hiexpression.HiExpression;
/*    */ import com.hisun.hilog4j.HiLog;
/*    */ import com.hisun.hilog4j.Logger;
/*    */ import com.hisun.message.HiMessage;
/*    */ import com.hisun.message.HiMessageContext;
/*    */ import com.hisun.util.HiStringManager;
/*    */ import java.util.List;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ 
/*    */ public class HiSwitchProcess extends HiEngineModel
/*    */ {
/*    */   private HiExpression exp;
/*    */   private String strExpression;
/*    */ 
/*    */   public HiSwitchProcess()
/*    */   {
/* 24 */     this.exp = null;
/*    */   }
/*    */ 
/*    */   public void setExpression(String strExpression)
/*    */   {
/* 30 */     this.strExpression = strExpression;
/* 31 */     this.exp = HiExpFactory.createExp(strExpression);
/*    */   }
/*    */ 
/*    */   public String getNodeName()
/*    */   {
/* 37 */     return "Switch";
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 43 */     return super.toString() + ":expression[" + this.strExpression + "]";
/*    */   }
/*    */ 
/*    */   public void process(HiMessageContext ctx) throws HiException
/*    */   {
/* 48 */     HiMessage msg = ctx.getCurrentMsg();
/* 49 */     Logger log = HiLog.getLogger(msg);
/* 50 */     String strValue = this.exp.getValue(ctx);
/* 51 */     if (log.isInfoEnabled())
/*    */     {
/* 53 */       log.info(sm.getString("HiSwitchProcess.process00", HiEngineUtilities.getCurFlowStep(), this.strExpression + "[" + strValue + "]"));
/*    */     }
/*    */ 
/* 57 */     HiEngineModel rsEn = null;
/* 58 */     HiEngineStack stack = HiEngineStack.getCurrentStack(ctx);
/* 59 */     if (stack != null)
/*    */     {
/* 61 */       rsEn = (HiEngineModel)stack.pop();
/*    */     }
/* 63 */     List childs = getChilds();
/* 64 */     boolean isJudge = true;
/* 65 */     for (int i = 0; i < childs.size(); ++i)
/*    */     {
/* 67 */       HiIAction child = (HiIAction)childs.get(i);
/*    */       try
/*    */       {
/* 70 */         if (rsEn != null)
/*    */         {
/* 72 */           if (child == rsEn)
/*    */           {
/* 74 */             rsEn = null;
/* 75 */             if (stack.size() == 0) {
/* 76 */               break label238:
/*    */             }
/*    */           }
/*    */         }
/* 80 */         if ((isJudge) && (child instanceof HiSwitchCase))
/*    */         {
/* 82 */           if (StringUtils.equals(((HiSwitchCase)child).getValue(), strValue))
/*    */           {
/* 86 */             isJudge = false;
/*    */           }
/*    */         }
/*    */ 
/* 90 */         HiEngineUtilities.setCurFlowStep(i);
/* 91 */         label238: HiProcess.process(child, ctx);
/*    */       }
/*    */       catch (HiResponseException e)
/*    */       {
/* 95 */         HiEngineStack.getEngineStack(ctx).push(this);
/* 96 */         throw e;
/*    */       }
/*    */       catch (HiBreakException e)
/*    */       {
/* 100 */         return;
/*    */       }
/*    */       catch (HiReturnException e)
/*    */       {
/* 105 */         throw e;
/*    */       }
/*    */     }
/*    */   }
/*    */ }