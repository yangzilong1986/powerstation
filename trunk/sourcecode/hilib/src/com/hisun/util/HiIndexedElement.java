/*     */ package com.hisun.util;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.dom4j.Attribute;
/*     */ import org.dom4j.Element;
/*     */ import org.dom4j.Node;
/*     */ import org.dom4j.QName;
/*     */ import org.dom4j.tree.BackedList;
/*     */ import org.dom4j.tree.DefaultElement;
/*     */ 
/*     */ public class HiIndexedElement extends DefaultElement
/*     */ {
/*     */   private Map elementIndex;
/*     */   private Map attributeIndex;
/*     */ 
/*     */   public HiIndexedElement(String name)
/*     */   {
/*  41 */     super(name);
/*     */   }
/*     */ 
/*     */   public HiIndexedElement(QName qname) {
/*  45 */     super(qname);
/*     */   }
/*     */ 
/*     */   public HiIndexedElement(QName qname, int attributeCount) {
/*  49 */     super(qname, attributeCount);
/*     */   }
/*     */ 
/*     */   public Attribute attribute(String name) {
/*  53 */     return ((Attribute)attributeIndex().get(name));
/*     */   }
/*     */ 
/*     */   public Attribute attribute(QName qName) {
/*  57 */     return ((Attribute)attributeIndex().get(qName));
/*     */   }
/*     */ 
/*     */   public Element element(String name) {
/*  61 */     return asElement(elementIndex().get(name));
/*     */   }
/*     */ 
/*     */   public Element element(QName qName) {
/*  65 */     return asElement(elementIndex().get(qName));
/*     */   }
/*     */ 
/*     */   public List elements(String name) {
/*  69 */     return asElementList(elementIndex().get(name));
/*     */   }
/*     */ 
/*     */   public List elements(QName qName) {
/*  73 */     return asElementList(elementIndex().get(qName));
/*     */   }
/*     */ 
/*     */   protected Element asElement(Object object)
/*     */   {
/*  79 */     if (object instanceof Element)
/*  80 */       return ((Element)object);
/*  81 */     if (object != null) {
/*  82 */       List list = (List)object;
/*     */ 
/*  84 */       if (list.size() >= 1) {
/*  85 */         return ((Element)list.get(0));
/*     */       }
/*     */     }
/*     */ 
/*  89 */     return null;
/*     */   }
/*     */ 
/*     */   protected List asElementList(Object object) {
/*  93 */     if (object instanceof Element)
/*  94 */       return createSingleResultList(object);
/*  95 */     if (object != null) {
/*  96 */       List list = (List)object;
/*  97 */       BackedList answer = createResultList();
/*     */ 
/*  99 */       int i = 0; for (int size = list.size(); i < size; ++i) {
/* 100 */         answer.addLocal(list.get(i));
/*     */       }
/*     */ 
/* 103 */       return answer;
/*     */     }
/*     */ 
/* 106 */     return createEmptyList();
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   protected Iterator asElementIterator(Object object)
/*     */   {
/* 120 */     return asElementList(object).iterator();
/*     */   }
/*     */ 
/*     */   public void add(Attribute attribute) {
/* 124 */     System.out.println("addAttribute");
/* 125 */     super.add(attribute);
/* 126 */     addToAttributeIndex(attribute);
/*     */   }
/*     */ 
/*     */   public boolean remove(Attribute attribute) {
/* 130 */     if (super.remove(attribute)) {
/* 131 */       removeFromAttributeIndex(attribute);
/* 132 */       return true;
/*     */     }
/* 134 */     return false;
/*     */   }
/*     */ 
/*     */   protected void addNewNode(int index, Node node)
/*     */   {
/* 139 */     super.addNewNode(index, node);
/* 140 */     if (node instanceof Element)
/* 141 */       addToElementIndex((Element)node);
/* 142 */     else if (node instanceof Attribute)
/* 143 */       addToAttributeIndex((Attribute)node);
/*     */   }
/*     */ 
/*     */   protected void addNewNode(Node node)
/*     */   {
/* 148 */     super.addNewNode(node);
/* 149 */     if (node instanceof Element)
/* 150 */       addToElementIndex((Element)node);
/* 151 */     else if (node instanceof Attribute)
/* 152 */       addToAttributeIndex((Attribute)node);
/*     */   }
/*     */ 
/*     */   protected void addNode(Node node)
/*     */   {
/* 157 */     super.addNode(node);
/* 158 */     if ((this.elementIndex != null) && (node instanceof Element))
/* 159 */       addToElementIndex((Element)node);
/* 160 */     else if ((this.attributeIndex != null) && (node instanceof Attribute))
/* 161 */       addToAttributeIndex((Attribute)node);
/*     */   }
/*     */ 
/*     */   protected boolean removeNode(Node node)
/*     */   {
/* 166 */     if (super.removeNode(node)) {
/* 167 */       if ((this.elementIndex != null) && (node instanceof Element))
/* 168 */         removeFromElementIndex((Element)node);
/* 169 */       else if ((this.attributeIndex != null) && (node instanceof Attribute)) {
/* 170 */         removeFromAttributeIndex((Attribute)node);
/*     */       }
/*     */ 
/* 173 */       return true;
/*     */     }
/*     */ 
/* 176 */     return false;
/*     */   }
/*     */ 
/*     */   protected Map attributeIndex()
/*     */   {
/*     */     Iterator iter;
/* 180 */     if (this.attributeIndex == null) {
/* 181 */       this.attributeIndex = createAttributeIndex();
/*     */ 
/* 183 */       for (iter = attributeIterator(); iter.hasNext(); ) {
/* 184 */         addToAttributeIndex((Attribute)iter.next());
/*     */       }
/*     */     }
/*     */ 
/* 188 */     return this.attributeIndex;
/*     */   }
/*     */ 
/*     */   protected Map elementIndex()
/*     */   {
/*     */     Iterator iter;
/* 192 */     if (this.elementIndex == null) {
/* 193 */       this.elementIndex = createElementIndex();
/*     */ 
/* 195 */       for (iter = elementIterator(); iter.hasNext(); ) {
/* 196 */         addToElementIndex((Element)iter.next());
/*     */       }
/*     */     }
/*     */ 
/* 200 */     return this.elementIndex;
/*     */   }
/*     */ 
/*     */   protected Map createAttributeIndex()
/*     */   {
/* 209 */     Map answer = createIndex();
/*     */ 
/* 211 */     return answer;
/*     */   }
/*     */ 
/*     */   protected Map createElementIndex()
/*     */   {
/* 220 */     Map answer = createIndex();
/*     */ 
/* 222 */     return answer;
/*     */   }
/*     */ 
/*     */   protected void addToElementIndex(Element element) {
/* 226 */     QName qName = element.getQName();
/* 227 */     String name = qName.getName();
/* 228 */     addToElementIndex(qName, element);
/* 229 */     addToElementIndex(name, element);
/*     */   }
/*     */ 
/*     */   protected void addToElementIndex(Object key, Element value) {
/* 233 */     if (this.elementIndex == null) {
/* 234 */       this.elementIndex = createIndex();
/*     */     }
/* 236 */     Object oldValue = this.elementIndex.get(key);
/*     */ 
/* 238 */     if (oldValue == null) {
/* 239 */       this.elementIndex.put(key, value);
/*     */     }
/*     */     else
/*     */     {
/*     */       List list;
/* 241 */       if (oldValue instanceof List) {
/* 242 */         list = (List)oldValue;
/* 243 */         list.add(value);
/*     */       } else {
/* 245 */         list = createList();
/* 246 */         list.add(oldValue);
/* 247 */         list.add(value);
/* 248 */         this.elementIndex.put(key, list);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void removeFromElementIndex(Element element) {
/* 254 */     QName qName = element.getQName();
/* 255 */     String name = qName.getName();
/* 256 */     removeFromElementIndex(qName, element);
/* 257 */     removeFromElementIndex(name, element);
/*     */   }
/*     */ 
/*     */   protected void removeFromElementIndex(Object key, Element value) {
/* 261 */     Object oldValue = this.elementIndex.get(key);
/*     */ 
/* 263 */     if (oldValue instanceof List) {
/* 264 */       List list = (List)oldValue;
/* 265 */       list.remove(value);
/*     */     } else {
/* 267 */       this.elementIndex.remove(key);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void addToAttributeIndex(Attribute attribute) {
/* 272 */     QName qName = attribute.getQName();
/* 273 */     String name = qName.getName();
/* 274 */     addToAttributeIndex(qName, attribute);
/* 275 */     addToAttributeIndex(name, attribute);
/*     */   }
/*     */ 
/*     */   protected void addToAttributeIndex(Object key, Attribute value) {
/* 279 */     if (this.attributeIndex == null) {
/* 280 */       this.attributeIndex = createAttributeIndex();
/*     */     }
/*     */ 
/* 285 */     this.attributeIndex.put(key, value);
/*     */   }
/*     */ 
/*     */   protected void removeFromAttributeIndex(Attribute attribute)
/*     */   {
/* 290 */     QName qName = attribute.getQName();
/* 291 */     String name = qName.getName();
/* 292 */     removeFromAttributeIndex(qName, attribute);
/* 293 */     removeFromAttributeIndex(name, attribute);
/*     */   }
/*     */ 
/*     */   protected void removeFromAttributeIndex(Object key, Attribute value) {
/* 297 */     Object oldValue = this.attributeIndex.get(key);
/*     */ 
/* 299 */     if ((oldValue != null) && (oldValue.equals(value)))
/* 300 */       this.attributeIndex.remove(key);
/*     */   }
/*     */ 
/*     */   protected Map createIndex()
/*     */   {
/* 310 */     return new HashMap(100, 10.0F);
/*     */   }
/*     */ 
/*     */   protected List createList()
/*     */   {
/* 319 */     return new ArrayList();
/*     */   }
/*     */ }