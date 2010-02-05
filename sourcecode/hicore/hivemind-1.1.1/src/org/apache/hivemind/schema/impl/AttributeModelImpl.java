 package org.apache.hivemind.schema.impl;
 
 import org.apache.hivemind.parse.BaseAnnotationHolder;
 import org.apache.hivemind.schema.AttributeModel;
 
 public final class AttributeModelImpl extends BaseAnnotationHolder
   implements AttributeModel
 {
   private String _name;
   private boolean _required;
   private boolean _unique;
   private String _translator;
 
   public String getName()
   {
     return this._name;
   }
 
   public boolean isRequired()
   {
     return this._required;
   }
 
   public void setName(String string)
   {
     this._name = string;
   }
 
   public void setRequired(boolean b)
   {
     this._required = b;
   }
 
   public void setUnique(boolean b)
   {
     this._unique = b;
   }
 
   public boolean isUnique()
   {
     return this._unique;
   }
 
   public String getTranslator()
   {
     return this._translator;
   }
 
   public void setTranslator(String string)
   {
     this._translator = string;
   }
 }