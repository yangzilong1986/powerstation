 package org.apache.hivemind.lib.util;
 
 import [Ljava.lang.Object;;
 import java.util.AbstractCollection;
 import java.util.HashMap;
 import java.util.Iterator;
 import java.util.LinkedList;
 import java.util.Map;
 import java.util.Map.Entry;
 import java.util.Set;
 import java.util.WeakHashMap;
 import org.apache.hivemind.service.ClassFabUtils;
 import org.apache.hivemind.util.Defense;
 
 public class StrategyRegistryImpl
   implements StrategyRegistry
 {
   private Map _registrations;
   private Map _cache;
 
   public StrategyRegistryImpl()
   {
     this._registrations = new HashMap();
 
     this._cache = new WeakHashMap();
   }
 
   public synchronized void register(Class registrationClass, Object adaptor) {
     Defense.notNull(registrationClass, "registrationClass");
     Defense.notNull(adaptor, "adaptor");
 
     if (this._registrations.containsKey(registrationClass)) {
       throw new IllegalArgumentException(UtilMessages.duplicateRegistration(registrationClass));
     }
 
     this._registrations.put(registrationClass, adaptor);
 
     this._cache.clear();
   }
 
   public synchronized Object getStrategy(Class subjectClass)
   {
     Defense.notNull(subjectClass, "subjectClass");
 
     Object result = this._cache.get(subjectClass);
 
     if (result != null) {
       return result;
     }
     result = searchForAdaptor(subjectClass);
 
     this._cache.put(subjectClass, result);
 
     return result;
   }
 
   private Object searchForAdaptor(Class subjectClass)
   {
     Class[] interfaces;
     int length;
     int i;
     LinkedList queue = null;
     Object result = null;
 
     Class searchClass = subjectClass;
 
     while ((searchClass != Object.class) && (searchClass != null))
     {
       result = this._registrations.get(searchClass);
       if (result != null) {
         return result;
       }
 
       interfaces = searchClass.getInterfaces();
       length = interfaces.length;
 
       if ((queue == null) && (length > 0)) {
         queue = new LinkedList();
       }
       for (i = 0; i < length; ++i) {
         queue.addLast(interfaces[i]);
       }
 
       searchClass = getSuperclass(searchClass);
     }
 
     while ((queue != null) && 
       (!(queue.isEmpty())))
     {
       searchClass = (Class)queue.removeFirst();
 
       result = this._registrations.get(searchClass);
       if (result != null) {
         return result;
       }
 
       interfaces = searchClass.getInterfaces();
       length = interfaces.length;
 
       for (i = 0; i < length; ++i) {
         queue.addLast(interfaces[i]);
       }
 
     }
 
     result = this._registrations.get(Object.class);
     if (result != null) {
       return result;
     }
 
     throw new IllegalArgumentException(UtilMessages.strategyNotFound(subjectClass));
   }
 
   private Class getSuperclass(Class searchClass)
   {
     if (searchClass.isArray())
     {
       Class componentType = searchClass.getComponentType();
 
       if ((!(componentType.isPrimitive())) && (componentType != Object.class)) {
         return [Ljava.lang.Object.class;
       }
     }
     return searchClass.getSuperclass();
   }
 
   public synchronized String toString()
   {
     StringBuffer buffer = new StringBuffer();
     buffer.append("AdaptorRegistry[");
 
     Iterator i = this._registrations.entrySet().iterator();
     boolean showSep = false;
 
     while (i.hasNext())
     {
       if (showSep) {
         buffer.append(' ');
       }
       Map.Entry entry = (Map.Entry)i.next();
 
       Class registeredClass = (Class)entry.getKey();
 
       buffer.append(ClassFabUtils.getJavaClassName(registeredClass));
       buffer.append("=");
       buffer.append(entry.getValue());
 
       showSep = true;
     }
 
     buffer.append("]");
 
     return buffer.toString();
   }
 }