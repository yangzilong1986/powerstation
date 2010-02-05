 package org.apache.hivemind.schema.rules;
 
 import org.apache.hivemind.Element;
 import org.apache.hivemind.schema.SchemaProcessor;
 
 public class SetParentRule extends BaseRule
 {
   private String _propertyName;
 
   public String getPropertyName()
   {
     return this._propertyName;
   }
 
   public void setPropertyName(String string)
   {
     this._propertyName = string;
   }
 
   public void begin(SchemaProcessor processor, Element element)
   {
     Object child = processor.peek();
     Object parent = processor.peek(1);
 
     RuleUtils.setProperty(processor, element, this._propertyName, child, parent);
   }
 }