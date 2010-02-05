 package com.hisun.xml;
 
 public class Located
   implements LocationAware
 {
   private Location loc;
 
   public Location getLocation()
   {
     return this.loc;
   }
 
   public void setLocation(Location loc) {
     this.loc = loc;
   }
 }