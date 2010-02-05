 package org.apache.hivemind.impl;
 
 import java.util.ArrayList;
 import java.util.Collections;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;
 import org.apache.hivemind.Attribute;
 import org.apache.hivemind.Element;
 import org.apache.hivemind.util.ToStringBuilder;
 
 public final class ElementImpl extends BaseLocatable
   implements Element
 {
   private String _elementName;
   private String _content;
   private List _elements;
   private List _safeElements;
   private List _attributes;
   private Map _attributesMap;
   private List _safeAttributes;
 
   public void setElementName(String elementName)
   {
     this._elementName = elementName;
   }
 
   public String getElementName()
   {
     return this._elementName;
   }
 
   public void addAttribute(Attribute attribute)
   {
     if (this._attributes == null)
     {
       this._attributes = new ArrayList();
       this._attributesMap = new HashMap();
     }
 
     this._attributes.add(attribute);
     this._attributesMap.put(attribute.getName(), attribute);
   }
 
   public void addElement(Element element)
   {
     if (this._elements == null) {
       this._elements = new ArrayList();
     }
     this._elements.add(element);
   }
 
   public synchronized List getAttributes()
   {
     if (this._attributes == null) {
       return Collections.EMPTY_LIST;
     }
     if (this._safeAttributes == null) {
       this._safeAttributes = Collections.unmodifiableList(this._attributes);
     }
     return this._safeAttributes;
   }
 
   public String getContent()
   {
     if (this._content == null) {
       return "";
     }
     return this._content;
   }
 
   public synchronized List getElements()
   {
     if (this._elements == null) {
       return Collections.EMPTY_LIST;
     }
     if (this._safeElements == null) {
       this._safeElements = Collections.unmodifiableList(this._elements);
     }
     return this._safeElements;
   }
 
   public String getAttributeValue(String attributeName)
   {
     if (this._attributesMap == null) {
       return null;
     }
     Attribute a = (Attribute)this._attributesMap.get(attributeName);
 
     if (a == null) {
       return null;
     }
     return a.getValue();
   }
 
   public boolean isEmpty()
   {
     return ((this._elements == null) || (this._elements.size() == 0));
   }
 
   public void setContent(String string)
   {
     this._content = string;
   }
 
   public String toString()
   {
     ToStringBuilder builder = new ToStringBuilder(this);
 
     builder.append("elementName", this._elementName);
     builder.append("attributes", this._attributes);
     builder.append("elements", this._elements);
     builder.append("content", this._content);
 
     return builder.toString();
   }
 }