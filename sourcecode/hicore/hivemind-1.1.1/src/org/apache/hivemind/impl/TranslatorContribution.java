 package org.apache.hivemind.impl;
 
 import org.apache.hivemind.schema.Translator;
 
 public class TranslatorContribution extends BaseLocatable
 {
   private String _name;
   private Class _translatorClass;
   private Translator _translator;
 
   public String getName()
   {
     return this._name;
   }
 
   public Class getTranslatorClass()
   {
     return this._translatorClass;
   }
 
   public void setName(String name)
   {
     this._name = name;
   }
 
   public void setTranslatorClass(Class translatorClass)
   {
     this._translatorClass = translatorClass;
   }
 
   public Translator getTranslator()
   {
     return this._translator;
   }
 
   public void setTranslator(Translator translator)
   {
     this._translator = translator;
   }
 }