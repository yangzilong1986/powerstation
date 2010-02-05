 package com.hisun.server;
 
 import com.hisun.exception.HiException;
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import com.hisun.util.HiResource;
 import java.net.URL;
 import java.util.HashMap;
 import java.util.Iterator;
 import org.dom4j.Attribute;
 import org.dom4j.Document;
 import org.dom4j.DocumentException;
 import org.dom4j.Element;
 import org.dom4j.io.SAXReader;
 
 public class HiParseServices
 {
   private String strFileName;
   public static final Logger log = HiLog.getLogger("wsadapter");
   private HashMap serviceMap = new HashMap();
 
   private static HashMap parseMaps = new HashMap();
 
   private HiParseServices(String strFileName)
     throws HiException
   {
     this.strFileName = strFileName;
     init();
   }
 
   private void init()
     throws HiException
   {
     SAXReader saxReader = new SAXReader();
     URL urlFilePath = HiResource.getResource(this.strFileName);
     if (urlFilePath == null)
       throw new HiException(this.strFileName + " is not exist");
     try {
       Document document = saxReader.read(urlFilePath);
       Element rootNode = document.getRootElement();
       Iterator iter = rootNode.elementIterator();
       while (iter.hasNext()) {
         Element serverEl = (Element)iter.next();
         String strName = serverEl.attribute("name").getValue();
         if (log.isDebugEnabled()) {
           log.debug("server name[" + strName + "]");
         }
         HashMap operMap = parseServerNode(serverEl);
         if (!(operMap.isEmpty()))
           this.serviceMap.put(strName, operMap);
       }
     } catch (DocumentException e) {
       throw new HiException(e);
     }
   }
 
   private HashMap parseServerNode(Element serverEl) throws HiException {
     HashMap operMap = new HashMap();
     Iterator iter = serverEl.elementIterator();
     while (iter.hasNext()) {
       Element operEl = (Element)iter.next();
       String strName = operEl.attribute("name").getValue();
       if (log.isDebugEnabled())
         log.debug("operation name[" + strName + "]");
       String strCode = operEl.attribute("code").getValue();
       if (log.isDebugEnabled())
         log.debug("operation code[" + strCode + "]");
       operMap.put(strName, strCode);
     }
     return operMap;
   }
 
   public HashMap getServiceMap() {
     return this.serviceMap;
   }
 
   public HashMap getOperationMap(String strServiceName) throws HiException {
     if (this.serviceMap.containsKey(strServiceName)) {
       return ((HashMap)this.serviceMap.get(strServiceName));
     }
     throw new HiException(strServiceName + " is not exist");
   }
 
   public static synchronized HiParseServices getDefaultParseService(String strFileName)
     throws HiException
   {
     HiParseServices parse = null;
     if (parseMaps.containsKey(strFileName)) {
       parse = (HiParseServices)parseMaps.get(strFileName);
     } else {
       parse = new HiParseServices(strFileName);
 
       parseMaps.put(strFileName, parse);
     }
     return parse;
   }
 }