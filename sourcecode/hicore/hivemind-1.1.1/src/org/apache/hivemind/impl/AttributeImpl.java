 package org.apache.hivemind.impl;
 
 import org.apache.hivemind.Attribute;
 import org.apache.hivemind.util.ToStringBuilder;
 
 public class AttributeImpl
   implements Attribute
 {
   private String _name;
   private String _value;
 
   public AttributeImpl(String name, String value)
   {
     this._name = name;
     this._value = value;
   }
 
   public String getName()
   {
     return this._name;
   }
 
   public String getValue()
   {
     return this._value;
   }
 
   public String toString()
   {
     ToStringBuilder builder = new ToStringBuilder(this);
 
     builder.append("name", this._name);
     builder.append("value", this._value);
 
     return builder.toString();
   }
 }