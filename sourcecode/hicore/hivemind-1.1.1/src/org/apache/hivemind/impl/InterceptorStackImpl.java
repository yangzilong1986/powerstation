 package org.apache.hivemind.impl;
 
 import org.apache.commons.logging.Log;
 import org.apache.hivemind.ApplicationRuntimeException;
 import org.apache.hivemind.InterceptorStack;
 import org.apache.hivemind.Locatable;
 import org.apache.hivemind.internal.ExtensionPoint;
 import org.apache.hivemind.internal.Module;
 import org.apache.hivemind.internal.ServiceInterceptorContribution;
 import org.apache.hivemind.internal.ServicePoint;
 import org.apache.hivemind.util.ToStringBuilder;
 
 public final class InterceptorStackImpl
   implements InterceptorStack
 {
   private final Log _log;
   private ServiceInterceptorContribution _contribution;
   private ServicePoint _sep;
   private Class _interfaceClass;
   private Object _top;
 
   public InterceptorStackImpl(Log log, ServicePoint sep, Object root)
   {
     this._log = log;
     this._sep = sep;
     this._top = root;
     this._interfaceClass = sep.getServiceInterface();
   }
 
   public String toString()
   {
     ToStringBuilder builder = new ToStringBuilder(this);
     builder.append("contribution", this._contribution);
     builder.append("interfaceClass", this._interfaceClass);
     builder.append("top", this._top);
 
     return builder.toString();
   }
 
   public String getServiceExtensionPointId()
   {
     return this._sep.getExtensionPointId();
   }
 
   public Module getServiceModule()
   {
     return this._sep.getModule();
   }
 
   public Class getServiceInterface()
   {
     return this._interfaceClass;
   }
 
   public Object peek()
   {
     return this._top;
   }
 
   public void push(Object interceptor)
   {
     if (interceptor == null) {
       throw new ApplicationRuntimeException(ImplMessages.nullInterceptor(this._contribution, this._sep), this._contribution.getLocation(), null);
     }
 
     if (!(this._interfaceClass.isAssignableFrom(interceptor.getClass()))) {
       throw new ApplicationRuntimeException(ImplMessages.interceptorDoesNotImplementInterface(interceptor, this._contribution, this._sep, this._interfaceClass), this._contribution.getLocation(), null);
     }
 
     this._top = interceptor;
   }
 
   public void process(ServiceInterceptorContribution contribution)
   {
     if (this._log.isDebugEnabled()) {
       this._log.debug("Applying interceptor factory " + contribution.getFactoryServiceId());
     }
 
     try
     {
       this._contribution = contribution;
 
       contribution.createInterceptor(this);
     }
     finally
     {
       this._contribution = null;
     }
   }
 
   public Log getServiceLog()
   {
     return this._log;
   }
 }