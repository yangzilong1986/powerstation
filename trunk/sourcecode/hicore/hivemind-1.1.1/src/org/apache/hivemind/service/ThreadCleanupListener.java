package org.apache.hivemind.service;

public abstract interface ThreadCleanupListener
{
  public abstract void threadDidCleanup();
}