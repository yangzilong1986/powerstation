/*    */ package com.hisun.atc.rpt.data;
/*    */ 
/*    */ import com.hisun.atc.rpt.HiDataRecord;
/*    */ 
/*    */ public class Matchers
/*    */ {
/*  6 */   private static Matcher _one = new Matcher() {
/*    */     public boolean match(HiDataRecord rec) {
/*  8 */       return true;
/*    */     }
/*  6 */   };
/*    */ 
/*    */   public static Matcher one()
/*    */   {
/* 13 */     return _one;
/*    */   }
/*    */ 
/*    */   public static Matcher withType(int type) {
/* 17 */     return new Matcher(type) { private final int val$type;
/*    */ 
/*    */       public boolean match(HiDataRecord rec) { return (rec.type == this.val$type);
/*    */       }
/*    */     };
/*    */   }
/*    */ 
/*    */   public static Matcher withValue(String name, String value) {
/* 25 */     return new Matcher(value, name) { private final String val$value;
/*    */       private final String val$name;
/*    */ 
/*    */       public boolean match(HiDataRecord rec) { return this.val$value.equals(rec.get(this.val$name));
/*    */       }
/*    */     };
/*    */   }
/*    */ 
/*    */   public static Matcher valueChange(String node)
/*    */   {
/* 34 */     return new Matcher(node) { boolean bfirstRec = true;
/*    */       private String value;
/*    */       private final String val$node;
/*    */ 
/*    */       public boolean match(HiDataRecord rec) {
/* 39 */         if (this.bfirstRec) {
/* 40 */           this.value = rec.get(this.val$node);
/* 41 */           this.bfirstRec = false;
/* 42 */           return false;
/*    */         }
/* 44 */         String v = rec.get(this.val$node);
/* 45 */         if ((v != null) && (v.equals(this.value))) {
/* 46 */           this.value = v;
/* 47 */           return false;
/*    */         }
/* 49 */         return true;
/*    */       }
/*    */     };
/*    */   }
/*    */ 
/*    */   public static Matcher valuesChange(String[] nodes, int from, int len)
/*    */   {
/* 56 */     Matcher[] ms = new Matcher[len];
/* 57 */     for (int i = 0; i < len; ++i) {
/* 58 */       ms[i] = valueChange(nodes[(from + i)]);
/*    */     }
/* 60 */     return and(ms);
/*    */   }
/*    */ 
/*    */   public static Matcher and(Matcher a, Matcher b) {
/* 64 */     return and(new Matcher[] { a, b });
/*    */   }
/*    */ 
/*    */   public static Matcher and(Matcher[] ms) {
/* 68 */     if (ms.length == 1)
/* 69 */       return ms[0];
/* 70 */     return new Matcher(ms) { private final Matcher[] val$ms;
/*    */ 
/*    */       public boolean match(HiDataRecord rec) { for (int i = 0; i < this.val$ms.length; ++i) {
/* 73 */           if (!(this.val$ms[i].match(rec)))
/* 74 */             return false;
/*    */         }
/* 76 */         return true;
/*    */       }
/*    */     };
/*    */   }
/*    */ 
/*    */   public static Matcher or(Matcher a, Matcher b) {
/* 82 */     return or(new Matcher[] { a, b });
/*    */   }
/*    */ 
/*    */   public static Matcher or(Matcher[] ms) {
/* 86 */     if (ms.length == 1)
/* 87 */       return ms[0];
/* 88 */     return new Matcher(ms) { private final Matcher[] val$ms;
/*    */ 
/*    */       public boolean match(HiDataRecord rec) { for (int i = 0; i < this.val$ms.length; ++i) {
/* 91 */           if (this.val$ms[i].match(rec))
/* 92 */             return true;
/*    */         }
/* 94 */         return false;
/*    */       }
/*    */     };
/*    */   }
/*    */ }