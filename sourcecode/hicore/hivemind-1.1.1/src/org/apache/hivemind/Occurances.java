 package org.apache.hivemind;
 
 public abstract class Occurances
 {
   public static final Occurances UNBOUNDED = new Occurances()
   {
     public boolean inRange(int count)
     {
       return true;
     }
   };
 
   public static final Occurances OPTIONAL = new Occurances()
   {
     public boolean inRange(int count)
     {
       return (count < 2);
     }
   };
 
   public static final Occurances REQUIRED = new Occurances()
   {
     public boolean inRange(int count)
     {
       return (count == 1);
     }
   };
 
   public static final Occurances ONE_PLUS = new Occurances()
   {
     public boolean inRange(int count)
     {
       return (count > 0);
     }
   };
 
   public static final Occurances NONE = new Occurances()
   {
     public boolean inRange(int count)
     {
       return (count == 0);
     }
   };
   private String _name;
 
   private Occurances(String name)
   {
     this._name = name;
   }
 
   public String getName()
   {
     return this._name;
   }
 
   public String toString()
   {
     return "Occurances[" + this._name + "]";
   }
 
   public abstract boolean inRange(int paramInt);
 
   Occurances(String x0, 1 x1)
   {
     this(x0);
   }
 }