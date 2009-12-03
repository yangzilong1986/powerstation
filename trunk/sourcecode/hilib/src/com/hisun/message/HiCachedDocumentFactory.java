/*    */ package com.hisun.message;
/*    */ 
/*    */ import org.dom4j.DocumentFactory;
/*    */ import org.dom4j.Element;
/*    */ import org.dom4j.QName;
/*    */ 
/*    */ public class HiCachedDocumentFactory extends DocumentFactory
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   protected transient HiElementCache elemcache;
/* 13 */   protected static transient HiCachedDocumentFactory singleton = new HiCachedDocumentFactory();
/*    */ 
/*    */   public HiCachedDocumentFactory()
/*    */   {
/* 18 */     this.elemcache = new HiElementCache();
/*    */   }
/*    */ 
/*    */   public Element createElement(QName qname) {
/* 22 */     return this.elemcache.get(qname);
/*    */   }
/*    */ 
/*    */   public void recycle(Element e) {
/* 26 */     this.elemcache.recycle(e);
/*    */   }
/*    */ 
/*    */   public static HiCachedDocumentFactory getSingleton() {
/* 30 */     return singleton;
/*    */   }
/*    */ }