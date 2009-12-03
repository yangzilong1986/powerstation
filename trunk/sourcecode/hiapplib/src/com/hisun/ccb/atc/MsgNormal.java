/*    */ package com.hisun.ccb.atc;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ import java.util.List;
/*    */ 
/*    */ public class MsgNormal extends MsgBase
/*    */ {
/* 22 */   private List<String> values = new ArrayList();
/*    */ 
/*    */   public MsgNormal(String appMmo, String msgCode, String msgInfo)
/*    */   {
/* 18 */     super(appMmo, msgCode, msgInfo);
/*    */   }
/*    */ 
/*    */   public void addValue(String value)
/*    */   {
/* 30 */     this.values.add(value);
/*    */   }
/*    */ 
/*    */   public Collection<String> getValues()
/*    */   {
/* 39 */     return this.values;
/*    */   }
/*    */ 
/*    */   public String getMsg()
/*    */   {
/* 46 */     return null;
/*    */   }
/*    */ }