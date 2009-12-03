/*     */ package com.hisun.parse;
/*     */ 
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiContext;
/*     */ import com.hisun.util.HiResource;
/*     */ import java.net.URL;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.dom4j.Document;
/*     */ import org.dom4j.DocumentException;
/*     */ import org.dom4j.Element;
/*     */ import org.dom4j.io.SAXReader;
/*     */ import org.dom4j.tree.DefaultElement;
/*     */ 
/*     */ public class HiPretreatment
/*     */ {
/*     */   private final Logger logger;
/*     */   private static final String DEFINE_NODE = "Define";
/*     */   private static final String INCLUDE_FILE = "file";
/*     */   private static final String INCLUDE_NODE = "Include";
/*     */   private static final String MACRO_NODE = "Macro";
/*     */   private static final String QUOTE_NODE = "Quote";
/*     */ 
/*     */   public HiPretreatment()
/*     */   {
/*  27 */     this.logger = ((Logger)HiContext.getCurrentContext().getProperty("SVR.log"));
/*     */   }
/*     */ 
/*     */   public static HashMap getAllElements(Element element, HashMap elements)
/*     */   {
/*     */     ArrayList list;
/*  46 */     if ((elements == null) || (elements.isEmpty())) {
/*  47 */       elements = new HashMap();
/*     */     }
/*  49 */     String strNodeName = element.getName();
/*     */ 
/*  51 */     if (elements.containsKey(strNodeName))
/*     */     {
/*  53 */       list = (ArrayList)elements.get(strNodeName);
/*  54 */       list.add(element);
/*     */     }
/*     */     else
/*     */     {
/*  58 */       list = new ArrayList();
/*  59 */       list.add(element);
/*  60 */       elements.put(strNodeName, list);
/*     */     }
/*  62 */     Iterator iter = element.elementIterator();
/*  63 */     while (iter.hasNext())
/*     */     {
/*  65 */       Element sub = (Element)iter.next();
/*  66 */       getAllElements(sub, elements);
/*     */     }
/*     */ 
/*  69 */     return elements;
/*     */   }
/*     */ 
/*     */   public static void parseInclude(HashMap allElements, Document document)
/*     */     throws HiException
/*     */   {
/*     */     List list;
/*     */     int index;
/*     */     Iterator iter;
/*  82 */     ArrayList includeNodes = (ArrayList)allElements.get("Include");
/*     */ 
/*  84 */     if ((includeNodes == null) || (includeNodes.size() == 0)) {
/*  85 */       return;
/*     */     }
/*  87 */     for (int i = 0; i < includeNodes.size(); ++i)
/*     */     {
/*  89 */       Element includeNode = (Element)includeNodes.get(i);
/*  90 */       String strFileName = includeNode.attributeValue("file");
/*     */ 
/*  92 */       Element rootInNode = null;
/*     */ 
/*  94 */       URL fileURL = HiResource.getResource(strFileName);
/*     */ 
/*  96 */       if (fileURL == null) {
/*  97 */         throw new HiException("213321", strFileName);
/*     */       }
/*     */ 
/*     */       try
/*     */       {
/* 102 */         SAXReader saxReader = new SAXReader();
/* 103 */         Document includeDoc = saxReader.read(fileURL);
/* 104 */         rootInNode = includeDoc.getRootElement();
/*     */ 
/* 106 */         HashMap allInElements = getAllElements(rootInNode, null);
/*     */ 
/* 110 */         parseInclude(allInElements, document);
/*     */ 
/* 113 */         parseMacro(allInElements);
/*     */ 
/* 116 */         if ((allElements.containsKey("Macro")) && (allInElements.containsKey("Macro")))
/*     */         {
/* 119 */           ArrayList macroNodes = (ArrayList)allElements.get("Macro");
/*     */ 
/* 122 */           macroNodes.addAll((ArrayList)allInElements.get("Macro"));
/*     */         }
/*     */ 
/*     */       }
/*     */       catch (DocumentException e)
/*     */       {
/* 130 */         throw new HiException("213320", strFileName, e);
/*     */       }
/*     */ 
/* 134 */       if (rootInNode == null) {
/* 135 */         return;
/*     */       }
/* 137 */       List replaceNode = rootInNode.elements();
/*     */ 
/* 139 */       list = includeNode.getParent().elements();
/*     */ 
/* 141 */       index = list.indexOf(includeNode);
/*     */ 
/* 143 */       if (index < 0)
/*     */         continue;
/* 145 */       Object d = list.remove(index);
/* 146 */       if (index >= list.size())
/*     */       {
/* 148 */         for (iter = replaceNode.iterator(); iter.hasNext(); )
/*     */         {
/* 150 */           list.add(((DefaultElement)iter.next()).clone());
/*     */         }
/*     */ 
/*     */       }
/*     */       else
/* 155 */         for (iter = replaceNode.iterator(); iter.hasNext(); )
/*     */         {
/* 157 */           list.add(index++, ((DefaultElement)iter.next()).clone());
/*     */         }
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void parseMacro(HashMap allElements)
/*     */     throws HiException
/*     */   {
/*     */     List list;
/*     */     int index;
/*     */     Iterator iter;
/* 177 */     ArrayList macroNodes = (ArrayList)allElements.get("Macro");
/*     */ 
/* 179 */     if ((macroNodes == null) || (macroNodes.size() == 0)) {
/* 180 */       return;
/*     */     }
/* 182 */     HashMap macroMap = new HashMap();
/* 183 */     for (int i = 0; i < macroNodes.size(); ++i)
/*     */     {
/* 185 */       Element macroNode = (Element)macroNodes.get(i);
/* 186 */       String strName = macroNode.attributeValue("name");
/*     */ 
/* 188 */       List childNodes = macroNode.elements();
/* 189 */       macroMap.put(strName, childNodes);
/*     */     }
/*     */ 
/* 192 */     ArrayList quoteNodes = (ArrayList)allElements.get("Quote");
/*     */ 
/* 194 */     if ((quoteNodes == null) || (quoteNodes.size() == 0)) {
/* 195 */       return;
/*     */     }
/* 197 */     for (int i = 0; i < quoteNodes.size(); ++i)
/*     */     {
/* 199 */       Element quoteNode = (Element)quoteNodes.get(i);
/* 200 */       String strName = quoteNode.attributeValue("name");
/* 201 */       if (!(macroMap.containsKey(strName))) {
/* 202 */         throw new HiException("213333", strName);
/*     */       }
/* 204 */       List macroChilds = (List)macroMap.get(strName);
/*     */ 
/* 206 */       list = quoteNode.getParent().elements();
/*     */ 
/* 208 */       index = list.indexOf(quoteNode);
/*     */ 
/* 210 */       if (index < 0)
/*     */         continue;
/* 212 */       Object d = list.remove(index);
/* 213 */       if (index >= list.size())
/*     */       {
/* 215 */         for (iter = macroChilds.iterator(); iter.hasNext(); )
/*     */         {
/* 217 */           list.add(((DefaultElement)iter.next()).clone());
/*     */         }
/*     */ 
/*     */       }
/*     */       else
/* 222 */         for (iter = macroChilds.iterator(); iter.hasNext(); )
/*     */         {
/* 224 */           list.add(index++, ((DefaultElement)iter.next()).clone());
/*     */         }
/*     */     }
/*     */   }
/*     */ }