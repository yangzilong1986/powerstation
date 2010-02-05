 package com.hisun.protocol.tcp.parser;
 
 import com.hisun.exception.HiException;
 import com.hisun.parse.HiVariableExpander;
 import java.io.IOException;
 import java.lang.reflect.InvocationTargetException;
 import java.net.URL;
 import java.util.ArrayList;
 import org.apache.commons.digester.Digester;
 import org.apache.commons.digester.substitution.VariableSubstitutor;
 import org.xml.sax.ErrorHandler;
 import org.xml.sax.SAXException;
 import org.xml.sax.SAXParseException;
 
 public class HiIPXMLParser
   implements ErrorHandler
 {
   private final Digester parser;
 
   public HiIPXMLParser()
   {
     this.parser = new Digester();
     this.parser.addObjectCreate("clientIpTable", ArrayList.class);
     Class[] types = { Object.class };
     this.parser.addCallMethod("clientIpTable/ipItem", "add", 1, types);
     this.parser.addCallParam("clientIpTable/ipItem", 0, "ip");
 
     this.parser.setErrorHandler(this);
 
     this.parser.setSubstitutor(new VariableSubstitutor(new HiVariableExpander(), null));
   }
 
   public void error(SAXParseException arg0) throws SAXException
   {
     throw arg0;
   }
 
   public void fatalError(SAXParseException arg0) throws SAXException
   {
     throw arg0;
   }
 
   public Object parse(URL url) throws HiException {
     this.parser.clear();
     try {
       this.parser.setClassLoader(Thread.currentThread().getContextClassLoader());
 
       return this.parser.parse(url.openStream());
     } catch (IOException e) {
       throw HiException.makeException(e);
     } catch (SAXException e) {
       Exception ec = e.getException();
       if (ec == null) {
         throw new HiException("212005", url.toString(), e);
       }
       if (ec instanceof InvocationTargetException) {
         Throwable t = ((InvocationTargetException)ec).getTargetException();
 
         if (t instanceof Exception) {
           ec = (Exception)t;
         }
       }
 
       if (ec instanceof HiException) {
         throw ((HiException)ec);
       }
 
       throw new HiException("212005", url.toString(), ec);
     }
   }
 
   public void warning(SAXParseException arg0)
     throws SAXException
   {
     throw arg0;
   }
 }