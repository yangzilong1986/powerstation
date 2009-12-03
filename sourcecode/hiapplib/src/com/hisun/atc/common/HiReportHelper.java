/*    */ package com.hisun.atc.common;
/*    */ 
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.parse.HiPretreatment;
/*    */ import com.hisun.util.HiResource;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.util.HashMap;
/*    */ import org.dom4j.Document;
/*    */ import org.dom4j.DocumentException;
/*    */ import org.dom4j.Element;
/*    */ import org.dom4j.io.SAXReader;
/*    */ 
/*    */ public class HiReportHelper
/*    */ {
/*    */   public static Document parser(String file)
/*    */     throws HiException, DocumentException, IOException
/*    */   {
/* 29 */     SAXReader saxReader = new SAXReader();
/* 30 */     InputStream is = HiResource.getResourceAsStream(file);
/* 31 */     if (is == null)
/*    */     {
/* 33 */       throw new IOException("文件:[" + file + "]不存在!");
/*    */     }
/* 35 */     Document document = saxReader.read(is);
/* 36 */     Element rootNode = document.getRootElement();
/* 37 */     HashMap allElements = HiPretreatment.getAllElements(rootNode, null);
/* 38 */     HiPretreatment.parseInclude(allElements, document);
/* 39 */     return document;
/*    */   }
/*    */ }