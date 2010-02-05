 package org.apache.hivemind.schema.rules;
 
 import org.apache.hivemind.Element;
 import org.apache.hivemind.Locatable;
 import org.apache.hivemind.schema.SchemaProcessor;
 import org.apache.hivemind.util.InstanceCreationUtils;
 
 public class CreateObjectRule extends BaseRule
 {
   private String _className;
 
   public CreateObjectRule()
   {
   }
 
   public CreateObjectRule(String className)
   {
     this._className = className;
   }
 
   public void begin(SchemaProcessor processor, Element element)
   {
     Object object = InstanceCreationUtils.createInstance(processor.getDefiningModule(), this._className, element.getLocation());
 
     processor.push(object);
   }
 
   public void end(SchemaProcessor processor, Element element)
   {
     processor.pop();
   }
 
   public String getClassName()
   {
     return this._className;
   }
 
   public void setClassName(String string)
   {
     this._className = string;
   }
 }