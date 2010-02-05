 package org.apache.hivemind.lib.impl;
 
 import java.util.List;
 import org.apache.hivemind.ApplicationRuntimeException;
 import org.apache.hivemind.ServiceImplementationFactory;
 import org.apache.hivemind.ServiceImplementationFactoryParameters;
 import org.apache.hivemind.impl.BaseLocatable;
 import org.apache.hivemind.service.BodyBuilder;
 import org.apache.hivemind.service.ClassFab;
 import org.apache.hivemind.service.ClassFabUtils;
 import org.apache.hivemind.service.ClassFactory;
 import org.apache.hivemind.service.MethodIterator;
 import org.apache.hivemind.service.MethodSignature;
 import org.apache.hivemind.util.ConstructorUtils;
 import org.apache.hivemind.util.PropertyAdaptor;
 import org.apache.hivemind.util.PropertyUtils;
 
 public class ServicePropertyFactory
   implements ServiceImplementationFactory
 {
   private ClassFactory _classFactory;
 
   public Object createCoreServiceImplementation(ServiceImplementationFactoryParameters factoryParameters)
   {
     ServicePropertyFactoryParameter p = (ServicePropertyFactoryParameter)factoryParameters.getParameters().get(0);
 
     Object targetService = p.getService();
     String propertyName = p.getPropertyName();
 
     PropertyAdaptor pa = PropertyUtils.getPropertyAdaptor(targetService, propertyName);
 
     String readMethodName = pa.getReadMethodName();
 
     if (readMethodName == null) {
       throw new ApplicationRuntimeException(ImplMessages.servicePropertyNotReadable(propertyName, targetService), null, p.getLocation(), null);
     }
 
     Class serviceInterface = factoryParameters.getServiceInterface();
 
     if (!(serviceInterface.isAssignableFrom(pa.getPropertyType()))) {
       throw new ApplicationRuntimeException(ImplMessages.servicePropertyWrongType(propertyName, targetService, pa.getPropertyType(), serviceInterface), p.getLocation(), null);
     }
 
     String name = ClassFabUtils.generateClassName(serviceInterface);
 
     ClassFab cf = this._classFactory.newClass(name, Object.class);
 
     addInfrastructure(cf, targetService, serviceInterface, propertyName, readMethodName);
 
     addMethods(cf, factoryParameters.getServiceId(), serviceInterface, propertyName, targetService);
 
     Class proxyClass = cf.createClass();
     try
     {
       return ConstructorUtils.invokeConstructor(proxyClass, new Object[] { targetService });
     }
     catch (Throwable ex)
     {
       throw new ApplicationRuntimeException(ex.getMessage(), p.getLocation(), ex);
     }
   }
 
   private void addInfrastructure(ClassFab cf, Object targetService, Class serviceInterface, String propertyName, String readPropertyMethodName)
   {
     cf.addInterface(serviceInterface);
 
     Class targetServiceClass = ClassFabUtils.getInstanceClass(targetService, serviceInterface);
 
     cf.addField("_targetService", targetServiceClass);
 
     cf.addConstructor(new Class[] { targetServiceClass }, null, "{ super(); _targetService = $1; }");
 
     BodyBuilder b = new BodyBuilder();
 
     b.begin();
     b.addln("{0} property = _targetService.{1}();", serviceInterface.getName(), readPropertyMethodName);
 
     b.addln("if (property == null)");
     b.add("  throw new java.lang.NullPointerException(");
     b.addQuoted(ImplMessages.servicePropertyWasNull(propertyName, targetService));
     b.addln(");");
 
     b.addln("return property;");
 
     b.end();
 
     MethodSignature sig = new MethodSignature(serviceInterface, "_targetServiceProperty", null, null);
 
     cf.addMethod(18, sig, b.toString());
   }
 
   private void addMethods(ClassFab cf, String serviceId, Class serviceInterface, String propertyName, Object targetService)
   {
     MethodIterator mi = new MethodIterator(serviceInterface);
 
     while (mi.hasNext())
     {
       MethodSignature sig = mi.next();
 
       String body = "return ($r) _targetServiceProperty()." + sig.getName() + "($$);";
 
       cf.addMethod(1, sig, body);
     }
 
     if (!(mi.getToString()))
       ClassFabUtils.addToStringMethod(cf, ImplMessages.servicePropertyToString(serviceId, serviceInterface, propertyName, targetService));
   }
 
   public void setClassFactory(ClassFactory factory)
   {
     this._classFactory = factory;
   }
 }