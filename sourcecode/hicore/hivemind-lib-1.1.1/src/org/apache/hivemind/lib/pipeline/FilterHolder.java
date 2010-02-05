 package org.apache.hivemind.lib.pipeline;
 
 import org.apache.hivemind.Locatable;
 import org.apache.hivemind.Location;
 
 public class FilterHolder
   implements Locatable
 {
   private Object _filter;
   private Location _location;
 
   public FilterHolder(Object filter, Location location)
   {
     this._filter = filter;
     this._location = location;
   }
 
   public Object getFilter()
   {
     return this._filter;
   }
 
   public Location getLocation()
   {
     return this._location;
   }
 }