/*     */ package com.hisun.common.util.pattern;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public abstract class Pattern
/*     */   implements Serializable
/*     */ {
/*     */   public static final int MISMATCH = -1;
/*     */ 
/*     */   public abstract int match(CharSequence paramCharSequence, int paramInt1, int paramInt2);
/*     */ 
/*     */   public final Pattern seq(Pattern p2)
/*     */   {
/*  55 */     return Patterns.seq(this, p2);
/*     */   }
/*     */ 
/*     */   public final Pattern optional()
/*     */   {
/*  62 */     return Patterns.optional(this);
/*     */   }
/*     */ 
/*     */   public final Pattern many()
/*     */   {
/*  70 */     return Patterns.many(this);
/*     */   }
/*     */ 
/*     */   public final Pattern many(int min)
/*     */   {
/*  79 */     return Patterns.many(min, this);
/*     */   }
/*     */ 
/*     */   public final Pattern many1()
/*     */   {
/*  87 */     return Patterns.many(1, this);
/*     */   }
/*     */ 
/*     */   public final Pattern some(int max)
/*     */   {
/*  96 */     return Patterns.some(max, this);
/*     */   }
/*     */ 
/*     */   public final Pattern some(int min, int max)
/*     */   {
/* 107 */     return Patterns.some(min, max, this);
/*     */   }
/*     */ 
/*     */   public final Pattern not()
/*     */   {
/* 115 */     return Patterns.not(this);
/*     */   }
/*     */ 
/*     */   public final Pattern peek()
/*     */   {
/* 123 */     return Patterns.peek(this);
/*     */   }
/*     */ 
/*     */   public final Pattern ifelse(Pattern yes, Pattern no)
/*     */   {
/* 135 */     return Patterns.ifelse(this, yes, no);
/*     */   }
/*     */ 
/*     */   public final Pattern repeat(int n)
/*     */   {
/* 143 */     return Patterns.repeat(n, this);
/*     */   }
/*     */ }