 package org.apache.hivemind.conditional;
 
 import org.apache.hivemind.util.Defense;
 
 public class NodeImpl
   implements Node
 {
   private Node _left;
   private Node _right;
   private Evaluator _evaluator;
 
   public NodeImpl(Node left, Node right, Evaluator evaluator)
   {
     Defense.notNull(evaluator, "evaluator");
 
     this._left = left;
     this._right = right;
     this._evaluator = evaluator;
   }
 
   public NodeImpl(Evaluator evaluator)
   {
     this(null, null, evaluator);
   }
 
   public Node getLeft()
   {
     return this._left;
   }
 
   public Node getRight()
   {
     return this._right;
   }
 
   Evaluator getEvaluator()
   {
     return this._evaluator;
   }
 
   public boolean evaluate(EvaluationContext context)
   {
     return this._evaluator.evaluate(context, this);
   }
 }