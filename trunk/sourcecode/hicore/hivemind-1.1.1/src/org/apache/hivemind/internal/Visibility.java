 package org.apache.hivemind.internal;
 
 public final class Visibility
 {
   private String _name;
   public static final Visibility PUBLIC = new Visibility("PUBLIC");
 
   public static final Visibility PRIVATE = new Visibility("PRIVATE");
 
   private Visibility(String name)
   {
     this._name = name;
   }
 
   public String toString()
   {
     return "Visibility[" + this._name + "]";
   }
 }