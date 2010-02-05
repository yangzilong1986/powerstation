package com.hisun.web.service;

import com.hisun.hilog4j.Logger;

public abstract interface HiLogFactory
{
  public abstract Logger getLogger(String paramString);

  public abstract Logger getLogger();
}