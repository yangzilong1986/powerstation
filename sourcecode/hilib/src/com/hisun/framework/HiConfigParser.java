package com.hisun.framework;

import com.hisun.exception.HiException;
import java.io.InputStream;

public abstract interface HiConfigParser
{
  public abstract HiDefaultServer parseServerXML(InputStream paramInputStream)
    throws HiException;
}