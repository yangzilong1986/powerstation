package com.hisun.util;

import org.dom4j.Attribute;
import org.dom4j.Element;

public abstract interface HiWalkXMLTreeHandler
{
  public abstract void handlerAttribute(Attribute paramAttribute)
    throws Exception;

  public abstract void handlerElement(Element paramElement)
    throws Exception;
}