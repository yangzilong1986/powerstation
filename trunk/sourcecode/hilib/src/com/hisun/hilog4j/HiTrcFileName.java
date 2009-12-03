/*    */ package com.hisun.hilog4j;
/*    */ 
/*    */ import com.hisun.util.HiICSProperty;
/*    */ 
/*    */ public class HiTrcFileName extends HiAbstractFileName
/*    */   implements IFileName
/*    */ {
/*    */   public HiTrcFileName(String name)
/*    */   {
/*  8 */     super(name);
/*    */   }
/*    */ 
/*    */   public HiTrcFileName(String name, int lineLength) {
/* 12 */     super(name, lineLength);
/*    */   }
/*    */ 
/*    */   public HiTrcFileName(String name, int lineLength, boolean fixSizeable) {
/* 16 */     super(name, lineLength, fixSizeable);
/*    */   }
/*    */ 
/*    */   public String get() {
/* 20 */     return HiICSProperty.getTrcDir() + this._name;
/*    */   }
/*    */ }