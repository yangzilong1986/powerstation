/*    */ package com.hisun.hilog4j;
/*    */ 
/*    */ public abstract class HiAbstractFileName
/*    */   implements IFileName
/*    */ {
/*  4 */   protected String _name = null;
/*  5 */   protected int _lineLength = -1;
/*  6 */   protected boolean _isFixedSizeable = true;
/*    */ 
/*    */   public HiAbstractFileName(String name, int lineLength) {
/*  9 */     this._name = name;
/* 10 */     this._lineLength = lineLength;
/*    */   }
/*    */ 
/*    */   public HiAbstractFileName(String name, int lineLength, boolean fixSizeable) {
/* 14 */     this._name = name;
/* 15 */     this._lineLength = lineLength;
/* 16 */     this._isFixedSizeable = fixSizeable;
/*    */   }
/*    */ 
/*    */   public HiAbstractFileName(String name)
/*    */   {
/* 21 */     this._name = name;
/*    */   }
/*    */ 
/*    */   public int getLineLength() {
/* 25 */     return this._lineLength;
/*    */   }
/*    */ 
/*    */   public void setLineLength(int lineLength) {
/* 29 */     this._lineLength = lineLength;
/*    */   }
/*    */ 
/*    */   public boolean isFixedSizeable() {
/* 33 */     return this._isFixedSizeable;
/*    */   }
/*    */ 
/*    */   public void setFixedSizeable(boolean isFixedSize) {
/* 37 */     this._isFixedSizeable = isFixedSize;
/*    */   }
/*    */ 
/*    */   public String name() {
/* 41 */     return this._name;
/*    */   }
/*    */ 
/*    */   public String toString() {
/* 45 */     return this._name + ":" + this._lineLength + ":" + this._isFixedSizeable;
/*    */   }
/*    */ }