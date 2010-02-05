package org.apache.hivemind.internal;

import java.util.List;

public abstract interface Contribution
{
  public abstract Module getContributingModule();

  public abstract List getElements();
}