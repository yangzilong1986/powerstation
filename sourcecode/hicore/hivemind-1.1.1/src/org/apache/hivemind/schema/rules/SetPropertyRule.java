 package org.apache.hivemind.schema.rules;
 
 import org.apache.commons.logging.Log;
 import org.apache.commons.logging.LogFactory;
 import org.apache.hivemind.Element;
 import org.apache.hivemind.ErrorHandler;
 import org.apache.hivemind.Locatable;
 import org.apache.hivemind.internal.Module;
 import org.apache.hivemind.schema.SchemaProcessor;
 import org.apache.hivemind.schema.Translator;
 import org.apache.hivemind.util.PropertyUtils;
 
 public class SetPropertyRule extends BaseRule
 {
   private static final Log LOG = LogFactory.getLog(SetPropertyRule.class);
   private String _propertyName;
   private String _value;
   private Translator _smartTranslator;
 
   public void begin(SchemaProcessor processor, Element element)
   {
     String value = RuleUtils.processText(processor, element, this._value);
 
     Object target = processor.peek();
     try
     {
       if (this._smartTranslator == null) {
         this._smartTranslator = RuleUtils.getTranslator(processor, "smart");
       }
       Class propertyType = PropertyUtils.getPropertyType(target, this._propertyName);
 
       Object finalValue = this._smartTranslator.translate(processor.getContributingModule(), propertyType, value, element.getLocation());
 
       PropertyUtils.write(target, this._propertyName, finalValue);
     }
     catch (Exception ex)
     {
       ErrorHandler eh = processor.getContributingModule().getErrorHandler();
 
       String message = RulesMessages.unableToSetProperty(this._propertyName, target, ex);
 
       eh.error(LOG, message, element.getLocation(), ex);
     }
   }
 
   public void setPropertyName(String string)
   {
     this._propertyName = string;
   }
 
   public void setValue(String string)
   {
     this._value = string;
   }
 
   public String getPropertyName()
   {
     return this._propertyName;
   }
 
   public String getValue()
   {
     return this._value;
   }
 }