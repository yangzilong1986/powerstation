 package com.hisun.engine.invoke.impl;
 
 import com.hisun.engine.HiEngineModel;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiContext;
 import org.apache.commons.lang.StringUtils;
 import org.w3c.dom.NamedNodeMap;
 import org.w3c.dom.Node;
 
 public class HiAttributes extends HiEngineModel
 {
   private final Logger logger = (Logger)HiContext.getCurrentContext().getProperty("SVR.log");
 
   public String getNodeName()
   {
     return "Attributes";
   }
 
   public void setProperty(Object value)
   {
     if (this.logger.isDebugEnabled()) {
       this.logger.debug("setPropertys(Object) - start");
     }
 
     HiAttributesHelper attr = HiAttributesHelper.getAttribute(HiContext.getCurrentContext());
 
     Node node = (Node)value;
     NamedNodeMap att = node.getAttributes();
     String name = null;
     for (int i = 0; i < att.getLength(); ++i) {
       Node n = att.item(i);
       String strKey = n.getNodeName();
       if (StringUtils.equalsIgnoreCase(strKey, "name")) {
         name = n.getNodeValue();
       }
       else
       {
         if (StringUtils.equalsIgnoreCase(strKey, "value")) {
           strKey = name;
         }
 
         String strValue = n.getNodeValue();
         attr.put(strKey, strValue);
       }
     }
     if (this.logger.isDebugEnabled()) {
       this.logger.debug("setPropertys(Object) - " + attr);
       this.logger.debug("setPropertys(Object) - end");
     }
   }
 }