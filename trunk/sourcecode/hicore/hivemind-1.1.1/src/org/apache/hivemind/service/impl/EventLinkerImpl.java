 package org.apache.hivemind.service.impl;
 
 import java.beans.BeanInfo;
 import java.beans.EventSetDescriptor;
 import java.beans.FeatureDescriptor;
 import java.beans.IntrospectionException;
 import java.beans.Introspector;
 import java.lang.reflect.Method;
 import java.util.HashMap;
 import java.util.Map;
 import org.apache.hivemind.ErrorLog;
 import org.apache.hivemind.HiveMind;
 import org.apache.hivemind.Location;
 import org.apache.hivemind.impl.BaseLocatable;
 import org.apache.hivemind.service.EventLinker;
 
 public class EventLinkerImpl extends BaseLocatable
   implements EventLinker
 {
   private ErrorLog _errorLog;
   private Map _producerEventSets;
 
   public EventLinkerImpl(ErrorLog errorLog)
   {
     this._errorLog = errorLog;
   }
 
   public void addEventListener(Object producer, String eventSetName, Object consumer, Location location)
   {
     EventSetDescriptor[] sets = getEventSets(producer);
     boolean nameMatch = HiveMind.isNonBlank(eventSetName);
     Class consumerClass = consumer.getClass();
 
     int count = 0;
     for (int i = 0; i < sets.length; ++i)
     {
       EventSetDescriptor set = sets[i];
       String name = set.getName();
 
       if (nameMatch)
       {
         if (!(eventSetName.equals(name))) {
           continue;
         }
         if (isAssignable(set, consumerClass)) {
           addEventListener(producer, set, consumer, location);
         }
         else {
           this._errorLog.error(ServiceMessages.notCompatibleWithEvent(consumer, set, producer), location, null);
         }
 
         return;
       }
 
       if (!(isAssignable(set, consumerClass)))
         continue;
       addEventListener(producer, set, consumer, location);
       ++count;
     }
 
     if (count != 0)
       return;
     if (nameMatch) {
       this._errorLog.error(ServiceMessages.noSuchEventSet(producer, eventSetName), location, null);
     }
     else
     {
       this._errorLog.error(ServiceMessages.noEventMatches(consumer, producer), location, null);
     }
   }
 
   private boolean isAssignable(EventSetDescriptor set, Class consumerClass)
   {
     return set.getListenerType().isAssignableFrom(consumerClass);
   }
 
   private void addEventListener(Object producer, EventSetDescriptor set, Object consumer, Location location)
   {
     Method m = set.getAddListenerMethod();
     try
     {
       m.invoke(producer, new Object[] { consumer });
     }
     catch (Exception ex)
     {
       this._errorLog.error(ServiceMessages.unableToAddListener(producer, set, consumer, location, ex), location, ex);
     }
   }
 
   private EventSetDescriptor[] getEventSets(Object producer)
   {
     return getEventSets(producer.getClass());
   }
 
   private synchronized EventSetDescriptor[] getEventSets(Class producerClass)
   {
     EventSetDescriptor[] result = null;
 
     if (this._producerEventSets == null)
       this._producerEventSets = new HashMap();
     else {
       result = (EventSetDescriptor[])(EventSetDescriptor[])this._producerEventSets.get(producerClass);
     }
     if (result == null)
     {
       result = findEventSets(producerClass);
 
       this._producerEventSets.put(producerClass, result);
     }
 
     return result;
   }
 
   private EventSetDescriptor[] findEventSets(Class producerClass)
   {
     Object Ljava/lang/Object;;
     monitorenter;
     try
     {
       BeanInfo beanInfo = Introspector.getBeanInfo(producerClass);
 
       return beanInfo.getEventSetDescriptors();
     }
     catch (IntrospectionException ex)
     {
       this._errorLog.error(ServiceMessages.unableToIntrospectClass(producerClass, ex), null, ex);
 
       return new EventSetDescriptor[0];
     } finally {
       monitorexit;
     }
   }
 }