 package com.hisun.common.util.pattern;
 
 public final class CharPredicates
 {
   private static final CharPredicate _never = new CharPredicate() {
     public boolean isChar(char c) { return false;
     }
   };
 
   private static final CharPredicate _always = new CharPredicate() {
     public boolean isChar(char c) { return true;
     }
   };
 
   public static CharPredicate isChar(char a)
   {
     return new CharPredicate(a) {
       public boolean isChar(char c) {
         return (c == this.val$a);
       }
     };
   }
 
   public static CharPredicate notChar(char a)
   {
     return new CharPredicate(a) {
       public boolean isChar(char c) {
         return (c != this.val$a);
       }
     };
   }
 
   public static CharPredicate range(char a, char b)
   {
     return new CharPredicate(a, b) {
       public boolean isChar(char c) {
         return ((c >= this.val$a) && (c <= this.val$b));
       }
     };
   }
 
   public static CharPredicate isDigit()
   {
     return range('0', '9');
   }
 
   public static CharPredicate notRange(char a, char b)
   {
     return new CharPredicate(a, b) {
       public boolean isChar(char c) {
         return ((c < this.val$a) || (c > this.val$b));
       }
     };
   }
 
   public static CharPredicate among(char[] chars)
   {
     return new CharPredicate(chars) {
       public boolean isChar(char c) {
         return CharPredicates.charAmong(c, this.val$chars);
       }
     };
   }
 
   public static CharPredicate notAmong(char[] chars)
   {
     return new CharPredicate(chars) {
       public boolean isChar(char c) {
         return (!(CharPredicates.charAmong(c, this.val$chars)));
       } } ;
   }
 
   static boolean charAmong(char c, char[] chars) {
     for (int i = 0; i < chars.length; ++i) {
       if (c == chars[i]) return true;
     }
     return false;
   }
 
   public static CharPredicate isHexDigit()
   {
     return new CharPredicate() {
       public boolean isChar(char c) {
         return (((c >= '0') && (c <= '9')) || ((c >= 'a') && (c <= 'f')) || ((c >= 'A') && (c <= 'F')));
       }
     };
   }
 
   public static CharPredicate isUppercase()
   {
     return new CharPredicate() {
       public boolean isChar(char c) { return Character.isUpperCase(c);
       }
     };
   }
 
   public static CharPredicate isLowercase()
   {
     return new CharPredicate() {
       public boolean isChar(char c) { return Character.isLowerCase(c);
       }
     };
   }
 
   public static CharPredicate isWhitespace()
   {
     return new CharPredicate() {
       public boolean isChar(char c) { return Character.isWhitespace(c);
       }
     };
   }
 
   public static CharPredicate isAlpha()
   {
     return new CharPredicate() {
       public boolean isChar(char c) {
         return (((c <= 'z') && (c >= 'a')) || ((c <= 'Z') && (c >= 'A')));
       }
     };
   }
 
   public static CharPredicate isAlpha_()
   {
     return new CharPredicate() {
       public boolean isChar(char c) {
         return ((c == '_') || ((c <= 'z') && (c >= 'a')) || ((c <= 'Z') && (c >= 'A')));
       }
     };
   }
 
   public static CharPredicate isLetter()
   {
     return new CharPredicate() {
       public boolean isChar(char c) {
         return Character.isLetter(c);
       }
     };
   }
 
   public static CharPredicate isAlphaNumeric()
   {
     return new CharPredicate() {
       public boolean isChar(char c) {
         return ((c == '_') || ((c >= 'A') && (c <= 'Z')) || ((c >= 'a') && (c <= 'z')) || ((c >= '0') && (c <= '9')));
       }
     };
   }
 
   public static CharPredicate not(CharPredicate cp)
   {
     return new CharPredicate(cp) {
       public boolean isChar(char c) {
         return (!(this.val$cp.isChar(c)));
       }
     };
   }
 
   public static CharPredicate and(CharPredicate cp1, CharPredicate cp2)
   {
     return new CharPredicate(cp1, cp2) {
       public boolean isChar(char c) {
         return ((this.val$cp1.isChar(c)) && (this.val$cp2.isChar(c)));
       }
     };
   }
 
   public static CharPredicate or(CharPredicate cp1, CharPredicate cp2)
   {
     return new CharPredicate(cp1, cp2) {
       public boolean isChar(char c) {
         return ((this.val$cp1.isChar(c)) || (this.val$cp2.isChar(c)));
       }
     };
   }
 
   public static CharPredicate and(CharPredicate[] cps)
   {
     if (cps.length == 0) return always();
     if (cps.length == 1) return cps[0];
     return new CharPredicate(cps) {
       public boolean isChar(char c) {
         for (int i = 0; i < this.val$cps.length; ++i) {
           if (!(this.val$cps[i].isChar(c))) return false;
         }
         return true;
       }
     };
   }
 
   public static CharPredicate or(CharPredicate[] cps)
   {
     if (cps.length == 0) return never();
     if (cps.length == 1) return cps[0];
     return new CharPredicate(cps) {
       public boolean isChar(char c) {
         for (int i = 0; i < this.val$cps.length; ++i) {
           if (this.val$cps[i].isChar(c)) return true;
         }
         return false;
       }
     };
   }
 
   public static CharPredicate never()
   {
     return _never;
   }
 
   public static CharPredicate always()
   {
     return _always;
   }
 }