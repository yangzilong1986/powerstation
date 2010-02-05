 package org.apache.hivemind.parse;
 
 import java.util.ArrayList;
 import java.util.HashMap;
 import java.util.Iterator;
 import java.util.List;
 import java.util.ListIterator;
 import java.util.Map;
 import org.apache.commons.logging.Log;
 import org.apache.commons.logging.LogFactory;
 import org.apache.hivemind.Element;
 import org.apache.hivemind.ErrorHandler;
 import org.apache.hivemind.Locatable;
 import org.apache.hivemind.impl.BaseLocatable;
 import org.apache.hivemind.schema.AttributeModel;
 import org.apache.hivemind.schema.ElementModel;
 import org.apache.hivemind.schema.Rule;
 import org.apache.hivemind.schema.SchemaProcessor;
 import org.apache.hivemind.schema.rules.BaseRule;
 import org.apache.hivemind.schema.rules.CreateObjectRule;
 import org.apache.hivemind.schema.rules.InvokeParentRule;
 import org.apache.hivemind.schema.rules.ReadAttributeRule;
 
 public class ConversionDescriptor extends BaseRule
 {
   private static final Log LOG = LogFactory.getLog(ConversionDescriptor.class);
   private ErrorHandler _errorHandler;
   private String _className;
   private String _parentMethodName = "addElement";
 
   private Map _attributeNameMappingMap = new HashMap();
 
   private List _attributeMappings = new ArrayList();
   private List _rules;
   private ElementModel _elementModel;
 
   public ConversionDescriptor(ErrorHandler errorHandler, ElementModel elementModel)
   {
     this._errorHandler = errorHandler;
     this._elementModel = elementModel;
   }
 
   public List getAttributeMappings()
   {
     return this._attributeMappings;
   }
 
   public void addAttributeMapping(AttributeMappingDescriptor descriptor)
   {
     String attributeName = descriptor.getAttributeName();
 
     AttributeMappingDescriptor existing = (AttributeMappingDescriptor)this._attributeNameMappingMap.get(attributeName);
 
     if (existing != null)
     {
       this._errorHandler.error(LOG, ParseMessages.dupeAttributeMapping(descriptor, existing), descriptor.getLocation(), null);
 
       return;
     }
 
     this._attributeNameMappingMap.put(attributeName, descriptor);
 
     this._attributeMappings.add(descriptor);
   }
 
   public String getClassName()
   {
     return this._className;
   }
 
   public void setClassName(String string)
   {
     this._className = string;
   }
 
   public String getParentMethodName()
   {
     return this._parentMethodName;
   }
 
   public void setParentMethodName(String string)
   {
     this._parentMethodName = string;
   }
 
   public void begin(SchemaProcessor processor, Element element)
   {
     for (Iterator i = this._rules.iterator(); i.hasNext(); )
     {
       Rule rule = (Rule)i.next();
 
       rule.begin(processor, element);
     }
   }
 
   public void end(SchemaProcessor processor, Element element)
   {
     for (ListIterator i = this._rules.listIterator(this._rules.size()); i.hasPrevious(); )
     {
       Rule rule = (Rule)i.previous();
 
       rule.end(processor, element);
     }
   }
 
   public void addRulesForModel()
   {
     this._rules = new ArrayList();
 
     this._rules.add(new CreateObjectRule(this._className));
 
     addAttributeRules();
 
     this._rules.add(new InvokeParentRule(this._parentMethodName));
   }
 
   private void addAttributeRules()
   {
     Iterator i = this._elementModel.getAttributeModels().iterator();
 
     while (i.hasNext())
     {
       AttributeModel am = (AttributeModel)i.next();
       String attributeName = am.getName();
 
       AttributeMappingDescriptor amd = (AttributeMappingDescriptor)this._attributeNameMappingMap.get(attributeName);
 
       if (amd == null)
       {
         this._rules.add(new ReadAttributeRule(attributeName, constructPropertyName(attributeName), null, super.getLocation()));
       }
       else
       {
         String propertyName = amd.getPropertyName();
         if (propertyName == null) {
           propertyName = constructPropertyName(attributeName);
         }
         this._rules.add(new ReadAttributeRule(attributeName, propertyName, null, amd.getLocation()));
 
         this._attributeNameMappingMap.remove(attributeName);
       }
     }
 
     if (!(this._attributeNameMappingMap.isEmpty()))
       this._errorHandler.error(LOG, ParseMessages.extraMappings(this._attributeNameMappingMap.keySet(), this._elementModel), this._elementModel.getLocation(), null);
   }
 
   private String constructPropertyName(String attributeName)
   {
     int dashx = attributeName.indexOf(45);
     if (dashx < 0) {
       return attributeName;
     }
     int length = attributeName.length();
     StringBuffer buffer = new StringBuffer(length);
 
     buffer.append(attributeName.substring(0, dashx));
     boolean toUpper = true;
 
     for (int i = dashx + 1; i < length; ++i)
     {
       char ch = attributeName.charAt(i);
 
       if (ch == '-')
       {
         toUpper = true;
       }
       else
       {
         if (toUpper) {
           ch = Character.toUpperCase(ch);
         }
         buffer.append(ch);
 
         toUpper = false;
       }
     }
     return buffer.toString();
   }
 }