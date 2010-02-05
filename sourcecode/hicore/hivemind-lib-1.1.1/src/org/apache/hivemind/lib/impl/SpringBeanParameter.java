 package org.apache.hivemind.lib.impl;
 
 import org.apache.hivemind.impl.BaseLocatable;
 import org.springframework.beans.factory.BeanFactory;
 
 public class SpringBeanParameter extends BaseLocatable
 {
   private String _name;
   private BeanFactory _beanFactory;
 
   public String getName()
   {
     return this._name;
   }
 
   public void setName(String string)
   {
     this._name = string;
   }
 
   public BeanFactory getBeanFactory()
   {
     return this._beanFactory;
   }
 
   public void setBeanFactory(BeanFactory beanFactory)
   {
     this._beanFactory = beanFactory;
   }
 }