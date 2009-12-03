/*    */ package com.hisun.engine.invoke.impl;
/*    */ 
/*    */ import com.hisun.engine.HiEngineModel;
/*    */ import com.hisun.message.HiContext;
/*    */ 
/*    */ public class HiDynSentence extends HiEngineModel
/*    */ {
/*    */   private String strName;
/*    */   private String strSentence;
/*    */   private String strFields;
/*    */ 
/*    */   public void setName(String strName)
/*    */   {
/* 34 */     this.strName = strName;
/*    */   }
/*    */ 
/*    */   public void setSentence(String strSentence)
/*    */   {
/* 39 */     this.strSentence = strSentence;
/* 40 */     HiContext.getCurrentContext().setProperty("SENTENCE." + this.strName.toUpperCase(), strSentence);
/*    */   }
/*    */ 
/*    */   public void setFields(String strFields)
/*    */   {
/* 47 */     this.strFields = strFields;
/*    */ 
/* 58 */     HiContext.getCurrentContext().setProperty("FIELDS." + this.strName.toUpperCase(), strFields);
/*    */   }
/*    */ 
/*    */   public String getNodeName()
/*    */   {
/* 66 */     return "DynSentence";
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 72 */     return super.toString() + ":name[" + this.strName + "],sentence[" + this.strSentence + "],fields[" + this.strFields + "]";
/*    */   }
/*    */ }