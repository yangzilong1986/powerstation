 package com.hisun.xml;
 
 import org.apache.commons.digester.Digester;
 import org.apache.commons.digester.Rule;
 import org.xml.sax.Attributes;
 import org.xml.sax.Locator;
 
 public class SetLocationRule extends Rule
 {
   public void begin(String namespace, String name, Attributes attributes)
     throws Exception
   {
     Object obj = this.digester.peek();
     if (obj instanceof Located)
       ((Located)obj).setLocation(currentLocation());
   }
 
   private Location currentLocation()
   {
     Locator l = this.digester.getDocumentLocator();
     return new Location(this.digester.getPublicId(), l.getLineNumber(), l.getColumnNumber());
   }
 }