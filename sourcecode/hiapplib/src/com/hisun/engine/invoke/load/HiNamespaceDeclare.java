/*    */ package com.hisun.engine.invoke.load;
/*    */ 
/*    */ import com.hisun.engine.HiEngineModel;
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.message.HiContext;
/*    */ 
/*    */ public class HiNamespaceDeclare extends HiEngineModel
/*    */ {
/*    */   public String getNodeName()
/*    */   {
/* 17 */     return "NamespaceDeclare";
/*    */   }
/*    */ 
/*    */   public void setItem(String strName, String value) throws HiException {
/* 21 */     HiContext.getCurrentContext().setProperty("NSDECLARE." + strName, value);
/*    */   }
/*    */ }