/*    */ package com.hisun.protocol.tcp.parser;
/*    */ 
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.parse.HiVariableExpander;
/*    */ import java.io.IOException;
/*    */ import java.lang.reflect.InvocationTargetException;
/*    */ import java.net.URL;
/*    */ import java.util.ArrayList;
/*    */ import org.apache.commons.digester.Digester;
/*    */ import org.apache.commons.digester.substitution.VariableSubstitutor;
/*    */ import org.xml.sax.ErrorHandler;
/*    */ import org.xml.sax.SAXException;
/*    */ import org.xml.sax.SAXParseException;
/*    */ 
/*    */ public class HiIPXMLParser
/*    */   implements ErrorHandler
/*    */ {
/*    */   private final Digester parser;
/*    */ 
/*    */   public HiIPXMLParser()
/*    */   {
/* 27 */     this.parser = new Digester();
/* 28 */     this.parser.addObjectCreate("clientIpTable", ArrayList.class);
/* 29 */     Class[] types = { Object.class };
/* 30 */     this.parser.addCallMethod("clientIpTable/ipItem", "add", 1, types);
/* 31 */     this.parser.addCallParam("clientIpTable/ipItem", 0, "ip");
/*    */ 
/* 33 */     this.parser.setErrorHandler(this);
/*    */ 
/* 36 */     this.parser.setSubstitutor(new VariableSubstitutor(new HiVariableExpander(), null));
/*    */   }
/*    */ 
/*    */   public void error(SAXParseException arg0) throws SAXException
/*    */   {
/* 41 */     throw arg0;
/*    */   }
/*    */ 
/*    */   public void fatalError(SAXParseException arg0) throws SAXException
/*    */   {
/* 46 */     throw arg0;
/*    */   }
/*    */ 
/*    */   public Object parse(URL url) throws HiException {
/* 50 */     this.parser.clear();
/*    */     try {
/* 52 */       this.parser.setClassLoader(Thread.currentThread().getContextClassLoader());
/*    */ 
/* 54 */       return this.parser.parse(url.openStream());
/*    */     } catch (IOException e) {
/* 56 */       throw HiException.makeException(e);
/*    */     } catch (SAXException e) {
/* 58 */       Exception ec = e.getException();
/* 59 */       if (ec == null) {
/* 60 */         throw new HiException("212005", url.toString(), e);
/*    */       }
/* 62 */       if (ec instanceof InvocationTargetException) {
/* 63 */         Throwable t = ((InvocationTargetException)ec).getTargetException();
/*    */ 
/* 65 */         if (t instanceof Exception) {
/* 66 */           ec = (Exception)t;
/*    */         }
/*    */       }
/*    */ 
/* 70 */       if (ec instanceof HiException) {
/* 71 */         throw ((HiException)ec);
/*    */       }
/*    */ 
/* 74 */       throw new HiException("212005", url.toString(), ec);
/*    */     }
/*    */   }
/*    */ 
/*    */   public void warning(SAXParseException arg0)
/*    */     throws SAXException
/*    */   {
/* 81 */     throw arg0;
/*    */   }
/*    */ }