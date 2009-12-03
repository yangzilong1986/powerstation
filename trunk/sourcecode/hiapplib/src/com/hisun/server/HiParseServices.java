/*    */ package com.hisun.server;
/*    */ 
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.hilog4j.HiLog;
/*    */ import com.hisun.hilog4j.Logger;
/*    */ import com.hisun.util.HiResource;
/*    */ import java.net.URL;
/*    */ import java.util.HashMap;
/*    */ import java.util.Iterator;
/*    */ import org.dom4j.Attribute;
/*    */ import org.dom4j.Document;
/*    */ import org.dom4j.DocumentException;
/*    */ import org.dom4j.Element;
/*    */ import org.dom4j.io.SAXReader;
/*    */ 
/*    */ public class HiParseServices
/*    */ {
/*    */   private String strFileName;
/* 25 */   public static final Logger log = HiLog.getLogger("wsadapter");
/* 26 */   private HashMap serviceMap = new HashMap();
/*    */ 
/* 79 */   private static HashMap parseMaps = new HashMap();
/*    */ 
/*    */   private HiParseServices(String strFileName)
/*    */     throws HiException
/*    */   {
/* 19 */     this.strFileName = strFileName;
/* 20 */     init();
/*    */   }
/*    */ 
/*    */   private void init()
/*    */     throws HiException
/*    */   {
/* 29 */     SAXReader saxReader = new SAXReader();
/* 30 */     URL urlFilePath = HiResource.getResource(this.strFileName);
/* 31 */     if (urlFilePath == null)
/* 32 */       throw new HiException(this.strFileName + " is not exist");
/*    */     try {
/* 34 */       Document document = saxReader.read(urlFilePath);
/* 35 */       Element rootNode = document.getRootElement();
/* 36 */       Iterator iter = rootNode.elementIterator();
/* 37 */       while (iter.hasNext()) {
/* 38 */         Element serverEl = (Element)iter.next();
/* 39 */         String strName = serverEl.attribute("name").getValue();
/* 40 */         if (log.isDebugEnabled()) {
/* 41 */           log.debug("server name[" + strName + "]");
/*    */         }
/* 43 */         HashMap operMap = parseServerNode(serverEl);
/* 44 */         if (!(operMap.isEmpty()))
/* 45 */           this.serviceMap.put(strName, operMap);
/*    */       }
/*    */     } catch (DocumentException e) {
/* 48 */       throw new HiException(e);
/*    */     }
/*    */   }
/*    */ 
/*    */   private HashMap parseServerNode(Element serverEl) throws HiException {
/* 53 */     HashMap operMap = new HashMap();
/* 54 */     Iterator iter = serverEl.elementIterator();
/* 55 */     while (iter.hasNext()) {
/* 56 */       Element operEl = (Element)iter.next();
/* 57 */       String strName = operEl.attribute("name").getValue();
/* 58 */       if (log.isDebugEnabled())
/* 59 */         log.debug("operation name[" + strName + "]");
/* 60 */       String strCode = operEl.attribute("code").getValue();
/* 61 */       if (log.isDebugEnabled())
/* 62 */         log.debug("operation code[" + strCode + "]");
/* 63 */       operMap.put(strName, strCode);
/*    */     }
/* 65 */     return operMap;
/*    */   }
/*    */ 
/*    */   public HashMap getServiceMap() {
/* 69 */     return this.serviceMap;
/*    */   }
/*    */ 
/*    */   public HashMap getOperationMap(String strServiceName) throws HiException {
/* 73 */     if (this.serviceMap.containsKey(strServiceName)) {
/* 74 */       return ((HashMap)this.serviceMap.get(strServiceName));
/*    */     }
/* 76 */     throw new HiException(strServiceName + " is not exist");
/*    */   }
/*    */ 
/*    */   public static synchronized HiParseServices getDefaultParseService(String strFileName)
/*    */     throws HiException
/*    */   {
/* 83 */     HiParseServices parse = null;
/* 84 */     if (parseMaps.containsKey(strFileName)) {
/* 85 */       parse = (HiParseServices)parseMaps.get(strFileName);
/*    */     } else {
/* 87 */       parse = new HiParseServices(strFileName);
/*    */ 
/* 89 */       parseMaps.put(strFileName, parse);
/*    */     }
/* 91 */     return parse;
/*    */   }
/*    */ }