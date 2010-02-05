 package org.apache.hivemind.impl;
 
 import org.apache.hivemind.Location;
 import org.apache.hivemind.Resource;
 
 public final class LocationImpl
   implements Location
 {
   private Resource _resource;
   private int _lineNumber;
   private int _columnNumber;
 
   public LocationImpl(Resource resource)
   {
     this._lineNumber = -1;
     this._columnNumber = -1;
 
     this._resource = resource;
   }
 
   public LocationImpl(Resource resource, int lineNumber)
   {
     this(resource);
 
     this._lineNumber = lineNumber;
   }
 
   public LocationImpl(Resource resource, int lineNumber, int columnNumber)
   {
     this(resource);
 
     this._lineNumber = lineNumber;
     this._columnNumber = columnNumber;
   }
 
   public Resource getResource()
   {
     return this._resource;
   }
 
   public int getLineNumber()
   {
     return this._lineNumber;
   }
 
   public int getColumnNumber()
   {
     return this._columnNumber;
   }
 
   public int hashCode()
   {
     return (237 + this._resource.hashCode() << 3 + this._lineNumber << 3 + this._columnNumber);
   }
 
   public boolean equals(Object other)
   {
     if (!(other instanceof Location)) {
       return false;
     }
     Location l = (Location)other;
 
     if (this._lineNumber != l.getLineNumber()) {
       return false;
     }
     if (this._columnNumber != l.getColumnNumber()) {
       return false;
     }
     return this._resource.equals(l.getResource());
   }
 
   public String toString()
   {
     if ((this._lineNumber <= 0) && (this._columnNumber <= 0)) {
       return this._resource.toString();
     }
     StringBuffer buffer = new StringBuffer(this._resource.toString());
 
     if (this._lineNumber > 0)
     {
       buffer.append(", line ");
       buffer.append(this._lineNumber);
     }
 
     if (this._columnNumber > 0)
     {
       buffer.append(", column ");
       buffer.append(this._columnNumber);
     }
 
     return buffer.toString();
   }
 }