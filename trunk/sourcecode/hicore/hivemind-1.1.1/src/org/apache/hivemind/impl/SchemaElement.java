 package org.apache.hivemind.impl;
 
 import java.util.ArrayList;
 import java.util.HashMap;
 import java.util.HashSet;
 import java.util.Iterator;
 import java.util.List;
 import java.util.Map;
 import java.util.Set;
 import org.apache.hivemind.ApplicationRuntimeException;
 import org.apache.hivemind.Attribute;
 import org.apache.hivemind.Element;
 import org.apache.hivemind.Locatable;
 import org.apache.hivemind.Location;
 import org.apache.hivemind.schema.AttributeModel;
 import org.apache.hivemind.schema.ElementModel;
 import org.apache.hivemind.schema.Rule;
 import org.apache.hivemind.schema.SchemaProcessor;
 import org.apache.hivemind.schema.Translator;
 import org.apache.hivemind.schema.rules.NullTranslator;
 
 final class SchemaElement
 {
   private SchemaProcessor _processor;
   private ElementModel _model;
   private List _requiredAttributes = new ArrayList();
 
   private Set _knownAttributes = new HashSet();
   private String _keyAttribute;
   private Map _nestedElements;
   private Map _attributeTranslators = new HashMap();
 
   private Map _attributeValues = new HashMap();
 
   private Translator _nullTranslator = new NullTranslator();
   private Translator _contentTranslator;
 
   SchemaElement(SchemaProcessor processor, ElementModel model)
   {
     this._processor = processor;
     this._model = model;
     this._keyAttribute = model.getKeyAttribute();
 
     List attributeModels = model.getAttributeModels();
     int count = attributeModels.size();
 
     for (int i = 0; i < count; ++i)
     {
       AttributeModel am = (AttributeModel)attributeModels.get(i);
 
       String name = am.getName();
 
       this._knownAttributes.add(name);
 
       if (am.isRequired()) {
         this._requiredAttributes.add(name);
       }
 
       if (am.isUnique()) {
         this._attributeValues.put(name, new HashMap());
       }
       if (name.equals(this._keyAttribute))
       {
         this._requiredAttributes.add(name);
         this._attributeValues.put(name, new HashMap());
       }
 
       this._attributeTranslators.put(name, am.getTranslator());
     }
   }
 
   SchemaElement getNestedElement(String elementName)
   {
     if (this._nestedElements == null) {
       buildNestedElements();
     }
     return ((SchemaElement)this._nestedElements.get(elementName));
   }
 
   private void buildNestedElements()
   {
     this._nestedElements = new HashMap();
 
     List l = this._model.getElementModel();
     int count = l.size();
 
     for (int i = 0; i < count; ++i)
     {
       ElementModel nested = (ElementModel)l.get(i);
 
       SchemaElement nestedElement = new SchemaElement(this._processor, nested);
 
       this._nestedElements.put(nested.getElementName(), nestedElement);
     }
   }
 
   void validateAttributes(Element element)
   {
     List l = element.getAttributes();
     int count = l.size();
     Set required = new HashSet(this._requiredAttributes);
     List errors = new ArrayList();
 
     for (int i = 0; i < count; ++i)
     {
       Attribute a = (Attribute)l.get(i);
       String name = a.getName();
 
       if (!(this._knownAttributes.contains(name))) {
         errors.add(ImplMessages.unknownAttribute(name));
       }
       checkUniquness(name, a.getValue(), element.getLocation(), errors);
 
       required.remove(name);
     }
 
     Iterator it = required.iterator();
 
     while (it.hasNext())
     {
       String name = (String)it.next();
       errors.add(ImplMessages.missingAttribute(name));
     }
 
     count = errors.size();
 
     if (count == 0) {
       return;
     }
     StringBuffer buffer = new StringBuffer();
 
     buffer.append(ImplMessages.elementErrors(this._processor, element));
 
     for (int i = 0; i < count; ++i)
     {
       buffer.append(' ');
       buffer.append(errors.get(i).toString());
     }
 
     throw new ApplicationRuntimeException(buffer.toString(), element.getLocation(), null);
   }
 
   private void checkUniquness(String name, String value, Location location, List errors)
   {
     Map valuesMap = (Map)this._attributeValues.get(name);
 
     if (valuesMap == null) {
       return;
     }
     Object translatedValue = getAttributeTranslator(name).translate(this._processor.getContributingModule(), Object.class, value, location);
 
     Location prior = (Location)valuesMap.get(translatedValue);
 
     if (prior == null)
     {
       valuesMap.put(translatedValue, location);
       return;
     }
 
     errors.add(ImplMessages.uniqueAttributeConstraintBroken(name, translatedValue.toString(), prior));
   }
 
   void fireBegin(Element element)
   {
     List rules = this._model.getRules();
     int count = rules.size();
 
     for (int i = 0; i < count; ++i)
     {
       Rule r = (Rule)rules.get(i);
 
       r.begin(this._processor, element);
     }
   }
 
   void fireEnd(Element element)
   {
     List rules = this._model.getRules();
     int count = rules.size();
 
     for (int i = count - 1; i >= 0; --i)
     {
       Rule r = (Rule)rules.get(i);
 
       r.end(this._processor, element);
     }
   }
 
   public Translator getContentTranslator()
   {
     if (this._contentTranslator == null) {
       this._contentTranslator = getTranslator(this._model.getContentTranslator());
     }
     return this._contentTranslator;
   }
 
   private Translator getTranslator(String translator)
   {
     if (translator == null) {
       return this._nullTranslator;
     }
     return this._processor.getTranslator(translator);
   }
 
   public Translator getAttributeTranslator(String attributeName)
   {
     String translator = (String)this._attributeTranslators.get(attributeName);
 
     return getTranslator(translator);
   }
 
   public String getKeyAttribute()
   {
     return this._keyAttribute;
   }
 }