 package org.apache.hivemind.lib.strategy;
 
 import org.apache.hivemind.impl.BaseLocatable;
 
 public class StrategyContribution extends BaseLocatable
 {
   private Class _registerClass;
   private Object _strategy;
 
   public Object getStrategy()
   {
     return this._strategy;
   }
 
   public void setStrategy(Object adaptor)
   {
     this._strategy = adaptor;
   }
 
   public Class getRegisterClass()
   {
     return this._registerClass;
   }
 
   public void setRegisterClass(Class registerClass)
   {
     this._registerClass = registerClass;
   }
 }