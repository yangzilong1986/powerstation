 package com.hisun.engine.invoke.load;
 
 import com.hisun.engine.HiEngineModel;
 import com.hisun.exception.HiException;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiContext;
 import com.hisun.util.HiResource;
 import com.hisun.util.HiStringManager;
 import java.net.URL;
 import java.util.ArrayList;
 import java.util.Iterator;
 import org.apache.commons.lang.StringUtils;
 import org.dom4j.Attribute;
 import org.dom4j.Document;
 import org.dom4j.DocumentException;
 import org.dom4j.Element;
 import org.dom4j.io.SAXReader;
 
 public class HiCodeSwitching extends HiEngineModel
 {
   private final Logger logger = (Logger)HiContext.getCurrentContext().getProperty("SVR.log");
   private String strTableName;
   private String strFileName;
   private int type = 0;
 
   private ArrayList columns = new ArrayList();
 
   public String getNodeName()
   {
     return "CodeSwitching";
   }
 
   public String toString() {
     return super.toString() + ":name[" + this.strTableName + "] file[" + this.strFileName + "]";
   }
 
   public void setColumn(String strColumn)
     throws HiException
   {
     this.columns.add(strColumn);
   }
 
   public void setName(String name) {
     this.strTableName = name;
   }
 
   public void setFile(String file) {
     this.strFileName = file;
   }
 
   public void setTable(String strTableName, String strFileName) throws HiException
   {
     if (this.logger.isDebugEnabled()) {
       this.logger.debug("HiCodeSwitching:setTable(String, String) - start");
     }
     this.strFileName = strFileName;
     this.strTableName = strTableName;
     if (this.logger.isDebugEnabled())
       this.logger.debug(HiStringManager.getManager().getString("HiCodeSwitching.setTable", strFileName, strTableName));
   }
 
   public HiTableInfo getTableInfo(String strTableName, String strFileName)
     throws HiException
   {
     if (this.logger.isDebugEnabled()) {
       this.logger.debug("HiCodeSwitching:loadFile(String) - start");
     }
     SAXReader saxReader = new SAXReader();
     URL urlFilePath = HiResource.getResource(strFileName);
     if (urlFilePath == null) {
       throw new HiException("213316", strFileName);
     }
 
     HiTableInfo tableInfo = new HiTableInfo();
     tableInfo.setType(this.type);
     tableInfo.setColumns(this.columns);
     try {
       Document document = saxReader.read(urlFilePath);
       Element rootNode = document.getRootElement();
       Iterator iter = rootNode.elementIterator();
       while (iter.hasNext()) {
         Element em = (Element)iter.next();
         if (!(StringUtils.equals(em.attributeValue("name"), strTableName))) {
           continue;
         }
 
         Iterator iter1 = em.elementIterator();
         while (iter1.hasNext())
         {
           Element em1 = (Element)iter1.next();
           Iterator iter2 = em1.attributeIterator();
           while (iter2.hasNext())
           {
             Attribute attr = (Attribute)iter2.next();
             if (StringUtils.equals(em1.getName(), "Default")) {
               tableInfo.addDefault(attr.getName(), attr.getValue());
             }
             else
               tableInfo.addName(attr.getName(), attr.getValue());
           }
         }
       }
     }
     catch (DocumentException e) {
       throw new HiException("213315", strFileName, e);
     }
 
     if (this.logger.isDebugEnabled()) {
       this.logger.debug("HiCodeSwitching:loadFile(String) - end");
     }
     return tableInfo;
   }
 
   public void loadAfter() throws HiException {
     if ((this.columns.size() != 0) && (this.columns.size() != 2)) {
       throw new HiException("CodeSwitch must have two columns");
     }
     String strNodeName = "CODESWITCHING";
 
     HiTableInfo tableInfo = getTableInfo(this.strTableName, this.strFileName);
 
     if (tableInfo == null) {
       throw new HiException("213317", this.strTableName);
     }
 
     if (this.logger.isDebugEnabled()) {
       this.logger.debug("HiCodeSwitching[" + HiContext.getCurrentContext());
     }
     HiContext.getCurrentContext().setProperty(strNodeName, this.strTableName, tableInfo);
 
     if (this.logger.isDebugEnabled())
       this.logger.debug("HiCodeSwitching:setTable(String, String) - end");
   }
 
   public int getType()
   {
     return this.type;
   }
 
   public void setType(int type) {
     this.type = type;
   }
 }