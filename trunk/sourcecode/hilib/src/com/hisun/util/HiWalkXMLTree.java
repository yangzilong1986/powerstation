/*    */ package com.hisun.util;
/*    */ 
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import org.apache.commons.beanutils.MethodUtils;
/*    */ import org.dom4j.Attribute;
/*    */ import org.dom4j.Document;
/*    */ import org.dom4j.Element;
/*    */ 
/*    */ public class HiWalkXMLTree
/*    */ {
/*    */   Object o;
/*    */ 
/*    */   public void setVisitor(Object o)
/*    */   {
/* 14 */     this.o = o;
/*    */   }
/*    */ 
/*    */   public void process(Document doc) throws Exception {
/* 18 */     walkTree(doc.getRootElement());
/*    */   }
/*    */ 
/*    */   private void walkTree(Element node) throws Exception {
/* 22 */     Iterator iter = node.attributeIterator();
/* 23 */     MethodUtils.invokeMethod(this.o, "handlerElement", node);
/* 24 */     while (iter.hasNext()) {
/* 25 */       MethodUtils.invokeMethod(this.o, "handlerAttribute", (Attribute)iter.next());
/*    */     }
/* 27 */     for (int i = 0; i < node.elements().size(); ++i)
/* 28 */       walkTree((Element)node.elements().get(i));
/*    */   }
/*    */ }