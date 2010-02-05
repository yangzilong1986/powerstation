 package org.apache.hivemind.lib.strategy;
 
 import org.apache.hivemind.impl.AbstractMessages;
 import org.apache.hivemind.impl.MessageFormatter;
 import org.apache.hivemind.service.ClassFabUtils;
 import org.apache.hivemind.service.MethodSignature;
 
 class StrategyMessages
 {
   protected static MessageFormatter _formatter = new MessageFormatter(StrategyMessages.class);
 
   static String strategyWrongInterface(Object adaptor, Class registerClass, Class serviceInterface)
   {
     return _formatter.format("strategy-wrong-interface", adaptor, ClassFabUtils.getJavaClassName(registerClass), serviceInterface.getName());
   }
 
   static String improperServiceMethod(MethodSignature sig)
   {
     return _formatter.format("improper-service-method", sig);
   }
 
   static String toString(String serviceId, Class serviceInterface)
   {
     return _formatter.format("to-string", serviceId, serviceInterface.getName());
   }
 }