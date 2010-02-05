 package com.hisun.atc.rpt.data;
 
 import com.hisun.atc.rpt.HiDataRecord;
 
 public class Matchers
 {
   private static Matcher _one = new Matcher() {
     public boolean match(HiDataRecord rec) {
       return true;
     }
   };
 
   public static Matcher one()
   {
     return _one;
   }
 
   public static Matcher withType(int type) {
     return new Matcher(type) { private final int val$type;
 
       public boolean match(HiDataRecord rec) { return (rec.type == this.val$type);
       }
     };
   }
 
   public static Matcher withValue(String name, String value) {
     return new Matcher(value, name) { private final String val$value;
       private final String val$name;
 
       public boolean match(HiDataRecord rec) { return this.val$value.equals(rec.get(this.val$name));
       }
     };
   }
 
   public static Matcher valueChange(String node)
   {
     return new Matcher(node) { boolean bfirstRec = true;
       private String value;
       private final String val$node;
 
       public boolean match(HiDataRecord rec) {
         if (this.bfirstRec) {
           this.value = rec.get(this.val$node);
           this.bfirstRec = false;
           return false;
         }
         String v = rec.get(this.val$node);
         if ((v != null) && (v.equals(this.value))) {
           this.value = v;
           return false;
         }
         return true;
       }
     };
   }
 
   public static Matcher valuesChange(String[] nodes, int from, int len)
   {
     Matcher[] ms = new Matcher[len];
     for (int i = 0; i < len; ++i) {
       ms[i] = valueChange(nodes[(from + i)]);
     }
     return and(ms);
   }
 
   public static Matcher and(Matcher a, Matcher b) {
     return and(new Matcher[] { a, b });
   }
 
   public static Matcher and(Matcher[] ms) {
     if (ms.length == 1)
       return ms[0];
     return new Matcher(ms) { private final Matcher[] val$ms;
 
       public boolean match(HiDataRecord rec) { for (int i = 0; i < this.val$ms.length; ++i) {
           if (!(this.val$ms[i].match(rec)))
             return false;
         }
         return true;
       }
     };
   }
 
   public static Matcher or(Matcher a, Matcher b) {
     return or(new Matcher[] { a, b });
   }
 
   public static Matcher or(Matcher[] ms) {
     if (ms.length == 1)
       return ms[0];
     return new Matcher(ms) { private final Matcher[] val$ms;
 
       public boolean match(HiDataRecord rec) { for (int i = 0; i < this.val$ms.length; ++i) {
           if (this.val$ms[i].match(rec))
             return true;
         }
         return false;
       }
     };
   }
 }