 package org.apache.hivemind.lib.impl;
 
 import org.apache.hivemind.Location;
 import org.apache.hivemind.internal.Module;
 import org.apache.hivemind.service.ObjectProvider;
 import org.springframework.beans.factory.BeanFactory;
 
 public class SpringObjectProvider
   implements ObjectProvider
 {
   private BeanFactory _beanFactory;
 
   public Object provideObject(Module contributingModule, Class propertyType, String locator, Location location)
   {
     return this._beanFactory.getBean(locator);
   }
 
   public void setBeanFactory(BeanFactory beanFactory)
   {
     this._beanFactory = beanFactory;
   }
 }