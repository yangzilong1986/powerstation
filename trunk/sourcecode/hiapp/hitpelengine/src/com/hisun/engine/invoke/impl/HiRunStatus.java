 package com.hisun.engine.invoke.impl;
 
 import com.hisun.message.HiContext;
 
 public class HiRunStatus
 {
   private static final String KEY = "__RUN_STATUS";
   private int crctReg;
   private int scrcStart;
   private int rsndReg;
   private boolean response;
 
   public HiRunStatus()
   {
     this.crctReg = 0;
 
     this.scrcStart = 0;
 
     this.rsndReg = 0;
 
     this.response = true; }
 
   public void setSCRCStart(boolean b) {
     if (b)
       this.scrcStart = 1;
     else
       this.scrcStart = 0;
   }
 
   public int getSCRCStart() {
     return this.scrcStart;
   }
 
   public boolean isSCRCStart()
   {
     return (this.scrcStart == 1);
   }
 
   public void setCRCTReg() {
     this.crctReg = 1;
   }
 
   public int getCRCTReg() {
     return this.crctReg;
   }
 
   public boolean isCRCTReg()
   {
     return (this.crctReg == 1);
   }
 
   public void setRSNDNotReg() {
     this.rsndReg = 0;
   }
 
   public int getRSNDReg() {
     return this.rsndReg;
   }
 
   public boolean isRSNDNotReg() {
     return (this.rsndReg == 0);
   }
 
   public void setRSNDReg() {
     this.rsndReg = 1;
   }
 
   public boolean isRSNDReg() {
     return (this.rsndReg == 1);
   }
 
   public void setRSNDPreReg() {
     this.rsndReg = 2;
   }
 
   public boolean isRSNDPreReg() {
     return (this.rsndReg == 2);
   }
 
   public boolean isResponse() {
     return this.response;
   }
 
   public void setResponse(boolean response) {
     this.response = response;
   }
 
   public static void setRunStatus(HiContext ctx, HiRunStatus runStatus) {
     ctx.setProperty("__RUN_STATUS", runStatus);
   }
 
   public static HiRunStatus getRunStatus(HiContext ctx) {
     return ((HiRunStatus)ctx.getProperty("__RUN_STATUS"));
   }
 
   public String toString() {
     return "crctReg:" + this.crctReg + ";scrcStart:" + this.scrcStart + ";rsndReg:" + this.rsndReg + ";response:" + this.response;
   }
 }