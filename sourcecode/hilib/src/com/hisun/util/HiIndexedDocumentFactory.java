/*    */ package com.hisun.util;
/*    */ 
/*    */ import org.dom4j.DocumentFactory;
/*    */ import org.dom4j.Element;
/*    */ import org.dom4j.QName;
/*    */ 
/*    */ public class HiIndexedDocumentFactory extends DocumentFactory
/*    */ {
/* 28 */   protected static transient HiIndexedDocumentFactory singleton = new HiIndexedDocumentFactory();
/*    */ 
/*    */   public static DocumentFactory getInstance()
/*    */   {
/* 39 */     return singleton;
/*    */   }
/*    */ 
/*    */   public Element createElement(QName qname)
/*    */   {
/* 45 */     return new HiIndexedElement(qname);
/*    */   }
/*    */ 
/*    */   public Element createElement(QName qname, int attributeCount) {
/* 49 */     return new HiIndexedElement(qname, attributeCount);
/*    */   }
/*    */ }