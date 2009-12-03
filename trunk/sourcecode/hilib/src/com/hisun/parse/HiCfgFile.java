/*     */ package com.hisun.parse;
/*     */ 
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.file.HiFileUtil;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiContext;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.File;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.StringWriter;
/*     */ import java.net.URL;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import org.apache.commons.digester.Digester;
/*     */ import org.apache.commons.digester.NodeCreateRule;
/*     */ import org.apache.commons.digester.Rule;
/*     */ import org.apache.commons.digester.RuleSet;
/*     */ import org.apache.commons.digester.xmlrules.FromXmlRuleSet;
/*     */ import org.dom4j.Document;
/*     */ import org.dom4j.DocumentException;
/*     */ import org.dom4j.Element;
/*     */ import org.dom4j.io.OutputFormat;
/*     */ import org.dom4j.io.SAXReader;
/*     */ import org.dom4j.io.XMLWriter;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ public class HiCfgFile
/*     */ {
/*  36 */   private final Logger logger = (Logger)HiContext.getCurrentContext().getProperty("SVR.log");
/*     */   private URL urlFilePath;
/*     */   private URL urlFileRulePath;
/*  49 */   private String strNodeRule = null;
/*     */ 
/*  51 */   private String strLogName = "engine.log";
/*     */ 
/*  96 */   private Document document = null;
/*     */ 
/* 292 */   private Object rootObj = null;
/*     */ 
/*     */   private HiCfgFile(URL urlFilePath, URL urlFileRulePath, String strNodeRule)
/*     */     throws HiException
/*     */   {
/*  56 */     this.urlFilePath = urlFilePath;
/*  57 */     this.urlFileRulePath = urlFileRulePath;
/*  58 */     this.strNodeRule = strNodeRule;
/*  59 */     readFile();
/*  60 */     jbInit();
/*  61 */     loadFile();
/*     */   }
/*     */ 
/*     */   private HiCfgFile(URL urlFilePath, URL urlFileRulePath, String strNodeRule, String strLogName) throws HiException
/*     */   {
/*  66 */     this.strLogName = strLogName;
/*  67 */     this.urlFilePath = urlFilePath;
/*  68 */     this.urlFileRulePath = urlFileRulePath;
/*  69 */     this.strNodeRule = strNodeRule;
/*  70 */     readFile();
/*  71 */     jbInit();
/*  72 */     loadFile();
/*     */   }
/*     */ 
/*     */   private HiCfgFile(Document document, URL urlFileRulePath) throws HiException
/*     */   {
/*  77 */     this.document = document;
/*  78 */     this.urlFileRulePath = urlFileRulePath;
/*  79 */     jbInit();
/*  80 */     loadFile();
/*     */   }
/*     */ 
/*     */   private HiCfgFile(File file, URL urlFileRulePath) throws HiException {
/*     */     try {
/*  85 */       SAXReader saxReader = new SAXReader();
/*  86 */       this.document = saxReader.read(file);
/*     */     } catch (DocumentException e) {
/*  88 */       throw new HiException("213319", new String[] { file.getPath(), e.getMessage() }, e);
/*     */     }
/*     */ 
/*  91 */     this.urlFileRulePath = urlFileRulePath;
/*  92 */     jbInit();
/*  93 */     loadFile();
/*     */   }
/*     */ 
/*     */   void readFile() throws HiException
/*     */   {
/*     */     try
/*     */     {
/* 100 */       SAXReader saxReader = new SAXReader();
/* 101 */       this.document = saxReader.read(this.urlFilePath);
/*     */     } catch (DocumentException e) {
/* 103 */       throw new HiException("213319", new String[] { this.urlFilePath.getFile(), e.getMessage() }, e);
/*     */     }
/*     */   }
/*     */ 
/*     */   void jbInit()
/*     */     throws HiException
/*     */   {
/*     */     int i;
/*     */     Element element;
/* 112 */     Element rootNode = this.document.getRootElement();
/*     */ 
/* 114 */     HashMap allElements = HiPretreatment.getAllElements(rootNode, null);
/*     */ 
/* 116 */     HiPretreatment.parseInclude(allElements, this.document);
/*     */ 
/* 120 */     List list = (List)allElements.get("Define");
/* 121 */     if ((list != null) && (list.size() > 0)) {
/* 122 */       for (i = 0; i < list.size(); ++i) {
/* 123 */         element = (Element)list.get(i);
/* 124 */         HiPretreatment.parseMacro(HiPretreatment.getAllElements(element, null));
/*     */       }
/*     */ 
/* 127 */       allElements = HiPretreatment.getAllElements(rootNode, null);
/*     */     }
/* 129 */     HiPretreatment.parseMacro(allElements);
/*     */ 
/* 131 */     if ((list != null) && (list.size() > 0))
/* 132 */       for (i = 0; i < list.size(); ++i) {
/* 133 */         element = (Element)list.get(i);
/*     */ 
/* 136 */         Element parEl = element.getParent();
/* 137 */         if (parEl == null)
/*     */           continue;
/* 139 */         List li = parEl.elements();
/* 140 */         li.remove(element);
/*     */       }
/*     */   }
/*     */ 
/*     */   void loadFile()
/*     */     throws HiException
/*     */   {
/* 149 */     ByteArrayInputStream inFile = null;
/* 150 */     InputStreamReader in = null;
/* 151 */     String strXML = null;
/* 152 */     Digester d = null;
/* 153 */     if (this.urlFileRulePath == null) {
/* 154 */       throw new HiException("212004", this.urlFilePath.getFile());
/*     */     }
/*     */     try
/*     */     {
/* 158 */       RuleSet ruleSet = new FromXmlRuleSet(this.urlFileRulePath, new HiConfigurationRuleParser());
/*     */ 
/* 160 */       d = new Digester();
/* 161 */       d.addRuleSet(ruleSet);
/*     */ 
/* 164 */       if (this.strNodeRule != null) {
/* 165 */         Rule rule = new NodeCreateRule();
/* 166 */         d.addRule("*/" + this.strNodeRule, rule);
/* 167 */         d.addSetNext("*/" + this.strNodeRule, "setProperty");
/*     */       }
/*     */ 
/* 172 */       d.setValidating(false);
/*     */ 
/* 174 */       strXML = this.document.asXML();
/*     */ 
/* 177 */       inFile = new ByteArrayInputStream(strXML.getBytes());
/*     */ 
/* 179 */       in = new InputStreamReader(inFile);
/* 180 */       d.setClassLoader(Thread.currentThread().getContextClassLoader());
/* 181 */       this.rootObj = d.parse(in);
/*     */ 
/* 184 */       if ((this.urlFilePath != null) && (this.logger.isInfoEnabled())) {
/* 185 */         StringWriter buffer = new StringWriter();
/* 186 */         OutputFormat format = OutputFormat.createPrettyPrint();
/* 187 */         format.setEncoding("ISO-8859-1");
/* 188 */         XMLWriter writer = new XMLWriter(buffer, format);
/* 189 */         writer.write(this.document);
/*     */ 
/* 191 */         String xml = buffer.toString();
/* 192 */         HiFileUtil.saveTempFile(this.urlFilePath, xml);
/* 193 */         writer.close();
/* 194 */         buffer.close();
/*     */       }
/*     */     } catch (SAXException sx) {
/* 197 */       this.logger.error("filename[" + this.urlFilePath + "]");
/*     */ 
/* 199 */       HiFileUtil.saveTempFile(this.urlFilePath, strXML);
/*     */ 
/* 201 */       Exception ex = sx.getException();
/*     */ 
/* 203 */       if (ex == null);
/* 206 */       throw HiException.makeException("213319", sx.getMessage(), ex);
/*     */     }
/*     */     catch (Exception sx)
/*     */     {
/*     */     }
/*     */     finally
/*     */     {
/*     */       try
/*     */       {
/* 225 */         if (in != null)
/* 226 */           in.close();
/* 227 */         if (inFile != null)
/* 228 */           inFile.close();
/* 229 */         d.clear();
/* 230 */         d = null;
/*     */       } catch (Exception e) {
/* 232 */         this.logger.error("loadFile()", e);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 237 */     if (this.logger.isDebugEnabled())
/* 238 */       this.logger.debug("loadFile() - end");
/*     */   }
/*     */ 
/*     */   void processCommon()
/*     */     throws HiException
/*     */   {
/* 246 */     if (this.logger.isDebugEnabled()) {
/* 247 */       this.logger.debug("processCommon() - start");
/*     */     }
/*     */ 
/* 266 */     if (this.logger.isDebugEnabled())
/* 267 */       this.logger.debug("processCommon() - end");
/*     */   }
/*     */ 
/*     */   public Object getRootInstance()
/*     */   {
/* 289 */     return this.rootObj;
/*     */   }
/*     */ 
/*     */   public static HiCfgFile getDefaultCfgFile(URL urlFilePath, URL urlFileRulePath, String strNodeRule)
/*     */     throws HiException
/*     */   {
/* 296 */     HiCfgFile cfgFile = new HiCfgFile(urlFilePath, urlFileRulePath, strNodeRule);
/*     */ 
/* 298 */     return cfgFile;
/*     */   }
/*     */ 
/*     */   public static HiCfgFile getDefaultCfgFile(URL urlFilePath, URL urlFileRulePath, String strNodeRule, String strLogName)
/*     */     throws HiException
/*     */   {
/* 304 */     HiCfgFile cfgFile = new HiCfgFile(urlFilePath, urlFileRulePath, strNodeRule, strLogName);
/*     */ 
/* 306 */     return cfgFile;
/*     */   }
/*     */ 
/*     */   public static HiCfgFile getCfgFile(Document document, URL urlFileRulePath) throws HiException
/*     */   {
/* 311 */     HiCfgFile cfgFile = new HiCfgFile(document, urlFileRulePath);
/* 312 */     return cfgFile;
/*     */   }
/*     */ 
/*     */   public static HiCfgFile getCfgFile(File file, URL urlFileRulePath) throws HiException
/*     */   {
/* 317 */     HiCfgFile cfgFile = new HiCfgFile(file, urlFileRulePath);
/* 318 */     return cfgFile;
/*     */   }
/*     */ }