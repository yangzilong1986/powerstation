 package org.apache.hivemind.lib.impl;
 
 import org.apache.hivemind.impl.BaseLocatable;
 import org.apache.hivemind.lib.SpringBeanFactoryHolder;
 import org.springframework.beans.factory.BeanFactory;
 
 public class SpringBeanFactoryHolderImpl extends BaseLocatable
   implements SpringBeanFactoryHolder
 {
   private BeanFactory _factory;
 
   public void setBeanFactory(BeanFactory factory)
   {
     this._factory = factory;
   }
 
   public BeanFactory getBeanFactory()
   {
     return this._factory;
   }
 }