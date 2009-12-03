/*    */ package com.hisun.ccb.atc;
/*    */ 
/*    */ public abstract class MsgBase
/*    */ {
/*    */   protected String appMmo;
/*    */   protected String msgCode;
/*    */   protected String msgInfo;
/*    */ 
/*    */   public MsgBase(String appMmo, String msgCode, String msgInfo)
/*    */   {
/* 26 */     this.appMmo = appMmo;
/* 27 */     this.msgCode = msgCode;
/* 28 */     this.msgInfo = msgInfo;
/*    */   }
/*    */ 
/*    */   public String getAppMmo()
/*    */   {
/* 38 */     return this.appMmo;
/*    */   }
/*    */ 
/*    */   public void setAppMmo(String appMmo)
/*    */   {
/* 48 */     this.appMmo = appMmo;
/*    */   }
/*    */ 
/*    */   public String getMsgCode()
/*    */   {
/* 58 */     return this.msgCode;
/*    */   }
/*    */ 
/*    */   public void setMsgCode(String msgCode)
/*    */   {
/* 68 */     this.msgCode = msgCode;
/*    */   }
/*    */ 
/*    */   public String getMsgInfo()
/*    */   {
/* 78 */     return this.msgInfo;
/*    */   }
/*    */ 
/*    */   public void setMsgInfo(String msgInfo)
/*    */   {
/* 88 */     this.msgInfo = msgInfo;
/*    */   }
/*    */ 
/*    */   public abstract String getMsg();
/*    */ }