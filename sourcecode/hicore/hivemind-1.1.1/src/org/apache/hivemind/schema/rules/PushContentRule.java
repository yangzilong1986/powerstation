 package org.apache.hivemind.schema.rules;
 
 import org.apache.hivemind.Element;
 import org.apache.hivemind.Locatable;
 import org.apache.hivemind.schema.Rule;
 import org.apache.hivemind.schema.SchemaProcessor;
 import org.apache.hivemind.schema.Translator;
 
 public class PushContentRule extends BaseRule
   implements Rule
 {
   public void begin(SchemaProcessor processor, Element element)
   {
     Translator t = processor.getContentTranslator();
 
     String value = RuleUtils.processText(processor, element, element.getContent());
 
     Object finalValue = t.translate(processor.getContributingModule(), Object.class, value, element.getLocation());
 
     processor.push(finalValue);
   }
 
   public void end(SchemaProcessor processor, Element element)
   {
     processor.pop();
   }
 }