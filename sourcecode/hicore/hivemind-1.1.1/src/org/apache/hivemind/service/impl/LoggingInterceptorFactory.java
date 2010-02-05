 package org.apache.hivemind.service.impl;
 
 import java.lang.reflect.Constructor;
 import java.util.Iterator;
 import java.util.List;
 import org.apache.commons.logging.Log;
 import org.apache.hivemind.ApplicationRuntimeException;
 import org.apache.hivemind.InterceptorStack;
 import org.apache.hivemind.ServiceInterceptorFactory;
 import org.apache.hivemind.internal.Module;
 import org.apache.hivemind.methodmatch.MethodMatcher;
 import org.apache.hivemind.service.BodyBuilder;
 import org.apache.hivemind.service.ClassFab;
 import org.apache.hivemind.service.ClassFabUtils;
 import org.apache.hivemind.service.ClassFactory;
 import org.apache.hivemind.service.MethodContribution;
 import org.apache.hivemind.service.MethodFab;
 import org.apache.hivemind.service.MethodIterator;
 import org.apache.hivemind.service.MethodSignature;
 
 public class LoggingInterceptorFactory
   implements ServiceInterceptorFactory
 {
   private ClassFactory _factory;
   private String _serviceId;
 
   private void addPassThruMethodImplementation(ClassFab classFab, MethodSignature sig)
   {
     BodyBuilder builder = new BodyBuilder();
     builder.begin();
 
     builder.add("return ($r) _delegate.");
     builder.add(sig.getName());
     builder.addln("($$);");
 
     builder.end();
 
     classFab.addMethod(1, sig, builder.toString());
   }
 
   protected void addServiceMethodImplementation(ClassFab classFab, MethodSignature sig)
   {
     Class returnType = sig.getReturnType();
     String methodName = sig.getName();
 
     boolean isVoid = returnType == Void.TYPE;
 
     BodyBuilder builder = new BodyBuilder();
 
     builder.begin();
     builder.addln("boolean debug = _log.isDebugEnabled();");
 
     builder.addln("if (debug)");
     builder.add("  org.apache.hivemind.service.impl.LoggingUtils.entry(_log, ");
     builder.addQuoted(methodName);
     builder.addln(", $args);");
 
     if (!(isVoid))
     {
       builder.add(ClassFabUtils.getJavaClassName(returnType));
       builder.add(" result = ");
     }
 
     builder.add("_delegate.");
     builder.add(methodName);
     builder.addln("($$);");
 
     if (isVoid)
     {
       builder.addln("if (debug)");
       builder.add("  org.apache.hivemind.service.impl.LoggingUtils.voidExit(_log, ");
       builder.addQuoted(methodName);
       builder.addln(");");
     }
     else
     {
       builder.addln("if (debug)");
       builder.add("  org.apache.hivemind.service.impl.LoggingUtils.exit(_log, ");
       builder.addQuoted(methodName);
       builder.addln(", ($w)result);");
       builder.addln("return result;");
     }
 
     builder.end();
 
     MethodFab methodFab = classFab.addMethod(1, sig, builder.toString());
 
     builder.clear();
 
     builder.begin();
     builder.add("org.apache.hivemind.service.impl.LoggingUtils.exception(_log, ");
     builder.addQuoted(methodName);
     builder.addln(", $e);");
     builder.addln("throw $e;");
     builder.end();
 
     String body = builder.toString();
 
     Class[] exceptions = sig.getExceptionTypes();
 
     int count = exceptions.length;
 
     for (int i = 0; i < count; ++i)
     {
       methodFab.addCatch(exceptions[i], body);
     }
 
     methodFab.addCatch(RuntimeException.class, body);
   }
 
   protected void addServiceMethods(InterceptorStack stack, ClassFab fab, List parameters)
   {
     MethodMatcher matcher = buildMethodMatcher(parameters);
 
     MethodIterator mi = new MethodIterator(stack.getServiceInterface());
 
     while (mi.hasNext())
     {
       MethodSignature sig = mi.next();
 
       if (includeMethod(matcher, sig))
         addServiceMethodImplementation(fab, sig);
       else {
         addPassThruMethodImplementation(fab, sig);
       }
     }
     if (!(mi.getToString()))
       addToStringMethod(stack, fab);
   }
 
   protected void addToStringMethod(InterceptorStack stack, ClassFab fab)
   {
     ClassFabUtils.addToStringMethod(fab, "<LoggingInterceptor for " + stack.getServiceExtensionPointId() + "(" + stack.getServiceInterface().getName() + ")>");
   }
 
   private MethodMatcher buildMethodMatcher(List parameters)
   {
     MethodMatcher result = null;
 
     Iterator i = parameters.iterator();
     while (i.hasNext())
     {
       MethodContribution mc = (MethodContribution)i.next();
 
       if (result == null) {
         result = new MethodMatcher();
       }
       result.put(mc.getMethodPattern(), mc);
     }
 
     return result;
   }
 
   private Class constructInterceptorClass(InterceptorStack stack, List parameters)
   {
     Class serviceInterfaceClass = stack.getServiceInterface();
 
     String name = ClassFabUtils.generateClassName(serviceInterfaceClass);
 
     ClassFab classFab = this._factory.newClass(name, Object.class);
 
     classFab.addInterface(serviceInterfaceClass);
 
     createInfrastructure(stack, classFab);
 
     addServiceMethods(stack, classFab, parameters);
 
     return classFab.createClass();
   }
 
   private void createInfrastructure(InterceptorStack stack, ClassFab classFab)
   {
     Class topClass = ClassFabUtils.getInstanceClass(stack.peek(), stack.getServiceInterface());
 
     classFab.addField("_log", Log.class);
 
     classFab.addField("_delegate", topClass);
 
     classFab.addConstructor(new Class[] { Log.class, topClass }, null, "{ _log = $1; _delegate = $2; }");
   }
 
   public void createInterceptor(InterceptorStack stack, Module contributingModule, List parameters)
   {
     Class interceptorClass = constructInterceptorClass(stack, parameters);
     try
     {
       Object interceptor = instantiateInterceptor(stack, interceptorClass);
 
       stack.push(interceptor);
     }
     catch (Exception ex)
     {
       throw new ApplicationRuntimeException(ServiceMessages.errorInstantiatingInterceptor(this._serviceId, stack, interceptorClass, ex), ex);
     }
   }
 
   private boolean includeMethod(MethodMatcher matcher, MethodSignature sig)
   {
     if (matcher == null) {
       return true;
     }
     MethodContribution mc = (MethodContribution)matcher.get(sig);
 
     return ((mc == null) || (mc.getInclude()));
   }
 
   private Object instantiateInterceptor(InterceptorStack stack, Class interceptorClass)
     throws Exception
   {
     Object stackTop = stack.peek();
 
     Constructor c = interceptorClass.getConstructors()[0];
 
     return c.newInstance(new Object[] { stack.getServiceLog(), stackTop });
   }
 
   public void setFactory(ClassFactory factory)
   {
     this._factory = factory;
   }
 
   public void setServiceId(String string)
   {
     this._serviceId = string;
   }
 }