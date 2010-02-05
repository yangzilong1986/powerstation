 package com.hisun.common.util.pattern;
 
 import java.io.Serializable;
 
 public abstract class Pattern
   implements Serializable
 {
   public static final int MISMATCH = -1;
 
   public abstract int match(CharSequence paramCharSequence, int paramInt1, int paramInt2);
 
   public final Pattern seq(Pattern p2)
   {
     return Patterns.seq(this, p2);
   }
 
   public final Pattern optional()
   {
     return Patterns.optional(this);
   }
 
   public final Pattern many()
   {
     return Patterns.many(this);
   }
 
   public final Pattern many(int min)
   {
     return Patterns.many(min, this);
   }
 
   public final Pattern many1()
   {
     return Patterns.many(1, this);
   }
 
   public final Pattern some(int max)
   {
     return Patterns.some(max, this);
   }
 
   public final Pattern some(int min, int max)
   {
     return Patterns.some(min, max, this);
   }
 
   public final Pattern not()
   {
     return Patterns.not(this);
   }
 
   public final Pattern peek()
   {
     return Patterns.peek(this);
   }
 
   public final Pattern ifelse(Pattern yes, Pattern no)
   {
     return Patterns.ifelse(this, yes, no);
   }
 
   public final Pattern repeat(int n)
   {
     return Patterns.repeat(n, this);
   }
 }