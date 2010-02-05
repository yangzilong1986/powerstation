 package org.apache.hivemind.lib.impl;
 
 import java.lang.reflect.Constructor;
 import java.rmi.RemoteException;
 import java.util.List;
 import org.apache.hivemind.ApplicationRuntimeException;
 import org.apache.hivemind.ServiceImplementationFactory;
 import org.apache.hivemind.ServiceImplementationFactoryParameters;
 import org.apache.hivemind.impl.BaseLocatable;
 import org.apache.hivemind.internal.Module;
 import org.apache.hivemind.lib.NameLookup;
 import org.apache.hivemind.lib.RemoteExceptionCoordinator;
 import org.apache.hivemind.service.BodyBuilder;
 import org.apache.hivemind.service.ClassFab;
 import org.apache.hivemind.service.ClassFabUtils;
 import org.apache.hivemind.service.ClassFactory;
 import org.apache.hivemind.service.MethodIterator;
 import org.apache.hivemind.service.MethodSignature;
 
 public class EJBProxyFactory extends BaseLocatable
   implements ServiceImplementationFactory
 {
   private NameLookup _nameLookup;
   private RemoteExceptionCoordinator _coordinator;
   private ClassFactory _classFactory;
 
   public Object createCoreServiceImplementation(ServiceImplementationFactoryParameters factoryParameters)
   {
     EJBProxyParameters proxyParameters = (EJBProxyParameters)factoryParameters.getParameters().get(0);
     String jndiName = proxyParameters.getJndiName();
     String homeInterfaceClassName = proxyParameters.getHomeInterfaceClassName();
 
     Module module = factoryParameters.getInvokingModule();
     Class serviceInterface = factoryParameters.getServiceInterface();
 
     Class homeInterface = module.resolveType(homeInterfaceClassName);
 
     String proxyClassName = ClassFabUtils.generateClassName(serviceInterface);
 
     ClassFab classFab = this._classFactory.newClass(proxyClassName, AbstractEJBProxy.class);
 
     classFab.addInterface(serviceInterface);
 
     classFab.addField("_remote", serviceInterface);
 
     addClearCachedMethod(classFab);
 
     addLookupMethod(classFab, homeInterface, serviceInterface, jndiName);
 
     addServiceMethods(classFab, serviceInterface, factoryParameters.getServiceId(), jndiName);
 
     addConstructor(classFab);
 
     Class proxyClass = classFab.createClass();
 
     return invokeConstructor(proxyClass, proxyParameters.getNameLookup(this._nameLookup));
   }
 
   private void addClearCachedMethod(ClassFab classFab)
   {
     classFab.addMethod(4, new MethodSignature(Void.TYPE, "_clearCachedReferences", null, null), "_remote = null;");
   }
 
   private void addLookupMethod(ClassFab classFab, Class homeInterface, Class remoteInterface, String jndiName)
   {
     String homeInterfaceName = homeInterface.getName();
 
     BodyBuilder builder = new BodyBuilder();
 
     builder.begin();
 
     builder.addln("if (_remote != null)");
     builder.addln("  return _remote;");
 
     builder.add(homeInterfaceName);
     builder.add(" home = (");
     builder.add(homeInterfaceName);
     builder.add(") _lookup(");
     builder.addQuoted(jndiName);
     builder.addln(");");
 
     builder.add("try");
     builder.begin();
     builder.add("_remote = home.create();");
     builder.end();
     builder.add("catch (javax.ejb.CreateException ex)");
     builder.begin();
     builder.add("throw new java.rmi.RemoteException(ex.getMessage(), ex);");
     builder.end();
 
     builder.add("return _remote;");
 
     builder.end();
 
     classFab.addMethod(34, new MethodSignature(remoteInterface, "_lookupRemote", null, new Class[] { RemoteException.class }), builder.toString());
   }
 
   private void addServiceMethods(ClassFab classFab, Class serviceInterface, String serviceId, String jndiName)
   {
     MethodIterator mi = new MethodIterator(serviceInterface);
 
     while (mi.hasNext())
     {
       addServiceMethod(classFab, mi.next());
     }
 
     if (!(mi.getToString()))
       addToStringMethod(classFab, serviceInterface, serviceId, jndiName);
   }
 
   private void addServiceMethod(ClassFab classFab, MethodSignature sig)
   {
     String methodName = sig.getName();
 
     boolean isVoid = sig.getReturnType().equals(Void.TYPE);
 
     BodyBuilder builder = new BodyBuilder();
 
     builder.begin();
 
     builder.addln("boolean first = true;");
     builder.add("while (true)");
     builder.begin();
 
     builder.add("try");
     builder.begin();
 
     if (!(isVoid)) {
       builder.add("return ");
     }
     builder.add("_lookupRemote().");
     builder.add(methodName);
     builder.addln("($$);");
 
     if (isVoid) {
       builder.addln("return;");
     }
     builder.end();
 
     builder.add("catch (java.rmi.RemoteException ex)");
     builder.begin();
 
     builder.addln("if (first)");
     builder.begin();
 
     builder.addln("_handleRemoteException(ex);");
     builder.addln("first = false;");
 
     builder.end();
     builder.addln("else");
     builder.add("  throw ex;");
     builder.end();
     builder.end();
     builder.end();
 
     classFab.addMethod(1, sig, builder.toString());
   }
 
   private void addToStringMethod(ClassFab classFab, Class serviceInterface, String serviceId, String jndiName)
   {
     ClassFabUtils.addToStringMethod(classFab, ImplMessages.ejbProxyDescription(serviceId, serviceInterface, jndiName));
   }
 
   private void addConstructor(ClassFab classFab)
   {
     classFab.addConstructor(new Class[] { NameLookup.class, RemoteExceptionCoordinator.class }, null, "super($1, $2);");
   }
 
   private Object invokeConstructor(Class proxyClass, NameLookup nameLookup)
   {
     try
     {
       Constructor c = proxyClass.getConstructor(new Class[] { NameLookup.class, RemoteExceptionCoordinator.class });
 
       return c.newInstance(new Object[] { nameLookup, this._coordinator });
     }
     catch (Exception ex)
     {
       throw new ApplicationRuntimeException(ex);
     }
   }
 
   public void setClassFactory(ClassFactory factory)
   {
     this._classFactory = factory;
   }
 
   public void setCoordinator(RemoteExceptionCoordinator coordinator)
   {
     this._coordinator = coordinator;
   }
 
   public void setNameLookup(NameLookup lookup)
   {
     this._nameLookup = lookup;
   }
 }