/*    */ package com.hisun.xml;
/*    */ 
/*    */ import org.apache.commons.digester.Digester;
/*    */ import org.apache.commons.digester.Rule;
/*    */ import org.xml.sax.Attributes;
/*    */ import org.xml.sax.Locator;
/*    */ 
/*    */ public class SetLocationRule extends Rule
/*    */ {
/*    */   public void begin(String namespace, String name, Attributes attributes)
/*    */     throws Exception
/*    */   {
/* 15 */     Object obj = this.digester.peek();
/* 16 */     if (obj instanceof Located)
/* 17 */       ((Located)obj).setLocation(currentLocation());
/*    */   }
/*    */ 
/*    */   private Location currentLocation()
/*    */   {
/* 22 */     Locator l = this.digester.getDocumentLocator();
/* 23 */     return new Location(this.digester.getPublicId(), l.getLineNumber(), l.getColumnNumber());
/*    */   }
/*    */ }