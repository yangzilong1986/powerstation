 package org.apache.hivemind.conditional;
 
 import org.apache.hivemind.util.Defense;
 
 public class PropertyEvaluator
   implements Evaluator
 {
   private String _propertyName;
 
   public PropertyEvaluator(String propertyName)
   {
     Defense.notNull(propertyName, "propertyName");
 
     this._propertyName = propertyName;
   }
 
   String getPropertyName()
   {
     return this._propertyName;
   }
 
   public boolean evaluate(EvaluationContext context, Node node)
   {
     return context.isPropertySet(this._propertyName);
   }
 }