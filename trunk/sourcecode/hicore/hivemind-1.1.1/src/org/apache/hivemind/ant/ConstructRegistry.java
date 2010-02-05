 package org.apache.hivemind.ant;
 
 import java.io.BufferedOutputStream;
 import java.io.File;
 import java.io.FileOutputStream;
 import java.io.FilterOutputStream;
 import java.io.IOException;
 import java.io.OutputStream;
 import java.net.URL;
 import java.util.ArrayList;
 import java.util.List;
 import org.apache.hivemind.ModuleDescriptorProvider;
 import org.apache.hivemind.Resource;
 import org.apache.hivemind.impl.DefaultClassResolver;
 import org.apache.hivemind.impl.XmlModuleDescriptorProvider;
 import org.apache.hivemind.util.FileResource;
 import org.apache.hivemind.util.URLResource;
 import org.apache.tools.ant.BuildException;
 import org.apache.tools.ant.ProjectComponent;
 import org.apache.tools.ant.Task;
 import org.apache.tools.ant.types.Path;
 import org.apache.xml.serialize.BaseMarkupSerializer;
 import org.apache.xml.serialize.OutputFormat;
 import org.apache.xml.serialize.XMLSerializer;
 import org.w3c.dom.Document;
 
 public class ConstructRegistry extends Task
 {
   private File _output;
   private Path _descriptorsPath;
   private List _resourceQueue;
 
   public ConstructRegistry()
   {
     this._resourceQueue = new ArrayList();
   }
 
   public void execute() throws BuildException {
     if (this._output == null) {
       throw new BuildException("You must specify an output file");
     }
     if (this._descriptorsPath == null) {
       throw new BuildException("You must specify a set of module descriptors");
     }
     long outputStamp = this._output.lastModified();
 
     String[] paths = this._descriptorsPath.list();
     int count = paths.length;
 
     boolean needsUpdate = false;
 
     File[] descriptors = new File[count];
 
     for (int i = 0; i < count; ++i)
     {
       File f = new File(paths[i]);
 
       if (f.isDirectory()) {
         continue;
       }
       if (f.lastModified() > outputStamp) {
         needsUpdate = true;
       }
       descriptors[i] = f;
     }
 
     if (!(needsUpdate))
       return;
     Document registry = constructRegistry(descriptors);
 
     super.log("Writing registry to " + this._output);
 
     writeDocument(registry, this._output);
   }
 
   private Document constructRegistry(File[] moduleDescriptors)
     throws BuildException
   {
     try
     {
       enqueue(moduleDescriptors);
 
       ModuleDescriptorProvider provider = new XmlModuleDescriptorProvider(new DefaultClassResolver(), this._resourceQueue);
 
       RegistrySerializer generator = new RegistrySerializer();
 
       generator.addModuleDescriptorProvider(provider);
 
       Document result = generator.createRegistryDocument();
 
       return result;
     }
     catch (Exception ex)
     {
       throw new BuildException(ex);
     }
   }
 
   private void enqueue(File[] descriptors) throws IOException
   {
     for (int i = 0; i < descriptors.length; ++i)
       enqueue(descriptors[i]);
   }
 
   private void enqueue(File file)
     throws IOException
   {
     if (file == null) {
       return;
     }
     if (file.getName().endsWith(".jar"))
     {
       enqueueJar(file);
       return;
     }
 
     String path = file.getPath().replace('\\', '/');
 
     Resource r = new FileResource(path);
 
     enqueue(r);
   }
 
   private void enqueue(Resource resource)
   {
     if (!(this._resourceQueue.contains(resource)))
       this._resourceQueue.add(resource);
   }
 
   private void enqueueJar(File jarFile) throws IOException
   {
     URL jarRootURL = new URL("jar:" + jarFile.toURL() + "!/");
 
     Resource jarResource = new URLResource(jarRootURL);
 
     enqueueIfExists(jarResource, "META-INF/hivemodule.xml");
   }
 
   private void enqueueIfExists(Resource jarResource, String path)
   {
     Resource r = jarResource.getRelativeResource(path);
 
     if (r.getResourceURL() != null)
       enqueue(r);
   }
 
   private void writeDocument(Document document, File file) throws BuildException
   {
     try
     {
       OutputStream out = new FileOutputStream(file);
       BufferedOutputStream buffered = new BufferedOutputStream(out);
 
       writeDocument(document, buffered);
 
       buffered.close();
     }
     catch (IOException ex)
     {
       throw new BuildException("Unable to write registry to " + file + ": " + ex.getMessage(), ex);
     }
   }
 
   private void writeDocument(Document document, OutputStream out)
     throws IOException
   {
     XMLSerializer serializer = new XMLSerializer(out, new OutputFormat(document, null, true));
     serializer.serialize(document);
   }
 
   public Path createDescriptors()
   {
     this._descriptorsPath = new Path(super.getProject());
     return this._descriptorsPath;
   }
 
   public File getOutput()
   {
     return this._output;
   }
 
   public void setOutput(File file)
   {
     this._output = file;
   }
 }