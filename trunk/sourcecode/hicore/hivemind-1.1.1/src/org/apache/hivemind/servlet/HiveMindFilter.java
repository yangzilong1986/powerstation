 package org.apache.hivemind.servlet;
 
 import java.io.IOException;
 import java.util.Locale;
 import javax.servlet.Filter;
 import javax.servlet.FilterChain;
 import javax.servlet.FilterConfig;
 import javax.servlet.ServletContext;
 import javax.servlet.ServletException;
 import javax.servlet.ServletRequest;
 import javax.servlet.ServletResponse;
 import javax.servlet.http.HttpServletRequest;
 import org.apache.commons.logging.Log;
 import org.apache.commons.logging.LogFactory;
 import org.apache.hivemind.ClassResolver;
 import org.apache.hivemind.ModuleDescriptorProvider;
 import org.apache.hivemind.Registry;
 import org.apache.hivemind.impl.DefaultClassResolver;
 import org.apache.hivemind.impl.RegistryBuilder;
 import org.apache.hivemind.impl.XmlModuleDescriptorProvider;
 import org.apache.hivemind.util.ContextResource;
 
 public class HiveMindFilter
   implements Filter
 {
   private static final Log LOG = LogFactory.getLog(HiveMindFilter.class);
   static final String REQUEST_KEY = "org.apache.hivemind.RequestRegistry";
   static final String REBUILD_REQUEST_KEY = "org.apache.hivemind.RebuildRegistry";
   static final String HIVE_MODULE_XML = "/WEB-INF/hivemodule.xml";
   private FilterConfig _filterConfig;
   private Registry _registry;
 
   public void init(FilterConfig config)
     throws ServletException
   {
     this._filterConfig = config;
 
     initializeRegistry();
   }
 
   private void initializeRegistry()
   {
     long startTime = System.currentTimeMillis();
 
     LOG.info(ServletMessages.filterInit());
     try
     {
       this._registry = constructRegistry(this._filterConfig);
 
       LOG.info(ServletMessages.constructedRegistry(this._registry, System.currentTimeMillis() - startTime));
     }
     catch (Exception ex)
     {
       LOG.error(ex.getMessage(), ex);
     }
   }
 
   protected Registry constructRegistry(FilterConfig config)
   {
     RegistryBuilder builder = new RegistryBuilder();
 
     ClassResolver resolver = new DefaultClassResolver();
 
     builder.addModuleDescriptorProvider(getModuleDescriptorProvider(resolver));
 
     addWebInfDescriptor(config.getServletContext(), resolver, builder);
 
     return builder.constructRegistry(getRegistryLocale());
   }
 
   protected void addWebInfDescriptor(ServletContext context, ClassResolver resolver, RegistryBuilder builder)
   {
     ContextResource r = new ContextResource(context, "/WEB-INF/hivemodule.xml");
 
     if (r.getResourceURL() == null)
       return;
     ModuleDescriptorProvider provider = new XmlModuleDescriptorProvider(resolver, r);
 
     builder.addModuleDescriptorProvider(provider);
   }
 
   protected Locale getRegistryLocale()
   {
     return Locale.getDefault();
   }
 
   protected ModuleDescriptorProvider getModuleDescriptorProvider(ClassResolver resolver)
   {
     return new XmlModuleDescriptorProvider(resolver);
   }
 
   public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
     throws IOException, ServletException
   {
     try
     {
       if (this._registry != null) {
         this._registry.setupThread();
       }
       request.setAttribute("org.apache.hivemind.RequestRegistry", this._registry);
 
       chain.doFilter(request, response);
     }
     finally
     {
       cleanupThread();
 
       checkRegistryRebuild(request);
     }
   }
 
   private synchronized void checkRegistryRebuild(ServletRequest request)
   {
     if (request.getAttribute("org.apache.hivemind.RebuildRegistry") == null) {
       return;
     }
     Registry oldRegistry = this._registry;
 
     initializeRegistry();
 
     oldRegistry.shutdown();
   }
 
   private void cleanupThread()
   {
     try
     {
       this._registry.cleanupThread();
     }
     catch (Exception ex)
     {
       LOG.error(ServletMessages.filterCleanupError(ex), ex);
     }
   }
 
   public void destroy()
   {
     if (this._registry != null) {
       this._registry.shutdown();
     }
     this._filterConfig = null;
   }
 
   public static Registry getRegistry(HttpServletRequest request)
   {
     return ((Registry)request.getAttribute("org.apache.hivemind.RequestRegistry"));
   }
 
   public static void rebuildRegistry(HttpServletRequest request)
   {
     request.setAttribute("org.apache.hivemind.RebuildRegistry", Boolean.TRUE);
   }
 }