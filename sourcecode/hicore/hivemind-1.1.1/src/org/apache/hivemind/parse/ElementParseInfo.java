 package org.apache.hivemind.parse;
 
 import java.util.HashSet;
 import java.util.Iterator;
 import java.util.Set;
 
 public class ElementParseInfo
 {
   private Set _knownAttributes;
   private Set _requiredAttributes;
 
   public ElementParseInfo()
   {
     this._knownAttributes = new HashSet();
     this._requiredAttributes = new HashSet();
   }
 
   public void addAttribute(String name, boolean required) {
     this._knownAttributes.add(name);
 
     if (required)
       this._requiredAttributes.add(name);
   }
 
   public boolean isKnown(String attributeName)
   {
     return this._knownAttributes.contains(attributeName);
   }
 
   public Iterator getRequiredNames()
   {
     return this._requiredAttributes.iterator();
   }
 }