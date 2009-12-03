/*    */ package com.hisun.message;
/*    */ 
/*    */ import edu.emory.mathcs.backport.java.util.LinkedList;
/*    */ import java.util.Iterator;
/*    */ import java.util.Map;
/*    */ import java.util.WeakHashMap;
/*    */ import org.dom4j.Element;
/*    */ import org.dom4j.QName;
/*    */ import org.dom4j.tree.DefaultElement;
/*    */ 
/*    */ public class HiElementCache
/*    */ {
/*    */   protected Map elementCache;
/*    */ 
/*    */   public HiElementCache()
/*    */   {
/* 18 */     this.elementCache = new WeakHashMap();
/*    */   }
/*    */ 
/*    */   public Element get(QName qname)
/*    */   {
/*    */     Element answer;
/* 21 */     LinkedList l = (LinkedList)this.elementCache.get(qname);
/*    */ 
/* 23 */     if ((l == null) || (l.size() == 0))
/* 24 */       answer = new DefaultElement(qname);
/*    */     else {
/* 26 */       answer = (Element)l.pop();
/*    */     }
/*    */ 
/* 29 */     return answer;
/*    */   }
/*    */ 
/*    */   public void recycle(Element e) {
/* 33 */     Iterator it = e.elementIterator();
/* 34 */     while (it.hasNext()) {
/* 35 */       recycle((Element)it.next());
/*    */     }
/* 37 */     e.clearContent();
/* 38 */     for (int i = 0; i < e.attributeCount(); ++i) {
/* 39 */       e.remove(e.attribute(i));
/*    */     }
/* 41 */     add(e.getQName(), e);
/*    */   }
/*    */ 
/*    */   void add(QName qname, Element e) {
/* 45 */     LinkedList l = (LinkedList)this.elementCache.get(qname);
/* 46 */     if (l == null) {
/* 47 */       l = new LinkedList();
/* 48 */       this.elementCache.put(qname, l);
/*    */     }
/* 50 */     l.push(e);
/*    */   }
/*    */ }