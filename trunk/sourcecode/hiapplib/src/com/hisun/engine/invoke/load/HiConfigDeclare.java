/*    */ package com.hisun.engine.invoke.load;
/*    */ 
/*    */ import com.hisun.engine.HiEngineModel;
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.message.HiContext;
/*    */ import com.hisun.parse.HiCfgFile;
/*    */ import com.hisun.parse.HiResourceRule;
/*    */ import com.hisun.util.HiResource;
/*    */ import java.net.URL;
/*    */ import org.dom4j.Document;
/*    */ import org.dom4j.DocumentException;
/*    */ import org.dom4j.Element;
/*    */ import org.dom4j.io.SAXReader;
/*    */ 
/*    */ public class HiConfigDeclare extends HiEngineModel
/*    */ {
/* 41 */   private String strNodeName = "CONFIGDECLARE";
/*    */ 
/*    */   public String getNodeName()
/*    */   {
/* 38 */     return "ConfigDeclare";
/*    */   }
/*    */ 
/*    */   public void setConfigInfo(String strAliasName, String strFileName)
/*    */     throws HiException
/*    */   {
/* 53 */     URL rule = HiResourceRule.getRuleForFile(strFileName);
/* 54 */     Object obj = null;
/* 55 */     if (rule != null)
/*    */     {
/* 57 */       obj = loadFileRule(strFileName, rule);
/*    */     }
/*    */     else
/*    */     {
/* 61 */       obj = loadFile(strFileName);
/*    */     }
/* 63 */     HiContext.getCurrentContext().setProperty(this.strNodeName + "." + strAliasName.toUpperCase(), obj);
/*    */   }
/*    */ 
/*    */   public Object loadFileRule(String strFileName, URL rule)
/*    */     throws HiException
/*    */   {
/* 70 */     URL urlFilePath = HiResource.getResource(strFileName);
/*    */ 
/* 72 */     if (urlFilePath == null) {
/* 73 */       throw new HiException("213302", strFileName);
/*    */     }
/* 75 */     HiCfgFile cfg = HiCfgFile.getDefaultCfgFile(urlFilePath, rule, null);
/*    */ 
/* 77 */     return cfg.getRootInstance();
/*    */   }
/*    */ 
/*    */   public Object loadFile(String strFileName)
/*    */     throws HiException
/*    */   {
/*    */     try
/*    */     {
/* 90 */       SAXReader saxReader = new SAXReader();
/* 91 */       URL urlFilePath = HiResource.getResource(strFileName);
/* 92 */       if (urlFilePath == null) {
/* 93 */         throw new HiException("213302", strFileName);
/*    */       }
/* 95 */       Document document = saxReader.read(urlFilePath);
/* 96 */       Element rootNode = document.getRootElement();
/* 97 */       return rootNode;
/*    */     }
/*    */     catch (DocumentException e)
/*    */     {
/* 101 */       throw new HiException("213323", strFileName, e);
/*    */     }
/*    */   }
/*    */ }