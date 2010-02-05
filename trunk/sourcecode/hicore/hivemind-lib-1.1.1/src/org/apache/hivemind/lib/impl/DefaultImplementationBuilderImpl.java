 package org.apache.hivemind.lib.impl;
 
 import java.util.Collections;
 import java.util.HashMap;
 import java.util.Map;
 import org.apache.hivemind.ApplicationRuntimeException;
 import org.apache.hivemind.impl.BaseLocatable;
 import org.apache.hivemind.lib.DefaultImplementationBuilder;
 import org.apache.hivemind.service.ClassFab;
 import org.apache.hivemind.service.ClassFabUtils;
 import org.apache.hivemind.service.ClassFactory;
 import org.apache.hivemind.service.MethodIterator;
 
 public class DefaultImplementationBuilderImpl extends BaseLocatable
   implements DefaultImplementationBuilder
 {
   private Map _instances;
   private ClassFactory _classFactory;
 
   public DefaultImplementationBuilderImpl()
   {
     this._instances = Collections.synchronizedMap(new HashMap());
   }
 
   public Object buildDefaultImplementation(Class interfaceType)
   {
     Object result = this._instances.get(interfaceType);
 
     if (result == null)
     {
       result = create(interfaceType);
 
       this._instances.put(interfaceType, result);
     }
 
     return result;
   }
 
   private Object create(Class interfaceType)
   {
     Class defaultClass = createClass(interfaceType);
     try
     {
       return defaultClass.newInstance();
     }
     catch (Exception ex)
     {
       throw new ApplicationRuntimeException(ImplMessages.unableToCreateDefaultImplementation(interfaceType, ex), ex);
     }
   }
 
   private Class createClass(Class interfaceType)
   {
     if (!(interfaceType.isInterface())) {
       throw new ApplicationRuntimeException(ImplMessages.notAnInterface(interfaceType));
     }
     String name = ClassFabUtils.generateClassName(interfaceType);
 
     ClassFab cf = this._classFactory.newClass(name, Object.class);
 
     cf.addInterface(interfaceType);
 
     MethodIterator mi = new MethodIterator(interfaceType);
 
     while (mi.hasNext())
     {
       ClassFabUtils.addNoOpMethod(cf, mi.next());
     }
 
     if (!(mi.getToString())) {
       ClassFabUtils.addToStringMethod(cf, ImplMessages.defaultImplementationDescription(interfaceType));
     }
 
     return cf.createClass();
   }
 
   public void setClassFactory(ClassFactory factory)
   {
     this._classFactory = factory;
   }
 }