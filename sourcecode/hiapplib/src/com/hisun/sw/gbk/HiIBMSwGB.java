/*     */ package com.hisun.sw.gbk;
/*     */ 
/*     */ import com.hisun.sw.HiChar;
/*     */ 
/*     */ public class HiIBMSwGB
/*     */ {
/*     */   public static void GBKtoEBCDIC(HiChar pchFrom, HiChar pchTo)
/*     */   {
/*  14 */     if (pchFrom.high() >= 254) {
/*  15 */       GBKtoEBCDIC3(pchFrom, pchTo);
/*  16 */       return;
/*     */     }
/*  18 */     if (pchFrom.high() > 161) {
/*  19 */       GBKtoEBCDIC2(pchFrom, pchTo);
/*  20 */       return;
/*     */     }
/*     */ 
/*  23 */     int iLineWord = 190;
/*  24 */     int iIndex = (pchFrom.high() - 129) * iLineWord + pchFrom.low() - 64;
/*  25 */     if (pchFrom.low() > 127) {
/*  26 */       --iIndex;
/*     */     }
/*  28 */     int iNewLineWord = 188;
/*  29 */     int iToLine = iIndex / iNewLineWord;
/*  30 */     int iToCol = iIndex % iNewLineWord;
/*     */ 
/*  32 */     pchTo.setHigh(iToLine + 129);
/*  33 */     pchTo.setLow(iToCol + 65);
/*  34 */     if (pchTo.low() >= 128)
/*  35 */       pchTo.setLow(pchTo.low() + 1);
/*     */   }
/*     */ 
/*     */   public static void GBKtoEBCDIC2(HiChar pchFrom, HiChar pchTo)
/*     */   {
/*  44 */     int iLineWord = 96;
/*  45 */     int iIndex = (pchFrom.high() - 170) * iLineWord + pchFrom.low();
/*  46 */     if (pchFrom.low() > 127) {
/*  47 */       --iIndex;
/*     */     }
/*  49 */     int iNewLineWord = 188;
/*  50 */     int iToLine = iIndex / iNewLineWord;
/*  51 */     int iToCol = iIndex % iNewLineWord;
/*     */ 
/*  53 */     pchTo.setHigh(iToLine + 161);
/*  54 */     pchTo.setLow(iToCol + 65);
/*     */ 
/*  56 */     if (pchTo.low() >= 128)
/*  57 */       pchTo.setLow(pchTo.low() + 1);
/*     */   }
/*     */ 
/*     */   public static void GBKtoEBCDIC3(HiChar pchFrom, HiChar pchTo)
/*     */   {
/*  64 */     pchTo.setHigh(pchFrom.highByte - 48);
/*  65 */     pchTo.setLow(pchFrom.lowByte + 6);
/*     */   }
/*     */ 
/*     */   public static void EBCDICtoGBK(HiChar pchFrom, HiChar pchTo)
/*     */   {
/*  72 */     if (pchFrom.high() == 206) {
/*  73 */       EBCDICtoGBK3(pchFrom, pchTo);
/*  74 */       return;
/*     */     }
/*  76 */     if ((pchFrom.high() >= 162) || ((pchFrom.high() == 161) && (pchFrom.low() >= 130))) {
/*  77 */       EBCDICtoGBK2(pchFrom, pchTo);
/*  78 */       return;
/*     */     }
/*     */ 
/*  81 */     int iLineWord = 188;
/*  82 */     int iIndex = (pchFrom.high() - 129) * iLineWord + pchFrom.low() - 65;
/*  83 */     if (pchFrom.low() > 128) {
/*  84 */       --iIndex;
/*     */     }
/*  86 */     int iNewLineWord = 190;
/*  87 */     int iToLine = iIndex / iNewLineWord;
/*  88 */     int iToCol = iIndex % iNewLineWord;
/*     */ 
/*  90 */     pchTo.setHigh(iToLine + 129);
/*  91 */     pchTo.setLow(iToCol + 64);
/*     */ 
/*  93 */     if (pchTo.low() >= 127)
/*  94 */       pchTo.setLow(pchTo.low() + 1);
/*     */   }
/*     */ 
/*     */   public static void EBCDICtoGBK2(HiChar pchFrom, HiChar pchTo)
/*     */   {
/* 104 */     int iLineWord = 188;
/* 105 */     int iIndex = (pchFrom.high() - 161) * iLineWord + pchFrom.low() - 129;
/* 106 */     if (pchFrom.low() > 128) {
/* 107 */       --iIndex;
/*     */     }
/* 109 */     int iNewLineWord = 96;
/* 110 */     int iToLine = iIndex / iNewLineWord;
/* 111 */     int iToCol = iIndex % iNewLineWord;
/*     */ 
/* 113 */     pchTo.setHigh(iToLine + 170);
/* 114 */     pchTo.setLow(iToCol + 64);
/*     */ 
/* 116 */     if (pchTo.low() >= 127)
/* 117 */       pchTo.setLow(pchTo.low() + 1);
/*     */   }
/*     */ 
/*     */   public static void EBCDICtoGBK3(HiChar pchFrom, HiChar pchTo)
/*     */   {
/* 124 */     pchTo.setHigh(pchFrom.highByte + 48);
/* 125 */     pchTo.setLow(pchFrom.lowByte - 6);
/*     */   }
/*     */ }