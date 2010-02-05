 package org.apache.hivemind.impl;
 
 import java.util.List;
 import org.apache.commons.logging.Log;
 import org.apache.hivemind.ErrorLog;
 import org.apache.hivemind.ServiceImplementationFactoryParameters;
 import org.apache.hivemind.internal.ExtensionPoint;
 import org.apache.hivemind.internal.Module;
 import org.apache.hivemind.internal.ServicePoint;
 import org.apache.hivemind.util.Defense;
 
 public class ServiceImplementationFactoryParametersImpl
   implements ServiceImplementationFactoryParameters
 {
   private ServicePoint _servicePoint;
   private Module _invokingModule;
   private List _parameters;
 
   public ServiceImplementationFactoryParametersImpl(ServicePoint servicePoint, Module invokingModule, List parameters)
   {
     Defense.notNull(servicePoint, "servicePoint");
     Defense.notNull(invokingModule, "invokingModule");
     Defense.notNull(parameters, "parameters");
 
     this._servicePoint = servicePoint;
     this._invokingModule = invokingModule;
     this._parameters = parameters;
   }
 
   public boolean equals(Object other)
   {
     ServiceImplementationFactoryParametersImpl p = (ServiceImplementationFactoryParametersImpl)other;
 
     return ((this._servicePoint == p._servicePoint) && (this._invokingModule == p._invokingModule) && (this._parameters.equals(p._parameters)));
   }
 
   public String getServiceId()
   {
     return this._servicePoint.getExtensionPointId();
   }
 
   public Class getServiceInterface()
   {
     return this._servicePoint.getServiceInterface();
   }
 
   public Log getLog()
   {
     return this._servicePoint.getLog();
   }
 
   public ErrorLog getErrorLog()
   {
     return this._servicePoint.getErrorLog();
   }
 
   public Module getInvokingModule()
   {
     return this._invokingModule;
   }
 
   public List getParameters()
   {
     return this._parameters;
   }
 
   public Object getFirstParameter()
   {
     return ((this._parameters.isEmpty()) ? null : this._parameters.get(0));
   }
 }