 package org.apache.hivemind.parse;
 
 import org.apache.hivemind.impl.BaseLocatable;
 
 public class AttributeMappingDescriptor extends BaseLocatable
 {
   private String _attributeName;
   private String _propertyName;
 
   public String getAttributeName()
   {
     return this._attributeName;
   }
 
   public String getPropertyName()
   {
     return this._propertyName;
   }
 
   public void setAttributeName(String string)
   {
     this._attributeName = string;
   }
 
   public void setPropertyName(String string)
   {
     this._propertyName = string;
   }
 }