 package com.hisun.ibs.swcode;
 
 public class HiChar
 {
   private byte _highbyte;
   private byte _lowbyte;
 
   public HiChar()
   {
   }
 
   public HiChar(int i)
   {
     setHighbyte(i / 255);
     setLowbyte(i % 255);
   }
 
   public HiChar(byte highbyte, byte lowbyte) {
     setHighbyte(highbyte);
     setLowbyte(lowbyte);
   }
 
   public byte getHighbyte() {
     return this._highbyte;
   }
 
   public void setHighbyte(byte highbyte) {
     this._highbyte = highbyte;
   }
 
   public void setHighbyte(int highbyte) {
     this._highbyte = (byte)highbyte;
   }
 
   public byte getLowbyte() {
     return this._lowbyte;
   }
 
   public void setLowbyte(byte lowbyte) {
     this._lowbyte = lowbyte;
   }
 
   public void setLowbyte(int lowbyte) {
     this._lowbyte = (byte)lowbyte;
   }
 }