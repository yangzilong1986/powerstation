package com.hisun.common.util.pattern;

import java.io.Serializable;

public abstract interface CharPredicate extends Serializable
{
  public abstract boolean isChar(char paramChar);
}