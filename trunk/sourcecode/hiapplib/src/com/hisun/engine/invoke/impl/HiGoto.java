/*    */ package com.hisun.engine.invoke.impl;
/*    */ 
/*    */ import com.hisun.engine.HiEngineModel;
/*    */ import com.hisun.engine.exception.HiGotoException;
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.message.HiMessageContext;
/*    */ 
/*    */ public class HiGoto extends HiEngineModel
/*    */ {
/*    */   private String strName;
/*    */ 
/*    */   public void setName(String strName)
/*    */   {
/* 19 */     this.strName = strName;
/*    */   }
/*    */ 
/*    */   public String getName()
/*    */   {
/* 24 */     return this.strName;
/*    */   }
/*    */ 
/*    */   public String getNodeName()
/*    */   {
/* 30 */     return "Goto";
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 35 */     return super.toString() + ":name[" + this.strName + "]";
/*    */   }
/*    */ 
/*    */   public void beforeProcess(HiMessageContext messContext)
/*    */     throws HiException
/*    */   {
/* 41 */     HiGotoException ex = new HiGotoException();
/* 42 */     ex.seGototName(this.strName);
/* 43 */     throw ex;
/*    */   }
/*    */ }