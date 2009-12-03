/*     */ package com.hisun.util;
/*     */ 
/*     */ import com.hisun.exception.HiException;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.URL;
/*     */ import java.util.Iterator;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.dom4j.DocumentException;
/*     */ import org.dom4j.DocumentHelper;
/*     */ import org.dom4j.Element;
/*     */ import org.dom4j.io.DOMReader;
/*     */ import org.dom4j.io.DOMWriter;
/*     */ import org.dom4j.io.OutputFormat;
/*     */ import org.dom4j.io.SAXReader;
/*     */ import org.dom4j.io.XMLWriter;
/*     */ 
/*     */ public class HiXmlHelper
/*     */ {
/*     */   public static Element parseText(String text)
/*     */     throws HiException
/*     */   {
/*     */     try
/*     */     {
/*  41 */       return DocumentHelper.parseText(text).getRootElement();
/*     */     }
/*     */     catch (DocumentException e)
/*     */     {
/*  45 */       throw new HiException("CO0010", "Build ETF Tree failure from text.", e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static Element getNodeByAttr(Element root, String nodeNam, String attrNam, String attrVal)
/*     */   {
/*  66 */     Element retNode = null;
/*  67 */     Iterator it = root.elementIterator(nodeNam);
/*  68 */     while (it.hasNext())
/*     */     {
/*  70 */       retNode = (Element)it.next();
/*  71 */       if (StringUtils.equals(retNode.attributeValue(attrNam), attrVal))
/*     */       {
/*  73 */         return retNode;
/*     */       }
/*     */     }
/*  76 */     return null;
/*     */   }
/*     */ 
/*     */   public static org.w3c.dom.Document toW3CDocument(org.dom4j.Document d4doc)
/*     */   {
/*  86 */     DOMWriter d4Writer = new DOMWriter();
/*     */     try
/*     */     {
/*  89 */       return d4Writer.write(d4doc);
/*     */     }
/*     */     catch (DocumentException e) {
/*     */     }
/*  93 */     return null;
/*     */   }
/*     */ 
/*     */   public static org.dom4j.Document buildDocment(org.w3c.dom.Document domDocument)
/*     */   {
/* 105 */     DOMReader xmlReader = new DOMReader();
/* 106 */     return xmlReader.read(domDocument);
/*     */   }
/*     */ 
/*     */   public static Element selectSingleNode(Element parent, String childNam, String attrNam, String attrVal)
/*     */   {
/* 119 */     return ((Element)parent.selectSingleNode(childNam + "[@" + attrNam + "='" + attrVal + "']"));
/*     */   }
/*     */ 
/*     */   public static Element selectSingleNode(Element parent, String childNam)
/*     */   {
/* 131 */     return ((Element)parent.selectSingleNode(childNam));
/*     */   }
/*     */ 
/*     */   public static void saveDoc(org.dom4j.Document doc, String fileName, String encoding)
/*     */     throws HiException
/*     */   {
/* 143 */     XMLWriter output = null;
/*     */ 
/* 145 */     OutputFormat format = OutputFormat.createPrettyPrint();
/* 146 */     format.setEncoding(encoding);
/*     */     try
/*     */     {
/* 150 */       output = new XMLWriter(new FileOutputStream(new File(fileName)), format);
/*     */ 
/* 152 */       output.write(doc);
/*     */ 
/* 154 */       output.close();
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 158 */       throw new HiException(e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void fileWriter(Element root, String fileName, String encoding)
/*     */     throws HiException, IOException
/*     */   {
/* 174 */     FileWriter fw = new FileWriter(fileName);
/* 175 */     if ((encoding != null) && (encoding != ""))
/*     */     {
/* 177 */       fw.write("<?xml version='1.0' encoding='" + encoding + "'?>\n");
/*     */     }
/* 179 */     fw.write(root.asXML());
/* 180 */     fw.flush();
/* 181 */     fw.close();
/*     */   }
/*     */ 
/*     */   public static Element updateChildNodeXPath(Element root, String updateNodeName, String value)
/*     */   {
/* 193 */     Element updateNode = selectSingleNode(root, updateNodeName);
/* 194 */     if (updateNode == null)
/*     */     {
/* 196 */       return null;
/*     */     }
/*     */ 
/* 199 */     updateNode.setText(value);
/*     */ 
/* 201 */     return updateNode;
/*     */   }
/*     */ 
/*     */   public static Element updateChildAttrXPath(Element root, String updateNodeName, String attrName, String value)
/*     */   {
/* 214 */     Element updateNode = selectSingleNode(root, updateNodeName);
/* 215 */     if (updateNode == null)
/*     */     {
/* 217 */       return null;
/*     */     }
/*     */ 
/* 220 */     updateNode.addAttribute(attrName, value);
/* 221 */     return updateNode;
/*     */   }
/*     */ 
/*     */   public static Element updateChildAttr(Element root, String updateNodeName, String attrName, String value)
/*     */   {
/* 234 */     Element updateNode = getChildNode(root, updateNodeName);
/* 235 */     if (updateNode == null)
/*     */     {
/* 237 */       return null;
/*     */     }
/*     */ 
/* 240 */     updateNode.addAttribute(attrName, value);
/* 241 */     return updateNode;
/*     */   }
/*     */ 
/*     */   public static Element updateChildNode(Element parent, String childName, String value)
/*     */   {
/* 253 */     if (parent == null)
/*     */     {
/* 255 */       return null;
/*     */     }
/*     */ 
/* 258 */     Element child = parent.element(childName);
/* 259 */     if (child != null)
/*     */     {
/* 261 */       child.setText(value);
/*     */     }
/* 263 */     return child;
/*     */   }
/*     */ 
/*     */   public static Element getChildNode(Element parent, String childName)
/*     */   {
/* 274 */     if (parent == null)
/*     */     {
/* 276 */       return null;
/*     */     }
/* 278 */     return parent.element(childName);
/*     */   }
/*     */ 
/*     */   public static org.dom4j.Document parser(String file) throws IOException, DocumentException
/*     */   {
/* 283 */     SAXReader saxReader = new SAXReader();
/* 284 */     InputStream is = HiResource.getResourceAsStream(file);
/* 285 */     if (is == null)
/*     */     {
/* 287 */       throw new IOException("文件:[" + file + "]不存在!");
/*     */     }
/* 289 */     return saxReader.read(is);
/*     */   }
/*     */ 
/*     */   public static Element updateOrInsertChildNode(Element parent, String childName, String value)
/*     */   {
/* 301 */     if (parent == null)
/*     */     {
/* 303 */       return null;
/*     */     }
/*     */ 
/* 306 */     Element child = parent.element(childName);
/* 307 */     if (child == null)
/*     */     {
/* 309 */       child = parent.addElement(childName);
/*     */     }
/* 311 */     child.setText(value);
/* 312 */     return child;
/*     */   }
/*     */ 
/*     */   public static Element getRootElement(String file) throws HiException {
/* 316 */     SAXReader reader = new SAXReader();
/* 317 */     org.dom4j.Document doc = null;
/*     */     try
/*     */     {
/* 322 */       URL url = HiResource.getResource(file);
/*     */ 
/* 325 */       doc = reader.read(url);
/*     */     } catch (DocumentException e) {
/* 327 */       throw HiException.makeException("210001", file, e);
/*     */     }
/* 329 */     if (doc == null)
/* 330 */       return null;
/* 331 */     return doc.getRootElement();
/*     */   }
/*     */ }