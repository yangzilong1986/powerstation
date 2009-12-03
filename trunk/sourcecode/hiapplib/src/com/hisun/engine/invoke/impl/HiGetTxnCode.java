/*    */ package com.hisun.engine.invoke.impl;
/*    */ 
/*    */ import com.hisun.engine.HiEngineModel;
/*    */ import com.hisun.engine.invoke.HiIStrategy;
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.hilog4j.HiLog;
/*    */ import com.hisun.hilog4j.Logger;
/*    */ import com.hisun.message.HiMessage;
/*    */ import com.hisun.message.HiMessageContext;
/*    */ import com.hisun.util.HiStringManager;
/*    */ import java.util.List;
/*    */ 
/*    */ public class HiGetTxnCode extends HiEngineModel
/*    */ {
/*    */   public void beforeProcess(HiMessageContext messContext)
/*    */     throws HiException
/*    */   {
/* 50 */     HiIStrategy strategy = (HiIStrategy)messContext.getProperty("STRATEGY_KEY");
/*    */ 
/* 52 */     if (strategy != null)
/* 53 */       strategy.beforeConverMess(messContext);
/*    */   }
/*    */ 
/*    */   public String getNodeName()
/*    */   {
/* 58 */     return "GetTxnCode";
/*    */   }
/*    */ 
/*    */   public void process(HiMessageContext messContext)
/*    */     throws HiException
/*    */   {
/* 70 */     HiMessage mess = messContext.getCurrentMsg();
/* 71 */     Logger log = HiLog.getLogger(mess);
/*    */ 
/* 73 */     String strCode = "";
/*    */ 
/* 75 */     List childs = getChilds();
/*    */ 
/* 77 */     if (childs.size() == 1)
/*    */     {
/* 79 */       HiGetTxnCodeItem child = (HiGetTxnCodeItem)childs.get(0);
/*    */ 
/* 81 */       child.process(messContext);
/*    */     }
/*    */     else
/*    */     {
/* 85 */       for (int i = 0; i < childs.size(); ++i)
/*    */       {
/* 87 */         HiGetTxnCodeItem child = (HiGetTxnCodeItem)childs.get(i);
/*    */ 
/* 89 */         child.process(messContext);
/*    */ 
/* 91 */         strCode = strCode + mess.getHeadItem("STC");
/*    */       }
/*    */ 
/* 94 */       mess.setHeadItem("STC", strCode);
/*    */     }
/*    */ 
/* 97 */     if (log.isDebugEnabled())
/* 98 */       log.debug(HiStringManager.getManager().getString("HiGetTxnCode.process", strCode));
/*    */   }
/*    */ }