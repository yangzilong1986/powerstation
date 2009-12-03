package com.hisun.pipeline;

import java.util.List;

public abstract interface PipelineBuilder
{
  public abstract Object buildPipeline(Object paramObject1, Object paramObject2);

  public abstract Object buildPipeline(List paramList, Object paramObject);
}