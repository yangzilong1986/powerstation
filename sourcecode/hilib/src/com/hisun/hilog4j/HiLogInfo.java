/*    */ package com.hisun.hilog4j;
/*    */ 
/*    */ public class HiLogInfo
/*    */ {
/*    */   private StringBuilder buf;
/*    */   private IFileName name;
/*    */ 
/*    */   HiLogInfo(IFileName name, StringBuilder buf)
/*    */   {
/* 18 */     this.buf = buf;
/* 19 */     this.name = name;
/*    */   }
/*    */ 
/*    */   public StringBuilder getBuf()
/*    */   {
/* 26 */     return this.buf;
/*    */   }
/*    */ 
/*    */   public void setBuf(StringBuilder buf)
/*    */   {
/* 33 */     this.buf = buf;
/*    */   }
/*    */ 
/*    */   public IFileName getName()
/*    */   {
/* 40 */     return this.name;
/*    */   }
/*    */ 
/*    */   public void setName(IFileName name)
/*    */   {
/* 47 */     this.name = name;
/*    */   }
/*    */ }