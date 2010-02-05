 package org.apache.hivemind.impl;
 
 import org.apache.hivemind.Location;
 import org.apache.hivemind.LocationHolder;
 
 public class BaseLocatable
   implements LocationHolder
 {
   private Location _location;
 
   public void setLocation(Location location)
   {
     this._location = location;
   }
 
   public Location getLocation()
   {
     return this._location;
   }
 }