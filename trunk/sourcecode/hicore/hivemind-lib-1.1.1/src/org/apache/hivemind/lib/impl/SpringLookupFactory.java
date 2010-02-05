 package org.apache.hivemind.lib.impl;
 
 import org.apache.hivemind.ServiceImplementationFactory;
 import org.apache.hivemind.ServiceImplementationFactoryParameters;
 import org.apache.hivemind.impl.BaseLocatable;
 import org.springframework.beans.factory.BeanFactory;
 
 public class SpringLookupFactory extends BaseLocatable
   implements ServiceImplementationFactory
 {
   private BeanFactory _defaultBeanFactory;
 
   public Object createCoreServiceImplementation(ServiceImplementationFactoryParameters factoryParameters)
   {
     SpringBeanParameter p = (SpringBeanParameter)factoryParameters.getFirstParameter();
     String beanName = p.getName();
 
     BeanFactory f = p.getBeanFactory();
 
     if (f == null) {
       f = this._defaultBeanFactory;
     }
     return f.getBean(beanName, factoryParameters.getServiceInterface());
   }
 
   public void setDefaultBeanFactory(BeanFactory defaultBeanFactory)
   {
     this._defaultBeanFactory = defaultBeanFactory;
   }
 }