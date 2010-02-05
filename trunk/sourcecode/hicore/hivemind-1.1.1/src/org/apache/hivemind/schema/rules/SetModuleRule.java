 package org.apache.hivemind.schema.rules;
 
 import org.apache.hivemind.Element;
 import org.apache.hivemind.internal.Module;
 import org.apache.hivemind.schema.SchemaProcessor;
 
 public class SetModuleRule extends BaseRule
 {
   private String _propertyName;
 
   public void begin(SchemaProcessor processor, Element element)
   {
     Object top = processor.peek();
     Module contributingModule = processor.getContributingModule();
 
     RuleUtils.setProperty(processor, element, this._propertyName, top, contributingModule);
   }
 
   public void setPropertyName(String string)
   {
     this._propertyName = string;
   }
 
   public String getPropertyName()
   {
     return this._propertyName;
   }
 }