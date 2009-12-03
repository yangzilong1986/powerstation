package com.hisun.pipeline;

public abstract interface PipelineBuilderFactory
{
  public abstract PipelineBuilder createPipelineBuilder(Class paramClass1, Class paramClass2);
}