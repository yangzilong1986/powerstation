/*    */ package com.hisun.hilog4j;
/*    */ 
/*    */ import com.hisun.util.HiICSProperty;
/*    */ 
/*    */ public class HiDynTrcFileName extends HiAbstractFileName
/*    */   implements IFileName
/*    */ {
/*    */   private String _trcName;
/*    */ 
/*    */   public HiDynTrcFileName(String name)
/*    */   {
/*  8 */     super(name);
/*  9 */     this._trcName = HiICSProperty.getTrcDir() + this._name + ".trc";
/*    */   }
/*    */ 
/*    */   public HiDynTrcFileName(String name, int lineLength) {
/* 13 */     super(name, lineLength);
/* 14 */     this._trcName = HiICSProperty.getTrcDir() + this._name + ".trc";
/*    */   }
/*    */ 
/*    */   public String get() {
/* 18 */     return this._trcName;
/*    */   }
/*    */ }