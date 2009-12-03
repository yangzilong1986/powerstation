/*     */ package com.hisun.resource;
/*     */ 
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.message.HiContext;
/*     */ import com.hisun.util.HiICSProperty;
/*     */ import com.hisun.util.HiResource;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.dom4j.Attribute;
/*     */ import org.dom4j.Document;
/*     */ import org.dom4j.DocumentException;
/*     */ import org.dom4j.Element;
/*     */ import org.dom4j.io.SAXReader;
/*     */ 
/*     */ public class HiSystem
/*     */ {
/*  29 */   private static HashMap nameMap = new HashMap();
/*  30 */   private static boolean initialized = false;
/*     */ 
/*     */   public static synchronized void initialize()
/*     */     throws HiException
/*     */   {
/*  37 */     if (initialized) {
/*  38 */       return;
/*     */     }
/*     */ 
/*  42 */     String value = HiICSProperty.getProperty("config.file");
/*     */ 
/*  44 */     if (StringUtils.isEmpty(value)) {
/*  45 */       throw new HiException("212007", "config.file");
/*     */     }
/*     */ 
/*  48 */     String[] values = StringUtils.split(value, " ,");
/*  49 */     for (int i = 0; i < values.length; ++i) {
/*  50 */       initialize(values[i]);
/*  51 */       nameMap.clear();
/*     */     }
/*     */ 
/*  62 */     nameMap.clear();
/*  63 */     initialized = true;
/*     */   }
/*     */ 
/*     */   private static void initialize(String file)
/*     */     throws HiException
/*     */   {
/*  73 */     InputStream is = HiResource.getResourceAsStream(file);
/*     */ 
/*  75 */     if (is == null) {
/*  76 */       throw new HiException("212004", file);
/*     */     }
/*     */ 
/*  79 */     Document doc = null;
/*  80 */     SAXReader saxReader = new SAXReader();
/*     */     try {
/*  82 */       doc = saxReader.read(is);
/*     */     } catch (DocumentException e) {
/*     */     }
/*     */     finally {
/*     */       try {
/*  87 */         is.close();
/*     */       } catch (IOException e1) {
/*     */       }
/*     */     }
/*  91 */     Element root = doc.getRootElement();
/*  92 */     walkTree(root.getName(), root);
/*     */   }
/*     */ 
/*     */   private static void walkTree(String parentName, Element node)
/*     */   {
/* 104 */     handler(parentName, node);
/*     */ 
/* 106 */     Iterator iter = node.elementIterator();
/*     */ 
/* 108 */     while (iter.hasNext()) {
/* 109 */       Element child = (Element)iter.next();
/* 110 */       String childName = parentName + "." + child.getName();
/* 111 */       walkTree(childName, child);
/*     */     }
/*     */   }
/*     */ 
/*     */   private static void handler(String parentName, Element node)
/*     */   {
/* 122 */     String value = node.getTextTrim();
/* 123 */     if (!(StringUtils.isEmpty(value))) {
/* 124 */       put(parentName, value);
/*     */     }
/*     */ 
/* 127 */     Iterator iter = node.attributeIterator();
/* 128 */     while (iter.hasNext()) {
/* 129 */       Attribute attr = (Attribute)iter.next();
/* 130 */       String name = parentName + "@" + attr.getName();
/* 131 */       put(name, attr.getValue());
/*     */     }
/*     */   }
/*     */ 
/*     */   private static String getName(String name)
/*     */   {
/* 141 */     if (!(nameMap.containsKey(name))) {
/* 142 */       nameMap.put(name, new Integer(1));
/* 143 */       return name;
/*     */     }
/* 145 */     int value = ((Integer)nameMap.get(name)).intValue();
/* 146 */     ++value;
/* 147 */     nameMap.put(name, new Integer(value));
/* 148 */     return name + "_" + value;
/*     */   }
/*     */ 
/*     */   private static String getValue(String rootName, String name)
/*     */   {
/* 159 */     name = rootName + "." + name;
/* 160 */     return HiContext.getRootContext().getStrProp(name);
/*     */   }
/*     */ 
/*     */   private static void put(String name, String value)
/*     */   {
/* 169 */     if (value.startsWith("_")) {
/* 170 */       value = getValue("ParaList.Public", value);
/*     */     }
/* 172 */     HiContext.getRootContext().setProperty(name, value, true);
/*     */   }
/*     */ }