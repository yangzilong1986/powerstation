 package org.apache.hivemind.conditional;
 
 import org.apache.hivemind.util.Defense;
 
 public class ClassNameEvaluator
   implements Evaluator
 {
   private String _className;
 
   public ClassNameEvaluator(String className)
   {
     Defense.notNull(className, "className");
 
     this._className = className;
   }
 
   String getClassName()
   {
     return this._className;
   }
 
   public boolean evaluate(EvaluationContext context, Node node)
   {
     return context.doesClassExist(this._className);
   }
 }