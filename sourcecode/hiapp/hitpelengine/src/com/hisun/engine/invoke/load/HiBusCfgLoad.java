 package com.hisun.engine.invoke.load;
 
 import com.hisun.engine.HiEngineModel;
 import com.hisun.exception.HiException;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiContext;
 import com.hisun.util.HiResource;
 import java.net.URL;
 import java.util.HashMap;
 import java.util.Iterator;
 import org.dom4j.Document;
 import org.dom4j.DocumentException;
 import org.dom4j.Element;
 import org.dom4j.io.SAXReader;
 
 public class HiBusCfgLoad extends HiEngineModel
 {
   private final Logger logger = (Logger)HiContext.getCurrentContext().getProperty("SVR.log");
 
   private final String strFileName = "etc/BUSCFG.XML";
 
   public String getNodeName()
   {
     return "BUSCFG.XML";
   }
 
   private void jbInit()
     throws HiException
   {
     if (this.logger.isDebugEnabled())
     {
       this.logger.debug("HiBusCfgLoad:jbInit() - start");
     }
 
     SAXReader saxReader = new SAXReader();
     URL urlFilePath = HiResource.getResource("etc/BUSCFG.XML");
     if (urlFilePath == null)
     {
       throw new HiException("213313", "etc/BUSCFG.XML");
     }
 
     HashMap map = new HashMap();
     try
     {
       Document document = saxReader.read(urlFilePath);
       Element rootNode = document.getRootElement();
       rootNode = rootNode.element("ParaList");
       Iterator iter = rootNode.elementIterator();
 
       while (iter.hasNext())
       {
         Element em = (Element)iter.next();
         String strName = em.getName();
         String strValue = em.getStringValue();
         map.put(strName.toUpperCase(), strValue);
       }
     }
     catch (DocumentException e)
     {
       throw new HiException("213314", "etc/BUSCFG.XML", e);
     }
 
     HiContext.getCurrentContext().setProperty("ROOT.BCFG", map);
 
     if (!(this.logger.isDebugEnabled()))
       return;
     this.logger.debug("HiBusCfgLoad:jbInit() - end");
   }
 
   public static void loadBusCfg()
     throws HiException
   {
     HiBusCfgLoad bus = new HiBusCfgLoad();
     bus.jbInit();
   }
 }