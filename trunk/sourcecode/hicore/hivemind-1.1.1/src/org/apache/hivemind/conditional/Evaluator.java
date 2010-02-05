package org.apache.hivemind.conditional;

public abstract interface Evaluator
{
  public abstract boolean evaluate(EvaluationContext paramEvaluationContext, Node paramNode);
}