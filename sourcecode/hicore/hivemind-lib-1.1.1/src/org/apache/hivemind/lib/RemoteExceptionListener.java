package org.apache.hivemind.lib;

import java.util.EventListener;

public abstract interface RemoteExceptionListener extends EventListener
{
  public abstract void remoteExceptionDidOccur(RemoteExceptionEvent paramRemoteExceptionEvent);
}