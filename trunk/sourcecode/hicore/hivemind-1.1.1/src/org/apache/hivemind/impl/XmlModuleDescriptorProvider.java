 package org.apache.hivemind.impl;
 
 import java.io.IOException;
 import java.net.URL;
 import java.util.ArrayList;
 import java.util.Enumeration;
 import java.util.Iterator;
 import java.util.List;
 import org.apache.commons.logging.Log;
 import org.apache.commons.logging.LogFactory;
 import org.apache.hivemind.ApplicationRuntimeException;
 import org.apache.hivemind.ClassResolver;
 import org.apache.hivemind.ErrorHandler;
 import org.apache.hivemind.HiveMind;
 import org.apache.hivemind.ModuleDescriptorProvider;
 import org.apache.hivemind.Resource;
 import org.apache.hivemind.parse.ModuleDescriptor;
 import org.apache.hivemind.parse.SubModuleDescriptor;
 import org.apache.hivemind.parse.XmlResourceProcessor;
 import org.apache.hivemind.util.URLResource;
 
 public class XmlModuleDescriptorProvider
   implements ModuleDescriptorProvider
 {
   private static final Log LOG = LogFactory.getLog(XmlModuleDescriptorProvider.class);
   public static final String HIVE_MODULE_XML = "META-INF/hivemodule.xml";
   private List _resources;
   private List _moduleDescriptors;
   private ClassResolver _resolver;
   private ErrorHandler _errorHandler;
   private XmlResourceProcessor _processor;
 
   public XmlModuleDescriptorProvider(ClassResolver resolver)
   {
     this(resolver, "META-INF/hivemodule.xml");
   }
 
   public XmlModuleDescriptorProvider(ClassResolver resolver, String resourcePath)
   {
     this._resources = new ArrayList();
 
     this._moduleDescriptors = new ArrayList();
 
     this._resolver = resolver;
     this._resources.addAll(getDescriptorResources(resourcePath, this._resolver));
   }
 
   public XmlModuleDescriptorProvider(ClassResolver resolver, Resource resource)
   {
     this._resources = new ArrayList();
 
     this._moduleDescriptors = new ArrayList();
 
     this._resolver = resolver;
     this._resources.add(resource);
   }
 
   public XmlModuleDescriptorProvider(ClassResolver resolver, List resources)
   {
     this._resources = new ArrayList();
 
     this._moduleDescriptors = new ArrayList();
 
     this._resolver = resolver;
     this._resources.addAll(resources);
   }
 
   private List getDescriptorResources(String resourcePath, ClassResolver resolver)
   {
     if (LOG.isDebugEnabled()) {
       LOG.debug("Processing modules visible to " + resolver);
     }
     List descriptors = new ArrayList();
 
     ClassLoader loader = resolver.getClassLoader();
     Enumeration e = null;
     try
     {
       e = loader.getResources(resourcePath);
     }
     catch (IOException ex)
     {
       throw new ApplicationRuntimeException(ImplMessages.unableToFindModules(resolver, ex), ex);
     }
 
     while (e.hasMoreElements())
     {
       URL descriptorURL = (URL)e.nextElement();
 
       descriptors.add(new URLResource(descriptorURL));
     }
 
     return descriptors;
   }
 
   public List getModuleDescriptors(ErrorHandler handler)
   {
     this._errorHandler = handler;
 
     this._processor = getResourceProcessor(this._resolver, handler);
 
     for (Iterator i = this._resources.iterator(); i.hasNext(); )
     {
       Resource resource = (Resource)i.next();
 
       processResource(resource);
     }
 
     this._processor = null;
 
     this._errorHandler = null;
 
     return this._moduleDescriptors;
   }
 
   private void processResource(Resource resource)
   {
     try
     {
       ModuleDescriptor md = this._processor.processResource(resource);
 
       this._moduleDescriptors.add(md);
 
       processSubModules(md);
     }
     catch (RuntimeException ex)
     {
       this._errorHandler.error(LOG, ex.getMessage(), HiveMind.getLocation(ex), ex);
     }
   }
 
   private void processSubModules(ModuleDescriptor moduleDescriptor)
   {
     List subModules = moduleDescriptor.getSubModules();
 
     if (subModules == null) {
       return;
     }
     for (Iterator i = subModules.iterator(); i.hasNext(); )
     {
       SubModuleDescriptor smd = (SubModuleDescriptor)i.next();
 
       Resource descriptorResource = smd.getDescriptor();
 
       if (descriptorResource.getResourceURL() == null)
       {
         this._errorHandler.error(LOG, ImplMessages.subModuleDoesNotExist(descriptorResource), smd.getLocation(), null);
       }
 
       processResource(smd.getDescriptor());
     }
   }
 
   protected XmlResourceProcessor getResourceProcessor(ClassResolver resolver, ErrorHandler handler)
   {
     return new XmlResourceProcessor(resolver, handler);
   }
 }