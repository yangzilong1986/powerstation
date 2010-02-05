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
 
 public class ReadContentRule extends BaseRule
 {
   private static final Log LOG = LogFactory.getLog(ReadContentRule.class);
   private String _propertyName;
 
   public void begin(SchemaProcessor processor, Element element)
   {
     String value = RuleUtils.processText(processor, element, element.getContent());
     try
     {
       Translator t = processor.getContentTranslator();
 
       Object target = processor.peek();
 
       Class propertyType = PropertyUtils.getPropertyType(target, this._propertyName);
 
       Object finalValue = t.translate(processor.getContributingModule(), propertyType, value, element.getLocation());
 
       PropertyUtils.write(target, this._propertyName, finalValue);
     }
     catch (Exception ex)
     {
       ErrorHandler eh = processor.getContributingModule().getErrorHandler();
 
       eh.error(LOG, RulesMessages.readContentFailure(processor, element, ex), element.getLocation(), ex);
     }
   }
 
   public String getPropertyName()
   {
     return this._propertyName;
   }
 
   public void setPropertyName(String string)
   {
     this._propertyName = string;
   }
 }