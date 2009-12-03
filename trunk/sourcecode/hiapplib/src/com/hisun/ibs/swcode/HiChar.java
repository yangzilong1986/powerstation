/*    */ package com.hisun.ibs.swcode;
/*    */ 
/*    */ public class HiChar
/*    */ {
/*    */   private byte _highbyte;
/*    */   private byte _lowbyte;
/*    */ 
/*    */   public HiChar()
/*    */   {
/*    */   }
/*    */ 
/*    */   public HiChar(int i)
/*    */   {
/* 10 */     setHighbyte(i / 255);
/* 11 */     setLowbyte(i % 255);
/*    */   }
/*    */ 
/*    */   public HiChar(byte highbyte, byte lowbyte) {
/* 15 */     setHighbyte(highbyte);
/* 16 */     setLowbyte(lowbyte);
/*    */   }
/*    */ 
/*    */   public byte getHighbyte() {
/* 20 */     return this._highbyte;
/*    */   }
/*    */ 
/*    */   public void setHighbyte(byte highbyte) {
/* 24 */     this._highbyte = highbyte;
/*    */   }
/*    */ 
/*    */   public void setHighbyte(int highbyte) {
/* 28 */     this._highbyte = (byte)highbyte;
/*    */   }
/*    */ 
/*    */   public byte getLowbyte() {
/* 32 */     return this._lowbyte;
/*    */   }
/*    */ 
/*    */   public void setLowbyte(byte lowbyte) {
/* 36 */     this._lowbyte = lowbyte;
/*    */   }
/*    */ 
/*    */   public void setLowbyte(int lowbyte) {
/* 40 */     this._lowbyte = (byte)lowbyte;
/*    */   }
/*    */ }