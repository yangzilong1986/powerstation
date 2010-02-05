 package org.apache.hivemind.internal.ser;
 
 import java.lang.ref.Reference;
 import java.lang.ref.WeakReference;
 import org.apache.hivemind.ApplicationRuntimeException;
 
 public class ServiceSerializationHelper
 {
   private static final ThreadLocal _threadLocal = new ThreadLocal();
 
   public static ServiceSerializationSupport getServiceSerializationSupport()
   {
     ServiceSerializationSupport result = null;
 
     WeakReference reference = (WeakReference)_threadLocal.get();
     if (reference != null) {
       result = (ServiceSerializationSupport)reference.get();
     }
     if (result == null) {
       throw new ApplicationRuntimeException(SerMessages.noSupportSet());
     }
     return result;
   }
 
   public static void setServiceSerializationSupport(ServiceSerializationSupport serviceSerializationSupport)
   {
     WeakReference reference = new WeakReference(serviceSerializationSupport);
 
     _threadLocal.set(reference);
   }
 }