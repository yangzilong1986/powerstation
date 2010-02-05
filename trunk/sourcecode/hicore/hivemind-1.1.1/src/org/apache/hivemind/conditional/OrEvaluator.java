 package org.apache.hivemind.conditional;
 
 public class OrEvaluator
   implements Evaluator
 {
   public boolean evaluate(EvaluationContext context, Node node)
   {
     return ((node.getLeft().evaluate(context)) || (node.getRight().evaluate(context)));
   }
 }