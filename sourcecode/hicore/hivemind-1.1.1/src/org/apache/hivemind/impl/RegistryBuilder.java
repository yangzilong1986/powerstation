 package org.apache.hivemind.impl;
 
 import java.io.PrintStream;
 import java.util.HashSet;
 import java.util.Iterator;
 import java.util.List;
 import java.util.Locale;
 import java.util.Set;
 import org.apache.commons.logging.Log;
 import org.apache.commons.logging.LogFactory;
 import org.apache.hivemind.ErrorHandler;
 import org.apache.hivemind.ModuleDescriptorProvider;
 import org.apache.hivemind.Registry;
 import org.apache.hivemind.internal.RegistryInfrastructure;
 import org.apache.hivemind.parse.ModuleDescriptor;
 
 public final class RegistryBuilder
 {
   private static final Log LOG = LogFactory.getLog(RegistryBuilder.class);
   private ErrorHandler _errorHandler;
   private RegistryAssemblyImpl _registryAssembly;
   private Set _moduleDescriptorProviders;
   private RegistryInfrastructureConstructor _constructor;
 
   public RegistryBuilder()
   {
     this(new DefaultErrorHandler());
   }
 
   public RegistryBuilder(ErrorHandler handler)
   {
     this._errorHandler = handler;
 
     this._registryAssembly = new RegistryAssemblyImpl();
 
     this._moduleDescriptorProviders = new HashSet();
 
     this._constructor = new RegistryInfrastructureConstructor(handler, LOG, this._registryAssembly);
   }
 
   public void addModuleDescriptorProvider(ModuleDescriptorProvider provider)
   {
     this._moduleDescriptorProviders.add(provider);
   }
 
   public Registry constructRegistry(Locale locale)
   {
     for (Iterator i = this._moduleDescriptorProviders.iterator(); i.hasNext(); )
     {
       ModuleDescriptorProvider provider = (ModuleDescriptorProvider)i.next();
 
       processModuleDescriptorProvider(provider);
     }
 
     this._registryAssembly.performPostProcessing();
 
     RegistryInfrastructure infrastructure = this._constructor.constructRegistryInfrastructure(locale);
 
     infrastructure.startup();
 
     return new RegistryImpl(infrastructure);
   }
 
   private void processModuleDescriptorProvider(ModuleDescriptorProvider provider)
   {
     List descriptors = provider.getModuleDescriptors(this._errorHandler);
 
     Iterator i = descriptors.iterator();
     while (i.hasNext())
     {
       ModuleDescriptor md = (ModuleDescriptor)i.next();
 
       this._constructor.addModuleDescriptor(md);
     }
   }
 
   public void addDefaultModuleDescriptorProvider()
   {
     addModuleDescriptorProvider(new XmlModuleDescriptorProvider(new DefaultClassResolver()));
   }
 
   public static Registry constructDefaultRegistry()
   {
     RegistryBuilder builder = new RegistryBuilder();
     builder.addDefaultModuleDescriptorProvider();
     return builder.constructRegistry(Locale.getDefault());
   }
 
   static
   {
     if (LOG.isErrorEnabled())
       return;
     System.err.println("********************************************************************************");
 
     System.err.println("* L O G G I N G   C O N F I G U R A T I O N   E R R O R                        *");
 
     System.err.println("* ---------------------------------------------------------------------------- *");
 
     System.err.println("* Logging is not enabled for org.apache.hivemind.impl.RegistryBuilder.         *");
 
     System.err.println("* Errors during HiveMind module descriptor parsing and validation may not be   *");
 
     System.err.println("* logged. This may result in difficult-to-trace runtime exceptions, if there   *");
 
     System.err.println("* are errors in any of your module descriptors. You should enable error        *");
 
     System.err.println("* logging for the org.apache.hivemind and hivemind loggers.                    *");
 
     System.err.println("********************************************************************************");
   }
 }