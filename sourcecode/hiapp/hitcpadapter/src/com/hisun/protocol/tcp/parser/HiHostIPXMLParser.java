 package com.hisun.protocol.tcp.parser;
 
 import com.hisun.parse.HiVariableExpander;
 import java.net.URL;
 import org.apache.commons.digester.Digester;
 import org.apache.commons.digester.substitution.VariableSubstitutor;
 import org.xml.sax.ErrorHandler;
 import org.xml.sax.SAXException;
 import org.xml.sax.SAXParseException;
 
 public class HiHostIPXMLParser
   implements ErrorHandler
 {
   private final Digester parser;
 
   public HiHostIPXMLParser()
   {
     this.parser = new Digester();
     this.parser.addObjectCreate("hostIpTable", HiHostIpTable.class);
     Class[] types = { String.class, String.class, String.class };
     this.parser.addCallMethod("hostIpTable/ipItem", "add", 3, types);
     this.parser.addCallParam("hostIpTable/ipItem", 0, "name");
     this.parser.addCallParam("hostIpTable/ipItem", 1, "ip");
     this.parser.addCallParam("hostIpTable/ipItem", 2, "port");
 
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
 
   public Object parse(URL url) throws Exception {
     this.parser.clear();
     return this.parser.parse(url.openStream());
   }
 
   public void warning(SAXParseException arg0)
     throws SAXException
   {
     throw arg0;
   }
 }