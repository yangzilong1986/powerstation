 package org.apache.hivemind.order;
 
 class ObjectOrdering
 {
   private String _name;
   private Object _object;
   private String _prereqs;
   private String _postreqs;
 
   ObjectOrdering(Object object, String name, String prereqs, String postreqs)
   {
     this._object = object;
     this._name = name;
     this._prereqs = prereqs;
     this._postreqs = postreqs;
   }
 
   public String getName()
   {
     return this._name;
   }
 
   public Object getObject()
   {
     return this._object;
   }
 
   public String getPostreqs()
   {
     return this._postreqs;
   }
 
   public String getPrereqs()
   {
     return this._prereqs;
   }
 }