/*     */ package com.hisun.common.util.pattern;
/*     */ 
/*     */ import java.util.regex.Matcher;
/*     */ 
/*     */ public final class Patterns
/*     */ {
/* 832 */   private static final Pattern _never = new Pattern()
/*     */   {
/*     */     public int match(CharSequence src, int len, int from) {
/* 835 */       return -1;
/*     */     }
/* 832 */   };
/*     */ 
/* 838 */   private static final Pattern _always = new Pattern()
/*     */   {
/*     */     public int match(CharSequence src, int len, int from) {
/* 841 */       return 0;
/*     */     }
/* 838 */   };
/*     */ 
/* 953 */   private static final Pattern regex_pattern = getRegularExpressionPattern();
/* 954 */   private static final Pattern regex_modifiers = getModifiersPattern();
/*     */ 
/*     */   public static Pattern hasAtLeast(int l)
/*     */   {
/*  34 */     return new Pattern(l)
/*     */     {
/*     */       public int match(CharSequence src, int len, int from) {
/*  37 */         if (from + this.val$l > len) return -1;
/*  38 */         return this.val$l;
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   public static Pattern hasExact(int l)
/*     */   {
/*  49 */     return new Pattern(l)
/*     */     {
/*     */       public int match(CharSequence src, int len, int from) {
/*  52 */         if (from + this.val$l != len) return -1;
/*  53 */         return this.val$l;
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   public static Pattern eof()
/*     */   {
/*  63 */     return hasExact(0);
/*     */   }
/*     */ 
/*     */   public static Pattern isChar(char c)
/*     */   {
/*  73 */     return new Pattern(c)
/*     */     {
/*     */       public int match(CharSequence src, int len, int from) {
/*  76 */         if (from >= len) return -1;
/*  77 */         if (src.charAt(from) != this.val$c) return -1;
/*  78 */         return 1;
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   public static Pattern range(char c1, char c2)
/*     */   {
/*  90 */     return new Pattern(c1, c2)
/*     */     {
/*     */       public int match(CharSequence src, int len, int from) {
/*  93 */         if (from >= len) return -1;
/*  94 */         char c = src.charAt(from);
/*  95 */         if ((c >= this.val$c1) && (c <= this.val$c2)) return 1;
/*  96 */         return -1;
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   public static Pattern notRange(char c1, char c2)
/*     */   {
/* 108 */     return new Pattern(c1, c2)
/*     */     {
/*     */       public int match(CharSequence src, int len, int from) {
/* 111 */         if (from >= len) return -1;
/* 112 */         char c = src.charAt(from);
/* 113 */         if ((c >= this.val$c1) && (c <= this.val$c2)) return -1;
/* 114 */         return 1;
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   public static Pattern among(char[] cs)
/*     */   {
/* 125 */     return isChar(CharPredicates.among(cs));
/*     */   }
/*     */ 
/*     */   public static Pattern notAmong(char[] cs)
/*     */   {
/* 134 */     return isChar(CharPredicates.notAmong(cs));
/*     */   }
/*     */ 
/*     */   public static Pattern notChar(char c)
/*     */   {
/* 144 */     return new Pattern(c)
/*     */     {
/*     */       public int match(CharSequence src, int len, int from) {
/* 147 */         if (from >= len) return -1;
/* 148 */         if (src.charAt(from) == this.val$c) return -1;
/* 149 */         return 1;
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   public static Pattern isChar(CharPredicate cp)
/*     */   {
/* 161 */     return new Pattern(cp)
/*     */     {
/*     */       public int match(CharSequence src, int len, int from) {
/* 164 */         if (from >= len) return -1;
/* 165 */         if (this.val$cp.isChar(src.charAt(from))) return 1;
/* 166 */         return -1; }
/*     */ 
/*     */       public String toString() {
/* 169 */         return "" + this.val$cp;
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   public static Pattern isEscaped()
/*     */   {
/* 180 */     return new Pattern()
/*     */     {
/*     */       public int match(CharSequence src, int len, int from) {
/* 183 */         if (from >= len - 1) return -1;
/* 184 */         if (src.charAt(from) == '\\') return 2;
/* 185 */         return -1;
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   public static Pattern isLineComment(String open)
/*     */   {
/* 197 */     return seq(isString(open), many(CharPredicates.notChar('\n')));
/*     */   }
/*     */ 
/*     */   public static Pattern isString(String str)
/*     */   {
/* 204 */     return new Pattern(str)
/*     */     {
/*     */       public int match(CharSequence src, int len, int from) {
/* 207 */         if (len - from < this.val$str.length()) return -1;
/* 208 */         return Patterns.access$000(this.val$str, src, len, from); }
/*     */ 
/*     */       public String toString() {
/* 211 */         return this.val$str;
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   public static Pattern isStringCI(String str)
/*     */   {
/* 220 */     return new Pattern(str)
/*     */     {
/*     */       public int match(CharSequence src, int len, int from) {
/* 223 */         if (len - from < this.val$str.length()) return -1;
/* 224 */         return Patterns.access$100(this.val$str, src, len, from);
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   public static Pattern notString(String str)
/*     */   {
/* 234 */     return new Pattern(str)
/*     */     {
/*     */       public int match(CharSequence src, int len, int from) {
/* 237 */         if (from >= len) return -1;
/* 238 */         if (Patterns.access$000(this.val$str, src, len, from) == -1)
/* 239 */           return 1;
/* 240 */         return -1;
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   public static Pattern notStringCI(String str)
/*     */   {
/* 250 */     return new Pattern(str)
/*     */     {
/*     */       public int match(CharSequence src, int len, int from) {
/* 253 */         if (from >= len) return -1;
/* 254 */         if (Patterns.access$100(this.val$str, src, len, from) == -1)
/* 255 */           return 1;
/* 256 */         return -1;
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   private static boolean compareIgnoreCase(char a, char b) {
/* 262 */     return (Character.toLowerCase(a) == Character.toLowerCase(b));
/*     */   }
/*     */ 
/*     */   private static int matchString(String str, CharSequence src, int len, int from)
/*     */   {
/* 268 */     int slen = str.length();
/* 269 */     if (len - from < slen) return -1;
/* 270 */     for (int i = 0; i < slen; ++i) {
/* 271 */       char exp = str.charAt(i);
/* 272 */       char enc = src.charAt(from + i);
/* 273 */       if (exp != enc) {
/* 274 */         return -1;
/*     */       }
/*     */     }
/* 277 */     return slen;
/*     */   }
/*     */ 
/*     */   private static int matchStringCI(String str, CharSequence src, int len, int from)
/*     */   {
/* 283 */     int slen = str.length();
/* 284 */     if (len - from < slen) return -1;
/* 285 */     for (int i = 0; i < slen; ++i) {
/* 286 */       char exp = str.charAt(i);
/* 287 */       char enc = src.charAt(from + i);
/* 288 */       if (!(compareIgnoreCase(exp, enc))) {
/* 289 */         return -1;
/*     */       }
/*     */     }
/* 292 */     return slen;
/*     */   }
/*     */ 
/*     */   public static Pattern not(Pattern pp)
/*     */   {
/* 302 */     return new Pattern(pp)
/*     */     {
/*     */       public int match(CharSequence src, int len, int from) {
/* 305 */         if (this.val$pp.match(src, len, from) != -1) return -1;
/* 306 */         return 0;
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   public static Pattern peek(Pattern pp)
/*     */   {
/* 317 */     return new Pattern(pp)
/*     */     {
/*     */       public int match(CharSequence src, int len, int from) {
/* 320 */         if (this.val$pp.match(src, len, from) == -1) return -1;
/* 321 */         return 0;
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   public static Pattern or(Pattern pp1, Pattern pp2)
/*     */   {
/* 332 */     return new Pattern(pp1, pp2)
/*     */     {
/*     */       public int match(CharSequence src, int len, int from) {
/* 335 */         int l1 = this.val$pp1.match(src, len, from);
/* 336 */         if (l1 != -1) return l1;
/* 337 */         return this.val$pp2.match(src, len, from);
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   public static Pattern and(Pattern[] pps)
/*     */   {
/* 350 */     if (pps.length == 0) return always();
/* 351 */     if (pps.length == 1) return pps[0];
/* 352 */     return _and(pps);
/*     */   }
/*     */ 
/*     */   public static Pattern seq(Pattern pp1, Pattern pp2)
/*     */   {
/* 365 */     return new Pattern(pp1, pp2)
/*     */     {
/*     */       public int match(CharSequence src, int len, int from) {
/* 368 */         int l1 = this.val$pp1.match(src, len, from);
/* 369 */         if (l1 == -1) return l1;
/* 370 */         int l2 = this.val$pp2.match(src, len, from + l1);
/* 371 */         if (l2 == -1) return l2;
/* 372 */         return (l1 + l2);
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   public static Pattern or(Pattern[] pps)
/*     */   {
/* 383 */     if (pps.length == 0) return never();
/* 384 */     if (pps.length == 1) return pps[0];
/* 385 */     return _or(pps);
/*     */   }
/*     */ 
/*     */   public static Pattern seq(Pattern[] pps)
/*     */   {
/* 395 */     if (pps.length == 0) return always();
/* 396 */     if (pps.length == 1) return pps[0];
/* 397 */     return _seq(pps);
/*     */   }
/*     */ 
/*     */   public static Pattern repeat(int n, CharPredicate cp)
/*     */   {
/* 408 */     if (n == 0) return always();
/* 409 */     if (n == 1) return isChar(cp);
/* 410 */     return new Pattern(n, cp)
/*     */     {
/*     */       public int match(CharSequence src, int len, int from) {
/* 413 */         return Patterns.access$200(this.val$n, this.val$cp, src, len, from, 0);
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   public static Pattern repeat(int n, Pattern pp)
/*     */   {
/* 424 */     if (n == 0) return always();
/* 425 */     if (n == 1) return pp;
/* 426 */     return new Pattern(n, pp)
/*     */     {
/*     */       public int match(CharSequence src, int len, int from) {
/* 429 */         return Patterns.access$300(this.val$n, this.val$pp, src, len, from, 0);
/*     */       } } ;
/*     */   }
/*     */ 
/*     */   private static int min(int a, int b) {
/* 434 */     return ((a > b) ? b : a);
/*     */   }
/*     */ 
/*     */   public static Pattern many(int min, CharPredicate cp)
/*     */   {
/* 445 */     if (min < 0) throw new IllegalArgumentException("min<0");
/* 446 */     return new Pattern(min, cp)
/*     */     {
/*     */       public int match(CharSequence src, int len, int from) {
/* 449 */         int minlen = Patterns.access$200(this.val$min, this.val$cp, src, len, from, 0);
/* 450 */         if (minlen == -1) return -1;
/* 451 */         return Patterns.access$400(this.val$cp, src, len, from + minlen, minlen);
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   public static Pattern many(CharPredicate cp)
/*     */   {
/* 461 */     return new Pattern(cp)
/*     */     {
/*     */       public int match(CharSequence src, int len, int from) {
/* 464 */         return Patterns.access$400(this.val$cp, src, len, from, 0);
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   public static Pattern many(int min, Pattern pp)
/*     */   {
/* 477 */     if (min < 0) throw new IllegalArgumentException("min<0");
/* 478 */     return new Pattern(min, pp)
/*     */     {
/*     */       public int match(CharSequence src, int len, int from) {
/* 481 */         int minlen = Patterns.access$300(this.val$min, this.val$pp, src, len, from, 0);
/* 482 */         if (-1 == minlen) return -1;
/* 483 */         return Patterns.access$500(this.val$pp, src, len, from + minlen, minlen);
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   public static Pattern many(Pattern pp)
/*     */   {
/* 494 */     return new Pattern(pp)
/*     */     {
/*     */       public int match(CharSequence src, int len, int from) {
/* 497 */         return Patterns.access$500(this.val$pp, src, len, from, 0);
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   public static Pattern some(int min, int max, CharPredicate cp)
/*     */   {
/* 511 */     if ((max < 0) || (min < 0) || (min > max)) throw new IllegalArgumentException();
/* 512 */     if (max == 0) return always();
/* 513 */     return new Pattern(min, cp, max)
/*     */     {
/*     */       public int match(CharSequence src, int len, int from) {
/* 516 */         int minlen = Patterns.access$200(this.val$min, this.val$cp, src, len, from, 0);
/* 517 */         if (minlen == -1) return -1;
/* 518 */         return Patterns.access$600(this.val$max - this.val$min, this.val$cp, src, len, from + minlen, minlen);
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   public static Pattern some(int max, CharPredicate cp)
/*     */   {
/* 530 */     if (max < 0) throw new IllegalArgumentException("max<0");
/* 531 */     if (max == 0) return always();
/* 532 */     return new Pattern(max, cp)
/*     */     {
/*     */       public int match(CharSequence src, int len, int from) {
/* 535 */         return Patterns.access$600(this.val$max, this.val$cp, src, len, from, 0);
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   public static Pattern some(int min, int max, Pattern pp)
/*     */   {
/* 549 */     if ((min < 0) || (max < 0) || (min > max)) throw new IllegalArgumentException();
/* 550 */     if (max == 0) return always();
/* 551 */     return new Pattern(min, pp, max)
/*     */     {
/*     */       public int match(CharSequence src, int len, int from) {
/* 554 */         int minlen = Patterns.access$300(this.val$min, this.val$pp, src, len, from, 0);
/* 555 */         if (-1 == minlen) return -1;
/* 556 */         return Patterns.access$700(this.val$max - this.val$min, this.val$pp, src, len, from + minlen, minlen);
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   public static Pattern some(int max, Pattern pp)
/*     */   {
/* 568 */     if (max < 0) throw new IllegalArgumentException("max<0");
/* 569 */     if (max == 0) return always();
/* 570 */     return new Pattern(max, pp)
/*     */     {
/*     */       public int match(CharSequence src, int len, int from) {
/* 573 */         return Patterns.access$700(this.val$max, this.val$pp, src, len, from, 0);
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   public static Pattern longer(Pattern p1, Pattern p2)
/*     */   {
/* 585 */     return longest(new Pattern[] { p1, p2 });
/*     */   }
/*     */ 
/*     */   public static Pattern longest(Pattern[] pps)
/*     */   {
/* 594 */     if (pps.length == 0) return never();
/* 595 */     if (pps.length == 1) return pps[0];
/* 596 */     return new Pattern(pps)
/*     */     {
/*     */       public int match(CharSequence src, int len, int from) {
/* 599 */         int r = -1;
/* 600 */         for (int i = 0; i < this.val$pps.length; ++i) {
/* 601 */           int l = this.val$pps[i].match(src, len, from);
/* 602 */           if (l <= r) continue; r = l;
/*     */         }
/* 604 */         return r;
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   public static Pattern shorter(Pattern p1, Pattern p2)
/*     */   {
/* 616 */     return shortest(new Pattern[] { p1, p2 });
/*     */   }
/*     */ 
/*     */   public static Pattern shortest(Pattern[] pps)
/*     */   {
/* 625 */     if (pps.length == 0) return never();
/* 626 */     if (pps.length == 1) return pps[0];
/* 627 */     return new Pattern(pps)
/*     */     {
/*     */       public int match(CharSequence src, int len, int from) {
/* 630 */         int r = -1;
/* 631 */         for (int i = 0; i < this.val$pps.length; ++i) {
/* 632 */           int l = this.val$pps[i].match(src, len, from);
/* 633 */           if ((l == -1) || (
/* 634 */             (r != -1) && (l >= r))) continue;
/* 635 */           r = l;
/*     */         }
/*     */ 
/* 638 */         return r;
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   public static Pattern ifelse(Pattern cond, Pattern yes, Pattern no)
/*     */   {
/* 652 */     return new Pattern(cond, no, yes)
/*     */     {
/*     */       public int match(CharSequence src, int len, int from) {
/* 655 */         int lc = this.val$cond.match(src, len, from);
/* 656 */         if (lc == -1) {
/* 657 */           return this.val$no.match(src, len, from);
/*     */         }
/*     */ 
/* 660 */         int ly = this.val$yes.match(src, len, from + lc);
/* 661 */         if (ly == -1) return -1;
/* 662 */         return (lc + ly);
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   public static Pattern many1(CharPredicate cp)
/*     */   {
/* 674 */     return many(1, cp);
/*     */   }
/*     */ 
/*     */   public static Pattern optional(Pattern pp)
/*     */   {
/* 681 */     return new Pattern(pp)
/*     */     {
/*     */       public int match(CharSequence src, int len, int from) {
/* 684 */         int l = this.val$pp.match(src, len, from);
/* 685 */         return ((l == -1) ? 0 : l);
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   public static Pattern never()
/*     */   {
/* 695 */     return _never;
/*     */   }
/*     */ 
/*     */   public static Pattern always()
/*     */   {
/* 702 */     return _always;
/*     */   }
/*     */ 
/*     */   public static Pattern isDecimalL()
/*     */   {
/* 714 */     CharPredicate cp = CharPredicates.isDigit();
/* 715 */     return seq(many1(cp), optional(seq(isChar('.'), many(cp))));
/*     */   }
/*     */ 
/*     */   public static Pattern isDecimalR()
/*     */   {
/* 726 */     return seq(isChar('.'), many1(CharPredicates.isDigit()));
/*     */   }
/*     */ 
/*     */   public static Pattern isDecimal()
/*     */   {
/* 733 */     return or(isDecimalL(), isDecimalR());
/*     */   }
/*     */ 
/*     */   public static Pattern isWord()
/*     */   {
/* 745 */     return regex("[a-zA-Z_][0-9a-zA-Z_]*");
/*     */   }
/*     */ 
/*     */   public static Pattern isInteger()
/*     */   {
/* 752 */     return many1(CharPredicates.isDigit());
/*     */   }
/*     */ 
/*     */   public static Pattern isOctInteger()
/*     */   {
/* 759 */     return seq(isChar('0'), many(CharPredicates.range('0', '7')));
/*     */   }
/*     */ 
/*     */   public static Pattern isDecInteger()
/*     */   {
/* 767 */     return seq(range('1', '9'), many(CharPredicates.isDigit()));
/*     */   }
/*     */ 
/*     */   public static Pattern isHexInteger()
/*     */   {
/* 775 */     return seq(or(isString("0x"), isString("0X")), many1(CharPredicates.isHexDigit()));
/*     */   }
/*     */ 
/*     */   public static Pattern isExponential()
/*     */   {
/* 784 */     return seq(new Pattern[] { among(new char[] { 'e', 'E' }), optional(isChar('-')), isInteger() });
/*     */   }
/*     */ 
/*     */   public static Pattern regex(java.util.regex.Pattern p)
/*     */   {
/* 796 */     return new Pattern(p)
/*     */     {
/*     */       public int match(CharSequence src, int len, int from) {
/* 799 */         if (from > len) return -1;
/* 800 */         Matcher matcher = this.val$p.matcher(src.subSequence(from, len));
/* 801 */         if (matcher.lookingAt()) {
/* 802 */           return matcher.end();
/*     */         }
/* 804 */         return -1;
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   public static Pattern regex(String s)
/*     */   {
/* 814 */     return regex(java.util.regex.Pattern.compile(s));
/*     */   }
/*     */ 
/*     */   public static Pattern regex_pattern()
/*     */   {
/* 823 */     return regex_pattern;
/*     */   }
/*     */ 
/*     */   public static Pattern regex_modifiers()
/*     */   {
/* 830 */     return regex_modifiers;
/*     */   }
/*     */ 
/*     */   private static int match_repeat(int n, CharPredicate cp, CharSequence src, int len, int from, int acc)
/*     */   {
/* 847 */     int tail = from + n;
/* 848 */     if (tail > len) return -1;
/* 849 */     for (int i = from; i < tail; ++i) {
/* 850 */       if (!(cp.isChar(src.charAt(i)))) return -1;
/*     */     }
/* 852 */     return (n + acc);
/*     */   }
/*     */ 
/*     */   private static int match_repeat(int n, Pattern pp, CharSequence src, int len, int from, int acc) {
/* 856 */     int end = from;
/* 857 */     for (int i = 0; i < n; ++i) {
/* 858 */       int l = pp.match(src, len, end);
/* 859 */       if (l == -1) return -1;
/* 860 */       end += l;
/*     */     }
/* 862 */     return (end - from + acc);
/*     */   }
/*     */ 
/*     */   private static int match_some(int max, CharPredicate cp, CharSequence src, int len, int from, int acc) {
/* 866 */     int k = min(max + from, len);
/* 867 */     for (int i = from; i < k; ++i) {
/* 868 */       if (!(cp.isChar(src.charAt(i)))) return (i - from + acc);
/*     */     }
/* 870 */     return (k - from + acc);
/*     */   }
/*     */ 
/*     */   private static int match_some(int max, Pattern pp, CharSequence src, int len, int from, int acc) {
/* 874 */     int begin = from;
/* 875 */     for (int i = 0; i < max; ++i) {
/* 876 */       int l = pp.match(src, len, begin);
/* 877 */       if (-1 == l) return (begin - from + acc);
/* 878 */       begin += l;
/*     */     }
/* 880 */     return (begin - from + acc);
/*     */   }
/*     */ 
/*     */   private static int match_many(CharPredicate cp, CharSequence src, int len, int from, int acc) {
/* 884 */     for (int i = from; i < len; ++i) {
/* 885 */       if (!(cp.isChar(src.charAt(i)))) return (i - from + acc);
/*     */     }
/* 887 */     return (len - from + acc);
/*     */   }
/*     */ 
/*     */   private static int match_many(Pattern pp, CharSequence src, int len, int from, int acc) {
/* 891 */     int i = from;
/*     */     while (true) { int l = pp.match(src, len, i);
/* 893 */       if (-1 == l) return (i - from + acc);
/*     */ 
/* 895 */       if (l == 0) return (i - from + acc);
/* 896 */       i += l; }
/*     */   }
/*     */ 
/*     */   private static Pattern _or(Pattern[] pps) {
/* 900 */     return new Pattern(pps)
/*     */     {
/*     */       public int match(CharSequence src, int len, int from) {
/* 903 */         for (int i = 0; i < this.val$pps.length; ++i) {
/* 904 */           int l = this.val$pps[i].match(src, len, from);
/* 905 */           if (l != -1) return l;
/*     */         }
/* 907 */         return -1;
/*     */       } } ;
/*     */   }
/*     */ 
/*     */   private static Pattern _seq(Pattern[] pps) {
/* 912 */     return new Pattern(pps)
/*     */     {
/*     */       public int match(CharSequence src, int len, int from) {
/* 915 */         int end = from;
/* 916 */         for (int i = 0; i < this.val$pps.length; ++i) {
/* 917 */           int l = this.val$pps[i].match(src, len, end);
/* 918 */           if (l == -1) return l;
/* 919 */           end += l;
/*     */         }
/* 921 */         return (end - from);
/*     */       } } ;
/*     */   }
/*     */ 
/*     */   private static Pattern _and(Pattern[] pps) {
/* 926 */     return new Pattern(pps)
/*     */     {
/*     */       public int match(CharSequence src, int len, int from) {
/* 929 */         int ret = 0;
/* 930 */         for (int i = 0; i < this.val$pps.length; ++i) {
/* 931 */           int l = this.val$pps[i].match(src, len, from);
/* 932 */           if (l == -1) return -1;
/* 933 */           if (l <= ret) continue; ret = l;
/*     */         }
/* 935 */         return ret;
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   private static final Pattern getRegularExpressionPattern() {
/* 941 */     Pattern quote = isChar('/');
/* 942 */     Pattern escape = isChar('\\').seq(hasAtLeast(1));
/*     */ 
/* 944 */     char[] not_allowed = { '/', '\n', '\r', '\\' };
/* 945 */     Pattern content = or(escape, notAmong(not_allowed));
/*     */ 
/* 948 */     return quote.seq(content.many()).seq(quote); }
/*     */ 
/*     */   private static final Pattern getModifiersPattern() {
/* 951 */     return isChar(CharPredicates.isAlpha()).many();
/*     */   }
/*     */ }