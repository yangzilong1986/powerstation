package org.apache.hivemind.conditional;

public abstract interface Node
{
  public abstract Node getLeft();

  public abstract Node getRight();

  public abstract boolean evaluate(EvaluationContext paramEvaluationContext);
}