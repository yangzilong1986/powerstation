 package org.apache.hivemind.lib.strategy;
 
 import java.lang.reflect.Constructor;
 import java.util.Iterator;
 import java.util.List;
 import org.apache.hivemind.ApplicationRuntimeException;
 import org.apache.hivemind.ErrorLog;
 import org.apache.hivemind.HiveMind;
 import org.apache.hivemind.ServiceImplementationFactory;
 import org.apache.hivemind.ServiceImplementationFactoryParameters;
 import org.apache.hivemind.impl.BaseLocatable;
 import org.apache.hivemind.lib.util.StrategyRegistry;
 import org.apache.hivemind.lib.util.StrategyRegistryImpl;
 import org.apache.hivemind.service.ClassFab;
 import org.apache.hivemind.service.ClassFabUtils;
 import org.apache.hivemind.service.ClassFactory;
 import org.apache.hivemind.service.MethodIterator;
 import org.apache.hivemind.service.MethodSignature;
 
 public class StrategyFactory
   implements ServiceImplementationFactory
 {
   private ClassFactory _classFactory;
 
   public Object createCoreServiceImplementation(ServiceImplementationFactoryParameters factoryParameters)
   {
     StrategyRegistry ar = new StrategyRegistryImpl();
 
     buildRegistry(factoryParameters, ar);
 
     Class implClass = buildImplementationClass(factoryParameters);
     try
     {
       Constructor c = implClass.getConstructors()[0];
 
       return c.newInstance(new Object[] { ar });
     }
     catch (Exception ex)
     {
       throw new ApplicationRuntimeException(ex.getMessage(), HiveMind.getLocation(factoryParameters.getFirstParameter()), ex);
     }
   }
 
   void buildRegistry(ServiceImplementationFactoryParameters factoryParameters, StrategyRegistry ar)
   {
     Class serviceInterface = factoryParameters.getServiceInterface();
 
     StrategyParameter p = (StrategyParameter)factoryParameters.getFirstParameter();
 
     List contributions = p.getContributions();
 
     Iterator i = contributions.iterator();
 
     while (i.hasNext())
     {
       StrategyContribution c = (StrategyContribution)i.next();
       try
       {
         Object adapter = c.getStrategy();
 
         if (!(serviceInterface.isAssignableFrom(adapter.getClass()))) {
           throw new ClassCastException(StrategyMessages.strategyWrongInterface(adapter, c.getRegisterClass(), serviceInterface));
         }
 
         ar.register(c.getRegisterClass(), adapter);
       }
       catch (Exception ex)
       {
         factoryParameters.getErrorLog().error(ex.getMessage(), c.getLocation(), ex);
       }
     }
   }
 
   private Class buildImplementationClass(ServiceImplementationFactoryParameters factoryParameters)
   {
     String name = ClassFabUtils.generateClassName(factoryParameters.getServiceInterface());
 
     return buildImplementationClass(factoryParameters, name);
   }
 
   Class buildImplementationClass(ServiceImplementationFactoryParameters factoryParameters, String name)
   {
     Class serviceInterface = factoryParameters.getServiceInterface();
 
     ClassFab cf = this._classFactory.newClass(name, Object.class);
 
     cf.addInterface(serviceInterface);
 
     cf.addField("_registry", StrategyRegistry.class);
 
     cf.addConstructor(new Class[] { StrategyRegistry.class }, null, "_registry = $1;");
 
     cf.addMethod(2, new MethodSignature(serviceInterface, "_getStrategy", new Class[] { Object.class }, null), "return (" + serviceInterface.getName() + ") _registry.getStrategy($1.getClass());");
 
     MethodIterator i = new MethodIterator(serviceInterface);
 
     while (i.hasNext())
     {
       MethodSignature sig = i.next();
 
       if (proper(sig))
       {
         addAdaptedMethod(cf, sig);
       }
       else
       {
         ClassFabUtils.addNoOpMethod(cf, sig);
 
         factoryParameters.getErrorLog().error(StrategyMessages.improperServiceMethod(sig), HiveMind.getLocation(factoryParameters.getFirstParameter()), null);
       }
 
     }
 
     if (!(i.getToString())) {
       ClassFabUtils.addToStringMethod(cf, StrategyMessages.toString(factoryParameters.getServiceId(), serviceInterface));
     }
 
     return cf.createClass();
   }
 
   private void addAdaptedMethod(ClassFab cf, MethodSignature sig)
   {
     String body = "return ($r) _getStrategy($1)." + sig.getName() + "($$);";
 
     cf.addMethod(1, sig, body);
   }
 
   private boolean proper(MethodSignature sig)
   {
     Class[] parameterTypes = sig.getParameterTypes();
 
     return ((parameterTypes != null) && (parameterTypes.length > 0) && (!(parameterTypes[0].isPrimitive())));
   }
 
   public void setClassFactory(ClassFactory classFactory)
   {
     this._classFactory = classFactory;
   }
 }