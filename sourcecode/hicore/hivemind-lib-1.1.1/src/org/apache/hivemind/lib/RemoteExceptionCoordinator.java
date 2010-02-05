package org.apache.hivemind.lib;

public abstract interface RemoteExceptionCoordinator
{
  public abstract void addRemoteExceptionListener(RemoteExceptionListener paramRemoteExceptionListener);

  public abstract void removeRemoteExceptionListener(RemoteExceptionListener paramRemoteExceptionListener);

  public abstract void fireRemoteExceptionDidOccur(Object paramObject, Throwable paramThrowable);
}