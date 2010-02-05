 package org.apache.hivemind.lib.impl;
 
 import java.lang.reflect.AccessibleObject;
 import java.lang.reflect.InvocationHandler;
 import java.lang.reflect.InvocationTargetException;
 import java.lang.reflect.Method;
 import java.lang.reflect.Proxy;
 import java.util.List;
 import org.aopalliance.intercept.MethodInterceptor;
 import org.aopalliance.intercept.MethodInvocation;
 import org.apache.hivemind.ClassResolver;
 import org.apache.hivemind.InterceptorStack;
 import org.apache.hivemind.ServiceInterceptorFactory;
 import org.apache.hivemind.impl.BaseLocatable;
 import org.apache.hivemind.internal.Module;
 import org.apache.hivemind.util.Defense;
 
 public class MethodInterceptorFactory extends BaseLocatable
   implements ServiceInterceptorFactory
 {
   public void createInterceptor(InterceptorStack stack, Module invokingModule, List parameters)
   {
     Class[] interfaces = { stack.getServiceInterface() };
     ClassLoader classLoader = invokingModule.getClassResolver().getClassLoader();
     Object parameter = parameters.get(0);
     Defense.isAssignable(parameter, MethodInterceptor.class, "Implementation Object");
     MethodInterceptor methodInterceptor = (MethodInterceptor)parameter;
     InvocationHandler invocationHandler = new MethodInterceptorInvocationHandler(methodInterceptor, stack);
     stack.push(Proxy.newProxyInstance(classLoader, interfaces, invocationHandler));
   }
 
   private final class MethodInvocationImpl
     implements MethodInvocation
   {
     private final Object next;
     private final Method method;
     private final Object[] arguments;
     private final Object proxy;
 
     public MethodInvocationImpl(Object paramObject1, Method paramMethod, Object[] paramArrayOfObject, Object paramObject2)
     {
       this.next = paramObject1;
       this.method = paramMethod;
       this.arguments = arguments;
       this.proxy = paramObject2;
     }
 
     public final Object proceed()
       throws Throwable
     {
       try
       {
         return this.method.invoke(this.next, this.arguments);
       }
       catch (InvocationTargetException e)
       {
         throw e.getTargetException();
       }
     }
 
     public final Method getMethod()
     {
       return this.method;
     }
 
     public final AccessibleObject getStaticPart()
     {
       return this.method;
     }
 
     public final Object getThis()
     {
       return this.proxy;
     }
 
     public final Object[] getArguments()
     {
       return this.arguments;
     }
   }
 
   private final class MethodInterceptorInvocationHandler
     implements InvocationHandler
   {
     private final MethodInterceptor methodInterceptor;
     private final InterceptorStack stack;
     private final Object target;
 
     public MethodInterceptorInvocationHandler(MethodInterceptor paramMethodInterceptor, InterceptorStack paramInterceptorStack)
     {
       this.stack = stack;
       this.target = stack.peek();
       this.methodInterceptor = paramMethodInterceptor;
     }
 
     public Object invoke(Object proxy, Method method, Object[] args)
       throws Throwable
     {
       return this.methodInterceptor.invoke(new MethodInterceptorFactory.MethodInvocationImpl(MethodInterceptorFactory.this, this.target, method, args, this.stack.peek()));
     }
   }
 }