/*     */ package com.hisun.engine.invoke.load;
/*     */ 
/*     */ import com.hisun.engine.HiEngineModel;
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiContext;
/*     */ import com.hisun.util.HiResource;
/*     */ import com.hisun.util.HiStringManager;
/*     */ import java.net.URL;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.dom4j.Attribute;
/*     */ import org.dom4j.Document;
/*     */ import org.dom4j.DocumentException;
/*     */ import org.dom4j.Element;
/*     */ import org.dom4j.io.SAXReader;
/*     */ 
/*     */ public class HiCodeSwitching extends HiEngineModel
/*     */ {
/*  40 */   private final Logger logger = (Logger)HiContext.getCurrentContext().getProperty("SVR.log");
/*     */   private String strTableName;
/*     */   private String strFileName;
/*  71 */   private int type = 0;
/*     */ 
/*  76 */   private ArrayList columns = new ArrayList();
/*     */ 
/*     */   public String getNodeName()
/*     */   {
/*  48 */     return "CodeSwitching";
/*     */   }
/*     */ 
/*     */   public String toString() {
/*  52 */     return super.toString() + ":name[" + this.strTableName + "] file[" + this.strFileName + "]";
/*     */   }
/*     */ 
/*     */   public void setColumn(String strColumn)
/*     */     throws HiException
/*     */   {
/*  80 */     this.columns.add(strColumn);
/*     */   }
/*     */ 
/*     */   public void setName(String name) {
/*  84 */     this.strTableName = name;
/*     */   }
/*     */ 
/*     */   public void setFile(String file) {
/*  88 */     this.strFileName = file;
/*     */   }
/*     */ 
/*     */   public void setTable(String strTableName, String strFileName) throws HiException
/*     */   {
/*  93 */     if (this.logger.isDebugEnabled()) {
/*  94 */       this.logger.debug("HiCodeSwitching:setTable(String, String) - start");
/*     */     }
/*  96 */     this.strFileName = strFileName;
/*  97 */     this.strTableName = strTableName;
/*  98 */     if (this.logger.isDebugEnabled())
/*  99 */       this.logger.debug(HiStringManager.getManager().getString("HiCodeSwitching.setTable", strFileName, strTableName));
/*     */   }
/*     */ 
/*     */   public HiTableInfo getTableInfo(String strTableName, String strFileName)
/*     */     throws HiException
/*     */   {
/* 111 */     if (this.logger.isDebugEnabled()) {
/* 112 */       this.logger.debug("HiCodeSwitching:loadFile(String) - start");
/*     */     }
/* 114 */     SAXReader saxReader = new SAXReader();
/* 115 */     URL urlFilePath = HiResource.getResource(strFileName);
/* 116 */     if (urlFilePath == null) {
/* 117 */       throw new HiException("213316", strFileName);
/*     */     }
/*     */ 
/* 120 */     HiTableInfo tableInfo = new HiTableInfo();
/* 121 */     tableInfo.setType(this.type);
/* 122 */     tableInfo.setColumns(this.columns);
/*     */     try {
/* 124 */       Document document = saxReader.read(urlFilePath);
/* 125 */       Element rootNode = document.getRootElement();
/* 126 */       Iterator iter = rootNode.elementIterator();
/* 127 */       while (iter.hasNext()) {
/* 128 */         Element em = (Element)iter.next();
/* 129 */         if (!(StringUtils.equals(em.attributeValue("name"), strTableName))) {
/*     */           continue;
/*     */         }
/*     */ 
/* 133 */         Iterator iter1 = em.elementIterator();
/* 134 */         while (iter1.hasNext())
/*     */         {
/* 136 */           Element em1 = (Element)iter1.next();
/* 137 */           Iterator iter2 = em1.attributeIterator();
/* 138 */           while (iter2.hasNext())
/*     */           {
/* 140 */             Attribute attr = (Attribute)iter2.next();
/* 141 */             if (StringUtils.equals(em1.getName(), "Default")) {
/* 142 */               tableInfo.addDefault(attr.getName(), attr.getValue());
/*     */             }
/*     */             else
/* 145 */               tableInfo.addName(attr.getName(), attr.getValue());
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (DocumentException e) {
/* 151 */       throw new HiException("213315", strFileName, e);
/*     */     }
/*     */ 
/* 155 */     if (this.logger.isDebugEnabled()) {
/* 156 */       this.logger.debug("HiCodeSwitching:loadFile(String) - end");
/*     */     }
/* 158 */     return tableInfo;
/*     */   }
/*     */ 
/*     */   public void loadAfter() throws HiException {
/* 162 */     if ((this.columns.size() != 0) && (this.columns.size() != 2)) {
/* 163 */       throw new HiException("CodeSwitch must have two columns");
/*     */     }
/* 165 */     String strNodeName = "CODESWITCHING";
/*     */ 
/* 167 */     HiTableInfo tableInfo = getTableInfo(this.strTableName, this.strFileName);
/*     */ 
/* 169 */     if (tableInfo == null) {
/* 170 */       throw new HiException("213317", this.strTableName);
/*     */     }
/*     */ 
/* 173 */     if (this.logger.isDebugEnabled()) {
/* 174 */       this.logger.debug("HiCodeSwitching[" + HiContext.getCurrentContext());
/*     */     }
/* 176 */     HiContext.getCurrentContext().setProperty(strNodeName, this.strTableName, tableInfo);
/*     */ 
/* 179 */     if (this.logger.isDebugEnabled())
/* 180 */       this.logger.debug("HiCodeSwitching:setTable(String, String) - end");
/*     */   }
/*     */ 
/*     */   public int getType()
/*     */   {
/* 185 */     return this.type;
/*     */   }
/*     */ 
/*     */   public void setType(int type) {
/* 189 */     this.type = type;
/*     */   }
/*     */ }