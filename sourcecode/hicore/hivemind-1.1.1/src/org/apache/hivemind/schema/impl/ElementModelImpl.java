 package org.apache.hivemind.schema.impl;
 
 import java.util.ArrayList;
 import java.util.Collections;
 import java.util.Iterator;
 import java.util.List;
 import org.apache.hivemind.schema.AttributeModel;
 import org.apache.hivemind.schema.ElementModel;
 import org.apache.hivemind.schema.Rule;
 
 public class ElementModelImpl extends SchemaImpl
   implements ElementModel
 {
   private String _elementName;
   private List _attributeModels;
   private List _shareableAttributeModels;
   private String _keyAttribute;
   private List _rules;
   private List _shareableRules;
   private String _contentTranslator;
 
   public String getElementName()
   {
     return this._elementName;
   }
 
   public void setElementName(String string)
   {
     this._elementName = string;
   }
 
   public void addAttributeModel(AttributeModel attributeModel)
   {
     if (this._attributeModels == null) {
       this._attributeModels = new ArrayList();
     }
     this._attributeModels.add(attributeModel);
     this._shareableAttributeModels = null;
   }
 
   public List getAttributeModels()
   {
     if (this._shareableAttributeModels == null) {
       this._shareableAttributeModels = ((this._attributeModels == null) ? Collections.EMPTY_LIST : Collections.unmodifiableList(this._attributeModels));
     }
 
     return this._shareableAttributeModels;
   }
 
   public AttributeModel getAttributeModel(String name)
   {
     if (this._attributeModels == null) {
       return null;
     }
     for (Iterator i = this._attributeModels.iterator(); i.hasNext(); )
     {
       AttributeModel am = (AttributeModel)i.next();
 
       if (am.getName().equals(name)) {
         return am;
       }
     }
     return null;
   }
 
   public void setKeyAttribute(String keyAttribute)
   {
     this._keyAttribute = keyAttribute;
   }
 
   public String getKeyAttribute()
   {
     return this._keyAttribute;
   }
 
   public void addRule(Rule rule)
   {
     if (this._rules == null) {
       this._rules = new ArrayList();
     }
     this._rules.add(rule);
     this._shareableRules = null;
   }
 
   public List getRules()
   {
     if (this._shareableRules == null) {
       this._shareableRules = ((this._rules == null) ? Collections.EMPTY_LIST : Collections.unmodifiableList(this._rules));
     }
 
     return this._shareableRules;
   }
 
   public String getContentTranslator()
   {
     return this._contentTranslator;
   }
 
   public void setContentTranslator(String string)
   {
     this._contentTranslator = string;
   }
 }