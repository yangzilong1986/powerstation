/*    */ package com.hisun.sw;
/*    */ 
/*    */ public class HiChar
/*    */ {
/*    */   public byte highByte;
/*    */   public byte lowByte;
/*    */ 
/*    */   public HiChar()
/*    */   {
/*    */   }
/*    */ 
/*    */   public HiChar(byte highByte, byte lowByte)
/*    */   {
/* 12 */     this.highByte = highByte;
/* 13 */     this.lowByte = lowByte;
/*    */   }
/*    */ 
/*    */   public void setHigh(int high) {
/* 17 */     this.highByte = (byte)high;
/*    */   }
/*    */ 
/*    */   public void setLow(int low) {
/* 21 */     this.lowByte = (byte)low;
/*    */   }
/*    */ 
/*    */   public int high() {
/* 25 */     return (this.highByte & 0xFF);
/*    */   }
/*    */ 
/*    */   public int low() {
/* 29 */     return (this.lowByte & 0xFF);
/*    */   }
/*    */ }