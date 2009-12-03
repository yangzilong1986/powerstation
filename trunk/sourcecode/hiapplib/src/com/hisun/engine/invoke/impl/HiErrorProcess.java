/*    */ package com.hisun.engine.invoke.impl;
/*    */ 
/*    */ import com.hisun.message.HiContext;
/*    */ 
/*    */ public class HiErrorProcess extends HiAbstractTransaction
/*    */ {
/*    */   private String strName;
/*    */ 
/*    */   public void setName(String strName)
/*    */   {
/* 20 */     this.strName = strName;
/* 21 */     this.context.setProperty("ERROR." + strName, this);
/*    */   }
/*    */ 
/*    */   public String getNodeName()
/*    */   {
/* 26 */     return "Error";
/*    */   }
/*    */ }