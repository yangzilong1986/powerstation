 package com.hisun.common.util.pattern;
 
 import java.util.regex.Matcher;
 
 public final class Patterns
 {
   private static final Pattern _never = new Pattern()
   {
     public int match(CharSequence src, int len, int from) {
       return -1;
     }
   };
 
   private static final Pattern _always = new Pattern()
   {
     public int match(CharSequence src, int len, int from) {
       return 0;
     }
   };
 
   private static final Pattern regex_pattern = getRegularExpressionPattern();
   private static final Pattern regex_modifiers = getModifiersPattern();
 
   public static Pattern hasAtLeast(int l)
   {
     return new Pattern(l)
     {
       public int match(CharSequence src, int len, int from) {
         if (from + this.val$l > len) return -1;
         return this.val$l;
       }
     };
   }
 
   public static Pattern hasExact(int l)
   {
     return new Pattern(l)
     {
       public int match(CharSequence src, int len, int from) {
         if (from + this.val$l != len) return -1;
         return this.val$l;
       }
     };
   }
 
   public static Pattern eof()
   {
     return hasExact(0);
   }
 
   public static Pattern isChar(char c)
   {
     return new Pattern(c)
     {
       public int match(CharSequence src, int len, int from) {
         if (from >= len) return -1;
         if (src.charAt(from) != this.val$c) return -1;
         return 1;
       }
     };
   }
 
   public static Pattern range(char c1, char c2)
   {
     return new Pattern(c1, c2)
     {
       public int match(CharSequence src, int len, int from) {
         if (from >= len) return -1;
         char c = src.charAt(from);
         if ((c >= this.val$c1) && (c <= this.val$c2)) return 1;
         return -1;
       }
     };
   }
 
   public static Pattern notRange(char c1, char c2)
   {
     return new Pattern(c1, c2)
     {
       public int match(CharSequence src, int len, int from) {
         if (from >= len) return -1;
         char c = src.charAt(from);
         if ((c >= this.val$c1) && (c <= this.val$c2)) return -1;
         return 1;
       }
     };
   }
 
   public static Pattern among(char[] cs)
   {
     return isChar(CharPredicates.among(cs));
   }
 
   public static Pattern notAmong(char[] cs)
   {
     return isChar(CharPredicates.notAmong(cs));
   }
 
   public static Pattern notChar(char c)
   {
     return new Pattern(c)
     {
       public int match(CharSequence src, int len, int from) {
         if (from >= len) return -1;
         if (src.charAt(from) == this.val$c) return -1;
         return 1;
       }
     };
   }
 
   public static Pattern isChar(CharPredicate cp)
   {
     return new Pattern(cp)
     {
       public int match(CharSequence src, int len, int from) {
         if (from >= len) return -1;
         if (this.val$cp.isChar(src.charAt(from))) return 1;
         return -1; }
 
       public String toString() {
         return "" + this.val$cp;
       }
     };
   }
 
   public static Pattern isEscaped()
   {
     return new Pattern()
     {
       public int match(CharSequence src, int len, int from) {
         if (from >= len - 1) return -1;
         if (src.charAt(from) == '\\') return 2;
         return -1;
       }
     };
   }
 
   public static Pattern isLineComment(String open)
   {
     return seq(isString(open), many(CharPredicates.notChar('\n')));
   }
 
   public static Pattern isString(String str)
   {
     return new Pattern(str)
     {
       public int match(CharSequence src, int len, int from) {
         if (len - from < this.val$str.length()) return -1;
         return Patterns.access$000(this.val$str, src, len, from); }
 
       public String toString() {
         return this.val$str;
       }
     };
   }
 
   public static Pattern isStringCI(String str)
   {
     return new Pattern(str)
     {
       public int match(CharSequence src, int len, int from) {
         if (len - from < this.val$str.length()) return -1;
         return Patterns.access$100(this.val$str, src, len, from);
       }
     };
   }
 
   public static Pattern notString(String str)
   {
     return new Pattern(str)
     {
       public int match(CharSequence src, int len, int from) {
         if (from >= len) return -1;
         if (Patterns.access$000(this.val$str, src, len, from) == -1)
           return 1;
         return -1;
       }
     };
   }
 
   public static Pattern notStringCI(String str)
   {
     return new Pattern(str)
     {
       public int match(CharSequence src, int len, int from) {
         if (from >= len) return -1;
         if (Patterns.access$100(this.val$str, src, len, from) == -1)
           return 1;
         return -1;
       }
     };
   }
 
   private static boolean compareIgnoreCase(char a, char b) {
     return (Character.toLowerCase(a) == Character.toLowerCase(b));
   }
 
   private static int matchString(String str, CharSequence src, int len, int from)
   {
     int slen = str.length();
     if (len - from < slen) return -1;
     for (int i = 0; i < slen; ++i) {
       char exp = str.charAt(i);
       char enc = src.charAt(from + i);
       if (exp != enc) {
         return -1;
       }
     }
     return slen;
   }
 
   private static int matchStringCI(String str, CharSequence src, int len, int from)
   {
     int slen = str.length();
     if (len - from < slen) return -1;
     for (int i = 0; i < slen; ++i) {
       char exp = str.charAt(i);
       char enc = src.charAt(from + i);
       if (!(compareIgnoreCase(exp, enc))) {
         return -1;
       }
     }
     return slen;
   }
 
   public static Pattern not(Pattern pp)
   {
     return new Pattern(pp)
     {
       public int match(CharSequence src, int len, int from) {
         if (this.val$pp.match(src, len, from) != -1) return -1;
         return 0;
       }
     };
   }
 
   public static Pattern peek(Pattern pp)
   {
     return new Pattern(pp)
     {
       public int match(CharSequence src, int len, int from) {
         if (this.val$pp.match(src, len, from) == -1) return -1;
         return 0;
       }
     };
   }
 
   public static Pattern or(Pattern pp1, Pattern pp2)
   {
     return new Pattern(pp1, pp2)
     {
       public int match(CharSequence src, int len, int from) {
         int l1 = this.val$pp1.match(src, len, from);
         if (l1 != -1) return l1;
         return this.val$pp2.match(src, len, from);
       }
     };
   }
 
   public static Pattern and(Pattern[] pps)
   {
     if (pps.length == 0) return always();
     if (pps.length == 1) return pps[0];
     return _and(pps);
   }
 
   public static Pattern seq(Pattern pp1, Pattern pp2)
   {
     return new Pattern(pp1, pp2)
     {
       public int match(CharSequence src, int len, int from) {
         int l1 = this.val$pp1.match(src, len, from);
         if (l1 == -1) return l1;
         int l2 = this.val$pp2.match(src, len, from + l1);
         if (l2 == -1) return l2;
         return (l1 + l2);
       }
     };
   }
 
   public static Pattern or(Pattern[] pps)
   {
     if (pps.length == 0) return never();
     if (pps.length == 1) return pps[0];
     return _or(pps);
   }
 
   public static Pattern seq(Pattern[] pps)
   {
     if (pps.length == 0) return always();
     if (pps.length == 1) return pps[0];
     return _seq(pps);
   }
 
   public static Pattern repeat(int n, CharPredicate cp)
   {
     if (n == 0) return always();
     if (n == 1) return isChar(cp);
     return new Pattern(n, cp)
     {
       public int match(CharSequence src, int len, int from) {
         return Patterns.access$200(this.val$n, this.val$cp, src, len, from, 0);
       }
     };
   }
 
   public static Pattern repeat(int n, Pattern pp)
   {
     if (n == 0) return always();
     if (n == 1) return pp;
     return new Pattern(n, pp)
     {
       public int match(CharSequence src, int len, int from) {
         return Patterns.access$300(this.val$n, this.val$pp, src, len, from, 0);
       } } ;
   }
 
   private static int min(int a, int b) {
     return ((a > b) ? b : a);
   }
 
   public static Pattern many(int min, CharPredicate cp)
   {
     if (min < 0) throw new IllegalArgumentException("min<0");
     return new Pattern(min, cp)
     {
       public int match(CharSequence src, int len, int from) {
         int minlen = Patterns.access$200(this.val$min, this.val$cp, src, len, from, 0);
         if (minlen == -1) return -1;
         return Patterns.access$400(this.val$cp, src, len, from + minlen, minlen);
       }
     };
   }
 
   public static Pattern many(CharPredicate cp)
   {
     return new Pattern(cp)
     {
       public int match(CharSequence src, int len, int from) {
         return Patterns.access$400(this.val$cp, src, len, from, 0);
       }
     };
   }
 
   public static Pattern many(int min, Pattern pp)
   {
     if (min < 0) throw new IllegalArgumentException("min<0");
     return new Pattern(min, pp)
     {
       public int match(CharSequence src, int len, int from) {
         int minlen = Patterns.access$300(this.val$min, this.val$pp, src, len, from, 0);
         if (-1 == minlen) return -1;
         return Patterns.access$500(this.val$pp, src, len, from + minlen, minlen);
       }
     };
   }
 
   public static Pattern many(Pattern pp)
   {
     return new Pattern(pp)
     {
       public int match(CharSequence src, int len, int from) {
         return Patterns.access$500(this.val$pp, src, len, from, 0);
       }
     };
   }
 
   public static Pattern some(int min, int max, CharPredicate cp)
   {
     if ((max < 0) || (min < 0) || (min > max)) throw new IllegalArgumentException();
     if (max == 0) return always();
     return new Pattern(min, cp, max)
     {
       public int match(CharSequence src, int len, int from) {
         int minlen = Patterns.access$200(this.val$min, this.val$cp, src, len, from, 0);
         if (minlen == -1) return -1;
         return Patterns.access$600(this.val$max - this.val$min, this.val$cp, src, len, from + minlen, minlen);
       }
     };
   }
 
   public static Pattern some(int max, CharPredicate cp)
   {
     if (max < 0) throw new IllegalArgumentException("max<0");
     if (max == 0) return always();
     return new Pattern(max, cp)
     {
       public int match(CharSequence src, int len, int from) {
         return Patterns.access$600(this.val$max, this.val$cp, src, len, from, 0);
       }
     };
   }
 
   public static Pattern some(int min, int max, Pattern pp)
   {
     if ((min < 0) || (max < 0) || (min > max)) throw new IllegalArgumentException();
     if (max == 0) return always();
     return new Pattern(min, pp, max)
     {
       public int match(CharSequence src, int len, int from) {
         int minlen = Patterns.access$300(this.val$min, this.val$pp, src, len, from, 0);
         if (-1 == minlen) return -1;
         return Patterns.access$700(this.val$max - this.val$min, this.val$pp, src, len, from + minlen, minlen);
       }
     };
   }
 
   public static Pattern some(int max, Pattern pp)
   {
     if (max < 0) throw new IllegalArgumentException("max<0");
     if (max == 0) return always();
     return new Pattern(max, pp)
     {
       public int match(CharSequence src, int len, int from) {
         return Patterns.access$700(this.val$max, this.val$pp, src, len, from, 0);
       }
     };
   }
 
   public static Pattern longer(Pattern p1, Pattern p2)
   {
     return longest(new Pattern[] { p1, p2 });
   }
 
   public static Pattern longest(Pattern[] pps)
   {
     if (pps.length == 0) return never();
     if (pps.length == 1) return pps[0];
     return new Pattern(pps)
     {
       public int match(CharSequence src, int len, int from) {
         int r = -1;
         for (int i = 0; i < this.val$pps.length; ++i) {
           int l = this.val$pps[i].match(src, len, from);
           if (l <= r) continue; r = l;
         }
         return r;
       }
     };
   }
 
   public static Pattern shorter(Pattern p1, Pattern p2)
   {
     return shortest(new Pattern[] { p1, p2 });
   }
 
   public static Pattern shortest(Pattern[] pps)
   {
     if (pps.length == 0) return never();
     if (pps.length == 1) return pps[0];
     return new Pattern(pps)
     {
       public int match(CharSequence src, int len, int from) {
         int r = -1;
         for (int i = 0; i < this.val$pps.length; ++i) {
           int l = this.val$pps[i].match(src, len, from);
           if ((l == -1) || (
             (r != -1) && (l >= r))) continue;
           r = l;
         }
 
         return r;
       }
     };
   }
 
   public static Pattern ifelse(Pattern cond, Pattern yes, Pattern no)
   {
     return new Pattern(cond, no, yes)
     {
       public int match(CharSequence src, int len, int from) {
         int lc = this.val$cond.match(src, len, from);
         if (lc == -1) {
           return this.val$no.match(src, len, from);
         }
 
         int ly = this.val$yes.match(src, len, from + lc);
         if (ly == -1) return -1;
         return (lc + ly);
       }
     };
   }
 
   public static Pattern many1(CharPredicate cp)
   {
     return many(1, cp);
   }
 
   public static Pattern optional(Pattern pp)
   {
     return new Pattern(pp)
     {
       public int match(CharSequence src, int len, int from) {
         int l = this.val$pp.match(src, len, from);
         return ((l == -1) ? 0 : l);
       }
     };
   }
 
   public static Pattern never()
   {
     return _never;
   }
 
   public static Pattern always()
   {
     return _always;
   }
 
   public static Pattern isDecimalL()
   {
     CharPredicate cp = CharPredicates.isDigit();
     return seq(many1(cp), optional(seq(isChar('.'), many(cp))));
   }
 
   public static Pattern isDecimalR()
   {
     return seq(isChar('.'), many1(CharPredicates.isDigit()));
   }
 
   public static Pattern isDecimal()
   {
     return or(isDecimalL(), isDecimalR());
   }
 
   public static Pattern isWord()
   {
     return regex("[a-zA-Z_][0-9a-zA-Z_]*");
   }
 
   public static Pattern isInteger()
   {
     return many1(CharPredicates.isDigit());
   }
 
   public static Pattern isOctInteger()
   {
     return seq(isChar('0'), many(CharPredicates.range('0', '7')));
   }
 
   public static Pattern isDecInteger()
   {
     return seq(range('1', '9'), many(CharPredicates.isDigit()));
   }
 
   public static Pattern isHexInteger()
   {
     return seq(or(isString("0x"), isString("0X")), many1(CharPredicates.isHexDigit()));
   }
 
   public static Pattern isExponential()
   {
     return seq(new Pattern[] { among(new char[] { 'e', 'E' }), optional(isChar('-')), isInteger() });
   }
 
   public static Pattern regex(java.util.regex.Pattern p)
   {
     return new Pattern(p)
     {
       public int match(CharSequence src, int len, int from) {
         if (from > len) return -1;
         Matcher matcher = this.val$p.matcher(src.subSequence(from, len));
         if (matcher.lookingAt()) {
           return matcher.end();
         }
         return -1;
       }
     };
   }
 
   public static Pattern regex(String s)
   {
     return regex(java.util.regex.Pattern.compile(s));
   }
 
   public static Pattern regex_pattern()
   {
     return regex_pattern;
   }
 
   public static Pattern regex_modifiers()
   {
     return regex_modifiers;
   }
 
   private static int match_repeat(int n, CharPredicate cp, CharSequence src, int len, int from, int acc)
   {
     int tail = from + n;
     if (tail > len) return -1;
     for (int i = from; i < tail; ++i) {
       if (!(cp.isChar(src.charAt(i)))) return -1;
     }
     return (n + acc);
   }
 
   private static int match_repeat(int n, Pattern pp, CharSequence src, int len, int from, int acc) {
     int end = from;
     for (int i = 0; i < n; ++i) {
       int l = pp.match(src, len, end);
       if (l == -1) return -1;
       end += l;
     }
     return (end - from + acc);
   }
 
   private static int match_some(int max, CharPredicate cp, CharSequence src, int len, int from, int acc) {
     int k = min(max + from, len);
     for (int i = from; i < k; ++i) {
       if (!(cp.isChar(src.charAt(i)))) return (i - from + acc);
     }
     return (k - from + acc);
   }
 
   private static int match_some(int max, Pattern pp, CharSequence src, int len, int from, int acc) {
     int begin = from;
     for (int i = 0; i < max; ++i) {
       int l = pp.match(src, len, begin);
       if (-1 == l) return (begin - from + acc);
       begin += l;
     }
     return (begin - from + acc);
   }
 
   private static int match_many(CharPredicate cp, CharSequence src, int len, int from, int acc) {
     for (int i = from; i < len; ++i) {
       if (!(cp.isChar(src.charAt(i)))) return (i - from + acc);
     }
     return (len - from + acc);
   }
 
   private static int match_many(Pattern pp, CharSequence src, int len, int from, int acc) {
     int i = from;
     while (true) { int l = pp.match(src, len, i);
       if (-1 == l) return (i - from + acc);
 
       if (l == 0) return (i - from + acc);
       i += l; }
   }
 
   private static Pattern _or(Pattern[] pps) {
     return new Pattern(pps)
     {
       public int match(CharSequence src, int len, int from) {
         for (int i = 0; i < this.val$pps.length; ++i) {
           int l = this.val$pps[i].match(src, len, from);
           if (l != -1) return l;
         }
         return -1;
       } } ;
   }
 
   private static Pattern _seq(Pattern[] pps) {
     return new Pattern(pps)
     {
       public int match(CharSequence src, int len, int from) {
         int end = from;
         for (int i = 0; i < this.val$pps.length; ++i) {
           int l = this.val$pps[i].match(src, len, end);
           if (l == -1) return l;
           end += l;
         }
         return (end - from);
       } } ;
   }
 
   private static Pattern _and(Pattern[] pps) {
     return new Pattern(pps)
     {
       public int match(CharSequence src, int len, int from) {
         int ret = 0;
         for (int i = 0; i < this.val$pps.length; ++i) {
           int l = this.val$pps[i].match(src, len, from);
           if (l == -1) return -1;
           if (l <= ret) continue; ret = l;
         }
         return ret;
       }
     };
   }
 
   private static final Pattern getRegularExpressionPattern() {
     Pattern quote = isChar('/');
     Pattern escape = isChar('\\').seq(hasAtLeast(1));
 
     char[] not_allowed = { '/', '\n', '\r', '\\' };
     Pattern content = or(escape, notAmong(not_allowed));
 
     return quote.seq(content.many()).seq(quote); }
 
   private static final Pattern getModifiersPattern() {
     return isChar(CharPredicates.isAlpha()).many();
   }
 }