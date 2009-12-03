/*     */ package com.hisun.common.util.pattern;
/*     */ 
/*     */ public final class CharPredicates
/*     */ {
/* 241 */   private static final CharPredicate _never = new CharPredicate() {
/*     */     public boolean isChar(char c) { return false;
/*     */     }
/* 241 */   };
/*     */ 
/* 244 */   private static final CharPredicate _always = new CharPredicate() {
/*     */     public boolean isChar(char c) { return true;
/*     */     }
/* 244 */   };
/*     */ 
/*     */   public static CharPredicate isChar(char a)
/*     */   {
/*  26 */     return new CharPredicate(a) {
/*     */       public boolean isChar(char c) {
/*  28 */         return (c == this.val$a);
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   public static CharPredicate notChar(char a)
/*     */   {
/*  36 */     return new CharPredicate(a) {
/*     */       public boolean isChar(char c) {
/*  38 */         return (c != this.val$a);
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   public static CharPredicate range(char a, char b)
/*     */   {
/*  46 */     return new CharPredicate(a, b) {
/*     */       public boolean isChar(char c) {
/*  48 */         return ((c >= this.val$a) && (c <= this.val$b));
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   public static CharPredicate isDigit()
/*     */   {
/*  56 */     return range('0', '9');
/*     */   }
/*     */ 
/*     */   public static CharPredicate notRange(char a, char b)
/*     */   {
/*  62 */     return new CharPredicate(a, b) {
/*     */       public boolean isChar(char c) {
/*  64 */         return ((c < this.val$a) || (c > this.val$b));
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   public static CharPredicate among(char[] chars)
/*     */   {
/*  72 */     return new CharPredicate(chars) {
/*     */       public boolean isChar(char c) {
/*  74 */         return CharPredicates.charAmong(c, this.val$chars);
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   public static CharPredicate notAmong(char[] chars)
/*     */   {
/*  82 */     return new CharPredicate(chars) {
/*     */       public boolean isChar(char c) {
/*  84 */         return (!(CharPredicates.charAmong(c, this.val$chars)));
/*     */       } } ;
/*     */   }
/*     */ 
/*     */   static boolean charAmong(char c, char[] chars) {
/*  89 */     for (int i = 0; i < chars.length; ++i) {
/*  90 */       if (c == chars[i]) return true;
/*     */     }
/*  92 */     return false;
/*     */   }
/*     */ 
/*     */   public static CharPredicate isHexDigit()
/*     */   {
/*  98 */     return new CharPredicate() {
/*     */       public boolean isChar(char c) {
/* 100 */         return (((c >= '0') && (c <= '9')) || ((c >= 'a') && (c <= 'f')) || ((c >= 'A') && (c <= 'F')));
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   public static CharPredicate isUppercase()
/*     */   {
/* 108 */     return new CharPredicate() {
/*     */       public boolean isChar(char c) { return Character.isUpperCase(c);
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   public static CharPredicate isLowercase()
/*     */   {
/* 116 */     return new CharPredicate() {
/*     */       public boolean isChar(char c) { return Character.isLowerCase(c);
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   public static CharPredicate isWhitespace()
/*     */   {
/* 124 */     return new CharPredicate() {
/*     */       public boolean isChar(char c) { return Character.isWhitespace(c);
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   public static CharPredicate isAlpha()
/*     */   {
/* 132 */     return new CharPredicate() {
/*     */       public boolean isChar(char c) {
/* 134 */         return (((c <= 'z') && (c >= 'a')) || ((c <= 'Z') && (c >= 'A')));
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   public static CharPredicate isAlpha_()
/*     */   {
/* 142 */     return new CharPredicate() {
/*     */       public boolean isChar(char c) {
/* 144 */         return ((c == '_') || ((c <= 'z') && (c >= 'a')) || ((c <= 'Z') && (c >= 'A')));
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   public static CharPredicate isLetter()
/*     */   {
/* 152 */     return new CharPredicate() {
/*     */       public boolean isChar(char c) {
/* 154 */         return Character.isLetter(c);
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   public static CharPredicate isAlphaNumeric()
/*     */   {
/* 162 */     return new CharPredicate() {
/*     */       public boolean isChar(char c) {
/* 164 */         return ((c == '_') || ((c >= 'A') && (c <= 'Z')) || ((c >= 'a') && (c <= 'z')) || ((c >= '0') && (c <= '9')));
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   public static CharPredicate not(CharPredicate cp)
/*     */   {
/* 172 */     return new CharPredicate(cp) {
/*     */       public boolean isChar(char c) {
/* 174 */         return (!(this.val$cp.isChar(c)));
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   public static CharPredicate and(CharPredicate cp1, CharPredicate cp2)
/*     */   {
/* 182 */     return new CharPredicate(cp1, cp2) {
/*     */       public boolean isChar(char c) {
/* 184 */         return ((this.val$cp1.isChar(c)) && (this.val$cp2.isChar(c)));
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   public static CharPredicate or(CharPredicate cp1, CharPredicate cp2)
/*     */   {
/* 192 */     return new CharPredicate(cp1, cp2) {
/*     */       public boolean isChar(char c) {
/* 194 */         return ((this.val$cp1.isChar(c)) || (this.val$cp2.isChar(c)));
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   public static CharPredicate and(CharPredicate[] cps)
/*     */   {
/* 203 */     if (cps.length == 0) return always();
/* 204 */     if (cps.length == 1) return cps[0];
/* 205 */     return new CharPredicate(cps) {
/*     */       public boolean isChar(char c) {
/* 207 */         for (int i = 0; i < this.val$cps.length; ++i) {
/* 208 */           if (!(this.val$cps[i].isChar(c))) return false;
/*     */         }
/* 210 */         return true;
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   public static CharPredicate or(CharPredicate[] cps)
/*     */   {
/* 218 */     if (cps.length == 0) return never();
/* 219 */     if (cps.length == 1) return cps[0];
/* 220 */     return new CharPredicate(cps) {
/*     */       public boolean isChar(char c) {
/* 222 */         for (int i = 0; i < this.val$cps.length; ++i) {
/* 223 */           if (this.val$cps[i].isChar(c)) return true;
/*     */         }
/* 225 */         return false;
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   public static CharPredicate never()
/*     */   {
/* 233 */     return _never;
/*     */   }
/*     */ 
/*     */   public static CharPredicate always()
/*     */   {
/* 239 */     return _always;
/*     */   }
/*     */ }