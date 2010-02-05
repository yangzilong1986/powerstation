 package org.apache.hivemind.impl.servicemodel;
 
 import org.apache.hivemind.impl.AbstractMessages;
 import org.apache.hivemind.impl.MessageFormatter;
 import org.apache.hivemind.internal.ExtensionPoint;
 import org.apache.hivemind.internal.ServicePoint;
 
 class ServiceModelMessages
 {
   protected static MessageFormatter _formatter = new MessageFormatter(ServiceModelMessages.class);
 
   static String factoryReturnedNull(ServicePoint point)
   {
     return _formatter.format("factory-returned-null", point.getExtensionPointId());
   }
 
   static String factoryWrongInterface(ServicePoint point, Object result, Class serviceType)
   {
     return _formatter.format("factory-wrong-interface", point.getExtensionPointId(), result, serviceType.getName());
   }
 
   static String registryCleanupIgnored(ServicePoint point)
   {
     return _formatter.format("registry-cleanup-ignored", point.getExtensionPointId());
   }
 
   static String unableToConstructService(ServicePoint point, Throwable cause)
   {
     return _formatter.format("unable-to-construct-service", point.getExtensionPointId(), cause);
   }
 }