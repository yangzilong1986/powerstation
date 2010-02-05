 package org.apache.hivemind.parse;
 
 import java.util.ArrayList;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;
 import org.apache.hivemind.ApplicationRuntimeException;
 import org.apache.hivemind.HiveMind;
 import org.apache.hivemind.Location;
 import org.apache.hivemind.Resource;
 import org.apache.hivemind.impl.LocationImpl;
 import org.xml.sax.Attributes;
 import org.xml.sax.Locator;
 import org.xml.sax.SAXException;
 import org.xml.sax.SAXParseException;
 import org.xml.sax.helpers.DefaultHandler;
 
 public abstract class AbstractParser extends DefaultHandler
 {
   private int _currentColumn;
   private int _currentLine;
   private Location _location;
   private Locator _locator;
   private Resource _resource;
   private List _stack;
   private int _state;
   private Item _top;
 
   public void characters(char[] ch, int start, int length)
     throws SAXException
   {
     this._top.addContent(ch, start, length);
   }
 
   public void error(SAXParseException ex)
     throws SAXException
   {
     fatalError(ex);
   }
 
   public void fatalError(SAXParseException ex)
     throws SAXException
   {
     throw ex;
   }
 
   protected String getElementPath()
   {
     StringBuffer buffer = new StringBuffer();
 
     int count = this._stack.size();
     for (int i = 0; i < count; ++i)
     {
       if (i > 0) {
         buffer.append('/');
       }
       Item item = (Item)this._stack.get(i);
 
       buffer.append(item._elementName);
     }
 
     return buffer.toString();
   }
 
   protected Location getLocation()
   {
     int line = this._locator.getLineNumber();
     int column = this._locator.getColumnNumber();
 
     if ((line != this._currentLine) || (column != this._currentColumn)) {
       this._location = null;
     }
     if (this._location == null) {
       this._location = new LocationImpl(this._resource, line, column);
     }
     this._currentLine = line;
     this._currentColumn = column;
 
     return this._location;
   }
 
   protected Resource getResource()
   {
     return this._resource;
   }
 
   protected int getState()
   {
     return this._state;
   }
 
   protected void initializeParser(Resource resource, int startState)
   {
     this._resource = resource;
     this._stack = new ArrayList();
 
     this._location = null;
     this._state = startState;
   }
 
   protected String peekContent()
   {
     return this._top.getContent();
   }
 
   protected String peekElementName()
   {
     return this._top._elementName;
   }
 
   protected Object peekObject()
   {
     return this._top._object;
   }
 
   protected void pop()
   {
     int count = this._stack.size();
 
     this._state = this._top._priorState;
 
     this._stack.remove(count - 1);
 
     if (count == 1)
       this._top = null;
     else
       this._top = ((Item)this._stack.get(count - 2));
   }
 
   protected void push(String elementName, Object object, int state)
   {
     push(elementName, object, state, true);
   }
 
   protected void push(String elementName, Object object, int state, boolean ignoreCharacterData)
   {
     HiveMind.setLocation(object, getLocation());
 
     Item item = new Item(elementName, object, this._state, ignoreCharacterData);
 
     this._stack.add(item);
 
     this._top = item;
     this._state = state;
   }
 
   protected void resetParser()
   {
     this._resource = null;
     this._locator = null;
     this._stack = null;
     this._location = null;
   }
 
   public void setDocumentLocator(Locator locator)
   {
     this._locator = locator;
   }
 
   protected void setState(int state)
   {
     this._state = state;
   }
 
   protected void unexpectedElement(String elementName)
   {
     throw new ApplicationRuntimeException(ParseMessages.unexpectedElement(elementName, getElementPath()), getLocation(), null);
   }
 
   protected void updateObject(Object object)
   {
     this._top._object = object;
   }
 
   public void warning(SAXParseException ex)
     throws SAXException
   {
     fatalError(ex);
   }
 
   private Map constructAttributesMap(Attributes attributes)
   {
     Map result = new HashMap();
     int count = attributes.getLength();
 
     for (int i = 0; i < count; ++i)
     {
       String key = attributes.getLocalName(i);
 
       if (HiveMind.isBlank(key)) {
         key = attributes.getQName(i);
       }
       String value = attributes.getValue(i);
 
       result.put(key, value);
     }
 
     return result;
   }
 
   protected abstract void begin(String paramString, Map paramMap);
 
   protected abstract void end(String paramString);
 
   public void endElement(String uri, String localName, String qName)
     throws SAXException
   {
     end(getElementName(localName, qName));
   }
 
   public void startElement(String uri, String localName, String qName, Attributes attributes)
     throws SAXException
   {
     String elementName = getElementName(localName, qName);
 
     begin(elementName, constructAttributesMap(attributes));
   }
 
   private String getElementName(String localName, String qName)
   {
     return ((qName != null) ? qName : localName);
   }
 
   private static class Item
   {
     StringBuffer _buffer;
     String _elementName;
     boolean _ignoreCharacterData;
     Object _object;
     int _priorState;
 
     Item(String elementName, Object object, int priorState, boolean ignoreCharacterData)
     {
       this._elementName = elementName;
       this._object = object;
       this._priorState = priorState;
       this._ignoreCharacterData = ignoreCharacterData;
     }
 
     void addContent(char[] buffer, int start, int length)
     {
       if (this._ignoreCharacterData) {
         return;
       }
       if (this._buffer == null) {
         this._buffer = new StringBuffer(length);
       }
       this._buffer.append(buffer, start, length);
     }
 
     String getContent()
     {
       if (this._buffer != null) {
         return this._buffer.toString().trim();
       }
       return null;
     }
   }
 }