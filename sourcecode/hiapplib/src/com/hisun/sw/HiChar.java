 package com.hisun.sw;
 
 public class HiChar
 {
   public byte highByte;
   public byte lowByte;
 
   public HiChar()
   {
   }
 
   public HiChar(byte highByte, byte lowByte)
   {
     this.highByte = highByte;
     this.lowByte = lowByte;
   }
 
   public void setHigh(int high) {
     this.highByte = (byte)high;
   }
 
   public void setLow(int low) {
     this.lowByte = (byte)low;
   }
 
   public int high() {
     return (this.highByte & 0xFF);
   }
 
   public int low() {
     return (this.lowByte & 0xFF);
   }
 }