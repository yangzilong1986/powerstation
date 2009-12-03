/*     */ package com.hisun.engine.invoke.impl;
/*     */ 
/*     */ import com.hisun.message.HiContext;
/*     */ 
/*     */ public class HiRunStatus
/*     */ {
/*     */   private static final String KEY = "__RUN_STATUS";
/*     */   private int crctReg;
/*     */   private int scrcStart;
/*     */   private int rsndReg;
/*     */   private boolean response;
/*     */ 
/*     */   public HiRunStatus()
/*     */   {
/*  12 */     this.crctReg = 0;
/*     */ 
/*  18 */     this.scrcStart = 0;
/*     */ 
/*  23 */     this.rsndReg = 0;
/*     */ 
/*  28 */     this.response = true; }
/*     */ 
/*     */   public void setSCRCStart(boolean b) {
/*  31 */     if (b)
/*  32 */       this.scrcStart = 1;
/*     */     else
/*  34 */       this.scrcStart = 0;
/*     */   }
/*     */ 
/*     */   public int getSCRCStart() {
/*  38 */     return this.scrcStart;
/*     */   }
/*     */ 
/*     */   public boolean isSCRCStart()
/*     */   {
/*  45 */     return (this.scrcStart == 1);
/*     */   }
/*     */ 
/*     */   public void setCRCTReg() {
/*  49 */     this.crctReg = 1;
/*     */   }
/*     */ 
/*     */   public int getCRCTReg() {
/*  53 */     return this.crctReg;
/*     */   }
/*     */ 
/*     */   public boolean isCRCTReg()
/*     */   {
/*  61 */     return (this.crctReg == 1);
/*     */   }
/*     */ 
/*     */   public void setRSNDNotReg() {
/*  65 */     this.rsndReg = 0;
/*     */   }
/*     */ 
/*     */   public int getRSNDReg() {
/*  69 */     return this.rsndReg;
/*     */   }
/*     */ 
/*     */   public boolean isRSNDNotReg() {
/*  73 */     return (this.rsndReg == 0);
/*     */   }
/*     */ 
/*     */   public void setRSNDReg() {
/*  77 */     this.rsndReg = 1;
/*     */   }
/*     */ 
/*     */   public boolean isRSNDReg() {
/*  81 */     return (this.rsndReg == 1);
/*     */   }
/*     */ 
/*     */   public void setRSNDPreReg() {
/*  85 */     this.rsndReg = 2;
/*     */   }
/*     */ 
/*     */   public boolean isRSNDPreReg() {
/*  89 */     return (this.rsndReg == 2);
/*     */   }
/*     */ 
/*     */   public boolean isResponse() {
/*  93 */     return this.response;
/*     */   }
/*     */ 
/*     */   public void setResponse(boolean response) {
/*  97 */     this.response = response;
/*     */   }
/*     */ 
/*     */   public static void setRunStatus(HiContext ctx, HiRunStatus runStatus) {
/* 101 */     ctx.setProperty("__RUN_STATUS", runStatus);
/*     */   }
/*     */ 
/*     */   public static HiRunStatus getRunStatus(HiContext ctx) {
/* 105 */     return ((HiRunStatus)ctx.getProperty("__RUN_STATUS"));
/*     */   }
/*     */ 
/*     */   public String toString() {
/* 109 */     return "crctReg:" + this.crctReg + ";scrcStart:" + this.scrcStart + ";rsndReg:" + this.rsndReg + ";response:" + this.response;
/*     */   }
/*     */ }