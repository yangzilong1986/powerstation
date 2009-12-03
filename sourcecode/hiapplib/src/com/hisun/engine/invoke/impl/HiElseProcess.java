/*    */ package com.hisun.engine.invoke.impl;
/*    */ 
/*    */ import com.hisun.engine.HiEngineModel;
/*    */ import com.hisun.engine.HiEngineStack;
/*    */ import com.hisun.engine.invoke.HiIAction;
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.exception.HiResponseException;
/*    */ import com.hisun.message.HiMessageContext;
/*    */ import java.util.List;
/*    */ import java.util.Vector;
/*    */ 
/*    */ public class HiElseProcess extends HiEngineModel
/*    */ {
/*    */   private List conditions;
/*    */ 
/*    */   public HiElseProcess()
/*    */   {
/* 19 */     this.conditions = new Vector();
/*    */   }
/*    */ 
/*    */   public void addControl(HiIAction control)
/*    */   {
/* 29 */     this.conditions.add(control);
/*    */   }
/*    */ 
/*    */   public String getNodeName()
/*    */   {
/* 34 */     return "Else";
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 39 */     String strNodeName = super.toString();
/*    */ 
/* 41 */     return strNodeName;
/*    */   }
/*    */ 
/*    */   public void process(HiMessageContext ctx) throws HiException {
/* 45 */     if ((getChilds() == null) || (getChilds().size() == 0)) {
/* 46 */       return;
/*    */     }
/* 48 */     for (int i = 0; i < getChilds().size(); ++i)
/*    */     {
/*    */       try
/*    */       {
/* 52 */         HiIAction child = (HiIAction)getChilds().get(i);
/* 53 */         HiProcess.process(child, ctx);
/*    */       }
/*    */       catch (HiResponseException e)
/*    */       {
/* 57 */         HiEngineStack.getEngineStack(ctx).push(this);
/* 58 */         throw e;
/*    */       }
/*    */     }
/*    */   }
/*    */ }