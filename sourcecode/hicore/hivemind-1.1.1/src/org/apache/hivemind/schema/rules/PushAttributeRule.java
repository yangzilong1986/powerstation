 package org.apache.hivemind.schema.rules;
 
 import org.apache.hivemind.Element;
 import org.apache.hivemind.Locatable;
 import org.apache.hivemind.schema.SchemaProcessor;
 import org.apache.hivemind.schema.Translator;
 
 public class PushAttributeRule extends BaseRule
 {
   private String _attributeName;
 
   public void begin(SchemaProcessor processor, Element element)
   {
     Translator t = processor.getAttributeTranslator(this._attributeName);
 
     String attributeValue = element.getAttributeValue(this._attributeName);
     String value = RuleUtils.processText(processor, element, attributeValue);
 
     Object finalValue = t.translate(processor.getContributingModule(), Object.class, value, element.getLocation());
 
     processor.push(finalValue);
   }
 
   public void end(SchemaProcessor processor, Element element)
   {
     processor.pop();
   }
 
   public void setAttributeName(String string)
   {
     this._attributeName = string;
   }
 
   public String getAttributeName()
   {
     return this._attributeName;
   }
 }