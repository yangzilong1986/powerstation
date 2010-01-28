 package com.hisun.xml;
 
 import java.io.Serializable;
 
 public class Location
   implements Serializable
 {
   private final String module;
   private final int line;
   private final int col;
 
   public int getColumnNo()
   {
     return this.col;
   }
 
   public int getLineNo()
   {
     return this.line;
   }
 
   public String getModule()
   {
     return this.module;
   }
 
   public Location(String module, int line, int col)
   {
     this.col = col;
     this.line = line;
     this.module = module; }
 
   public boolean equals(Object obj) {
     if (obj instanceof Location) {
       Location other = (Location)obj;
       return ((this.line == other.line) && (this.col == other.col) && (this.module.equals(other.module)));
     }
     return false; }
 
   public int hashCode() {
     return ((this.module.hashCode() * 31 + this.line) * 31 + this.col); }
 
   public String toString() {
     return this.module + "(line " + this.line + ")";
   }
 }