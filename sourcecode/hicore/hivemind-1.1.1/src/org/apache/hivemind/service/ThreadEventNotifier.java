package org.apache.hivemind.service;

public abstract interface ThreadEventNotifier
{
  public abstract void addThreadCleanupListener(ThreadCleanupListener paramThreadCleanupListener);

  public abstract void removeThreadCleanupListener(ThreadCleanupListener paramThreadCleanupListener);

  public abstract void fireThreadCleanup();
}