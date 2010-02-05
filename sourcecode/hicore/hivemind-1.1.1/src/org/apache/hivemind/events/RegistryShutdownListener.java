package org.apache.hivemind.events;

import java.util.EventListener;

public abstract interface RegistryShutdownListener extends EventListener
{
  public abstract void registryDidShutdown();
}