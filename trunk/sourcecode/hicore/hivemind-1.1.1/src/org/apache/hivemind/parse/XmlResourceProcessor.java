 package org.apache.hivemind.parse;
 
 import java.io.IOException;
 import java.net.URL;
 import javax.xml.parsers.FactoryConfigurationError;
 import javax.xml.parsers.ParserConfigurationException;
 import javax.xml.parsers.SAXParser;
 import javax.xml.parsers.SAXParserFactory;
 import org.apache.commons.logging.Log;
 import org.apache.commons.logging.LogFactory;
 import org.apache.hivemind.ApplicationRuntimeException;
 import org.apache.hivemind.ClassResolver;
 import org.apache.hivemind.ErrorHandler;
 import org.apache.hivemind.Resource;
 import org.xml.sax.InputSource;
 import org.xml.sax.SAXException;
 
 public class XmlResourceProcessor
 {
   private static final Log LOG = LogFactory.getLog(XmlResourceProcessor.class);
   protected ClassResolver _resolver;
   protected ErrorHandler _errorHandler;
   private DescriptorParser _contentHandler;
   private SAXParser _saxParser;
 
   public XmlResourceProcessor(ClassResolver resolver, ErrorHandler errorHandler)
   {
     this._resolver = resolver;
     this._errorHandler = errorHandler;
   }
 
   public ModuleDescriptor processResource(Resource resource)
   {
     if (this._contentHandler == null) {
       this._contentHandler = new DescriptorParser(this._errorHandler);
     }
     this._contentHandler.initialize(resource, this._resolver);
     try
     {
       if (LOG.isDebugEnabled()) {
         LOG.debug("Parsing " + resource);
       }
       ModuleDescriptor descriptor = parseResource(resource, getSAXParser(), this._contentHandler);
 
       if (LOG.isDebugEnabled()) {
         LOG.debug("Result: " + descriptor);
       }
       ModuleDescriptor localModuleDescriptor1 = descriptor;
 
       return localModuleDescriptor1;
     }
     catch (ApplicationRuntimeException e)
     {
     }
     catch (Exception e)
     {
       throw new ApplicationRuntimeException(ParseMessages.errorReadingDescriptor(resource, e), resource, this._contentHandler.getLocation(), e);
     }
     finally
     {
       this._contentHandler.resetParser();
     }
   }
 
   protected ModuleDescriptor parseResource(Resource resource, SAXParser parser, DescriptorParser contentHandler)
     throws SAXException, IOException
   {
     InputSource source = getInputSource(resource);
 
     parser.parse(source, contentHandler);
 
     return contentHandler.getModuleDescriptor();
   }
 
   private InputSource getInputSource(Resource resource)
   {
     try
     {
       URL url = resource.getResourceURL();
 
       return new InputSource(url.openStream());
     }
     catch (Exception e)
     {
       throw new ApplicationRuntimeException(ParseMessages.missingResource(resource), resource, null, e);
     }
   }
 
   private SAXParser getSAXParser()
     throws ParserConfigurationException, SAXException, FactoryConfigurationError
   {
     if (this._saxParser == null) {
       this._saxParser = SAXParserFactory.newInstance().newSAXParser();
     }
     return this._saxParser;
   }
 }