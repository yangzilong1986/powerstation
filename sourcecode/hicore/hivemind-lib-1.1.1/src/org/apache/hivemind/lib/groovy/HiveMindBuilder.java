 package org.apache.hivemind.lib.groovy;
 
 import groovy.xml.SAXBuilder;
 import java.util.HashMap;
 import java.util.Map;
 import org.xml.sax.Attributes;
 import org.xml.sax.ContentHandler;
 import org.xml.sax.Locator;
 import org.xml.sax.helpers.AttributesImpl;
 
 public class HiveMindBuilder extends SAXBuilder
 {
   public static final Locator GROOVY_LOCATOR = new GroovyLocator(null);
 
   private static final Map CAMEL_TO_HYPHEN_MAP = new HashMap();
 
   public HiveMindBuilder(ContentHandler parser)
   {
     super(parser);
 
     parser.setDocumentLocator(GROOVY_LOCATOR);
   }
 
   protected void nodeCompleted(Object parent, Object node)
   {
     super.nodeCompleted(parent, getHyphenatedName(node.toString()));
   }
 
   protected void doStartElement(Object name, Attributes attributes)
   {
     super.doStartElement(getHyphenatedName(name.toString()), getHyphenatedAttributes(attributes));
   }
 
   private String getHyphenatedName(String name)
   {
     String hyphenatedName = (String)CAMEL_TO_HYPHEN_MAP.get(name);
 
     if (hyphenatedName == null)
     {
       char[] chars = name.toCharArray();
 
       StringBuffer hyphenated = new StringBuffer();
 
       for (int i = 0; i < name.length(); ++i)
       {
         if (Character.isUpperCase(chars[i]))
           hyphenated.append('-').append(Character.toLowerCase(chars[i]));
         else {
           hyphenated.append(chars[i]);
         }
       }
       hyphenatedName = hyphenated.toString();
 
       CAMEL_TO_HYPHEN_MAP.put(name, hyphenatedName);
     }
 
     return hyphenatedName;
   }
 
   private Attributes getHyphenatedAttributes(Attributes attributes)
   {
     AttributesImpl result = (AttributesImpl)attributes;
 
     for (int i = 0; i < result.getLength(); ++i)
     {
       result.setLocalName(i, getHyphenatedName(result.getLocalName(i)));
     }
 
     return result; }
 
   private static class GroovyLocator implements Locator {
     private GroovyLocator() {
     }
 
     public String getPublicId() {
       return null;
     }
 
     public String getSystemId()
     {
       return null;
     }
 
     public int getLineNumber()
     {
       try
       {
         throw new Throwable();
       }
       catch (Throwable t)
       {
         StackTraceElement[] trace = t.getStackTrace();
 
         int i = 0; if (i >= trace.length)
           break label58;
         String fileName = trace[i].getFileName();
         if ((fileName != null) && (fileName.endsWith(".groovy")))
           return trace[i].getLineNumber();
         ++i;
       }
 
       label58: return -1;
     }
 
     public int getColumnNumber()
     {
       return -1;
     }
 
     GroovyLocator(HiveMindBuilder.1 x0)
     {
     }
   }
 }