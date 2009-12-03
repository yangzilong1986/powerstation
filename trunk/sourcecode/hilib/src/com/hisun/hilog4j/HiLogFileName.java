/*    */ package com.hisun.hilog4j;
/*    */ 
/*    */ import com.hisun.util.HiICSProperty;
/*    */ 
/*    */ public class HiLogFileName extends HiAbstractFileName
/*    */   implements IFileName
/*    */ {
/*    */   public HiLogFileName(String name, int lineLength)
/*    */   {
/*  7 */     super(name, lineLength);
/*    */   }
/*    */ 
/*    */   public HiLogFileName(String name) {
/* 11 */     super(name);
/*    */   }
/*    */ 
/*    */   public String get() {
/* 15 */     return HiICSProperty.getLogDir() + this._name;
/*    */   }
/*    */ }