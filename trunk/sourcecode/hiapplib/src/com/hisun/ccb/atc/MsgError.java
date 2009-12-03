/*    */ package com.hisun.ccb.atc;
/*    */ 
/*    */ import com.hisun.util.HiStringUtils;
/*    */ 
/*    */ public class MsgError extends MsgBase
/*    */ {
/*    */   private String errorFieldName;
/*    */ 
/*    */   public MsgError(String appMmo, String msgCode, String msgInfo)
/*    */   {
/* 16 */     super(appMmo, msgCode, msgInfo);
/*    */   }
/*    */ 
/*    */   public String getErrorFieldName()
/*    */   {
/* 29 */     return this.errorFieldName;
/*    */   }
/*    */ 
/*    */   public void setErrorFieldName(String errorFieldName)
/*    */   {
/* 39 */     this.errorFieldName = errorFieldName;
/*    */   }
/*    */ 
/*    */   public String getMsg()
/*    */   {
/* 46 */     StringBuilder sb = new StringBuilder();
/* 47 */     int length = 0;
/* 48 */     if (this.msgInfo != null)
/*    */     {
/* 50 */       length = this.msgInfo.getBytes().length; }
/* 51 */     int totalLength = 16 + length;
/*    */ 
/* 53 */     String tmpstr = HiStringUtils.leftPad(totalLength, 4);
/* 54 */     sb.append(tmpstr);
/* 55 */     sb.append("SCD04");
/* 56 */     sb.append("N");
/* 57 */     sb.append(this.appMmo);
/* 58 */     sb.append(this.msgCode);
/*    */ 
/* 60 */     tmpstr = HiStringUtils.leftPad(length, 4);
/* 61 */     sb.append(tmpstr);
/* 62 */     sb.append(this.msgInfo);
/* 63 */     return sb.toString();
/*    */   }
/*    */ }