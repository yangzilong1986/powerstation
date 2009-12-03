/*    */ package com.hisun.web.tag;
/*    */ 
/*    */ import javax.servlet.jsp.tagext.TagData;
/*    */ import javax.servlet.jsp.tagext.TagExtraInfo;
/*    */ import javax.servlet.jsp.tagext.VariableInfo;
/*    */ 
/*    */ public class HiIteratorTei extends TagExtraInfo
/*    */ {
/*    */   public VariableInfo[] getVariableInfo(TagData data)
/*    */   {
/*    */     VariableInfo[] result;
/* 14 */     VariableInfo[] variables = new VariableInfo[2];
/*    */ 
/* 17 */     int counter = 0;
/*    */ 
/* 20 */     String id = data.getAttributeString("id");
/*    */ 
/* 22 */     String type = "com.hisun.message.HiETF";
/*    */ 
/* 24 */     if (id != null) {
/* 25 */       if (type == null) {
/* 26 */         type = "java.lang.Object";
/*    */       }
/*    */ 
/* 29 */       variables[(counter++)] = new VariableInfo(data.getAttributeString("id"), type, true, 0);
/*    */     }
/*    */ 
/* 35 */     String indexId = data.getAttributeString("indexId");
/*    */ 
/* 37 */     if (indexId != null) {
/* 38 */       variables[(counter++)] = new VariableInfo(indexId, "java.lang.Integer", true, 0);
/*    */     }
/*    */ 
/* 46 */     if (counter > 0) {
/* 47 */       result = new VariableInfo[counter];
/* 48 */       System.arraycopy(variables, 0, result, 0, counter);
/*    */     } else {
/* 50 */       result = new VariableInfo[0];
/*    */     }
/*    */ 
/* 53 */     return result;
/*    */   }
/*    */ }