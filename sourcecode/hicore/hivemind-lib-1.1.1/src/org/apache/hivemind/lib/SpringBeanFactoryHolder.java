package org.apache.hivemind.lib;

import org.springframework.beans.factory.BeanFactory;

public abstract interface SpringBeanFactoryHolder extends SpringBeanFactorySource
{
  public abstract void setBeanFactory(BeanFactory paramBeanFactory);
}