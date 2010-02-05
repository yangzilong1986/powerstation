 package org.apache.hivemind.lib.groovy;
 
 import groovy.lang.Binding;
 import groovy.lang.GroovyCodeSource;
 import groovy.lang.GroovyShell;
 import groovy.lang.Script;
 import java.io.IOException;
 import javax.xml.parsers.SAXParser;
 import org.apache.hivemind.ApplicationRuntimeException;
 import org.apache.hivemind.ClassResolver;
 import org.apache.hivemind.ErrorHandler;
 import org.apache.hivemind.Resource;
 import org.apache.hivemind.parse.DescriptorParser;
 import org.apache.hivemind.parse.ModuleDescriptor;
 import org.apache.hivemind.parse.XmlResourceProcessor;
 import org.xml.sax.SAXException;
 
 class GroovyScriptProcessor extends XmlResourceProcessor
 {
   private GroovyShell _groovyShell;
 
   public GroovyScriptProcessor(ClassResolver resolver, ErrorHandler errorHandler)
   {
     super(resolver, errorHandler);
   }
 
   protected ModuleDescriptor parseResource(Resource resource, SAXParser parser, DescriptorParser contentHandler)
     throws SAXException, IOException
   {
     Script script;
     HiveMindBuilder builder = new HiveMindBuilder(contentHandler);
 
     GroovyCodeSource source = new GroovyCodeSource(resource.getResourceURL());
     try
     {
       script = getGroovyShell().parse(source);
     }
     catch (Exception e)
     {
       throw new ApplicationRuntimeException(e);
     }
 
     Binding processorBinding = new Binding();
     processorBinding.setVariable("processor", builder);
 
     script.setBinding(processorBinding);
 
     script.run();
 
     return contentHandler.getModuleDescriptor();
   }
 
   private GroovyShell getGroovyShell()
   {
     if (this._groovyShell == null) {
       this._groovyShell = new GroovyShell(this._resolver.getClassLoader());
     }
     return this._groovyShell;
   }
 }