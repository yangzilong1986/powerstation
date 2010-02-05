 package org.apache.hivemind.lib.groovy;
 
 import java.util.List;
 import org.apache.hivemind.ClassResolver;
 import org.apache.hivemind.ErrorHandler;
 import org.apache.hivemind.ModuleDescriptorProvider;
 import org.apache.hivemind.Resource;
 import org.apache.hivemind.impl.XmlModuleDescriptorProvider;
 import org.apache.hivemind.parse.XmlResourceProcessor;
 
 public class GroovyModuleDescriptorProvider extends XmlModuleDescriptorProvider
   implements ModuleDescriptorProvider
 {
   public GroovyModuleDescriptorProvider(ClassResolver resolver, Resource resource)
   {
     super(resolver, resource);
   }
 
   public GroovyModuleDescriptorProvider(ClassResolver resolver, List resources)
   {
     super(resolver, resources);
   }
 
   protected XmlResourceProcessor getResourceProcessor(ClassResolver resolver, ErrorHandler handler)
   {
     return new GroovyScriptProcessor(resolver, handler);
   }
 }