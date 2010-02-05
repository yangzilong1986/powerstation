package com.hisun.template;

import java.util.Map;

public abstract interface TemplateEngine
{
  public abstract StringBuffer run(Map paramMap, String paramString)
    throws Exception;
}