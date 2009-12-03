/*    */ package com.hisun.atc.rpt.data;
/*    */ 
/*    */ import com.hisun.atc.rpt.HiDataRecord;
/*    */ 
/*    */ public abstract class Matcher
/*    */ {
/*    */   public abstract boolean match(HiDataRecord paramHiDataRecord);
/*    */ 
/*    */   public Matcher and(Matcher a)
/*    */   {
/*  9 */     return Matchers.and(this, a);
/*    */   }
/*    */ 
/*    */   public Matcher or(Matcher a) {
/* 13 */     return Matchers.or(this, a);
/*    */   }
/*    */ }