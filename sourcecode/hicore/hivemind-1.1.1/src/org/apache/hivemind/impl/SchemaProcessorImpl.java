 package org.apache.hivemind.impl;
 
 import java.util.ArrayList;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;
 import org.apache.hivemind.Element;
 import org.apache.hivemind.ErrorLog;
 import org.apache.hivemind.Locatable;
 import org.apache.hivemind.internal.Module;
 import org.apache.hivemind.schema.ElementModel;
 import org.apache.hivemind.schema.Schema;
 import org.apache.hivemind.schema.SchemaProcessor;
 import org.apache.hivemind.schema.Translator;
 
 public final class SchemaProcessorImpl
   implements SchemaProcessor
 {
   private ErrorLog _errorLog;
   private Schema _schema;
   private List _elements = new ArrayList();
   private boolean _canElementsBeMapped;
   private Map _mappedElements = new HashMap();
 
   private List _stack = new ArrayList();
   private Module _contributingModule;
   private Map _elementMap = new HashMap();
 
   private List _elementStack = new ArrayList();
   private SchemaElement _activeElement;
 
   public SchemaProcessorImpl(ErrorLog errorLog, Schema schema)
   {
     this._errorLog = errorLog;
     this._schema = schema;
     this._stack.add(this);
 
     if (this._schema == null)
       return;
     List l = this._schema.getElementModel();
 
     int count = l.size();
     for (int i = 0; i < count; ++i)
     {
       ElementModel model = (ElementModel)l.get(i);
       this._elementMap.put(model.getElementName(), new SchemaElement(this, model));
     }
 
     this._canElementsBeMapped = schema.canInstancesBeKeyed();
   }
 
   public void addElement(Object element)
   {
     this._elements.add(element);
 
     if (!(this._canElementsBeMapped))
       return;
     Element currentElement = peekElement();
     String keyAttribute = this._activeElement.getKeyAttribute();
 
     String expandedKey = getContributingModule().expandSymbols(currentElement.getAttributeValue(keyAttribute), currentElement.getLocation());
 
     Translator t = getAttributeTranslator(keyAttribute);
 
     Object finalValue = t.translate(getContributingModule(), Object.class, expandedKey, currentElement.getLocation());
 
     this._mappedElements.put(finalValue, element);
   }
 
   public List getElements()
   {
     return this._elements;
   }
 
   public Map getMappedElements()
   {
     if (this._canElementsBeMapped) {
       return this._mappedElements;
     }
     return null;
   }
 
   public void push(Object object)
   {
     this._stack.add(object);
   }
 
   public Object pop()
   {
     if (this._stack.isEmpty()) {
       throw new ArrayIndexOutOfBoundsException(ImplMessages.schemaStackViolation(this));
     }
     return this._stack.remove(this._stack.size() - 1);
   }
 
   public Object peek()
   {
     return peek(0);
   }
 
   public Object peek(int depth)
   {
     int count = this._stack.size();
 
     int position = count - 1 - depth;
 
     if (position < 0) {
       throw new ArrayIndexOutOfBoundsException(ImplMessages.schemaStackViolation(this));
     }
     return this._stack.get(count - 1 - depth);
   }
 
   public Module getContributingModule()
   {
     return this._contributingModule;
   }
 
   public Module getDefiningModule()
   {
     return this._schema.getDefiningModule();
   }
 
   public String getElementPath()
   {
     StringBuffer buffer = new StringBuffer();
     int count = this._elementStack.size();
 
     for (int i = 0; i < count; ++i)
     {
       if (i > 0) {
         buffer.append('/');
       }
       buffer.append(((Element)this._elementStack.get(i)).getElementName());
     }
 
     return buffer.toString();
   }
 
   private void pushElement(Element element)
   {
     this._elementStack.add(element);
   }
 
   private Element peekElement()
   {
     return ((Element)this._elementStack.get(this._elementStack.size() - 1));
   }
 
   private void popElement()
   {
     this._elementStack.remove(this._elementStack.size() - 1);
   }
 
   public void process(List elements, Module contributingModule)
   {
     if (elements == null) {
       return;
     }
     if (this._schema == null)
     {
       this._elements.addAll(elements);
       return;
     }
 
     this._contributingModule = contributingModule;
 
     int count = elements.size();
 
     for (int i = 0; i < count; ++i)
     {
       Element e = (Element)elements.get(i);
 
       processRootElement(e);
     }
 
     this._contributingModule = null;
   }
 
   private void processRootElement(Element element)
   {
     String name = element.getElementName();
 
     SchemaElement schemaElement = (SchemaElement)this._elementMap.get(name);
 
     processElement(element, schemaElement);
   }
 
   private void processElement(Element element, SchemaElement schemaElement)
   {
     pushElement(element);
 
     if (schemaElement == null) {
       this._errorLog.error(ImplMessages.unknownElement(this, element), element.getLocation(), null);
     }
     else
     {
       SchemaElement prior = this._activeElement;
 
       schemaElement.validateAttributes(element);
 
       this._activeElement = schemaElement;
 
       schemaElement.fireBegin(element);
 
       processNestedElements(element, schemaElement);
 
       schemaElement.fireEnd(element);
 
       this._activeElement = prior;
     }
 
     popElement();
   }
 
   private void processNestedElements(Element element, SchemaElement schemaElement)
   {
     List l = element.getElements();
     int count = l.size();
 
     for (int i = 0; i < count; ++i)
     {
       Element nested = (Element)l.get(i);
       String name = nested.getElementName();
 
       processElement(nested, schemaElement.getNestedElement(name));
     }
   }
 
   public Translator getContentTranslator()
   {
     return this._activeElement.getContentTranslator();
   }
 
   public Translator getAttributeTranslator(String attributeName)
   {
     return this._activeElement.getAttributeTranslator(attributeName);
   }
 
   public Translator getTranslator(String translator)
   {
     return getContributingModule().getTranslator(translator);
   }
 }