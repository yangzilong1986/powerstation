 package com.hisun.atc.common;
 
 import com.hisun.exception.HiException;
 import com.hisun.parse.HiPretreatment;
 import com.hisun.util.HiResource;
 import java.io.IOException;
 import java.io.InputStream;
 import java.util.HashMap;
 import org.dom4j.Document;
 import org.dom4j.DocumentException;
 import org.dom4j.Element;
 import org.dom4j.io.SAXReader;
 
 public class HiReportHelper
 {
   public static Document parser(String file)
     throws HiException, DocumentException, IOException
   {
     SAXReader saxReader = new SAXReader();
     InputStream is = HiResource.getResourceAsStream(file);
     if (is == null)
     {
       throw new IOException("文件:[" + file + "]不存在!");
     }
     Document document = saxReader.read(is);
     Element rootNode = document.getRootElement();
     HashMap allElements = HiPretreatment.getAllElements(rootNode, null);
     HiPretreatment.parseInclude(allElements, document);
     return document;
   }
 }