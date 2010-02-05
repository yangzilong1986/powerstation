 package org.apache.hivemind.schema.rules;
 
 import org.apache.commons.logging.Log;
 import org.apache.commons.logging.LogFactory;
 import org.apache.hivemind.Element;
 import org.apache.hivemind.ErrorHandler;
 import org.apache.hivemind.Locatable;
 import org.apache.hivemind.Location;
 import org.apache.hivemind.impl.BaseLocatable;
 import org.apache.hivemind.internal.Module;
 import org.apache.hivemind.schema.SchemaProcessor;
 import org.apache.hivemind.schema.Translator;
 import org.apache.hivemind.util.PropertyUtils;
 
 public class ReadAttributeRule extends BaseRule
 {
   private static final Log LOG = LogFactory.getLog(ReadAttributeRule.class);
   private String _attributeName;
   private String _propertyName;
   private boolean _skipIfNull = true;
   private String _translator;
 
   public ReadAttributeRule()
   {
   }
 
   public ReadAttributeRule(String attributeName, String propertyName, String translator, Location location)
   {
     super.setLocation(location);
 
     this._attributeName = attributeName;
     this._propertyName = propertyName;
     this._translator = translator;
   }
 
   public void begin(SchemaProcessor processor, Element element)
   {
     String rawValue = element.getAttributeValue(this._attributeName);
 
     if ((rawValue == null) && (this._skipIfNull)) {
       return;
     }
     String value = RuleUtils.processText(processor, element, rawValue);
 
     Object target = processor.peek();
     try
     {
       Translator t = (this._translator == null) ? processor.getAttributeTranslator(this._attributeName) : processor.getTranslator(this._translator);
 
       Class propertyType = PropertyUtils.getPropertyType(target, this._propertyName);
 
       Object finalValue = t.translate(processor.getContributingModule(), propertyType, value, element.getLocation());
 
       PropertyUtils.write(target, this._propertyName, finalValue);
     }
     catch (Exception ex)
     {
       ErrorHandler eh = processor.getContributingModule().getErrorHandler();
 
       eh.error(LOG, RulesMessages.readAttributeFailure(this._attributeName, element, processor, ex), element.getLocation(), ex);
     }
   }
 
   public String getAttributeName()
   {
     return this._attributeName;
   }
 
   public String getPropertyName()
   {
     return this._propertyName;
   }
 
   public boolean getSkipIfNull()
   {
     return this._skipIfNull;
   }
 
   public String getTranslator()
   {
     return this._translator;
   }
 
   public void setAttributeName(String string)
   {
     this._attributeName = string;
   }
 
   public void setPropertyName(String string)
   {
     this._propertyName = string;
   }
 
   public void setSkipIfNull(boolean b)
   {
     this._skipIfNull = b;
   }
 
   public void setTranslator(String string)
   {
     this._translator = string;
   }
 }