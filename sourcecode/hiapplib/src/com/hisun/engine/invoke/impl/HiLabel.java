/*    */ package com.hisun.engine.invoke.impl;
/*    */ 
/*    */ import com.hisun.engine.HiEngineModel;
/*    */ 
/*    */ public class HiLabel extends HiEngineModel
/*    */ {
/*    */   private String strName;
/*    */ 
/*    */   public void setName(String strName)
/*    */   {
/* 16 */     this.strName = strName;
/*    */   }
/*    */ 
/*    */   public String getName()
/*    */   {
/* 21 */     return this.strName;
/*    */   }
/*    */ 
/*    */   public String getNodeName()
/*    */   {
/* 27 */     return "Lable";
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 33 */     return super.toString() + ":name[" + this.strName + "]";
/*    */   }
/*    */ }