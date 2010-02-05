 package org.apache.hivemind.conditional;
 
 public class NotEvaluator
   implements Evaluator
 {
   public boolean evaluate(EvaluationContext context, Node node)
   {
     return (!(node.getLeft().evaluate(context)));
   }
 }