/*    */ package com.hisun.protocol.tcp.parser;
/*    */ 
/*    */ import com.hisun.parse.HiVariableExpander;
/*    */ import java.net.URL;
/*    */ import org.apache.commons.digester.Digester;
/*    */ import org.apache.commons.digester.substitution.VariableSubstitutor;
/*    */ import org.xml.sax.ErrorHandler;
/*    */ import org.xml.sax.SAXException;
/*    */ import org.xml.sax.SAXParseException;
/*    */ 
/*    */ public class HiHostIPXMLParser
/*    */   implements ErrorHandler
/*    */ {
/*    */   private final Digester parser;
/*    */ 
/*    */   public HiHostIPXMLParser()
/*    */   {
/* 19 */     this.parser = new Digester();
/* 20 */     this.parser.addObjectCreate("hostIpTable", HiHostIpTable.class);
/* 21 */     Class[] types = { String.class, String.class, String.class };
/* 22 */     this.parser.addCallMethod("hostIpTable/ipItem", "add", 3, types);
/* 23 */     this.parser.addCallParam("hostIpTable/ipItem", 0, "name");
/* 24 */     this.parser.addCallParam("hostIpTable/ipItem", 1, "ip");
/* 25 */     this.parser.addCallParam("hostIpTable/ipItem", 2, "port");
/*    */ 
/* 27 */     this.parser.setErrorHandler(this);
/*    */ 
/* 30 */     this.parser.setSubstitutor(new VariableSubstitutor(new HiVariableExpander(), null));
/*    */   }
/*    */ 
/*    */   public void error(SAXParseException arg0) throws SAXException
/*    */   {
/* 35 */     throw arg0;
/*    */   }
/*    */ 
/*    */   public void fatalError(SAXParseException arg0) throws SAXException
/*    */   {
/* 40 */     throw arg0;
/*    */   }
/*    */ 
/*    */   public Object parse(URL url) throws Exception {
/* 44 */     this.parser.clear();
/* 45 */     return this.parser.parse(url.openStream());
/*    */   }
/*    */ 
/*    */   public void warning(SAXParseException arg0)
/*    */     throws SAXException
/*    */   {
/* 51 */     throw arg0;
/*    */   }
/*    */ }