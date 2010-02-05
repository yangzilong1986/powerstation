package org.apache.hivemind.lib;

import org.springframework.beans.factory.BeanFactory;

public abstract interface SpringBeanFactorySource
{
  public abstract BeanFactory getBeanFactory();
}