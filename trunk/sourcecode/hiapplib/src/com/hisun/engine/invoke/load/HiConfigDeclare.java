 package com.hisun.engine.invoke.load;
 
 import com.hisun.engine.HiEngineModel;
 import com.hisun.exception.HiException;
 import com.hisun.message.HiContext;
 import com.hisun.parse.HiCfgFile;
 import com.hisun.parse.HiResourceRule;
 import com.hisun.util.HiResource;
 import java.net.URL;
 import org.dom4j.Document;
 import org.dom4j.DocumentException;
 import org.dom4j.Element;
 import org.dom4j.io.SAXReader;
 
 public class HiConfigDeclare extends HiEngineModel
 {
   private String strNodeName = "CONFIGDECLARE";
 
   public String getNodeName()
   {
     return "ConfigDeclare";
   }
 
   public void setConfigInfo(String strAliasName, String strFileName)
     throws HiException
   {
     URL rule = HiResourceRule.getRuleForFile(strFileName);
     Object obj = null;
     if (rule != null)
     {
       obj = loadFileRule(strFileName, rule);
     }
     else
     {
       obj = loadFile(strFileName);
     }
     HiContext.getCurrentContext().setProperty(this.strNodeName + "." + strAliasName.toUpperCase(), obj);
   }
 
   public Object loadFileRule(String strFileName, URL rule)
     throws HiException
   {
     URL urlFilePath = HiResource.getResource(strFileName);
 
     if (urlFilePath == null) {
       throw new HiException("213302", strFileName);
     }
     HiCfgFile cfg = HiCfgFile.getDefaultCfgFile(urlFilePath, rule, null);
 
     return cfg.getRootInstance();
   }
 
   public Object loadFile(String strFileName)
     throws HiException
   {
     try
     {
       SAXReader saxReader = new SAXReader();
       URL urlFilePath = HiResource.getResource(strFileName);
       if (urlFilePath == null) {
         throw new HiException("213302", strFileName);
       }
       Document document = saxReader.read(urlFilePath);
       Element rootNode = document.getRootElement();
       return rootNode;
     }
     catch (DocumentException e)
     {
       throw new HiException("213323", strFileName, e);
     }
   }
 }