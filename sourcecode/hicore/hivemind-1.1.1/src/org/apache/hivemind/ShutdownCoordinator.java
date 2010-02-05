package org.apache.hivemind;

import org.apache.hivemind.events.RegistryShutdownListener;

public abstract interface ShutdownCoordinator
{
  public abstract void addRegistryShutdownListener(RegistryShutdownListener paramRegistryShutdownListener);

  public abstract void removeRegistryShutdownListener(RegistryShutdownListener paramRegistryShutdownListener);

  public abstract void shutdown();
}