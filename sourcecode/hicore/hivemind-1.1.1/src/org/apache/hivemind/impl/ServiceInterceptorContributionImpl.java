 package org.apache.hivemind.impl;
 
 import java.util.List;
 import org.apache.hivemind.InterceptorStack;
 import org.apache.hivemind.ServiceInterceptorFactory;
 import org.apache.hivemind.internal.ExtensionPoint;
 import org.apache.hivemind.internal.Module;
 import org.apache.hivemind.internal.ServiceInterceptorContribution;
 import org.apache.hivemind.internal.ServicePoint;
 import org.apache.hivemind.schema.Schema;
 import org.apache.hivemind.util.ToStringBuilder;
 
 public final class ServiceInterceptorContributionImpl extends BaseLocatable
   implements ServiceInterceptorContribution
 {
   private String _factoryServiceId;
   private Module _contributingModule;
   private List _parameters;
   private List _convertedParameters;
   private ServiceInterceptorFactory _factory;
   private String _precedingInterceptorIds;
   private String _followingInterceptorIds;
   private String _name;
 
   public String toString()
   {
     ToStringBuilder builder = new ToStringBuilder(this);
     builder.append("factoryServiceId", this._factoryServiceId);
     builder.append("parameters", this._parameters);
     builder.append("precedingInterceptorIds", this._precedingInterceptorIds);
     builder.append("followingInterceptorIds", this._followingInterceptorIds);
     builder.append("name", this._name);
     return builder.toString();
   }
 
   public String getName()
   {
     if (this._name == null)
     {
       return getFactoryServiceId();
     }
     return this._name;
   }
 
   public void setName(String name)
   {
     this._name = name;
   }
 
   public String getFactoryServiceId()
   {
     return this._factoryServiceId;
   }
 
   public void setFactoryServiceId(String string)
   {
     this._factoryServiceId = string;
   }
 
   public void createInterceptor(InterceptorStack stack)
   {
     setup();
 
     this._factory.createInterceptor(stack, this._contributingModule, this._convertedParameters);
   }
 
   private synchronized void setup()
   {
     if (this._factory != null)
       return;
     ServicePoint factoryPoint = this._contributingModule.getServicePoint(this._factoryServiceId);
 
     this._factory = ((ServiceInterceptorFactory)factoryPoint.getService(ServiceInterceptorFactory.class));
 
     Schema schema = factoryPoint.getParametersSchema();
 
     SchemaProcessorImpl processor = new SchemaProcessorImpl(factoryPoint.getErrorLog(), schema);
 
     processor.process(this._parameters, this._contributingModule);
 
     this._convertedParameters = processor.getElements();
   }
 
   public void setContributingModule(Module module)
   {
     this._contributingModule = module;
   }
 
   public void setParameters(List list)
   {
     this._parameters = list;
   }
 
   public String getFollowingInterceptorIds()
   {
     return this._followingInterceptorIds;
   }
 
   public String getPrecedingInterceptorIds()
   {
     return this._precedingInterceptorIds;
   }
 
   public void setFollowingInterceptorIds(String string)
   {
     this._followingInterceptorIds = string;
   }
 
   public void setPrecedingInterceptorIds(String string)
   {
     this._precedingInterceptorIds = string;
   }
 }