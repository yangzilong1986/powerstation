/*     */ package com.hisun.cfg;
/*     */ 
/*     */ import com.hisun.util.HiConvHelper;
/*     */ 
/*     */ public class HiTlvHelper
/*     */ {
/*     */   public static String getTag(byte[] bytes, int tag_type)
/*     */   {
/*  97 */     switch (tag_type)
/*     */     {
/*     */     case 1:
/* 101 */       return HiConvHelper.bcd2AscStr(bytes);
/*     */     case 2:
/* 105 */       return new String(bytes);
/*     */     case 0:
/* 109 */       return Integer.valueOf(HiConvHelper.bcd2AscStr(bytes), 16).toString();
/*     */     }
/*     */ 
/* 112 */     return HiConvHelper.bcd2AscStr(bytes);
/*     */   }
/*     */ }