/*     */ package com.hisun.framework.parser;
/*     */ 
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.framework.HiConfigParser;
/*     */ import com.hisun.framework.HiDefaultServer;
/*     */ import com.hisun.framework.HiFrameworkBuilder;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.parse.HiConfigurationRuleParser;
/*     */ import com.hisun.parse.HiVariableExpander;
/*     */ import com.hisun.service.IObjectDecorator;
/*     */ import java.io.InputStream;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.net.URL;
/*     */ import org.apache.commons.digester.Digester;
/*     */ import org.apache.commons.digester.RuleSet;
/*     */ import org.apache.commons.digester.substitution.VariableSubstitutor;
/*     */ import org.apache.commons.digester.xmlrules.FromXmlRuleSet;
/*     */ import org.xml.sax.ErrorHandler;
/*     */ import org.xml.sax.SAXException;
/*     */ import org.xml.sax.SAXParseException;
/*     */ 
/*     */ public class HiDigesterParserImp
/*     */   implements HiConfigParser, ErrorHandler
/*     */ {
/*     */   private static final String DOCTYPE_ID = "-//HISUN//DTD//ATR";
/*     */   private static final String ATR_DTD = "ATR.dtd";
/*     */   private static final String SERVER_RULE_PATH = "serverRules.xml";
/*     */   private Digester serverParser;
/*     */ 
/*     */   public HiDigesterParserImp()
/*     */   {
/*  40 */     RuleSet ruleSet = new FromXmlRuleSet(HiDigesterParserImp.class.getResource("serverRules.xml"), new HiConfigurationRuleParser());
/*     */ 
/*  42 */     this.serverParser = new Digester();
/*  43 */     this.serverParser.addRuleSet(ruleSet);
/*     */ 
/*  45 */     this.serverParser.setErrorHandler(this);
/*  46 */     String dtdpath = HiDigesterParserImp.class.getResource("ATR.dtd").toString();
/*     */ 
/*  51 */     this.serverParser.register("-//HISUN//DTD//ATR", dtdpath);
/*  52 */     this.serverParser.setValidating(true);
/*     */ 
/*  55 */     this.serverParser.setSubstitutor(new VariableSubstitutor(new HiVariableExpander(), null));
/*     */   }
/*     */ 
/*     */   public HiDefaultServer parseServerXML(InputStream serverXml)
/*     */     throws HiException
/*     */   {
/*     */     HiDefaultServer server;
/*  60 */     this.serverParser.clear();
/*     */     try {
/*  62 */       this.serverParser.setClassLoader(Thread.currentThread().getContextClassLoader());
/*     */ 
/*  64 */       HiDefaultServer server = (HiDefaultServer)this.serverParser.parse(serverXml);
/*     */ 
/*  69 */       server = (HiDefaultServer)HiFrameworkBuilder.getObjectDecorator().decorate(server, "addFilter");
/*     */ 
/*  73 */       server.endBuild();
/*     */ 
/*  75 */       return server;
/*     */     } catch (SAXException e) {
/*  77 */       if (this.serverParser.getRoot() != null) {
/*  78 */         server = (HiDefaultServer)this.serverParser.getRoot();
/*     */ 
/*  80 */         if (e.getException() != null)
/*  81 */           server.getLog().error(e.getException(), e.getException());
/*     */         else {
/*  83 */           server.getLog().error(e, e);
/*     */         }
/*  85 */         server.endBuild();
/*  86 */         server.destroy();
/*     */       }
/*     */ 
/*  89 */       Exception ec = e.getException();
/*  90 */       if (ec == null) {
/*  91 */         throw new HiException("211001", e.getMessage(), e);
/*     */       }
/*     */ 
/*  94 */       if (ec instanceof InvocationTargetException) {
/*  95 */         Throwable t = ((InvocationTargetException)ec).getTargetException();
/*     */ 
/*  97 */         if (t instanceof Exception) {
/*  98 */           ec = (Exception)t;
/*     */         }
/*     */       }
/*     */ 
/* 102 */       if (ec instanceof HiException) {
/* 103 */         throw ((HiException)ec);
/*     */       }
/*     */ 
/* 106 */       throw new HiException("211001", ec.getMessage(), ec);
/*     */     }
/*     */     catch (Throwable e)
/*     */     {
/* 118 */       if (this.serverParser.getRoot() != null) {
/* 119 */         server = (HiDefaultServer)this.serverParser.getRoot();
/*     */ 
/* 121 */         server.getLog().error(e, e);
/* 122 */         server.endBuild();
/* 123 */         server.destroy();
/*     */       }
/*     */ 
/* 126 */       throw new HiException("211001", e.getMessage(), e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void warning(SAXParseException arg0)
/*     */     throws SAXException
/*     */   {
/* 133 */     throw arg0;
/*     */   }
/*     */ 
/*     */   public void error(SAXParseException arg0) throws SAXException {
/* 137 */     throw arg0;
/*     */   }
/*     */ 
/*     */   public void fatalError(SAXParseException arg0) throws SAXException
/*     */   {
/* 142 */     throw arg0;
/*     */   }
/*     */ }